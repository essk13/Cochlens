<template>
  <div class="course-roof">
    <div class="course-roof-img" id="course-roof"></div>
    <div class="profile-roof-cover"></div>
    <div class="course-img text-white" id="course-img"></div>
    <p class="course-detail-title">{{ state.courseData.courseName }}</p>
    <p class="course-detail-instructor">강사 : {{ state.courseData.instructorName }}</p>
  </div>

  <div class="row course-detail-upper-area">
    <div class="col-7">
      <!-- 강좌 소개 -->
      <div class="q-mb-xl">
        <p class="profile-menu-title">강좌 소개</p>
        <p>
          {{ state.courseData.courseDescription }}
        </p>
      </div>

      <!-- 커리큘럼 -->
      <div>
        <div class="row justify-between item-center content-center">
          <p class="profile-menu-title q-mb-none">커리큘럼</p>
          <q-btn @click="clickCreate" color="blue" label="강의 생성" dense />
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
          <q-btn @click="joinLecture" color="purple">강의 시작하기</q-btn>
        </div>
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
            <div class="text-subtitle2">시작: {{ state.courseData.courseOpenDate }} / 종료: {{ state.courseData.courseCloseDate }}</div>
          </q-card-section>

          <q-card-actions align="around">
            <q-btn v-if="state.courseData.isJoin" @click="clickRegister" flat>신청하기</q-btn>
            <q-bt v-else @click="clickDeregister" flat>수강중인 강좌</q-bt>
            <q-btn v-if="state.courseData.isWish" @click="clickWish" flat>찜 하기</q-btn>
            <q-bt v-else @click="clickUnwish" flat>찜한 강좌</q-bt>
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
          <q-list v-if="state.courseData.reviewList" bordered class="course-review-preview rounded-borders">
            <course-review
              v-for="(reviewItem, index) in state.courseData.reviewList"
              :key="index"
              :review-item = "reviewItem"
            ></course-review>
          </q-list>
          <div v-else>리뷰가 없습니다.</div>
        </q-card>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive } from '@vue/reactivity'
import { useStore } from 'vuex'
import { useRoute, useRouter } from 'vue-router'
import CourseReview from '@/components/course/CourseReview'
import { onMounted } from '@vue/runtime-core'

export default {
  name: 'CourseDetailView',
  components: {
    CourseReview,
  },
  setup() {
    const store = useStore()
    const router = useRouter()
    const route = useRoute()
    const url = 'wss://' + location.host + '/groupcall'
    store.dispatch('courseStore/setWs', url)

    console.log(store.state.courseStore.courseData)

    const state = reactive({
      name: 'test',
      room: '',
      courseData: store.state.courseStore.courseData,
    })

    // Mounted
    onMounted(() => {
      const courseImg = document.getElementById('profile-img')
      const courseRoofImg = document.getElementById('profile-roof')
      if (state.courseData.instructorImg) {
        courseImg.style.backgroundImage = `url(${state.courseData.instructorImg})`
      } else {
        courseImg.style.backgroundImage = `url('/img/kang.968d430f.png')`
      }

      if (state.courseData.courseThumbnail) {
        courseRoofImg.style.backgroundImage = `url(${state.courseData.courseThumbnail})`
      } else {
        courseRoofImg.style.backgroundImage = `url('/img/cooking_roof.c34b6973.jpg')`
      }
    })

    // Function
    function moveCourseReview() {
      router.push({ name: 'courseReviewList', params: { courseId: route.params.courseId } })
    }

    function clickRegister() {
      state.courseData.isJoin = true
      store.dispatch('registerCourse')
    }

    function clickDeregister() {
      state.courseData.isJoin = false
      store.dispatch('deregisterCourse')
    }

    function clickWish() {
      state.courseData.isWish = true
      store.dispatch('wishCourse')
    }

    function clickUnwish() {
      state.courseData.isWish = false
      store.dispatch('unwishCourse')
    }

    function createLecture() {
      store.dispatch('createLecture', {})
    }

    function startLecture() {
      let message = {
        id : 'joinRoom',
        name : 'Instructor',
        room : 'testroom',
      }
      store.dispatch('courseStore/register', message)
      router.push({ name: 'liveLecture', params: { courseId: route.params.courseId, lectureId: '001' } })
    }
    
    function joinLecture() {
      let message = {
        id : 'joinRoom',
        name : state.name,
        room : 'testroom',
      }
      store.dispatch('courseStore/register', message)
      router.push({ name: 'liveLecture', params: { courseId: route.params.courseId, lectureId: '001' } })
    }

    return {
      state, url,
      clickRegister,
      clickDeregister,
      clickWish,
      clickUnwish,
      createLecture,
      startLecture,
      joinLecture,
      moveCourseReview,
    }
  }
}
</script>

<style>
.course-roof {
  position: sticky;
  top: 0;
}

.course-roof-img {
  width: 100%;
  height: 180px;
  background-image: url('@/assets/cooking_roof.jpg');
  background-size: cover;
  background-position: 20%;
}

.course-img {
  width: 150px;
  height: 150px;
  border-radius: 50px;
  background-image: url('@/assets/kang.png');
  background-size: cover;
  position: absolute;
  z-index: 3;
  top: 60px;
  left: 60px;
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

.course-review-preview {
  background-color: rgb(255, 255, 238);
}
</style>