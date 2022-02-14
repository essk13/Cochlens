<template>
  <!-- 강좌 정보 -->
  <div class="my-course row shadow-2">
    <!-- 강좌 썸네일 -->
    <div class="my-course-img col-auto">
      <img src="https://cdn.quasar.dev/img/mountains.jpg">
    </div>
    <!-- 강좌 정보 -->
    <div class="my-course-info col column">
      <div class="col-3 row">
        <div class="col-6 text-bold">강쉡의 요리교실 <span class="text-caption">★5.0</span></div>
        <div class="col-6 text-right text-caption text-bold">현재 진도 : 3강 / 15강</div>
      </div>
      <div class="col-6 text-caption q-pt-xs">이 강좌는 그리스 로마부터 시작되어 현대에 이르기까지</div>
      <div class="col-3 row">
        <div class="text-left col-7 full-height">
          <p class="text-bold text-caption q-mt-xs">강사 : 강태훈 | #요리조리</p>
        </div>
        <div class="text-right col-5">
          <span class="my-course-continue text-bold">이어서 학습하기 >></span>
        </div>
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
    })

    return {
      state
    }
  },
}
</script>

<style scoped>
.my-course {
  background-color: lightgray;
  border-radius: 0.3vw;
  overflow: hidden;
}

.my-course-img {
  height: 100%;
}

.my-course-img > img {
  width: 95%;
  height: 100%;
}

.my-course-info {
  padding: 1vh;
  height: 100%;
}

.my-course-continue {
  background-color: rgb(187, 210, 255);
  padding: 0.25vh 0.75vh;
  border-radius: 1.2vh;
  font-size: 1.2vh;
}
</style>