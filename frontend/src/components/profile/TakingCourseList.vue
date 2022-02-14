<template>
  <p class="profile-menu-title">수강 중인 강좌</p>
  <div v-if="state.takingCourse" class="row">
    <div
      v-for="course in state.takingCourse"
      :key="course"
      class="col-6">
      <div class="profile-course-item">
      </div>
    </div>
  </div>
  <div v-else>수강 중인 강좌가 없습니다.</div>
</template>
<script>
import { reactive } from '@vue/reactivity'
import { useStore } from 'vuex'

export default {
  name: 'TakingCourseList',

  setup () {
    const store = useStore()
    const state = reactive({
      takingCourse: store.state.profileStore.takingList
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