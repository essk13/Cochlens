<template>
  <div class="instructor-profile">
    <div class="instructor-profile-img">
      <img src="https://cdn.quasar.dev/img/mountains.jpg">
    </div>
    <div class="instructor-profile-info">
      <h6 class="q-mb-xs text-bold">아이유</h6>
      <div>이메일 : iu@gmail.com</div>
      <div>강좌 수 : 200</div>
      <div>별점 : ★5.0 (200000)</div>
    </div>
  </div>
</template>

<script>
import { reactive } from '@vue/reactivity'

export default {
  name: 'InstructorProfile',
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
.instructor-profile {
  position: absolute;
  top: 100px;
  left: 60px;
  width: 380px;
  height: 150px;
}

.instructor-profile-img {
  border-radius: 50px;
  width: 150px;
  height: 150px;
  overflow: hidden;
}

.instructor-profile-img > img {
  position: relative;
  height: 100%;
  top: 50%;
  left: 50%;
  z-index: 2;
  transform: translate(-50%, -50%);
}

.instructor-profile-info {
  position: absolute;
  top: 10px;
  right: 0px;
  z-index: 1;
  background-color: rgba(0, 0, 0, 0.6);
  border-radius: 8px;
  width: 300px;
  height: 130px;
  padding: 12px 0px 0px 100px;
  color: white;
}
</style>