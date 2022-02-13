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
          <div id="participants" class="row fit q-gutter-xs q-col-gutter no-wrap">
            <canvas id="overlay" />
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
        <div style="position: relative" class="margin" id="main-position">
          <canvas id="face-video"></canvas>
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
        <img style="width: 4vh; height: 4vh; border-radius: 4vh;" src="https://cdn.quasar.dev/img/cat.jpg" />
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
import { watch, computed, onMounted } from '@vue/runtime-core'

export default {
  name: 'LiveLectureView',
  components: {
  },

  setup() {
    const store = useStore()
    const state = reactive({
      screenSlide: 1,
      isMainScreen: true,
      face_width: null,
      face_height: null,
      face_x: null,
      face_y: null
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
    // let withBoxes = true

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
    // function onChangeHideBoundingBoxes(e) {
    //   withBoxes = !$(e.target).prop('checked')
    // }
    // 영상 재생
    async function onPlay() {
      const cam = document.getElementById('video-test')

      if(cam.paused || cam.ended || !isFaceDetectionModelLoaded())
        return setTimeout(() => onPlay())


      const options = getFaceDetectorOptions()

      const ts = Date.now()

      const result = await faceapi.detectSingleFace(cam, options)

      updateTimeStats(Date.now() - ts)

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
      // load face detection model
      await changeFaceDetector(TINY_FACE_DETECTOR)
      changeInputSize(224)
    }
    // 업데이트 결과
    function updateResults() {}


    // local-script
    // Watch
    watch(
      computed(() => store.state.courseStore.isJoin),
      (newJoin, oldJoin) => {
        console.log('new' + newJoin, 'old' + oldJoin)
        if (newJoin) {
          onCam()
          onPlay()
        }
    })

    // Function
    function doLoad() {
      const video = document.getElementById("video-test")
      video.addEventListener("play", computeFrame())
    }
    // 화면 캡처
    function computeFrame() {
      const video = document.getElementById("video-test")
      const width = state.face_width;
      const height = state.face_height;
      const canv = document.getElementById("face-video")
      canv.style.width = `${state.face_width * 4}px`
      canv.style.height = `${state.face_height * 4}px`
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
      const cam = document.getElementById('video-test')
      cam.addEventListener('onloadedmetadata', onPlay())
      document.addEventListener("DOMContentLoaded", doLoad())
    }

    function leaveRoom() {
      store.dispatch('courseStore/leaveLecture')
      router.push({ name: 'course' })
    }

    onMounted(() => {
      setTimeout(() => { onCam(), onPlay() }, 1000 )
    })

    initFaceDetectionControls()
    run()

    return {
      state,
      leaveRoom, onPlay, onCam,
      onIncreaseScoreThreshold,
      onDecreaseScoreThreshold,
      onIncreaseMinConfidence,
      onDecreaseMinConfidence,
    }
  }
}
</script>

<style scoped>
#overlay {
  position: absolute;
  left: 0;
}

.face-video {
  width: 200px;
  height: 200px;
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