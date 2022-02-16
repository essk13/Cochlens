<template>
  <!-- 강사 헤더 -->
  <instructor-header></instructor-header>
  <!-- 검색 바 -->
  <div class="search">
    <div class="search-title">전체 강좌</div>
  </div>
  <!-- 강사의 전체 강좌 목록 -->
  <div class="q-px-xl q-mx-xl q-pb-xl">
    <div class="row justify-between q-gutter-xl">
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
      <course-item></course-item>
      <course-item></course-item>
      <course-item></course-item>
      <course-item></course-item>
      <course-item></course-item>
      <course-item></course-item>
      <course-item></course-item>
      <course-item></course-item>
      <course-item></course-item>
      <course-item></course-item>
      <course-item></course-item>
      <course-item></course-item>
      <course-item></course-item>
      <course-item></course-item>
      <course-item></course-item>
      <course-item></course-item>
      <course-item></course-item>
      <course-item></course-item>
      <course-item></course-item>
      <course-item></course-item>
      <course-item></course-item>
      <course-item></course-item>
      <course-item></course-item>
      <course-item></course-item>
      <course-item></course-item>
      <course-item></course-item>
      <course-item></course-item>
      <course-item></course-item>
      <course-item></course-item>
      <course-item></course-item>
    </div>
  </div>
</template>

<script>
import InstructorHeader from "@/components/instructor/InstructorHeader"
import CourseItem from '@/components/course/CourseItem'
import { reactive } from '@vue/reactivity'
import { useStore } from 'vuex'
import { watchEffect } from 'vue'

export default {
  name: 'InstructorCourseListView',
  components: {
    InstructorHeader,
    CourseItem,
  },

  setup() {
    const store = useStore()
    const state = reactive({
      vodOpenCourseList: store.state.instructorStore.instructorDetail.vodOpenCourseList
    })

    watchEffect(() => {
      state.vodOpenCourseList = store.state.instructorStore.instructorDetail.vodOpenCourseList
    })

    return {
      state
    }
  }
}
</script>

<style scoped lang="scss">
.search {
  height: 120px;
  padding: 50px 9vh 0.5vh 9vh;
}

.search-title {
  font-size: 32px;
  font-weight: bold;
  margin-bottom: 0.5vh;
}

.search-bar {

}

::v-deep {
  .course-item {
    background-size: auto 100%;
    width: 26vh;
    height: 18vh;
  }
}
</style>