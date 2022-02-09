<template>
  <div class="instructor-bg">
    <img src="https://cdn.quasar.dev/img/mountains.jpg">
  </div>
  <instructor-profile></instructor-profile>
</template>

<script>
import { reactive } from '@vue/reactivity'
import InstructorProfile from "@/components/instructor/InstructorProfile"

export default {
  name: 'InstructorHeader',
  components: {
    InstructorProfile,
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