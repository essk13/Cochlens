<template>
  <p class="profile-menu-title">수강 중인 강좌</p>
  <div class="row">
    <div
      v-for="course in state.takingCourse"
      :key="course"
      class="col-6">
      <div class="profile-course-item">
      </div>
    </div>
  </div>
</template>
<script>
import { reactive } from '@vue/reactivity'

export default {
  name: 'TakingCourse',

  setup () {
    const state = reactive({
      takingCourse: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
    })
    return {
      state
    }
  },
}
</script>
<style>
  .profile-course-item{
    width: 94%;
    height: 150px;
    margin-bottom: 50px;
    border-radius: 5px;
    background: gray;
  }
</style>