import Participant from './js/participant.js'
import kurentoUtils from 'kurento-utils'
import axios from 'axios'

const BASE_URL = 'https://i6d102.p.ssafy.io:8443/api/v1/'

const courseStore = {
  namespaced: true,
  state: {
    ws: '',
    participants: {},
    name: '',
    room: '',
    courseList: [],
    bestCourseList: [],
    courseData: null,
  },

  getters: {
  },

  mutations: {
    SET_WS(state, url) {
      state.ws = new WebSocket(url)
    },
    // SET_SOCKET(state, url) {
    //   state.sockJs = new SockJS("/stomp/chat")
    // },
    SET_NAME(state, name) {
      state.name = name
      state.room = 'testroom'
      console.log('now_state' + state.name + state.room)
    },

    ADD_PARTICIPANT(state, { name, participant }) {
      console.log('ADD_PARTICIPANT: ' + name)
      state.participants[name] = participant
      console.log('now participants' + state.participants)
    },

    DISPOSE_PARTICIPANT(state, name) {
      delete state.participants[name]
    },
    
    SET_COURSE_LIST(state, list) {
      console.log('SET_COURSE_LIST :', list)
      if (list) {
        state.courseList = list
      }
    },

    SET_BEST_COURSE_LIST(state, list) {
      if (list) {
        state.bestCourseList = list
      }
    },

    SET_COURSE_DATA(state, data) {
      state.courseData = data
    }
  },

  actions: {
    // ws 설정
    setWs({ commit, dispatch, state }, url) {
      console.log('set_ws: ' + url)
      commit('SET_WS', url)
      state.ws.onmessage = function (msg) {
        // console.log('set_ws: ' + msg.data)
        let parsedMessage = JSON.parse(msg.data)
        dispatch('onMessage', parsedMessage)
      }
    },

    // msg에 따른 동작
    onMessage({ dispatch, state }, msg) {
      console.log('on_message: ' + msg)
      switch (msg.id) {
        case 'existingParticipants':
          dispatch('onExistingParticipants', msg)
          break
        case 'newParticipantArrived':
          dispatch('onNewParticipant', msg)
          break
        case 'participantLeft':
          dispatch('onParticipantLeft', msg)
          break
        case 'receiveVideoAnswer':
          dispatch('receiveVideoResponse', msg)
          break
        /**
         * recoding
         */
        case 'startResponse':
          dispatch('startResponse', msg)
          break
        case 'playResponse':
          dispatch('playResponse', msg)
          break
        case 'playEnd':
          dispatch('playEnd')
          break
        case 'error':
          // setState(NO_CALL);
          dispatch('onError','Error message from server: ' + msg.message)
          break
        case 'stopped':
          break
        case 'paused':
          break
        case 'recording':
          break
        /**
         * recoding end
         */
        case 'iceCandidate':
          state.participants[msg.name].rtcPeer.addIceCandidate(
            msg.candidate,
            function (error) {
              if (error) {
                console.error("Error adding candidate: " + error)
                return
              }
            });
            break
        default:
          console.error('Unrecognized message', msg)
      }
    },

    // 새 참가자 식별
    onNewParticipant({ dispatch }, request) {
      console.log('on_new_participant: ' + request)
      dispatch('receiveVideo', request.name)
    },

    // 다른 유저 비디오 수신
    receiveVideo({ commit }, sender) {
      console.log('recive_video: ' + sender)
      let participant = new Participant(sender)
      var video = participant.getVideoElement()

      var options = {
          remoteVideo: video,
          onicecandidate: participant.onIceCandidate.bind(participant),
          configuration: {
            iceServers: [{
              'urls': 'turn:3.34.253.8:3478?transport=udp',
              'username': 'myuser',
              'credential': 'mypassword'
            }]
          }
        }

      participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly(
        options,
        function (error) {
          if(error) {
            return console.error(error);
          }
          this.generateOffer (participant.offerToReceiveVideo.bind(participant));
      })

      commit('ADD_PARTICIPANT', { name: sender, participant })
    },

    receiveVideoResponse({ state }, res) {
      state.participants[res.name].rtcPeer.processAnswer (
        res.sdpAnswer,
        function (error) {
        if (error) return console.error (error);
      });
    },

    // 유저 기본 설정
    onExistingParticipants({ commit, dispatch, state }, msg) {
      let constraints = {
        audio : true,
        video : {
          mandatory : {
            maxWidth : 900,
            maxHeight : 600,
            maxFrameRate : 30,
            minFrameRate : 15
          }
        }
      }
      console.log(state.name + " registered in room " + state.room)
      let participant = new Participant(state.name)
      commit('ADD_PARTICIPANT', { name: state.name, participant })
      let video = participant.getVideoElement()

      let options = {
            localVideo: video,
            mediaConstraints: constraints,
            onicecandidate: participant.onIceCandidate.bind(participant),
            configuration: {
              iceServers: [{
                'urls': 'turn:3.34.253.8:3478?transport=udp',
                'username': 'myuser',
                'credential': 'mypassword'
              }]
            }
          }
      participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerSendonly(
        options,
        function (error) {
          if(error) {
            return console.error(error)
          }
          this.generateOffer (participant.offerToReceiveVideo.bind(participant))
          this.audioEnabled = false
      })

      msg.data.forEach(function (sender) {
        dispatch('receiveVideo', sender)
      })
    },

    // 다른 유저 퇴실
    onParticipantLeft({ state }, request) {
      let participant = state.participants[request.name]
      participant.dispose()
    },

    // 입실
    register({ commit, dispatch }, msg) {
      console.log('registerregisterregisterregister!!!!!!!!')
      commit('SET_NAME', msg.name)
      dispatch('sendMessage', msg)
    },

    // 퇴실
    leaveLecture({ dispatch, state }) {
      dispatch('sendMessage', {id: 'leaveRoom'})

      for ( var key in state.participants) {
        state.participants[key].dispose()
      }
      
      state.ws.close();
    },

    /**
     * recoding start
     */

    startResponse({ state }, msg) {
      // setState(IN_CALL);
      state.participants[msg.name].rtcPeer.processAnswer(msg.sdpAnswer, function(error) {
        if (error)
          return console.error(error);
      });
    },

    playResponse({ state }, msg) {
      // setState(IN_PLAY);
      state.participants[msg.name].rtcPeer.processAnswer(msg.sdpAnswer, function(error) {
        if (error)
          return console.error(error);
      });
    },

    playEnd() {
      // setState(POST_CALL);
      // hideSpinner(videoInput, videoOutput);
    },

    onError(error) {
      console.error(error);
    },

    /**
     * recording end
     */
  
    /**
     * recording view function
     */

    startRecording({ state }, videoOutput) {
      let participant = state.participants[state.name]
      let videoInput = document.getElementById("video-" + state.name)
      let constraints = {
        audio : true,
        video : {
          mandatory : {
            minWidth : 320,
            minHeight : 120,
            maxWidth : 320,
            maxHeight : 120,
            maxFrameRate : 15,
            minFrameRate : 15
          }
        }
      }
      let options = {
          localVideo : videoInput,
          remoteVideo : videoOutput,
          mediaConstraints : constraints,
          onicecandidate : participant.onIceCandidate.bind(participant),
          configuration: {
            iceServers: [{
              'urls': 'turn:3.34.253.8:3478?transport=udp',
              'username': 'myuser',
              'credential': 'mypassword'
            }]
          }
      }

      participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerSendrecv(
        options,
        function(error) {
          if (error)
            return console.error(error);

          this.generateOffer (participant.offerToRecordingVideo.bind(participant))
          this.audioEnabled = false
        });
    }, 

    getConstraints() {
      console.log('getConstraints................... ...');
      // let mode = 'video-and-audio';
      let constraints = {
        audio : true,
        video :{
          width: 640,
          framerate: 15
        }
      }
      // if (mode == 'video-only') {
      //   constraints.audio = false;
      // } else if (mode == 'audio-only') {
      //   constraints.video = false;
      // }
      
      return constraints;
    },
    onIceCandidate({ dispatch }, candidate) {
      let message = {
          id : 'onIceCandidate',
          candidate : candidate
      };
      dispatch('sendMessage', message);
    },
    
    onOffer({ dispatch }, error, offerSdp) {
      if (error)
        return console.error('Error generating the offer');
      let message = {
          id : 'start',
          sdpOffer : offerSdp,
          mode :  'video-and-audio'
      }
      dispatch('sendMessage', message)
    },
    stopRecording({ dispatch, state }) {
      // var stopMessageId = (state == IN_CALL) ? 'stop' : 'stopPlay';
      // console.log('Stopping video while in ' + state + '...');
      // setState(POST_CALL);
      let participant = state.participants[state.name]
      if (participant.rtcPeer) {
        participant.rtcPeer.dispose();
        participant.rtcPeer = null;

        let message = {
            // id : stopMessageId
            id : 'stopRecording'
        }
        dispatch('sendMessage', message)
      }
      // hideSpinner(videoInput, videoOutput);
    },
    playRecording({ state }, videoOutput) {
      // Disable start button
      // setState(DISABLED);
      // showSpinner(videoOutput);
      let constraints = {
        audio : true,
        video : {
          mandatory : {
            minWidth : 320,
            minHeight : 120,
            maxWidth : 320,
            maxHeight : 120,
            maxFrameRate : 15,
            minFrameRate : 15
          }
        }
      }
      let participant = state.participants[state.name]

      var options = {
          remoteVideo : videoOutput,
          mediaConstraints :constraints,
          onicecandidate : participant.onIceCandidate.bind(participant),
          configuration: {
            iceServers: [{
              'urls': 'turn:3.34.253.8:3478?transport=udp',
              'username': 'myuser',
              'credential': 'mypassword'
            }]
          }
      }

      participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly(
        options,
        function(error) {
          if (error)
            return console.error(error);
          this.generateOffer (participant.offerToPlayVideo.bind(participant))
        });
    },

    onPlayOffer({ dispatch }, error, offerSdp) {
      if (error)
        return console.error('Error generating the offer');
      let message = {
          id : 'play',
          sdpOffer : offerSdp
      }
      dispatch('sendMessage', message)
    },

    // 데이터 전송
    sendMessage({ state }, msg) {
      var jsonMessage = JSON.stringify(msg);
      // console.log('send_message: ' + jsonMessage);
      state.ws.send(jsonMessage);
    },

    // 유저 제거
    disposeParticipant({ commit }, participantName) {
      commit('DISPOSE_PARTICIPANT', participantName);
    },

    // 강의 종료 후 자막 저장
    saveSubtitles({ commit }, data) {
      return new Promise((resolve, reject) => {
        axios({
          method: 'post',
          url: `${BASE_URL}STT/${data.id}`,
          headers: {
            Authorization: `Bearer ${localStorage.getItem('JWT')}`
          },
          data: data.subtitles
        })
        .then(res => {
          commit('')
          resolve(res)
        })
        .catch(err => {
          console.log(err)
          reject(err)
        })
      })
    },

    // 강의 자막 조회
    getSubtitles({ commit }, lectureId) {
      return new Promise((resolve, reject) => {
        axios({
          method: 'get',
          url: `${BASE_URL}STT/${lectureId}`,
          headers: {
            Authorization: `Bearer ${localStorage.getItem('JWT')}`
          },
        })
        .then(res => {
          console.log('getSubtitles!! :', res.data)
          console.log('subtitlesLectureId :', lectureId)
          commit('')
          resolve(res)
        })
        .catch(err => {
          console.log(err)
          reject(err)
        })
      })
    },

    // 강좌 생성
    createCourse({ commit }, data) {
      axios({
        method: 'post',
        url: `${BASE_URL}course`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('JWT')}`
        },
        data: data,
      })
        .then((res) => {
          console.log(res)
          commit('')
        })
        .catch((err) => {
          console.log(err)
        })
    },

    // 강좌 목록 조회
    getCourseList({ commit }, page) {
      axios({
        method: 'get',
        url: `${BASE_URL}course?page=${page}&size=15`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('JWT')}`
        },
      })
        .then((res) => {
          console.log(res.data)
          commit('SET_COURSE_LIST', res.data)
        })
        .catch((err) => {
          console.log(err)
        })
    },

    // 베스트 강좌 조회
    getBestCourseList({ commit }) {
      axios({
        method: 'get',
        url: `${BASE_URL}course/best`,
        headers: {
        },
      })
        .then((res) => {
          console.log(res.data)
          commit('SET_BEST_COURSE_LIST', res.data)
        })
        .catch((err) => {
          console.log(err)
        })
    },

    // 강좌 검색
    searchCourse({ commit }, data) {
      axios({
        method: 'get',
        url: `${BASE_URL}course/search?courseName=${data.text}&page=${data.page}&size=15`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('JWT')}`
        },
      })
        .then((res) => {
          console.log(res.data)
          commit('SET_COURSE_LIST', res.data)
        })
        .catch((err) => {
          console.log(err)
        })
    },

    // 강좌 상세 정보
    getCourseDetail({ commit }, id) {
      return new Promise((resolve, reject) => {
        axios({
          method: 'get',
          url: `${BASE_URL}course/${id}`,
          headers: {
            Authorization: `Bearer ${localStorage.getItem('JWT')}`
          },
        })
          .then((res) => {
            console.log(res)
            commit('SET_COURSE_DATA', res.data)
            resolve(res)
          })
          .catch((err) => {
            console.log(err)
            reject(err)
          })
      })
    },

    // 강좌 수강 신청
    registerCourse({ commit }, id) { 
      axios({
        method: 'post',
        url: `${BASE_URL}course/${id}/register`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('JWT')}`
        },
      })
        .then((res) => {
          console.log(res)
          commit('')
        })
        .catch((err) => {
          console.log(err)
        })
    },

    // 강좌 수강 신청 취소
    deregisterCourse({ commit }, id) {
      axios({
        method: 'delete',
        url: `${BASE_URL}course/${id}/deregister`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('JWT')}`
        },
      })
        .then((res) => {
          console.log(res)
          commit('')
        })
        .catch((err) => {
          console.log(err)
        })
    },

    // 강좌 찜
    wishCourse({ commit }, id) {
      axios({
        method: 'post',
        url: `${BASE_URL}course/${id}/wish`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('JWT')}`
        },
      })
        .then((res) => {
          console.log(res)
          commit('')
        })
        .catch((err) => {
          console.log(err)
        })
    },

    // 강좌 찜 취소
    unwishCourse({ commit }, id) {
      axios({
        method: 'delete',
        url: `${BASE_URL}course/${id}/wish`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('JWT')}`
        },
      })
        .then((res) => {
          console.log(res)
          commit('')
        })
        .catch((err) => {
          console.log(err)
        })
    },

    // 강의 생성
    createLecture({ dispatch }, data) {
      axios({
        method: 'post',
        // 임시(추 후 Parameter 수정)
        url: `${BASE_URL}lecture/${data.id}`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('JWT')}`
        },
        data: data.data
      })
        .then((res) => {
          console.log(res)
          dispatch('getCourseDetail', data.id)
        })
        .catch((err) => {
          console.log(err)
        })
    },

    // 강좌 수정
    updateCourse({ commit }, data) {
      axios({
        method: 'put',
        url: `${BASE_URL}course/${data.id}`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('JWT')}`
        },
        data: data.data,
      })
        .then((res) => {
          console.log(res)
          commit('SET_COURSE_DATA', res.data)
        })
        .catch((err) => {
          console.log(err)
        })
    },

    // 리뷰 작성
    createReview({ dispatch }, data) {
      console.log('creatingReview!!!!!!')
      axios({
        method: 'post',
        url: `${BASE_URL}course/${data.id}/review`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('JWT')}`
        },
        data: data.data,
      })
        .then((res) => {
          console.log(res)
          dispatch('getCourseDetail', data.id)
        })
        .catch((err) => {
          console.log(err)
        })
    },

    // 강의 시작
    startLecture({ commit }, id) {
      return new Promise((resolve, reject) => {
        axios({
          method: 'post',
          url: `${BASE_URL}lecture/${id}/open`,
          headers: {
            Authorization: `Bearer ${localStorage.getItem('JWT')}`
          },
        })
          .then((res) => {
            console.log(res)
            commit('')
            resolve(res)
          })
          .catch((err) => {
            console.log(err)
            reject(err)
          })
      })
    }
  }
}

export default courseStore