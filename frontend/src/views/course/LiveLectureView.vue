<template>
  <div>
    <div v-if="state.isMainScreen" class="screen-list-block">
      <q-carousel
        v-model="state.screenSlide"
        swipeable
        control-color="primary"
        padding
        arrows
        height="100%"
        class="screen-list bg-grey-1 shadow-2"
      >
        <q-carousel-slide :name="1" class="column no-wrap">
          <div id="participants" class="row fit items-center q-gutter-xs q-col-gutter no-wrap">

          </div>
        </q-carousel-slide>
      </q-carousel>
    </div>
    <div v-if="state.isMainScreen" class="main-screen-block">
      <div style="position: relative" id="face-detection">
        <canvas id="face-video"></canvas>
      </div>
      <img src="https://cdn.quasar.dev/img/mountains.jpg">
      <span v-if="state.isSubtitles" class="main-screen-subtitles">{{ state.subtitles }}</span>
        <video id="videoOutput" autoplay style="width: 480px; height: 320px;"
					></video>
      <span class="main-screen-subtitles">{{ state.res }}</span>
    </div>
    <div v-else class="all-screen-list-block">
      
    </div>
    <div class="menu-block row justify-between">
      <div class="col-1 row justify-center items-center">

      </div>
      <div class="col-2 row justify-between items-center">
        <div style="width: 4vh; height: 4vh; border-radius: 4vh;" class="mic-on-img"></div>
        <!-- <div style="width: 4vh; height: 4vh; border-radius: 4vh;" class="mic-off-img"></div> -->
        <div @click="init" style="width: 4vh; height: 4vh; border-radius: 4vh;" class="video-on-img"></div>
        <!-- <div @click="init" style="width: 4vh; height: 4vh; border-radius: 4vh;" class="video-off-img"></div> -->
        <div @click="leaveRoom" style="width: 4vh; height: 4vh; border-radius: 4vh;" class="exit-img"></div>
      </div>
      <div class="col-1 row justify-center items-center">

      </div>
    </div>
  </div>
</template>

<script>
import { reactive } from '@vue/reactivity'
import { useStore } from 'vuex'
import router from '@/router'
import { onMounted } from '@vue/runtime-core'
import { useRoute } from 'vue-router'

import * as faceapi from 'face-api.js'
import $ from 'jquery'
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
// import logo from '@/assets/logo.svc'
// import model_json from '@/assets/my_model/model.json'
// import metadata_json from '@/assets/my_model/metadata.json'

export default {
  name: 'LiveLectureView',
  components: {
  },

  setup() {
    const store = useStore()
    const route = useRoute()


    const URL = "https://teachablemachine.withgoogle.com/models/a2NpjKcPa/"
    // let webcam, model, maxPredictions
    const state = reactive({
      screenSlide: 1,
      isMainScreen: true,
      isSubtitles: store.state.user.isSubtitle,
      subtitles: '',
      subtitles_text: '',
      res: '',
      face_width: null,
      face_height: null,
      face_x: null,
      face_y: null,
      videoOutput: document.getElementById('videoOutput'),
      chatList: [],
      stompClient: null,
      // logo
    })

    // Function
    function connect() {
      var socket = new SockJS('/cochlens')
      state.stompClient = Stomp.over(socket)

      state.stompClient.connect({}, () => {
          subscribeChat()
      })
    }

    const subscribeChat = () => {
      state.stompClient.subscribe(`/topic/${route.params.lectureId}`, res => {
        const chatMsg = JSON.parse(res.body)
        console.log(chatMsg)
        console.log(store.state.user.userName)
        console.log(store.state.courseStore.courseData.instructorName)
        if (store.state.user.userName != store.state.courseStore.courseData.instructorName) {
          state.subtitles = chatMsg.content
          console.log(state.subtitles)
        }
        state.chatList.push(chatMsg)
      })
    }

    function sendChat(subtitles) {
      console.log('send')
      // 전달할 객체
      if (state.stompClient) {
        const msg = {
          lectureId: 102,
          userName: store.state.user.userName,
          content: subtitles
        }
        state.stompClient.send(`/app/chat/${route.params.lectureId}`, JSON.stringify(msg), {})
        state.text = ''
      }
    }

    function disconnect() {
      state.stompClient.disconnect()
      state.stompClient = null
    }

    connect()

    // [STT 변수]
    const sdk = require('microsoft-cognitiveservices-speech-sdk')
    let recognizer
    let subtitles_data = {"subtitles": []}

    // [STT 실행 - 강사인 경우]
    // (강사 이름을 닉네임으로 변경한 후 코드 변경)
    // if (store.state.user.userNickname == store.state.courseStore.courseData.instructorNickname) {
    if (store.state.user.userName == store.state.courseStore.courseData.instructorName) {
      doContinuousRecognition()
    }

    // [STT 함수]
    // 연속 자막 시작
    function doContinuousRecognition() {
      console.log('doContinuousRecognition')
      state.isSubtitles = true
      state.subtitles = ''
      state.subtitles_text = ''

      const audioConfig = sdk.AudioConfig.fromDefaultMicrophoneInput();
      let speechConfig = getSpeechConfig()

      // Create the SpeechRecognizer and set up common event handlers and PhraseList data
      recognizer = new sdk.SpeechRecognizer(speechConfig, audioConfig);
      applyCommonConfigurationTo(recognizer);

      // 연속 자막 통신 시작
      recognizer.startContinuousRecognitionAsync();
    }

    // 연속 자막 중지
    function stopContinuousRecognition() {
      console.log('stopContinuousRecognition')
      state.isSubtitles = false
      recognizer.close()
      recognizer = undefined
      // 자막 DB에 저장 요청
      store.dispatch('courseStore/saveSubtitles', { id: route.params.lectureId, subtitles: subtitles_data.subtitles })
      subtitles_data.subtitles = []
    }

    // 자막 정보 설정 (키, 지역, 언어, 상세 데이터 여부)
    function getSpeechConfig() {
      const speechConfig = sdk.SpeechConfig.fromSubscription(process.env.VUE_APP_SPEECH_KEY, process.env.VUE_APP_SPEECH_REGION)
      speechConfig.speechRecognitionLanguage = 'ko-KR';  // 인식 언어
      speechConfig.outputFormat = sdk.OutputFormat.Detailed  // 자막 상세 데이터 수신 여부 (Detailed)
      return speechConfig
    }

    // 문장이 진행되는 중, 문장이 끝났을 때 작동
    function applyCommonConfigurationTo(recognizer) {
      // 문장이 진행되는 중
      // The 'recognizing' event signals that an intermediate recognition result is received.
      // Intermediate results arrive while audio is being processed and represent the current "best guess" about
      // what's been spoken so far.
      recognizer.recognizing = onRecognizing;

      // 문장이 끝났을 때
      // The 'recognized' event signals that a finalized recognition result has been received. These results are
      // formed across complete utterance audio (with either silence or eof at the end) and will include
      // punctuation, capitalization, and potentially other extra details.
      // 
      // * In the case of continuous scenarios, these final results will be generated after each segment of audio
      //   with sufficient silence at the end.
      recognizer.recognized = onRecognized;
    }

    // 문장이 진행되는 중
    function onRecognizing(sender, recognitionEventArgs) {
      var result = recognitionEventArgs.result;

      // Update the hypothesis line in the phrase/result view (only have one)
      state.subtitles = state.subtitles.replace(/(.*)(^|[\r\n]+).*\[\.\.\.\][\r\n]+/, '$1$2') + `${result.text} [...]\r\n`;
    }

    // 문장이 끝났을 때
    function onRecognized(sender, recognitionEventArgs) {
      onRecognizedResult(recognitionEventArgs.result);
    }

    // 문장이 끝나고 결과 출력
    function onRecognizedResult(result) {
      state.subtitles = state.subtitles.replace(/(.*)(^|[\r\n]+).*\[\.\.\.\][\r\n]+/, '$1$2');

      switch (result.reason) {
        case sdk.ResultReason.RecognizedSpeech:
        case sdk.ResultReason.RecognizedIntent:

          var detailedResultJson = JSON.parse(result.json);
          delete detailedResultJson.NBest
          detailedResultJson.displayText = detailedResultJson.DisplayText
          detailedResultJson.id = detailedResultJson.Id
          detailedResultJson.recognitionStatus = detailedResultJson.RecognitionStatus
          detailedResultJson.offset = detailedResultJson.Offset / 10000000
          detailedResultJson.duration = detailedResultJson.Duration / 10000000
          delete detailedResultJson.DisplayText
          delete detailedResultJson.Id
          delete detailedResultJson.RecognitionStatus
          delete detailedResultJson.Offset
          delete detailedResultJson.Duration

          subtitles_data.subtitles.push(detailedResultJson)

          // Detailed result JSON includes substantial extra information:
          //  detailedResultJson['NBest'] is an array of recognition alternates
          //  detailedResultJson['NBest'][0] is the highest-confidence alternate
          //  ...['Confidence'] is the raw confidence score of an alternate
          //  ...['Lexical'] and others provide different result forms
          state.subtitles_text += `${JSON.stringify(detailedResultJson, null, 2)}\r\n`;

          state.subtitles = `${result.text}\r\n`;

          // 소켓 통신으로 사용자들에게 pub...
          sendChat(state.subtitles)

          console.log('detailedResultJson : ', detailedResultJson)
          console.log('state.subtitles_text : ', state.subtitles_text)
          console.log('subtitles_data.subtitles : ', subtitles_data.subtitles)
      }
    }

    // [Face Detection]
    // 얼굴 인식 변수
    const SSD_MOBILENETV1 = 'ssd_mobilenetv1'
    const TINY_FACE_DETECTOR = 'tiny_face_detector'
    let selectedFaceDetector = SSD_MOBILENETV1
    // ssd_mobilenetv1 options
    let minConfidence = 0.4
    // tiny_face_detector options
    let inputSize = 224
    let scoreThreshold = 0.4

    // 얼굴 인식 옵션
    function getFaceDetectorOptions() {
      return selectedFaceDetector === SSD_MOBILENETV1
        ? new faceapi.SsdMobilenetv1Options({ minConfidence })
        : new faceapi.TinyFaceDetectorOptions({ inputSize, scoreThreshold })
    }
    // 얼굴 인식 모델 로드
    function isFaceDetectionModelLoaded() {
      return !!getCurrentFaceDetectionNet().params
    }
    // 얼굴 인식 모델 선정
    function getCurrentFaceDetectionNet() {
      if (selectedFaceDetector === SSD_MOBILENETV1) {
        return faceapi.nets.ssdMobilenetv1
      }
      if (selectedFaceDetector === TINY_FACE_DETECTOR) {
        return faceapi.nets.tinyFaceDetector
      }
    }
    // 얼굴 인식 컨트롤러 변경
    async function changeFaceDetector(detector) {
      ['#ssd_mobilenetv1_controls', '#tiny_face_detector_controls']
        .forEach(id => $(id).hide())

      selectedFaceDetector = detector
      const faceDetectorSelect = $('#selectFaceDetector')
      faceDetectorSelect.val(detector)
      faceDetectorSelect.select()

      $('#loader').show()
      if (!isFaceDetectionModelLoaded()) {
        await getCurrentFaceDetectionNet().load('https://storage.googleapis.com/cochlens/models/tiny_face_detector_model-weights_manifest.json')
      }

      $(`#${detector}_controls`).show()
      $('#loader').hide()
    }
    // 얼굴 인식 컨트롤러 삽입
    function initFaceDetectionControls() {
      const faceDetectorSelect = $('#selectFaceDetector')
      faceDetectorSelect.val(selectedFaceDetector)
      faceDetectorSelect.select()

      const inputSizeSelect = $('#inputSize')
      inputSizeSelect.val(inputSize)
      inputSizeSelect.select()
    }

    // 영상 재생
    async function onPlay() {
      const cam = document.getElementById('video-Instructor')

      if(cam.paused || cam.ended || !isFaceDetectionModelLoaded())
        return setTimeout(() => onPlay())


      const options = getFaceDetectorOptions()

      const result = await faceapi.detectSingleFace(cam, options)

      if (result) {
        state.face_width = result.box.width
        state.face_height = result.box.height
        state.face_x = result.box.x
        state.face_y = result.box.y
      }

      setTimeout(() => onPlay())
    }
    // 얼굴 인식 시작
    async function run() {
      await changeFaceDetector(TINY_FACE_DETECTOR)
    }

    // 캡처 대상 인식(강사 화면)
    function doLoad() {
      const video = document.getElementById("video-Instructor")
      video.addEventListener("play", computeFrame())
    }
    // 화면 캡처
    function computeFrame() {
      const video = document.getElementById("video-Instructor")
      const width = state.face_width;
      const height = state.face_height;
      const canv = document.getElementById("face-video")
      canv.style.width = `400px`
      canv.style.height = `360px`
      const ctx = canv.getContext("2d")
      ctx.drawImage(video, state.face_x, state.face_y, width, height, 0, 0, canv.width, canv.height)
      if (width > 0) {
        let frame = ctx.getImageData(0, 0, width, height)
        let l = frame.data.length / 4
  
        for (let i = 0; i < l; i++) {
          let r = frame.data[i * 4 + 0]
          let g = frame.data[i * 4 + 1]
          let b = frame.data[i * 4 + 2]
          if (g > 100 && r > 100 && b < 43)
            frame.data[i * 4 + 3] = 0
        }
        ctx.putImageData(frame, 0, 0)
      }
      setTimeout(() => {computeFrame()}, 0.03*1000)
    }
    // 캡처 실행
    function onCam() {
      const cam = document.getElementById('video-Instructor')
      cam.addEventListener('onloadedmetadata', onPlay())
      document.addEventListener("DOMContentLoaded", doLoad())
    }

    onMounted(() => {
      // init()
      setTimeout(() => { onCam(), onPlay() }, 1000 )
    })

    initFaceDetectionControls()
    run()

    // Function
    function leaveRoom() {
      // 강사 이름을 닉네임을 변경한 후 사용
      // if (store.state.user.userNickname == store.state.courseStore.courseData.instructorNickname) {
      if (store.state.user.userName == store.state.courseStore.courseData.instructorName) {
        stopContinuousRecognition()
      }
      disconnect()
      store.dispatch('courseStore/leaveLecture')
      store.dispatch('courseStore/getCourseDetail', route.params.courseId)
      .then(() => {
        router.push({ name: 'courseDetail', params: { courseId: route.params.courseId } })
      })
    }

    /**
     * 녹화 관련 함수들
     */

    function startRecording() {
      console.log('startRecording')
      state.videoOutput = document.getElementById('videoOutput')
      console.log(state.videoOutput)
      store.dispatch('courseStore/startRecording', state.videoOutput)
    }

    function stopRecording() {
      console.log('stopRecording')
      store.dispatch('courseStore/stopRecording')
    }

    function playRecording() {
      console.log('playRecording')
      store.dispatch('courseStore/playRecording', state.videoOutput)
    }

  // [Motion Command]
  //   async function init() {
  //     const modelURL = URL + "model.json"
  //     const metadataURL = URL + "metadata.json"

  //     // console.log('start')
  //     // console.log(modelURL)
  //     // console.log(metadataURL)
  //     model = await window.tmImage.load(modelURL, metadataURL)
  //     maxPredictions = model.getTotalClasses()

  //     // console.log('start2')
  //     const flip = true; // whether to flip the webcam
  //     webcam = new window.tmImage.Webcam(200, 200, flip);
  //     await webcam.setup(); // request access to the webcam
  //     await webcam.play();
  //     // console.log('start3')
  //     window.requestAnimationFrame(loop);
  //     // console.log('start-done')
  //   }

  //   async function loop() {
  //     // console.log('loop')
  //     webcam.update()
  //     await predict();
  //     window.requestAnimationFrame(loop);
  //   }

  //   async function predict() {
  //     // console.log('check')
  //     // predict can take in an image, video or canvas html element
  //     const prediction = await model.predict(webcam.canvas);
  //     // console.log('check2')
  //     for (let i = 0; i < maxPredictions; i++) {
  //         if (prediction[i].className === "close" && prediction[i].probability.toFixed(2) >= 0.9) {
  //           leaveRoom()
  //         } else if (prediction[i].className === "help" && prediction[i].probability.toFixed(2) >= 0.9) {
  //           state.res = 'HELP!!!! 도와주세요!!!!!!!!!'
  //         } else if (prediction[i].className === "thanks" && prediction[i].probability.toFixed(2) >= 0.9) {
  //           state.res = 'THANKS!!!!!!! 감사합니다!!!!!!!'
  //         }
  //     }
  // }

    return {
      state, URL,
      leaveRoom,
      doContinuousRecognition,
      stopContinuousRecognition,
      startRecording, stopRecording, playRecording,
      connect, sendChat, disconnect
    }
  }
}
</script>

<style scoped lang="scss">
.screen-list-block {
  width: 100%;
  height: 15vh;
}

.screen-list {
  background-color: lightgray;
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
  z-index: 5;
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

.mic-on-img {
  background-image: url('@/assets/mic-on.png');
  background-size: cover;
}

.mic-off-img {
  background-image: url('@/assets/mic-off.png');
  background-size: cover;
}

.video-on-img {
  background-image: url('@/assets/video-on.png');
  background-size: cover;
}

.video-off-img {
  background-image: url('@/assets/video-off.png');
  background-size: cover;
}

.exit-img {
  background-image: url('@/assets/exit.png');
  background-size: cover;
}

.q-carousel__slide {
  padding: 4px;
}

.q-panel-parent {
  overflow: visible;
}

::v-deep {
  .participant {
    height: 100%;
    width: 16.6667%;
    min-width: 16.6667%;
    border-radius: 4px;
    overflow: hidden;
  }

  .participant.main {
    position: absolute;
    top: 15vh;
    width: auto;
    height: 77vh;
    border-radius: 0;
    margin-top: 0;
    margin-left: 0;
    left: 50%;
    z-index: 1;
    transform: translate(-50%, 0);
  }

  .participant > video {
    position: relative;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
  }

  .participant.main > video {
    height: 77vh;
  }

  .participant > span {
    position: relative;
    top: -109px;
    left: 0;
    z-index: 2;
    background-color: white;
  }

  .participant.main > span {
    display: none;
  }

  #face-video {
    position: absolute;
    right: 0;
    top: 0;
    z-index: 3;
  }
}
</style>