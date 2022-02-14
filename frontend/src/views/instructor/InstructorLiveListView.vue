<template>
  <!-- 강사 헤더 -->
  <instructor-header></instructor-header>
  <!-- 검색 바 -->
  <div class="search">
    <div class="search-title">라이브 강좌</div>
    <q-input
      outlined
      bottom-slots
      v-model="text"
      label="찾으시는 강좌를 입력하세요."
      counter
      maxlength="30"
      :dense="state.searchDense"
      class="search-bar"
    >
      <template v-slot:append>
        <q-icon v-if="state.searchText !== ''" name="close" @click="state.searchText = ''" class="cursor-pointer" />
        <q-icon name="search" />
      </template>
    </q-input>
  </div>
  <!-- 강사의 라이브 강좌 목록 -->
  <div class="contents q-px-xl q-mx-xl q-pb-xl">
    <div class="row justify-between q-gutter-xl">
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
import { reactive } from '@vue/reactivity'
import InstructorHeader from "@/components/instructor/InstructorHeader"
import CourseItem from '@/components/course/CourseItem'

export default {
  name: 'InstructorLiveListView',
  components: {
    InstructorHeader,
    CourseItem,
  },

  setup() {
    const state = reactive({
      searchText: '',
      searchDense: true,
    })

    return {
      state
    }
  }
}
</script>

<style scoped lang="scss">
.search {
  height: 180px;
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