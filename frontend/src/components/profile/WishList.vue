<template>
  <p class="profile-menu-title">내가 찜한 강좌</p>
  <div v-if="state.wishList" class="row">
    <div
      v-for="course in state.wishCourse"
      :key="course.courseId"
      class="col-6">
      <div class="profile-course-item">
      </div>
    </div>
  </div>
  <div v-else>찜한 강좌가 없습니다.</div>
</template>
<script>
import { reactive } from '@vue/reactivity'
import { useStore } from 'vuex'

export default {
    setup () {
      const store = useStore()
      const state = reactive({
        wishList: store.state.courseStore.wishList
      })
    return {
      state
    }
  },
}
</script>
<style lang="">
  
</style>