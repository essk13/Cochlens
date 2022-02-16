<template>
  <div class="course-roof">
    <div class="profile-roof"></div>
    <div class="profile-roof-cover"></div>
    <div class="profile-img"></div>
    <div class="instructor-description text-white">
      <h6 class="q-mb-sm q-mt-none text-bold">{{ state.instructorDetail.userNickname }}</h6>
      <div>이메일 : {{ state.instructorDetail.email }}</div>
      <div>강좌 수 : {{ state.instructorDetail.courseCount }}</div>
      <div>별점 : ★{{ state.instructorDetail.courseReviewRateAverage }} ({{ state.instructorDetail.courseReviewCount }})</div>
    </div>
  </div>
</template>

<script>
import { reactive } from '@vue/reactivity'
import { useStore } from 'vuex'
import { watchEffect } from 'vue'

export default {
  name: 'InstructorHeader',
  components: {
  },

  setup() {
    const store = useStore()
    const state = reactive({
      instructorDetail: store.state.instructorStore.instructorDetail,
    })

    watchEffect(() => {
      state.instructorDetail = store.state.instructorStore.instructorDetail
    })

    return {
      state
    }
  }
}
</script>

<style scoped>
.instructor-description {
  width: 310px;
  height: 130px;
  border-radius: 5px;
  background: rgba(0, 0, 0, 0.5);
  position: absolute;
  padding: 12px 10px 12px 60px;
  top: 70px;
  left: 170px;
  z-index: 2;
}
</style>