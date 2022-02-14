<template>
  <div class="course-roof">
    <div class="profile-roof"></div>
    <div class="profile-roof-cover"></div>
    <div class="profile-img"></div>
    <div class="instructor-description text-white">
      <h6 class="q-mb-sm q-mt-none text-bold">아이유</h6>
      <div>이메일 : iu@gmail.com</div>
      <div>강좌 수 : 200</div>
      <div>별점 : ★5.0 (200000)</div>
    </div>
  </div>
</template>

<script>
import { reactive } from '@vue/reactivity'

export default {
  name: 'InstructorHeader',
  components: {
  },

  setup() {
    const state = reactive({
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