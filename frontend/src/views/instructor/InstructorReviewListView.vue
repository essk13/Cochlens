<template>
  <!-- 강사 헤더 -->
  <instructor-header></instructor-header>
  <!-- 강사 리뷰 타이틀 -->
  <div class="header">
    <div class="instructor-review-title">강사 리뷰</div>
  </div>
  <!-- 강사 리뷰 목록 -->
  <div class="contents q-mx-xl q-pb-xl">
    <q-list bordered class="instructor-review-list rounded-borders q-mx-xl">
      <course-review></course-review>
      <course-review></course-review>
      <course-review></course-review>
      <course-review></course-review>
      <course-review></course-review>
      <course-review></course-review>
      <course-review></course-review>
      <course-review></course-review>
      <course-review></course-review>
      <course-review></course-review>
      <course-review></course-review>
      <course-review></course-review>
      <course-review></course-review>
      <course-review></course-review>
      <course-review></course-review>
      <course-review></course-review>
      <course-review></course-review>
      <course-review></course-review>
      <course-review></course-review>
      <course-review></course-review>
    </q-list>
  </div>
</template>

<script>
import { reactive } from '@vue/reactivity'
import InstructorHeader from "@/components/instructor/InstructorHeader"
import CourseReview from '@/components/course/CourseReview'

export default {
  name: 'InstructorReviewListView',
  components: {
    InstructorHeader,
    CourseReview,
  },

  setup() {
    const state = reactive({
    })

    return {
      state
    }
  }
}
</script>

<style scoped lang="scss">
.header {
  height: 120px;
  padding: 50px 9vh 0.5vh 9vh;
}

.contents {
  border-radius: 0.5vh;
}

.instructor-review-list {
  background-color: rgb(255, 255, 238);
}

.instructor-review-title {
  font-size: 32px;
  font-weight: bold;
  margin-bottom: 0.5vh;
}

::v-deep {

}
</style>