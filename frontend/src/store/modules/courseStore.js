import Participant from './js/participant.js'
import kurentoUtils from 'kurento-utils'
import axios from 'axios'

const BASE_URL = 'https://localhost:8443/api/v1/'

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
      state.courseList = list
    },

    SET_BEST_COURSE_LIST(state, list) {
      state.bestCourseList = list
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
        console.log('set_ws: ' + msg.data)
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
      console.log('receive_video_response: ' + res)
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
            maxWidth : 320,
            maxHeight : 120,
            maxFrameRate : 15,
            minFrameRate : 15
          }
        }
      }
      console.log(state.name + " registered in room " + state.room)
      var participant = new Participant(state.name)
      commit('ADD_PARTICIPANT', { name: state.name, participant })
      var video = participant.getVideoElement()

      var options = {
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
      console.log('Participant ' + request.name + ' left')
      var participant = state.participants[request.name]
      participant.dispose()
    },

    // 입실
    register({ commit, dispatch }, msg) {
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

    // 데이터 전송
    sendMessage({ state }, msg) {
      var jsonMessage = JSON.stringify(msg);
      console.log('send_message: ' + jsonMessage);
      state.ws.send(jsonMessage);
    },

    // 유저 제거
    disposeParticipant({ commit }, participantName) {
      commit('DISPOSE_PARTICIPANT', participantName);
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
        })
        .catch((err) => {
          console.log(err)
        })
    },

    // 강좌 수강 신청
    registerCourse() { 
      axios({
        method: 'post',
        // 임시(추 후 Parameter 수정)
        url: `${BASE_URL}course/2/register`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('JWT')}`
        },
      })
        .then((res) => {
          console.log(res)
        })
        .catch((err) => {
          console.log(err)
        })
    },

    // 강좌 수강 신청 취소
    deregisterCourse() {
      axios({
        method: 'delete',
        // 임시(추 후 Parameter 수정)
        url: `${BASE_URL}course/2/deregister`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('JWT')}`
        },
      })
        .then((res) => {
          console.log(res)
        })
        .catch((err) => {
          console.log(err)
        })
    },

    // 강좌 찜
    wishCourse() {
      axios({
        method: 'post',
        // 임시(추 후 Parameter 수정)
        url: `${BASE_URL}course/2/wish`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('JWT')}`
        },
      })
        .then((res) => {
          console.log(res)
        })
        .catch((err) => {
          console.log(err)
        })
    },

    // 강좌 찜 취소
    unwishCourse() {
      axios({
        method: 'delete',
        // 임시(추 후 Parameter 수정)
        url: `${BASE_URL}course/2/wish`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('JWT')}`
        },
      })
        .then((res) => {
          console.log(res)
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
    }
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
    console.log('axios')
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
  }
}

export default courseStore