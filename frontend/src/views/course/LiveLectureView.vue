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
      <video id="detection" width="400" height="400" autoplay muted></video>
      <span class="main-screen-subtitles">이것은 자막입니다. 이것은 자막입니다.</span>
    </div>
    <div v-else class="all-screen-list-block">

    </div>
    <div class="menu-block row justify-between">
      <div class="col-1 row justify-center items-center">
        <img style="width: 4vh; height: 4vh; border-radius: 4vh;" src="https://cdn.quasar.dev/img/cat.jpg" />
      </div>
      <div class="col-2 row justify-between items-center">
        <img @click="startVideo" style="width: 4vh; height: 4vh; border-radius: 4vh;" src="https://cdn.quasar.dev/img/cat.jpg" />
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

    function leaveRoom() {
      store.dispatch('courseStore/leaveLecture')
      router.push({ name: 'course' })
      const video = document.getElementById('detection')
      video.srcObject = null
    }

    function startVideo() {
      const video = document.getElementById('detection')
      navigator.getUserMedia(
        { video: {} },
        stream => video.srcObject = stream,
        err => console.error(err)
      )
      console.log('video start')
      console.log(video)
      addEvent()
    }

    function addEvent() {
      const video = document.getElementById('detection')
      video.addEventListener('play', () => {
        console.log('add event')
        const canvas = faceapi.createCanvasFromMedia(video)
        document.body.append(canvas)
        const displaySize = { width: video.width, height: video.height }
        faceapi.matchDimensions(canvas, displaySize)
        setInterval(async () => {
          const detections = await faceapi.detectAllFaces(video, new faceapi.TinyFaceDetectorOptions()).withFaceLandmarks().withFaceExpressions()
          const resizedDetections = faceapi.resizeResults(detections, displaySize)
          canvas.getContext('2d').clearRect(0, 0, canvas.width, canvas.height)
          faceapi.draw.drawDetections(canvas, resizedDetections)
          faceapi.draw.drawFaceLandmarks(canvas, resizedDetections)
        }, 100)
      })
    }

    function load() {
      Promise.all([
        faceapi.nets.ssdMobilenetv1.loadFromUri('/models')
      ]).then(console.log('success')).catch(console.log('err'))
      console.log('nets')
      console.log(faceapi.nets)
    }

    load()

    return {
      state,
      leaveRoom, load, startVideo
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

canvas {
  position: absolute;
}
</style>