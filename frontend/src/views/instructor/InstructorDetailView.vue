<template>
  <!-- 강사 헤더 -->
  <div class="header">
    <instructor-header></instructor-header>
  </div>
  <!-- 강사 정보 -->
  <div class="contents column q-px-xl q-mx-xl q-pb-xl">
    <div class="col-4 row q-pb-sm">
      <!-- 강사 소개 -->
      <div class="instructor-description-block col-6 column q-pr-lg">
        <div class="contents-header col-auto">
          <span class="text-h6 text-bold">강사 소개</span>
        </div>
        <div class="col">
          <div>{{ state.instructorDetail.userDescription }}</div>
        </div>
      </div>
      <!-- 강사 리뷰 -->
      <div class="instructor-review-block col-6 column q-pl-lg">
        <div class="contents-header col-auto">
          <span class="contents-title">수강후기</span>
          <span class="contents-more" @click="moveToReviewList">더보기></span>
        </div>
        <div class="instructor-review-list col">
          <q-list bordered class="rounded-borders">
            <!-- <course-review
              v-for="courseReview in state.instructorReviewList"
              :key="courseReview.reviewId"
              :course-review="courseReview"
            >
            </course-review> -->
            <course-review></course-review>
            <course-review></course-review>
            <course-review></course-review>
            <course-review></course-review>
          </q-list>
        </div>
      </div>
    </div>
    <!-- 강사의 라이브 강좌 -->
    <div class="in-progress-course-block col-4 column q-py-sm">
      <div class="contents-header col-auto">
        <span class="contents-title">진행중인 강좌</span>
        <span class="contents-more" @click="moveToLiveCourseList">더보기></span>
      </div>
      <div class="in-progress-course-list col row justify-around items-center no-wrap">
        <!-- <course-item
          v-for="liveOpenCourse in state.liveOpenCourseList"
          :key="liveOpenCourse.courseId"
          :course-item="liveOpenCourse"
        >
        </course-item> -->
        <course-item></course-item>
        <course-item></course-item>
        <course-item></course-item>
        <course-item></course-item>
        <course-item></course-item>
      </div>
    </div>
    <!-- 강사의 전체 강좌 -->
    <div class="all-course-block col-4 column q-py-sm">
      <div class="contents-header col-auto">
        <span class="contents-title">전체 강좌</span>
        <span class="contents-more" @click="moveToVodCourseList">더보기></span>
      </div>
      <div class="all-course-list col row justify-around items-center no-wrap">
        <!-- <course-item
          v-for="vodOpenCourse in state.vodOpenCourseList"
          :key="vodOpenCourse.courseId"
          :course-item="vodOpenCourse"
        >
        </course-item> -->
        <course-item></course-item>
        <course-item></course-item>
        <course-item></course-item>
        <course-item></course-item>
        <course-item></course-item>
      </div>
    </div>
  </div>
  <q-btn :label="state.dialogText" color="primary" @click="state.isDialog = true" />
  <div>{{ state.date }}</div>
  <q-dialog v-model="state.isDialog" persistent>
    <q-card style="min-width: 350px">
      <q-card-section>
        <div class="text-h6">강의 생성</div>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <q-input dense v-model="state.dialogText" autofocus @keyup.enter="prompt = false" />
        <div class="q-gutter-md row items-start">
          <q-date v-model="state.date" mask="YYYY-MM-DD HH:mm" color="purple" />
          <q-time v-model="state.date" mask="YYYY-MM-DD HH:mm" color="purple" />
        </div>
      </q-card-section>

      <q-card-actions align="right" class="text-primary">
        <q-btn flat label="강의 생성" v-close-popup @click="createLecture" />
        <q-btn flat label="취소" v-close-popup />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script>
import InstructorHeader from "@/components/instructor/InstructorHeader"
import CourseItem from '@/components/course/CourseItem'
import CourseReview from '@/components/course/CourseReview'
import { reactive } from '@vue/reactivity'
import { useStore } from 'vuex'
import { useRouter, useRoute } from 'vue-router'
import { watchEffect } from 'vue'

export default {
  name: 'InstructorDetailView',
  components: {
    InstructorHeader,
    CourseItem,
    CourseReview,
  },

  setup() {
    const store = useStore()
    const router = useRouter()
    const route = useRoute()
    let today = new Date()
    const state = reactive({
      instructorDetail: store.state.instructorStore.instructorDetail,
      instructorReviewList: store.state.instructorStore.instructorDetail.courseReviewList.slice(0, 4),
      liveOpenCourseList: store.state.instructorStore.instructorDetail.liveOpenCourseList.slice(0, 5),
      vodOpenCourseList: store.state.instructorStore.instructorDetail.vodOpenCourseList.slice(0, 5),
      isDialog: false,
      dialogText: 'click',
      date: `${today.getFullYear()}-${(today.getMonth() + 1).toString().padStart(2, '0')}-${today.getDate().toString().padStart(2, '0')} ${today.getHours().toString().padStart(2, '0')}:${today.getMinutes().toString().padStart(2, '0')}`,
    })

    // 수강후기로 이동
    function moveToReviewList() {
      router.push({ name: 'instructorReviewList', params: { instructorId: route.params.instructorId }})
    }

    // 라이브 강좌로 이동
    function moveToLiveCourseList() {
      router.push({ name: 'instructorLiveList', params: { instructorId: route.params.instructorId }})
    }

    // 전체 강좌로 이동
    function moveToVodCourseList() {
      router.push({ name: 'instructorCourseList', params: { instructorId: route.params.instructorId }})
    }

    function createLecture() {
      console.log('createLecture test')
      console.log('----------------------------------------')
      console.log(state.dialogText)
      console.log(state.date)
      console.log('----------------------------------------')
    }

    watchEffect(() => {
      state.instructorDetail = store.state.instructorStore.instructorDetail
    })

    watchEffect(() => {
      state.instructorReviewList = store.state.instructorStore.instructorDetail.courseReviewList.slice(0, 4)
    })

    watchEffect(() => {
      state.liveOpenCourseList = store.state.instructorStore.instructorDetail.liveOpenCourseList.slice(0, 5)
    })

    watchEffect(() => {
      state.vodOpenCourseList = store.state.instructorStore.instructorDetail.vodOpenCourseList.slice(0, 5)
    })

    return {
      state,
      moveToReviewList,
      moveToLiveCourseList,
      moveToVodCourseList,
      createLecture
    }
  }
}
</script>

<style scoped lang="scss">
.header {
  height: 23vh;
}

.contents {
  height: 77vh;
}

.contents-header {
  margin-bottom: 0.5vh;
}

.contents-title {
  font-size: 1.5vh;
  font-weight: bold;
}

.contents-more {
  font-size: 1.2vh;
  color: blue;
  margin-left: 0.4vw;
  cursor: pointer;
}

.instructor-review-list {
  background-color: rgb(255, 255, 238);
  border-radius: 0.5vh;
}

.in-progress-course-list {
  background-color: lightgoldenrodyellow;
  border-radius: 0.5vh;
}

.all-course-list {
  background-color: lightblue;
  border-radius: 0.5vh;
}

::v-deep {
  .course-item {
    background-size: auto 100%;
    width: 24vh;
    height: 16vh;
  }
}
</style>