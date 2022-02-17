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
      <span v-if="state.isSubtitles" class="main-screen-subtitles">{{ state.subtitles }}</span>
        <video id="videoOutput" autoplay style="width: 480px; height: 320px;"
					></video>
      <span class="main-screen-subtitles">{{ state.res }}</span>
    </div>
    <div v-else class="all-screen-list-block">
      {{ store.state.courseStore.participants }}
    </div>
    <div class="menu-block row justify-between">
      <div class="col-1 row justify-center items-center">
        <img @click="startRecording" style="width: 4vh; height: 4vh; border-radius: 4vh;" src="https://cdn.quasar.dev/img/cat.jpg" />
      </div>
      <div class="col-2 row justify-between items-center">
        <img @click="stopRecording" style="width: 4vh; height: 4vh; border-radius: 4vh;" src="https://cdn.quasar.dev/img/cat.jpg" />
        <img @click="playRecording" style="width: 4vh; height: 4vh; border-radius: 4vh;" src="https://cdn.quasar.dev/img/cat.jpg" />
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
import { useStore } from 'vuex'
import router from '@/router'
import { onMounted } from '@vue/runtime-core'
import { useRoute } from 'vue-router'
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

    const sockJs = new SockJS("/stomp/chat");
    const stomp = Stomp.over(sockJs);

    const URL = "https://teachablemachine.withgoogle.com/models/a2NpjKcPa/"
    let webcam, model, maxPredictions
    const state = reactive({
      screenSlide: 1,
      isMainScreen: true,
      isSubtitles: store.state.user.isSubtitle,
      subtitles: '',
      subtitles_text: '',
      res: '',
      videoOutput: document.getElementById('videoOutput'),
      // logo
    })

    // [STT 변수]
    const sdk = require('microsoft-cognitiveservices-speech-sdk')
    let recognizer
    let subtitles_data = {"subtitles": []}

    // [STT 실행 - 강사인 경우]
    // (강사 이름을 닉네임으로 변경한 후 코드 변경)
    // if (store.state.user.userNickname == store.state.courseStore.courseData.instructorNickname) {
    if (store.state.user.userName == store.state.courseStore.courseData.instructorName) {
      doContinuousRecognition()
      connect()
    }

    // [STT 함수]
    // 연속 자막 시작
    function doContinuousRecognition() {
      console.log('doContinuousRecognition')
      state.isSubtitles = true
      state.subtitles = ''
      state.subtitles_text = ''

      const audioConfig = sdk.AudioConfig.fromDefaultMicrophoneInput();
      console.log('1')
      let speechConfig = getSpeechConfig()
      console.log('2')

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
          stomp.send('/pub/chat/message', {}, JSON.stringify({roomId: route.params.lectureId, message: state.subtitles, writer: store.state.user.userName}));


          console.log('detailedResultJson : ', detailedResultJson)
          console.log('state.subtitles_text : ', state.subtitles_text)
          console.log('subtitles_data.subtitles : ', subtitles_data.subtitles)
      }
    }

    // Mounted
    onMounted(() => {
      // init()
    })

    // Function
    function leaveRoom() {
      // 강사 이름을 닉네임을 변경한 후 사용
      // if (store.state.user.userNickname == store.state.courseStore.courseData.instructorNickname) {
      if (store.state.user.userName == store.state.courseStore.courseData.instructorName) {
        stopContinuousRecognition()
      }
      store.dispatch('courseStore/leaveLecture')
      router.push({ name: 'courseDetail', params: { courseId: route.params.courseId } })
    }

    function connect() {
        console.log("STOMP connect start")
            // pub/sub event
      stomp.connect({}, function() {
        console.log("STOMP Connection")
        stomp.subscribe("/sub/chat/room/"+ route.params.lectureId, function(message) {
          var recv = JSON.parse(message.body)
          // stomp.recvMessage(recv);
          console.log("/sub/chat/room/" + recv)
        });
        console.log("/pub/chat/enter" + JSON.stringify({roomId: route.params.lectureId, writer: store.state.user.userName}))
        stomp.send("/pub/chat/enter", {}, JSON.stringify({roomId: route.params.lectureId, message: '', writer: store.state.user.userName}))
      });
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

    /**
     * motion
     */

    async function init() {
      const modelURL = URL + "model.json"
      const metadataURL = URL + "metadata.json"

      // console.log('start')
      // console.log(modelURL)
      // console.log(metadataURL)
      model = await window.tmImage.load(modelURL, metadataURL)
      maxPredictions = model.getTotalClasses()

      // console.log('start2')
      const flip = true; // whether to flip the webcam
      webcam = new window.tmImage.Webcam(200, 200, flip);
      await webcam.setup(); // request access to the webcam
      await webcam.play();
      // console.log('start3')
      window.requestAnimationFrame(loop);
      // console.log('start-done')
    }

    async function loop() {
      // console.log('loop')
      webcam.update()
      await predict();
      window.requestAnimationFrame(loop);
    }

    async function predict() {
      // console.log('check')
      // predict can take in an image, video or canvas html element
      const prediction = await model.predict(webcam.canvas);
      // console.log('check2')
      for (let i = 0; i < maxPredictions; i++) {
          if (prediction[i].className === "close" && prediction[i].probability.toFixed(2) >= 0.9) {
            leaveRoom()
          } else if (prediction[i].className === "help" && prediction[i].probability.toFixed(2) >= 0.9) {
            state.res = 'HELP!!!! 도와주세요!!!!!!!!!'
          } else if (prediction[i].className === "thanks" && prediction[i].probability.toFixed(2) >= 0.9) {
            state.res = 'THANKS!!!!!!! 감사합니다!!!!!!!'
          }
      }
  }

    return {
      state, URL, connect,
      leaveRoom, init, predict,
      doContinuousRecognition,
      stopContinuousRecognition,
      startRecording, stopRecording, playRecording
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

::v-deep {
  .participant {
    height: 100%;
    width: 16.6667%;
    border-radius: 4px;
    overflow: hidden;
  }

  .participant > video {
    position: relative;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
  }

  .participant > span {
    position: absolute;
    top: 10px;
    z-index: 2;
    background-color: white;
  }

  // .participant.main > span {
  //   position: absolute;
  //   top: 0;
  //   z-index: 2;
  //   background-color: white;
  //   display: none;
  // }
}
</style>