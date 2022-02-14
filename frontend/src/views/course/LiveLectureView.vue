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
      <span class="main-screen-subtitles">{{ state.res }}</span>
    </div>
    <div v-else class="all-screen-list-block">

    </div>
    <div class="menu-block row justify-between">
      <div class="col-1 row justify-center items-center">
        <img style="width: 4vh; height: 4vh; border-radius: 4vh;" src="https://cdn.quasar.dev/img/cat.jpg" />
      </div>
      <div class="col-2 row justify-between items-center">
        <img style="width: 4vh; height: 4vh; border-radius: 4vh;" src="https://cdn.quasar.dev/img/cat.jpg" />
        <img @click="init" style="width: 4vh; height: 4vh; border-radius: 4vh;" src="https://cdn.quasar.dev/img/cat.jpg" />
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
    let webcam, model, maxPredictions
    const state = reactive({
      screenSlide: 1,
      isMainScreen: true,
      res: '',
    })

    // Mounted
    onMounted(() => {
      init()
    })

    // Function
    function leaveRoom() {
      store.dispatch('courseStore/leaveLecture')
      router.push({ name: 'courseDetail', params: { courseId: route.params.courseId } })
    }

    async function init() {
      const modelURL = URL + "model.json"
      const metadataURL = URL + "metadata.json"

      console.log('start')
      console.log(modelURL)
      console.log(metadataURL)
      model = await window.tmImage.load(modelURL, metadataURL)
      maxPredictions = model.getTotalClasses()

      console.log('start2')
      const flip = true; // whether to flip the webcam
      webcam = new window.tmImage.Webcam(200, 200, flip);
      await webcam.setup(); // request access to the webcam
      await webcam.play();
      console.log('start3')
      window.requestAnimationFrame(loop);
      console.log('start-done')
    }

    async function loop() {
      console.log('loop')
      webcam.update()
      await predict();
      window.requestAnimationFrame(loop);
    }

    async function predict() {
      console.log('check')
      // predict can take in an image, video or canvas html element
      const prediction = await model.predict(webcam.canvas);
      console.log('check2')
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
      state, URL,
      leaveRoom, init, predict
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
</style>