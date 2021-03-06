import store from "@/store/index.js";

export default function Participant(name) {
  const PARTICIPANT_MAIN_CLASS = 'participant main';
  const PARTICIPANT_CLASS = 'participant';

  this.name = name;

  let container = document.createElement('div');
  container.className = isPresentMainParticipant() ? PARTICIPANT_CLASS : PARTICIPANT_MAIN_CLASS;
  container.id = name;
  let span = document.createElement('span');
  let video = document.createElement('video');

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
    var msg =  { 
      id : "receiveVideoFrom",
      sender : name,
      sdpOffer : offerSdp
    };
    store.dispatch('courseStore/sendMessage', msg)
  }

  this.offerToRecordingVideo = function(error, offerSdp) {
    if (error) return console.error ("sdp offer error")
    console.log('Invoking SDP offer callback function');
    var msg =  { 
      id : 'startRecording',
      sender : name,
      sdpOffer : offerSdp,
      mode : 'video-and-audio'
    };
    store.dispatch('courseStore/sendMessage', msg)
  }

  this.offerToPlayVideo = function(error, offerSdp) {
    if (error) return console.error ("sdp offer error")
    console.log('Invoking SDP offer callback function');
    var msg =  { 
      id : 'playRecording',
      sender : name,
      sdpOffer : offerSdp
    };
    store.dispatch('courseStore/sendMessage', msg)
  }

  // candidate, wp
  this.onIceCandidate = function (candidate) {
      console.log("Local candidate" + JSON.stringify(candidate));

      var msg = {
        id: 'onIceCandidate',
        candidate: candidate,
        name: name,
      };
      store.dispatch('courseStore/sendMessage', msg)
  }

  Object.defineProperty(this, 'rtcPeer', { writable: true});

  this.dispose = function() {
    console.log('Disposing participant ' + this.name);
    this.rtcPeer.dispose();
    container.parentNode.removeChild(container);
  };
}