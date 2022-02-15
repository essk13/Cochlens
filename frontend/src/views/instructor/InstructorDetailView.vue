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
        <span class="contents-more">더보기></span>
      </div>
      <div class="in-progress-course-list col row justify-around items-center no-wrap">
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
        <span class="contents-more">더보기></span>
      </div>
      <div class="all-course-list col row justify-around items-center no-wrap">
        <course-item></course-item>
        <course-item></course-item>
        <course-item></course-item>
        <course-item></course-item>
        <course-item></course-item>
      </div>
    </div>
  </div>
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
    const state = reactive({
      instructorDetail: store.state.instructorStore.instructorDetail,
    })

    // 수강후기로 이동
    function moveToReviewList() {
      router.push({ name: 'instructorReviewList', params: { instructorId: route.params.instructorId }})
    }

    watchEffect(() => {
      state.instructorDetail = store.state.instructorStore.instructorDetail
    })

    return {
      state,
      moveToReviewList
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