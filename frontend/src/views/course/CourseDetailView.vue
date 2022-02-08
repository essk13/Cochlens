<template lang="">
  <div>
    <input v-model="state.name">
    <input @click="joinLeacture" type="button" value="Join!">
    <div id="room" style="display: block;">
      <h2 id="room-header"></h2>
      <div id="participants"></div>
      <input type="button" id="button-leave" onmouseup="leaveRoom"
        value="Leave room">
    </div>
  </div>
</template>

<script>
import { reactive } from '@vue/reactivity'
import { useStore } from 'vuex'
import router from '@/router'

export default {
  name: 'CourseDetailView',
  components: {},
  setup() {
    const store = useStore()
    const url = 'wss://' + location.host + '/groupcall'
    store.dispatch('courseStore/setWs', url)

    const state = reactive({
      name: '',
      room: '',
    })

    function joinLeacture() {
      let message = {
        id : 'joinRoom',
        name : state.name,
        room : 'testroom',
      }
      router.push({ name: 'live' })
      store.dispatch('courseStore/register', message)
    }


    // -----------------------------------------------------
    // const ws = new WebSocket('wss://' + location.host + '/groupcall');
    // let participants = {};

    // ws.onmessage = function(message) {
    //   let parsedMessage = JSON.parse(message.data);
    //   console.info('Received message: ' + message.data);

    //   switch (parsedMessage.id) {
    //   case 'existingParticipants':
    //     onExistingParticipants(parsedMessage);
    //     break;
    //   case 'newParticipantArrived':
    //     onNewParticipant(parsedMessage);
    //     break;
    //   case 'participantLeft':
    //     onParticipantLeft(parsedMessage);
    //     break;
    //   case 'receiveVideoAnswer':
    //     receiveVideoResponse(parsedMessage);
    //     break;
    //   case 'iceCandidate':
    //     participants[parsedMessage.name].rtcPeer.addIceCandidate(parsedMessage.candidate, function (error) {
    //           if (error) {
    //           console.error("Error adding candidate: " + error);
    //           return;
    //           }
    //       });
    //       break;
    //   default:
    //     console.error('Unrecognized message', parsedMessage);
    //   }
    // }

    // function register() {
    //   // name = document.getElementById('name').value;
    //   // let room = document.getElementById('roomName').value;

    //   let message = {
    //     id : 'joinRoom',
    //     name : state.name,
    //     room : 'testroom',
    //   }
    //   sendMessage(message);
    // }

    // function onNewParticipant(request) {
    //   receiveVideo(request.name);
    // }

    // function receiveVideoResponse(result) {
    //   participants[result.name].rtcPeer.processAnswer (result.sdpAnswer, function (error) {
    //     if (error) return console.error (error);
    //   });
    // }

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

    // function onExistingParticipants(msg) {
    //   let constraints = {
    //     audio : true,
    //     video : {
    //       mandatory : {
    //         maxWidth : 320,
    //         maxFrameRate : 15,
    //         minFrameRate : 15
    //       }
    //     }
    //   };
    //   console.log(state.name + " registered in room " + state.room);
    //   var participant = new Participant(state.name);
    //   participants[state.name] = participant;
    //   var video = participant.getVideoElement();

    //   var options = {
    //         localVideo: video,
    //         mediaConstraints: constraints,
    //         onicecandidate: participant.onIceCandidate.bind(participant)
    //       }
    //   participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerSendonly(options,
    //     function (error) {
    //       if(error) {
    //         return console.error(error);
    //       }
    //       this.generateOffer (participant.offerToReceiveVideo.bind(participant));
    //   });

    //   msg.data.forEach(receiveVideo);
    // }

    // function leaveRoom() {
    //   sendMessage({
    //     id : 'leaveRoom'
    //   });

    //   for ( var key in participants) {
    //     participants[key].dispose();
    //   }

    //   document.getElementById('join').style.display = 'block';
    //   document.getElementById('room').style.display = 'none';

    //   ws.close();
    // }

    // function receiveVideo(sender) {
    //   var participant = new Participant(sender);
    //   participants[sender] = participant;
    //   var video = participant.getVideoElement();

    //   var options = {
    //       remoteVideo: video,
    //       onicecandidate: participant.onIceCandidate.bind(participant)
    //     }

    //   participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly(options,
    //       function (error) {
    //         if(error) {
    //           return console.error(error);
    //         }
    //         this.generateOffer (participant.offerToReceiveVideo.bind(participant));
    //   });
    // }

    // function onParticipantLeft(request) {
    //   console.log('Participant ' + request.name + ' left');
    //   var participant = participants[request.name];
    //   participant.dispose();
    //   delete participants[request.name];
    // }

    // function sendMessage(message) {
    //   var jsonMessage = JSON.stringify(message);
    //   console.log('Sending message: ' + jsonMessage);
    //   ws.send(jsonMessage);
    // }

    /* participant.js */

    // const PARTICIPANT_MAIN_CLASS = 'participant main';
    // const PARTICIPANT_CLASS = 'participant';

    /**
     * Creates a video element for a new participant
     *
     * @param {String} name - the name of the new participant, to be used as tag
     *                        name of the video element.
     *                        The tag of the new element will be 'video<name>'
     * @return
     */
    // function Participant(name) {
    //   this.name = name;
    //   let container = document.createElement('div');
    //   container.className = isPresentMainParticipant() ? PARTICIPANT_CLASS : PARTICIPANT_MAIN_CLASS;
    //   container.id = name;
    //   let span = document.createElement('span');
    //   let video = document.createElement('video');
    //   let rtcPeer;

    //   container.appendChild(video);
    //   container.appendChild(span);
    //   container.onclick = switchContainerClass;
    //   document.getElementById('participants').appendChild(container);

    //   span.appendChild(document.createTextNode(name));

    //   video.id = 'video-' + name;
    //   video.autoplay = true;
    //   video.controls = false;


    //   this.getElement = function() {
    //     return container;
    //   }

    //   this.getVideoElement = function() {
    //     return video;
    //   }

    //   function switchContainerClass() {
    //     if (container.className === PARTICIPANT_CLASS) {
    //       var elements = Array.prototype.slice.call(document.getElementsByClassName(PARTICIPANT_MAIN_CLASS));
    //       elements.forEach(function(item) {
    //           item.className = PARTICIPANT_CLASS;
    //         });

    //         container.className = PARTICIPANT_MAIN_CLASS;
    //       } else {
    //       container.className = PARTICIPANT_CLASS;
    //     }
    //   }

    //   function isPresentMainParticipant() {
    //     return ((document.getElementsByClassName(PARTICIPANT_MAIN_CLASS)).length != 0);
    //   }

    //   // error, offerSdp, wp
    //   this.offerToReceiveVideo = function(error, offerSdp){
    //     if (error) return console.error ("sdp offer error")
    //     console.log('Invoking SDP offer callback function');
    //     var msg =  { id : "receiveVideoFrom",
    //         sender : name,
    //         sdpOffer : offerSdp
    //       };
    //     sendMessage(msg);
    //   }

    //   // candidate, wp
    //   this.onIceCandidate = function (candidate) {
    //       console.log("Local candidate" + JSON.stringify(candidate));

    //       var message = {
    //         id: 'onIceCandidate',
    //         candidate: candidate,
    //         name: name
    //       };
    //       sendMessage(message);
    //   }

    //   Object.defineProperty(this, 'rtcPeer', { writable: true});

    //   this.dispose = function() {
    //     console.log('Disposing participant ' + this.name);
    //     rtcPeer.dispose();
    //     container.parentNode.removeChild(container);
    //   };
    // }

    return {
      state, url, joinLeacture
    }
  }
}
</script>

<style>
  
</style>