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
  
  <!-- <div class="instructor-bg">
    <img src="https://cdn.quasar.dev/img/mountains.jpg">
  </div>
  <instructor-profile></instructor-profile> -->
</template>

<script>
import { reactive } from '@vue/reactivity'
// import InstructorProfile from "@/components/instructor/InstructorProfile"

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
    background: rgb(102, 102, 102);
    position: absolute;
    padding: 10px 50px;
    top: 70px;
    left: 170px;
    z-index: 2;
  }

.instructor-bg {
  width: 100%;
  height: 80%;
  overflow: hidden;
}

.instructor-bg > img {
  position: relative;
  width: 100%;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
</style>