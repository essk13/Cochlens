<template>
  <instructor-header></instructor-header>
  <div class="search">
    <div class="search-title">전체 강좌</div>
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
  <div class="contents q-px-xl q-mx-xl q-pb-xl">
    <div class="row justify-between q-gutter-xl">
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
      <course></course>
    </div>
  </div>
</template>

<script>
import { reactive } from '@vue/reactivity'
import InstructorHeader from "@/components/instructor/InstructorHeader"
import Course from '@/components/course/Course'

export default {
  name: 'InstructorCourseListView',
  components: {
    InstructorHeader,
    Course,
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
  .course {
    background-size: auto 100%;
    width: 26vh;
    height: 18vh;
  }
}
</style>