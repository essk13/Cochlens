<template>
  <!-- 베스트 강사 정보 -->
  <div class="best-instructor column">
    <!-- 베스트 강사 사진 -->
    <div class="best-instructor-img col-9">
      <img class="shadow-4" src="https://cdn.quasar.dev/img/mountains.jpg">
    </div>
    <!-- 베스트 강사 설명 -->
    <div class="best-instructor-info col-3">
      <span>강태훈</span>
      <span class="best-instructor-rate">★5.0</span>
    </div>
  </div>
</template>

<script>
import { reactive } from '@vue/reactivity'

export default {
  name: 'BestInstructor',
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
.best-instructor {
  width: 14vh;
  height: 100%;
}

.best-instructor-img {
  height: 50%;
  text-align: center;
  overflow: hidden;
}

.best-instructor-img > img {
  width: 11.5vh;
  height: 11.5vh;
  border-radius: 11.5vh;
}

.best-instructor-info {
  text-align: center;
  font-size: 1.8vh;
  font-weight: bold;
  padding-top: 1.2vh;
}

.best-instructor-rate {
  font-size: 1.5vh;
}
</style>