<template>
  <div class="home">
    <!-- 상단 광고 -->
    <div class="home-advertisement shadow-4">
      <q-carousel
        animated
        v-model="state.slide"
        height="100%"
        arrows
        navigation
        infinite
      >
        <q-carousel-slide :name="1" img-src="https://cdn.quasar.dev/img/mountains.jpg" />
        <q-carousel-slide :name="2" img-src="https://cdn.quasar.dev/img/parallax1.jpg" />
        <q-carousel-slide :name="3" img-src="https://cdn.quasar.dev/img/parallax2.jpg" />
        <q-carousel-slide :name="4" img-src="https://cdn.quasar.dev/img/quasar.jpg" />
      </q-carousel>
    </div>

    <!-- 하단 컨텐츠 -->
    <div class="home-contents row q-py-sm q-px-xl">
      <div class="col-auto q-py-xl q-pl-xl full-height">
        <!-- 나의 수강 강좌 -->
        <div class="home-contents-block column">
          <div class="contents-header col-auto">
            <span class="contents-title">나의 수강 강좌</span>
            <span class="contents-more">더보기></span>
          </div>
          <!-- 강좌 목록 -->
          <div class="my-course-list col column justify-between no-wrap">
            <taking-course></taking-course>
            <taking-course></taking-course>
            <taking-course></taking-course>
            <taking-course></taking-course>
          </div>
        </div>
      </div>
      <div class="col column full-height">
        <!-- 인기 강사 -->
        <div class="col-6 q-px-xl q-pt-xl q-pb-sm">
          <div class="home-contents-block column">
            <div class="contents-header col-auto">
              <span class="contents-title">인기 강사</span>
              <span class="contents-more">더보기></span>
            </div>

              <!-- 인기 강사 목록 -->
            <div class="contents-body best-instructor-list col row justify-between shadow-2 no-wrap">
              <best-instructor></best-instructor>
              <best-instructor></best-instructor>
              <best-instructor></best-instructor>
              <best-instructor></best-instructor>
              <best-instructor></best-instructor>
            </div>
          </div>
        </div>
        <!-- 인기 강좌 -->
        <div class="col-6 q-px-xl q-pb-xl q-pt-sm">
          <div class="home-contents-block column">
            <div class="contents-header col-auto">
              <span class="contents-title">인기 강좌</span>
              <span class="contents-more">더보기></span>
            </div>
            <!-- 인기 강좌 목록 -->
            <div class="contents-body best-course-list col row justify-between shadow-2 no-wrap">
              <course class="course"></course>
              <course class="course"></course>
              <course class="course"></course>
              <course class="course"></course>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import TakingCourse from '@/components/profile/TakingCourse.vue'
import BestInstructor from '@/components/instructor/BestInstructor.vue'
import Course from '@/components/course/Course.vue'
import router from "@/router"
import { reactive } from '@vue/reactivity'

export default {
  name: 'HomeView',
  components: {
    TakingCourse,
    BestInstructor,
    Course,
  },

  setup() {
    // Created
    if (localStorage.getItem('JWT')) {
      console.log('login!')
    } else {
      router.push({ name: 'login' })
    }

    const state = reactive({
      slide: 1
    })

    return {
      state,
    }
  }
}
</script>

<style scoped lang="scss">
.home-advertisement {
  height: 30vh;
}

.home-advertisement-carousel {
  border-radius: 24px;
}

.home-contents {
  height: 70vh;
}

.home-contents-block {
  height: 100%;
}

.contents-header {
  margin-bottom: 0.5vw;
}

.contents-body {
  background-color: lightgray;
  height: 100%;
  border-radius: 0.3vw;
}

.contents-title {
  font-size: 2.2vh;
  font-weight: bold;
}

.contents-more {
  font-size: 1.2vh;
  color: blue;
  margin-left: 0.4vw;
}

/* .my-course-list {

} */

.best-instructor-list {
  padding: 4vh;
}

.best-course-list {
  padding: 4vh 4.5vh;
}

::v-deep {
  .my-course {
  width: 48vh;
  height: 11vh;
  }

  .course {
    width: 21.5vh;
    height: 100%;
    background-size: auto 100%;
  }
}
</style>