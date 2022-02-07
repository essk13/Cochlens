<template>
  <div>
    <div v-if="state.isMainScreen" class="screen-list-block">
      <q-carousel
        v-model="state.screenSlide"
        transition-prev="slide-right"
        transition-next="slide-left"
        swipeable
        control-color="primary"
        padding
        arrows
        height="100%"
        class="screen-list bg-grey-1 shadow-2"
      >
        <q-carousel-slide :name="1" class="column no-wrap">
          <div id="participants" class="row fit justify-center items-center q-gutter-xs q-col-gutter no-wrap">
            <!-- <q-img class="rounded-borders col-2 full-height" src="https://cdn.quasar.dev/img/mountains.jpg" />
            <q-img class="rounded-borders col-2 full-height" src="https://cdn.quasar.dev/img/parallax1.jpg" /> -->
            <q-img class="rounded-borders col-2 full-height" src="https://cdn.quasar.dev/img/mountains.jpg" />
            <q-img class="rounded-borders col-2 full-height" src="https://cdn.quasar.dev/img/parallax1.jpg" />
            <q-img class="rounded-borders col-2 full-height" src="https://cdn.quasar.dev/img/mountains.jpg" />
            <q-img class="rounded-borders col-2 full-height" src="https://cdn.quasar.dev/img/parallax1.jpg" />
          </div>
        </q-carousel-slide>
        <q-carousel-slide :name="2" class="column no-wrap">
          <div class="row fit justify-center items-center q-gutter-xs q-col-gutter no-wrap">
            <q-img class="rounded-borders col-2 full-height" src="https://cdn.quasar.dev/img/parallax2.jpg" />
            <q-img class="rounded-borders col-2 full-height" src="https://cdn.quasar.dev/img/quasar.jpg" />
            <q-img class="rounded-borders col-2 full-height" src="https://cdn.quasar.dev/img/parallax2.jpg" />
            <q-img class="rounded-borders col-2 full-height" src="https://cdn.quasar.dev/img/quasar.jpg" />
            <q-img class="rounded-borders col-2 full-height" src="https://cdn.quasar.dev/img/parallax2.jpg" />
            <q-img class="rounded-borders col-2 full-height" src="https://cdn.quasar.dev/img/quasar.jpg" />
          </div>
        </q-carousel-slide>
        <q-carousel-slide :name="3" class="column no-wrap">
          <div class="row fit justify-center items-center q-gutter-xs q-col-gutter no-wrap">
            <q-img class="rounded-borders col-2 full-height" src="https://cdn.quasar.dev/img/cat.jpg" />
            <q-img class="rounded-borders col-2 full-height" src="https://cdn.quasar.dev/img/linux-avatar.png" />
            <q-img class="rounded-borders col-2 full-height" src="https://cdn.quasar.dev/img/cat.jpg" />
            <q-img class="rounded-borders col-2 full-height" src="https://cdn.quasar.dev/img/linux-avatar.png" />
            <q-img class="rounded-borders col-2 full-height" src="https://cdn.quasar.dev/img/cat.jpg" />
            <q-img class="rounded-borders col-2 full-height" src="https://cdn.quasar.dev/img/linux-avatar.png" />
          </div>
        </q-carousel-slide>
      </q-carousel>
    </div>
    <div v-if="state.isMainScreen" class="main-screen-block">
      <img src="https://cdn.quasar.dev/img/mountains.jpg">
      <span class="main-screen-subtitles">이것은 자막입니다. 이것은 자막입니다.</span>
    </div>
    <div v-else class="all-screen-list-block">

    </div>
    <div class="menu-block row justify-between">
      <div class="col-1 row justify-center items-center">
        <img style="width: 4vh; height: 4vh; border-radius: 4vh;" src="https://cdn.quasar.dev/img/cat.jpg" />
      </div>
      <div class="col-2 row justify-between items-center">
        <!-- <img style="width: 4vh; height: 4vh; border-radius: 4vh;" src="https://cdn.quasar.dev/img/cat.jpg" /> -->
        <input v-model="state.name">
        <img @click="register" style="width: 4vh; height: 4vh; border-radius: 4vh;" src="https://cdn.quasar.dev/img/cat.jpg" />
        <img @click="leaveRoom" style="width: 4vh; height: 4vh; border-radius: 4vh;" src="https://cdn.quasar.dev/img/cat.jpg" />
      </div>
      <div class="col-1 row justify-center items-center">
        <img style="width: 4vh; height: 4vh; border-radius: 4vh;" src="https://cdn.quasar.dev/img/cat.jpg" />
      </div>
    </div>
  </div>
</template>

<script>
import { reactive } from '@vue/reactivity'
import kurentoUtils from 'kurento-utils'

export default {
  name: 'LiveLectureView',
  components: {
  },

  setup() {
    const ws = new WebSocket('wss://' + location.host + '/groupcall');
    let participants = {};

    const state = reactive({
      screenSlide: 1,
      isMainScreen: true,
      name: '',
      room: '',
    })

    ws.onmessage = function(message) {
      let parsedMessage = JSON.parse(message.data);
      console.info('Received message: ' + message.data);

      switch (parsedMessage.id) {
      case 'existingParticipants':
        onExistingParticipants(parsedMessage);
        break;
      case 'newParticipantArrived':
        onNewParticipant(parsedMessage);
        break;
      case 'participantLeft':
        onParticipantLeft(parsedMessage);
        break;
      case 'receiveVideoAnswer':
        receiveVideoResponse(parsedMessage);
        break;
      case 'iceCandidate':
        participants[parsedMessage.name].rtcPeer.addIceCandidate(parsedMessage.candidate, function (error) {
              if (error) {
              console.error("Error adding candidate: " + error);
              return;
              }
          });
          break;
      default:
        console.error('Unrecognized message', parsedMessage);
      }
    }

    function register() {
      // name = document.getElementById('name').value;
      // let room = document.getElementById('roomName').value;

      let message = {
        id : 'joinRoom',
        name : state.name,
        room : 'testroom',
      }
      sendMessage(message);
    }

    function onNewParticipant(request) {
      receiveVideo(request.name);
    }

    function receiveVideoResponse(result) {
      participants[result.name].rtcPeer.processAnswer (result.sdpAnswer, function (error) {
        if (error) return console.error (error);
      });
    }

    // function callResponse(message) {
    //   if (message.response != 'accepted') {
    //     console.info('Call not accepted by peer. Closing call');
    //     stop();
    //   } else {
    //     webRtcPeer.processAnswer(message.sdpAnswer, function (error) {
    //       if (error) return console.error (error);
    //     });
    //   }
    // }

    function onExistingParticipants(msg) {
      let constraints = {
        audio : true,
        video : {
          mandatory : {
            maxWidth : 320,
            maxFrameRate : 15,
            minFrameRate : 15
          }
        }
      };
      console.log(state.name + " registered in room " + state.room);
      var participant = new Participant(state.name);
      participants[state.name] = participant;
      var video = participant.getVideoElement();

      var options = {
            localVideo: video,
            mediaConstraints: constraints,
            onicecandidate: participant.onIceCandidate.bind(participant)
          }
      participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerSendonly(options,
        function (error) {
          if(error) {
            return console.error(error);
          }
          this.generateOffer (participant.offerToReceiveVideo.bind(participant));
      });

      msg.data.forEach(receiveVideo);
    }

    function leaveRoom() {
      sendMessage({
        id : 'leaveRoom'
      });

      for ( var key in participants) {
        participants[key].dispose();
      }

      document.getElementById('join').style.display = 'block';
      document.getElementById('room').style.display = 'none';

      ws.close();
    }

    function receiveVideo(sender) {
      var participant = new Participant(sender);
      participants[sender] = participant;
      var video = participant.getVideoElement();

      var options = {
          remoteVideo: video,
          onicecandidate: participant.onIceCandidate.bind(participant)
        }

      participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly(options,
          function (error) {
            if(error) {
              return console.error(error);
            }
            this.generateOffer (participant.offerToReceiveVideo.bind(participant));
      });
    }

    function onParticipantLeft(request) {
      console.log('Participant ' + request.name + ' left');
      var participant = participants[request.name];
      participant.dispose();
      delete participants[request.name];
    }

    function sendMessage(message) {
      var jsonMessage = JSON.stringify(message);
      console.log('Sending message: ' + jsonMessage);
      ws.send(jsonMessage);
    }

    /* participant.js */

    const PARTICIPANT_MAIN_CLASS = 'participant main';
    const PARTICIPANT_CLASS = 'participant';

    /**
     * Creates a video element for a new participant
     *
     * @param {String} name - the name of the new participant, to be used as tag
     *                        name of the video element.
     *                        The tag of the new element will be 'video<name>'
     * @return
     */
    function Participant(name) {
      this.name = name;
      let container = document.createElement('div');
      container.className = isPresentMainParticipant() ? PARTICIPANT_CLASS : PARTICIPANT_MAIN_CLASS;

      // quasar 클래스 추가
      container.classList.add('rounded-borders', 'col-2', 'full-height');

      container.id = name;
      let span = document.createElement('span');
      let video = document.createElement('video');
      let rtcPeer;
      console.log(rtcPeer)

      container.appendChild(video);
      container.appendChild(span);
      container.onclick = switchContainerClass;
      document.getElementById('participants').appendChild(container);

      span.appendChild(document.createTextNode(name));

      video.id = 'video-' + name;
      video.autoplay = true;
      video.controls = false;


      this.getElement = function() {
        return container;
      }

      this.getVideoElement = function() {
        return video;
      }

      function switchContainerClass() {
        if (container.className === PARTICIPANT_CLASS) {
          var elements = Array.prototype.slice.call(document.getElementsByClassName(PARTICIPANT_MAIN_CLASS));
          elements.forEach(function(item) {
              item.className = PARTICIPANT_CLASS;
            });

            container.className = PARTICIPANT_MAIN_CLASS;
          } else {
          container.className = PARTICIPANT_CLASS;
        }
      }

      function isPresentMainParticipant() {
        return ((document.getElementsByClassName(PARTICIPANT_MAIN_CLASS)).length != 0);
      }

      // error, offerSdp, wp
      this.offerToReceiveVideo = function(error, offerSdp){
        if (error) return console.error ("sdp offer error")
        console.log('Invoking SDP offer callback function');
        var msg =  { id : "receiveVideoFrom",
            sender : name,
            sdpOffer : offerSdp
          };
        sendMessage(msg);
      }

      // candidate, wp
      this.onIceCandidate = function (candidate) {
          console.log("Local candidate" + JSON.stringify(candidate));

          var message = {
            id: 'onIceCandidate',
            candidate: candidate,
            name: name
          };
          sendMessage(message);
      }

      Object.defineProperty(this, 'rtcPeer', { writable: true});

      this.dispose = function() {
        console.log('Disposing participant ' + this.name);
        this.rtcPeer.dispose();
        container.parentNode.removeChild(container);
      };
    }

    return {
      state,
      register,
      leaveRoom,
    }
  }
}
</script>

<style scoped>
.screen-list-block {
  width: 100%;
  height: 15vh;
}

.screen-list {
  background-color: lightgray;
  width: 100%;
  height: 100%;
}

.participant > video {
  width: 100%;
  height: 100%;
}

.main-screen-block {
  background-color: black;
  width: 100%;
  height: 77vh;
  overflow: hidden;
  text-align: center;
}

.main-screen-block > img {
  position: relative;
  height: 100%;
}

.main-screen-subtitles {
  position: absolute;
  left: 50%;
  bottom: 9vh;
  transform: translate(-50%, -50%);
  font-size: 2vh;
  color: white;
  background-color: rgba(0, 0, 0, 0.5);
  padding: 0.5vh 2vh;
}

.all-screen-list-block {
  background-color: beige;
  width: 100%;
  height: 92vh;
}

.menu-block {
  width: 100%;
  height: 8vh;
}
</style>