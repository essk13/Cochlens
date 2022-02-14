<template>
  <p class="profile-menu-title">내가 찜한 강좌</p>
  <div class="row">
    <div
      v-for="course in state.wishCourse"
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
    setup () {
    const state = reactive({
      wishCourse: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
    })
    return {
      state
    }
  },
}
</script>
<style lang="">
  
</style>