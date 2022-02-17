<template>
  <!-- 강사 헤더 -->
  <instructor-header></instructor-header>
  <!-- 검색 바 -->
  <div class="search">
    <div class="search-title">라이브 강좌</div>
  </div>
  <!-- 강사의 라이브 강좌 목록 -->
  <div class="contents q-px-xl q-mx-xl q-pb-xl">
    <div class="row justify-between q-gutter-xl">
      <course-item
        v-for="liveOpenCourse in state.liveOpenCourseList"
        :key="liveOpenCourse.courseId"
        :course-item="liveOpenCourse"
      >
      </course-item>
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
  name: 'InstructorLiveListView',
  components: {
    InstructorHeader,
    CourseItem,
  },

  setup() {
    const store = useStore()
    const state = reactive({
      liveOpenCourseList: store.state.instructorStore.instructorDetail.liveOpenCourseList
    })

    watchEffect(() => {
      state.liveOpenCourseList = store.state.instructorStore.instructorDetail.liveOpenCourseList
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