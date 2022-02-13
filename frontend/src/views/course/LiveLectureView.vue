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
            <q-img class="rounded-borders col-2 full-height" src="https://cdn.quasar.dev/img/parallax1.jpg" />
            <q-img class="rounded-borders col-2 full-height" src="https://cdn.quasar.dev/img/mountains.jpg" />
            <q-img class="rounded-borders col-2 full-height" src="https://cdn.quasar.dev/img/parallax1.jpg" />
            <q-img class="rounded-borders col-2 full-height" src="https://cdn.quasar.dev/img/mountains.jpg" />
            <q-img class="rounded-borders col-2 full-height" src="https://cdn.quasar.dev/img/parallax1.jpg" /> -->
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
      <!-- face detection test -->

      <div class="page-container">

        <div class="progress" id="loader">
          <div class="indeterminate"></div>
        </div>
        <div style="position: relative" class="margin" id="face-position">
          <canvas id="overlay" />
        </div>

        <div class="row side-by-side">

          <!-- face_detector_selection_control -->
          <div id="face_detector_selection_control" class="row input-field" style="margin-right: 20px;">
            <select id="selectFaceDetector">
              <option value="ssd_mobilenetv1">SSD Mobilenet V1</option>
              <option value="tiny_face_detector">Tiny Face Detector</option>
            </select>
            <label>Select Face Detector</label>
          </div>
          <!-- face_detector_selection_control -->

          <!-- check boxes -->
          <div class="row" style="width: 220px;">
            <input type="checkbox" id="hideBoundingBoxesCheckbox" @onchange="onChangeHideBoundingBoxes(event)" />
            <label for="hideBoundingBoxesCheckbox">Hide Bounding Boxes</label>
          </div>
          <!-- check boxes -->

          <!-- fps_meter -->
          <div id="fps_meter" class="row side-by-side">
            <div>
              <label for="time">Time:</label>
              <input disabled value="-" id="time" type="text" class="bold">
              <label for="fps">Estimated Fps:</label>
              <input disabled value="-" id="fps" type="text" class="bold">
            </div>
          </div>
          <!-- fps_meter -->

        </div>


        <!-- ssd_mobilenetv1_controls -->
        <span id="ssd_mobilenetv1_controls">
          <div class="row side-by-side">
            <div class="row">
              <label for="minConfidence">Min Confidence:</label>
              <input disabled value="0.5" id="minConfidence" type="text" class="bold">
            </div>
            <button
              class="waves-effect waves-light btn"
              @onclick="onDecreaseMinConfidence()"
            >
              <i class="material-icons left">-</i>
            </button>
            <button
              class="waves-effect waves-light btn"
              @onclick="onIncreaseMinConfidence()"
            >
              <i class="material-icons left">+</i>
            </button>
          </div>
        </span>
        <!-- ssd_mobilenetv1_controls -->

        <!-- tiny_face_detector_controls -->
        <span id="tiny_face_detector_controls">
          <div class="row side-by-side">
            <div class="row input-field" style="margin-right: 20px;">
              <select id="inputSize">
                <option value="" disabled selected>Input Size:</option>
                <option value="128">128 x 128</option>
                <option value="160">160 x 160</option>
                <option value="224">224 x 224</option>
                <option value="320">320 x 320</option>
                <option value="416">416 x 416</option>
                <option value="512">512 x 512</option>
                <option value="608">608 x 608</option>
              </select>
              <label>Input Size</label>
            </div>
            <div class="row">
              <label for="scoreThreshold">Score Threshold:</label>
              <input disabled value="0.5" id="scoreThreshold" type="text" class="bold">
            </div>
            <button
              class="waves-effect waves-light btn"
              @onclick="onDecreaseScoreThreshold()"
            >
              <i class="material-icons left">-</i>
            </button>
            <button
              class="waves-effect waves-light btn"
              @onclick="onIncreaseScoreThreshold()"
            >
              <i class="material-icons left">+</i>
            </button>
          </div>
        </span>
        <!-- tiny_face_detector_controls -->
      </div>

      <!-- face detection test -->
    </div>
    <div v-else class="all-screen-list-block">
    </div>
    <div class="menu-block row justify-between">
      <div class="col-1 row justify-center items-center">
        <img style="width: 4vh; height: 4vh; border-radius: 4vh;" src="https://cdn.quasar.dev/img/cat.jpg" />
      </div>
      <div class="col-2 row justify-between items-center">
        <img @click="onCam" style="width: 4vh; height: 4vh; border-radius: 4vh;" src="https://cdn.quasar.dev/img/cat.jpg" />
        <img @click="instructorFace" style="width: 4vh; height: 4vh; border-radius: 4vh;" src="https://cdn.quasar.dev/img/cat.jpg" />
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

import * as faceapi from 'face-api.js'
import $ from 'jquery'
import { onMounted } from '@vue/runtime-core'

export default {
  name: 'LiveLectureView',
  components: {
  },

  setup() {
    const store = useStore()
    const state = reactive({
      screenSlide: 1,
      isMainScreen: true,
    })

    // js-var
    const SSD_MOBILENETV1 = 'ssd_mobilenetv1'
    const TINY_FACE_DETECTOR = 'tiny_face_detector'
    let selectedFaceDetector = SSD_MOBILENETV1
    // ssd_mobilenetv1 options
    let minConfidence = 0.4
    // tiny_face_detector options
    let inputSize = 512
    let scoreThreshold = 0.4

    // html-var
    let forwardTimes = []
    let withBoxes = true

    // js-script
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
    // 얼굴 인식 사이즈 변경
    function changeInputSize(size) {
      inputSize = parseInt(size)

      const inputSizeSelect = $('#inputSize')
      inputSizeSelect.val(inputSize)
      inputSizeSelect.select()
    }
    // 얼굴 인식 컨트롤러 변경 선택
    async function onSelectedFaceDetectorChanged(e) {
      selectedFaceDetector = e.target.value

      await changeFaceDetector(e.target.value)
      updateResults()
    }
    // 얼굴 인식 사이즈 변경 입력
    function onInputSizeChanged(e) {
      changeInputSize(e.target.value)
      updateResults()
    }
    // 얼굴 인식 컨트롤러 삽입
    function initFaceDetectionControls() {
      const faceDetectorSelect = $('#selectFaceDetector')
      faceDetectorSelect.val(selectedFaceDetector)
      faceDetectorSelect.on('change', onSelectedFaceDetectorChanged)
      faceDetectorSelect.select()

      const inputSizeSelect = $('#inputSize')
      inputSizeSelect.val(inputSize)
      inputSizeSelect.on('change', onInputSizeChanged)
      inputSizeSelect.select()
    }
    // 일치율 증가
    function onIncreaseScoreThreshold() {
      scoreThreshold = Math.min(faceapi.utils.round(scoreThreshold + 0.1), 1.0)
      $('#scoreThreshold').val(scoreThreshold)
      updateResults()
    }
    // 일치율 감소
    function onDecreaseScoreThreshold() {
      scoreThreshold = Math.max(faceapi.utils.round(scoreThreshold - 0.1), 0.1)
      $('#scoreThreshold').val(scoreThreshold)
      updateResults()
    }
    // 최소 신뢰도 증가
    function onIncreaseMinConfidence() {
      minConfidence = Math.min(faceapi.utils.round(minConfidence + 0.1), 1.0)
      $('#minConfidence').val(minConfidence)
      updateResults()
    }
    // 최소 신뢰도 감소
    function onDecreaseMinConfidence() {
      minConfidence = Math.max(faceapi.utils.round(minConfidence - 0.1), 0.1)
      $('#minConfidence').val(minConfidence)
      updateResults()
    }



    // html-script
    // 업데이트 상태 표시
    function updateTimeStats(timeInMs) {
      forwardTimes = [timeInMs].concat(forwardTimes).slice(0, 30)
      const avgTimeInMs = forwardTimes.reduce((total, t) => total + t) / forwardTimes.length
      $('#time').val(`${Math.round(avgTimeInMs)} ms`)
      $('#fps').val(`${faceapi.utils.round(1000 / avgTimeInMs)}`)
    }
    // 박스 체인지
    function onChangeHideBoundingBoxes(e) {
      withBoxes = !$(e.target).prop('checked')
    }
    // 영상 재생
    async function onPlay() {
      const cam = document.getElementById('video-test')
      // const videoEl = $('#inputVideo').get(0)

      if(cam.paused || cam.ended || !isFaceDetectionModelLoaded())
        return setTimeout(() => onPlay())


      const options = getFaceDetectorOptions()

      const ts = Date.now()

      const result = await faceapi.detectSingleFace(cam, options).withFaceLandmarks()

      updateTimeStats(Date.now() - ts)

      if (result) {
        const canvas = $('#overlay').get(0)
        const dims = faceapi.matchDimensions(canvas, cam, true)
        const resizedResult = faceapi.resizeResults(result, dims)

        if (withBoxes) {
          faceapi.draw.drawDetections(canvas, resizedResult)
        }
        faceapi.draw.drawFaceLandmarks(canvas, resizedResult)
      }

      setTimeout(() => onPlay())
    }
    // 얼굴 인식 시작
    async function run() {
      // load face detection model
      await changeFaceDetector(TINY_FACE_DETECTOR)
      console.log(faceapi.nets)
      await faceapi.loadFaceLandmarkModel('https://storage.googleapis.com/cochlens/models/face_landmark_68_model-weights_manifest.json')
      changeInputSize(224)

      // try to access users webcam and stream the images
      // to the video element
      // const stream = await navigator.mediaDevices.getUserMedia({ video: {} })
      // const videoEl = $('#inputVideo').get(0)
      // videoEl.srcObject = stream
    }
    // 업데이트 결과
    function updateResults() {}


    function onCam() {
      const cam = document.getElementById('video-test')
      console.log('append')
      const fp = document.getElementById('face-position')
      fp.appendChild(cam)
      console.log('done')
      cam.addEventListener('play', onPlay())
    }

    function leaveRoom() {
      store.dispatch('courseStore/leaveLecture')
      router.push({ name: 'course' })
    }

    onMounted(() => {
      onPlay()
    })

    onPlay()
    initFaceDetectionControls()
    run()

    return {
      state,
      leaveRoom, onPlay, onCam,
      onIncreaseScoreThreshold,
      onDecreaseScoreThreshold,
      onIncreaseMinConfidence,
      onDecreaseMinConfidence,
      onChangeHideBoundingBoxes,
    }
  }
}
</script>

<style scoped>
#overlay {
  position: absolute;
  left: 0;
}

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
  background-color: gray;
  width: 100%;
  height: 77vh;
  overflow: hidden;
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