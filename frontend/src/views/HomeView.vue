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
        <q-carousel-slide :name="1" img-src="https://res.klook.com/images/fl_lossy.progressive,q_65/c_fill,w_1295,h_971/w_80,x_15,y_15,g_south_west,l_klook_water/activities/xol1bfcqo2xvtciab6lp/%EB%B6%80%ED%82%B7%EB%B0%94%ED%86%A1%EA%BD%83%EA%BD%82%EC%9D%B4%EC%9B%90%EB%8D%B0%EC%9D%B4%ED%81%B4%EB%9E%98%EC%8A%A4.webp" />
        <q-carousel-slide :name="2" img-src="https://res.klook.com/images/fl_lossy.progressive,q_65/c_fill,w_1295,h_934/w_80,x_15,y_15,g_south_west,l_klook_water/activities/n3idrlfaqsfiyznbzfoy/%EA%B3%A0%EA%B8%89%EB%AF%B8%EC%88%A0%EC%9E%91%ED%92%88%EC%A0%9C%EC%9E%91%EC%9D%BC%EC%9D%BC%ED%81%B4%EB%9E%98%EC%8A%A4.webp" />
        <q-carousel-slide :name="3" img-src="https://post-phinf.pstatic.net/MjAyMDA2MjlfMTQz/MDAxNTkzNDEwNDg3MDMx.1eJup46_Aa9UByikXD7IkArDYpGbQitnFj0F-Hz6Xrcg.kJ4es3YEDEl3Y4JZLvIHi8tOiHCff7tLUJFNfAykz7Ig.PNG/%EC%9D%B4%EB%AF%B8%EC%A7%80_72.png?type=w1200" />
      </q-carousel>
    </div>

    <!-- 하단 컨텐츠 -->
    <div class="home-contents row q-py-sm q-px-xl">
      <div class="col-auto q-py-xl q-pl-xl full-height">
        <!-- 나의 수강 강좌 -->
        <div class="home-contents-block column">
          <div class="contents-header col-auto">
            <span class="contents-title">나의 수강 강좌</span>
            <span class="contents-more" @click="moveToTakingCourse">더보기></span>
          </div>
          <!-- 강좌 목록 -->
          <div class="my-course-list col column justify-between no-wrap">
            <taking-course
              v-for="takingCourse in state.takingCourseList"
              :key="takingCourse.courseId"
              :taking-course-item="takingCourse"
            >
            </taking-course>
          </div>
        </div>
      </div>
      <div class="col column full-height">
        <!-- 인기 강사 -->
        <div class="col-6 q-px-xl q-pt-xl q-pb-sm">
          <div class="home-contents-block column">
            <div class="contents-header col-auto">
              <span class="contents-title">인기 강사</span>
            </div>

              <!-- 인기 강사 목록 -->
            <div class="contents-body best-instructor-list col row justify-between shadow-2 no-wrap">
              <best-instructor
                v-for="bestInstructor in state.bestInstructorList"
                :key="bestInstructor.userId"
                :best-instructor="bestInstructor"
              >
              </best-instructor>
            </div>
          </div>
        </div>
        <!-- 인기 강좌 -->
        <div class="col-6 q-px-xl q-pb-xl q-pt-sm">
          <div class="home-contents-block column">
            <div class="contents-header col-auto">
              <span class="contents-title">인기 강좌</span>
            </div>
            <!-- 인기 강좌 목록 -->
            <div class="contents-body best-course-list col row justify-between shadow-2 no-wrap">
              <course-item
                v-for="bestCourse in state.bestCourseList"
                :key="bestCourse.courseId"
                :course-item="bestCourse"
              >
              </course-item>
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
import CourseItem from '@/components/course/CourseItem.vue'
import router from "@/router"
import { reactive } from '@vue/reactivity'
import { useStore } from 'vuex'
import { watchEffect } from 'vue'

export default {
  name: 'HomeView',
  components: {
    TakingCourse,
    BestInstructor,
    CourseItem,
  },

  setup() {
    // Created
    if (localStorage.getItem('JWT')) {
      console.log('login!')
    } else {
      router.push({ name: 'login' })
    }

    const store = useStore()
    const state = reactive({
      slide: 1,
      takingCourseList: store.state.profileStore.takingList,
      bestInstructorList: store.state.instructorStore.bestInstructorList,
      bestCourseList: store.state.courseStore.bestCourseList,
    })

    store.dispatch('getHomeData')

    watchEffect(() => {
      state.takingCourseList = store.state.profileStore.takingList
    })

    watchEffect(() => {
      state.bestInstructorList = store.state.instructorStore.bestInstructorList
    })

    watchEffect(() => {
      state.bestCourseList = store.state.courseStore.bestCourseList
    })

    function moveToTakingCourse() {
      router.push({ name: 'profile' })
    }

    return {
      state,
      moveToTakingCourse,
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
  cursor: pointer;
}

/* .my-course-list {

} */

.best-instructor-list {
  background-color: lightblue;
  padding: 4vh;
}

.best-course-list {
  background-color: rgb(187, 210, 255);
  padding: 4vh 4.5vh;
}

::v-deep {
  .my-course {
  width: 48vh;
  height: 11vh;
  }

  .course-item {
    width: 21.5vh;
    height: 100%;
    background-size: auto 100%;
  }
}
</style>