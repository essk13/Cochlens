<template>
  <div class="course-roof">
    <div class="profile-roof"></div>
    <div class="profile-roof-cover"></div>
    <div class="profile-img text-white"></div>
    <p class="course-detail-title">강쉡의 요리교실 후기</p>
    <p class="course-detail-instructor">강사 : 강태훈</p>
  </div>

  <!-- 리뷰 -->
  <div class="course-detail-upper-area q-mx-xl q-pb-xl">
    <div class="course-review-title">강좌 리뷰</div>
    <q-list bordered class="course-review-list rounded-borders">
      <course-review
        v-for="(reviewItem, index) in state.reviewList"
        :key="index"
        :course-review="reviewItem"
      ></course-review>
    </q-list>
  </div>
</template>

<script>
import { reactive } from '@vue/reactivity'
import CourseReview from '@/components/course/CourseReview'
import { useStore } from 'vuex'

export default {
  name: 'CourseReviewListView',
  components: {
    CourseReview,
  },

  setup() {
    const store = useStore()
    const state = reactive({
      reviewList: store.state.courseStore.courseData.reviewList,
    })

    return {
      state
    }
  }
}
</script>

<style scoped lang="scss">
.course-review-list {
  background-color: rgb(255, 255, 238);
}

.course-review-title {
  font-size: 32px;
  font-weight: bold;
  margin-bottom: 0.5vh;
}
</style>