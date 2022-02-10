<template>
  <div class="course-roof">
    <div class="profile-roof"></div>
    <div class="profile-roof-cover"></div>
    <div class="profile-img text-white"></div>
    <p class="course-detail-title">강쉡의 요리교실</p>
    <p class="course-detail-instructor">강사 : 강태훈</p>
  </div>

  <div class="row course-detail-upper-area">
    <div class="col-7">
      <!-- 강좌 소개 -->
      <div class="q-mb-xl">
        <p class="profile-menu-title">강좌 소개</p>
        <p>
          강태훈 강사님의 신나는 요리교실. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다.  이것은 샘플 텍스트입니다.
          <br>
          이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 
          이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다.
        </p>
      </div>

      <!-- 커리큘럼 -->
      <div>
        <div>
          <p class="profile-menu-title">커리큘럼</p>
        </div>
        <div class="course-detail-curriculum row">
          <div class="course-detail-thumbnail"></div>
          <div>
            <p class="curriculum-title">제 1강 요리란 무엇인가? 강쉡이 알려드립니다!</p>
            <p class="curriculum-description">
              강사 : 강태훈<br>
              요리에 대한 모든 것! 강쉡이 하나부터 열까지 다 알려드립니다! 초보에게 강추!<br>
              별점 : 10/10 (10000+) / 길이 : 1H
            </p>
          </div>
        </div>
        <div class="course-detail-curriculum row">
          <div class="course-detail-thumbnail"></div>
          <div>
            <p class="curriculum-title">제 2강 닭 한마리 완전 정복</p>
            <p class="curriculum-description">
              강사 : 강태훈<br>
            </p>
          </div>
          <q-btn @click="joinLeacture" color="purple">강의 시작하기</q-btn>
        </div>
        <div class="course-detail-curriculum"></div>
        <div class="course-detail-curriculum"></div>
        <div class="course-detail-curriculum"></div>
        <div class="course-detail-curriculum"></div>
        <div class="course-detail-curriculum"></div>
        <div class="course-detail-curriculum"></div>
        <div class="course-detail-curriculum"></div>
        <div class="course-detail-curriculum"></div>
      </div>
    </div>
    <div class="col-1"></div>

    <div class="col-4 q-pa-md">
      <div class="reserve">
        <!-- 수강신청 -->
        <q-card class="reservation-card q-mb-xl">
          <q-card-section class="bg-purple text-white">
            <div class="text-h6">수강신청</div>
            <div class="text-subtitle2">by John Doe</div>
          </q-card-section>

          <q-card-actions align="around">
            <q-btn flat>신청하기</q-btn>
            <q-btn flat>찜 하기</q-btn>
          </q-card-actions>
        </q-card>

        <!-- 강좌 리뷰 -->
        <q-card class="reservation-card">
          <q-card-section class="bg-purple text-white">
            <div class="row">
              <p class="text-h6 q-mr-sm q-mb-none">강좌 리뷰</p>
              <a @click="moveCourseReview">더보기</a>
            </div>
            <div class="text-subtitle2">by John Doe</div>
          </q-card-section>

          <q-card-actions align="around">
            <div class="course-detail-review">이것은 리뷰 입니다</div>
            <div class="course-detail-review">이것은 리뷰 입니다</div>
            <div class="course-detail-review">이것은 리뷰 입니다</div>
            <div class="course-detail-review">이것은 리뷰 입니다</div>
            <div class="course-detail-review">이것은 리뷰 입니다</div>
            <div class="course-detail-review">이것은 리뷰 입니다</div>
            <div class="course-detail-review">이것은 리뷰 입니다</div>
            <div class="course-detail-review">이것은 리뷰 입니다</div>
            <div class="course-detail-review">이것은 리뷰 입니다</div>
            <div class="course-detail-review">이것은 리뷰 입니다</div>
            <div class="course-detail-review">이것은 리뷰 입니다</div>
            <div class="course-detail-review">이것은 리뷰 입니다</div>
            <div class="course-detail-review">이것은 리뷰 입니다</div>
            <div class="course-detail-review">이것은 리뷰 입니다</div>
            <div class="course-detail-review">이것은 리뷰 입니다</div>
            <div class="course-detail-review">이것은 리뷰 입니다</div>
            <div class="course-detail-review">이것은 리뷰 입니다</div>
          </q-card-actions>
        </q-card>
      </div>
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
      name: 'test',
      room: '',
    })

    // Function
    function moveCourseReview() {
      router.push({ name: 'courseReview' })
    }
    
    function joinLeacture() {
      let message = {
        id : 'joinRoom',
        name : state.name,
        room : 'testroom',
      }
      store.dispatch('courseStore/register', message)
      router.push({ name: 'live' })
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
      state, url, joinLeacture, moveCourseReview 
    }
  }
}
</script>
<style>
  .course-roof {
    position: sticky;
    top: 0;
  }

  .course-detail-title {
    position: absolute;
    top: 100px;
    left: 220px;
    font-size: 30px;
    font-weight: bold;
    color: white;
    z-index: 2;
  }

  .course-detail-instructor {
    position: absolute;
    top: 145px;
    left: 225px;
    font-size: 15px;
    color: white;
    z-index: 2;
  }

  .course-detail-upper-area {
    padding: 60px 60px 0 60px;
  }

  .reservation-card {
    width: 100%;
  }

  .course-detail-review {
    width: 100%;
  }

  .course-detail-thumbnail {
    width: 200px;
    height: 130px;
    border-radius: 5px;
    background-image: url('@/assets/cooking.jpg');
    background-size: cover;
  }

  .course-detail-curriculum {
    width: 100%;
    height: 130px;
    border-radius: 5px;
    margin-bottom: 20px;
    background: gray;
  }

  .curriculum-title {
    font-size: 20px;
    font-weight: 500;
    color: white;
    margin: 10px;
  }

  .curriculum-description {
    font-size: 15px;
    font-weight: 200;
    color: white;
    margin: 10px;
  }

  .reserve {
    position: -webkit-sticky;
    position: sticky !important;
    top: 255px;
  }
</style>