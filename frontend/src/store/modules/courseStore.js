import Participant from './js/participant.js'
import kurentoUtils from 'kurento-utils'
// import { part } from 'core-js/core/function'

const courseStore = {
  namespaced: true,
  state: {
    ws: '',
    participants: {},
    name: '',
    room: '',
    webRtcPeer: Object,
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
      console.log('Participant ' + request.name + ' left')
      let participant = state.participants[request.name]
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

    /**
     * recoding start
     */

    startResponse({ state }, msg) {
      // setState(IN_CALL);
      console.log('SDP answer received from server. Processing ...');
      console.log(msg)
      state.participants[msg.name].rtcPeer.processAnswer(msg.sdpAnswer, function(error) {
        if (error)
          return console.error(error);
      });
    },

    playResponse({ state }, msg) {
      console.log('SDP answer received from server. Processing ...');
      console.log(msg)
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
      console.log('Creating WebRtcPeer and generating local sdp offer ...');
      console.log(state.participants)

      let participant = state.participants[state.name]
      console.log(state.name)
      console.log(participant)
      let videoInput = document.getElementById("video-" + state.name)
      console.log(videoInput)
      console.log(videoOutput)
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
      console.log('onIceCandidate................... ...');
      console.log('Local candidate' + JSON.stringify(candidate));
    
      let message = {
          id : 'onIceCandidate',
          candidate : candidate
      };
      dispatch('sendMessage', message);
    },
    
    onOffer({ dispatch }, error, offerSdp) {
      if (error)
        return console.error('Error generating the offer');
      console.info('Invoking SDP offer callback function ' + location.host);
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
      console.log("Starting to play recorded video...");

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
      console.log('Creating WebRtcPeer and generating local sdp offer ...');
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
      console.info('Invoking SDP offer callback function ' + location.host);
      let message = {
          id : 'play',
          sdpOffer : offerSdp
      }
      dispatch('sendMessage', message)
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
  },
}

export default courseStore