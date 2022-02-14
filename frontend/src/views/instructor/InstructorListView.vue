<template>
  <div class="header">
    <!-- 강사 검색 -->
    <q-input
      outlined
      bottom-slots
      v-model="text"
      label="찾으시는 강사를 입력하세요."
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
  <!-- 인기 강사 -->
  <div class="top-instructor-block column shadow-2">
    <div class="col-auto text-bold q-pb-sm">인기강사 Top</div>
    <div class="col top-instructor-list row justify-between no-wrap">
      <instructor></instructor>
      <instructor></instructor>
      <instructor></instructor>
      <instructor></instructor>
    </div>
  </div>
  <!-- 강사 목록 -->
  <div class="instructor-block column justify-around">
    <div class="instructor-list col row justify-between no-wrap">
      <instructor></instructor>
      <instructor></instructor>
      <instructor></instructor>
      <instructor></instructor>
    </div>
    <div class="instructor-list col row justify-between no-wrap">
      <instructor></instructor>
      <instructor></instructor>
      <instructor></instructor>
      <instructor></instructor>
    </div>
    <div class="instructor-list col row justify-between no-wrap">
      <instructor></instructor>
      <instructor></instructor>
      <instructor></instructor>
      <instructor></instructor>
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
import Instructor from '@/components/instructor/Instructor'

export default {
  name: 'InstructorListView',
  components: {
    Instructor,
  },

  setup() {
    const state = reactive({
      searchText: '',
      searchDense: true,
      paginationCurrent: 1,
    })

    return {
      state
    }
  }
}
</script>

<style scoped>
.header {
  height: 9vh;
}

.search-bar {
  padding: 2.5vh 10vh;
}

.top-instructor-block {
  background-color: rgb(187, 210, 255);
  height: 23vh;
  padding: 2vh 10vh 3vh 10vh;
}

.top-instructor-list {

}

.top-instructor {

}

.instructor-block {
  height: 60vh;
  padding: 3.5vh 10vh 0vh 10vh;
}

.instructor-list {
  height: 100%;
}

.pagination-block {
  height: 8vh;
}
</style>