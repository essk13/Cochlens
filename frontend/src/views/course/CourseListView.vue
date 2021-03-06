<template>
  <div class="header">
    <!-- 강좌 검색 -->
    <q-input
      outlined
      bottom-slots
      v-model="state.searchText"
      label="찾으시는 강좌를 입력하세요."
      counter
      maxlength="30"
      :dense="state.searchDense"
      class="search-bar"
      @keyup.enter="searchCourse"
    >
      <template v-slot:append>
        <q-icon v-if="state.searchText !== ''" name="close" @click="state.searchText = ''" class="cursor-pointer" />
        <q-icon name="search" />
      </template>
    </q-input>
  </div>
  <!-- 인기 강좌 -->
  <div class="top-course-block column shadow-2">
    <div class="col-auto text-bold q-pb-sm">인기강좌 Top</div>
    <div class="col top-course-list row justify-between no-wrap">
      <course-item
        v-for="course in state.bestCourseList"
        :key="course.courseId"
        :course-item="course"
      ></course-item>
    </div>
  </div>
  <!-- 강좌 목록 -->
  <div class="course-block column justify-around">
    <div class="course-list col row justify-between no-wrap">
      <course-item
        v-for="course in state.courseList.slice(0, 5)"
        :key="course.courseId"
        :course-item="course"
      ></course-item>
    </div>
    <div class="course-list col row justify-between no-wrap">
      <course-item
        v-for="course in state.courseList.slice(5, 10)"
        :key="course.courseId"
        :course-item="course"
      ></course-item>
    </div>
    <div class="course-list col row justify-between no-wrap">
      <course-item
        v-for="course in state.courseList.slice(10, 15)"
        :key="course.courseId"
        :course-item="course"
      ></course-item>
    </div>
  </div>
  <!-- pagination -->
  <div class="pagination-block q-pa-lg flex flex-center">
    <q-pagination
      v-model="state.paginationCurrent"
      :max="15"
      :max-pages="5"
      :ellipses="false"
      :boundary-numbers="false"
      direction-links
    />
  </div>
</template>

<script>
import { reactive } from '@vue/reactivity'
import CourseItem from '@/components/course/CourseItem'
import { useStore } from 'vuex'
import { watchEffect } from '@vue/runtime-core'

export default {
  name: 'CourseListView',
  components: {
    CourseItem,
  },
  setup() {
    const store = useStore()
    const state = reactive({
      searchText: '',
      searchResult: '',
      searchDense: true,
      paginationCurrent: 1,
      courseList: store.state.courseStore.courseList,
      bestCourseList : store.state.courseStore.bestCourseList,
    })

    // Created
    store.dispatch('courseStore/getBestCourseList')
    store.dispatch('courseStore/getCourseList', 1)

    // Watch
    watchEffect(() => {
      state.courseList = store.state.courseStore.courseList
    })
    watchEffect(() => {
      state.bestCourseList = store.state.courseStore.bestCourseList
    })

    watchEffect(() => {
      store.dispatch('courseStore/searchCourse', { text: state.searchResult, page: state.paginationCurrent })
    })

    // Function
    // 강좌 검색
    function searchCourse() {
      state.searchResult = state.searchText
      state.paginationCurrent = 1
    }

    return {
      state,
      searchCourse,
    }
  }
}
</script>

<style scoped lang="scss">
.header {
  height: 9vh;
}

.search-bar {
  padding: 2.5vh 10vh;
}

.top-course-block {
  background-color: rgb(187, 210, 255);
  height: 23vh;
  padding: 2vh 10vh 3vh 10vh;
}

.course-block {
  height: 60vh;
  padding: 3.5vh 10vh 0vh 10vh;
}

.course-list {
  height: 100%;
}

.pagination-block {
  height: 8vh;
}

::v-deep {
  .course-item {
    background-size: 100% auto;
    width: 24vh;
    height: 16vh;
  }
}
</style>