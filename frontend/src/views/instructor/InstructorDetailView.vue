<template>
  <div class="header">
    <instructor-header></instructor-header>
  </div>
  <div class="contents column q-px-xl q-mx-xl q-pb-xl">
    <div class="col-4 row q-pb-sm">
      <div class="instructor-description-block col-6 column q-pr-lg">
        <div class="contents-header col-auto">
          <span class="text-h6 text-bold">강사 소개</span>
        </div>
        <div class="col">
          <div>네이버, 카카오, 라인플러스, 쿠팡, 배달의 민족, 당근마켓, 토스를 거친 AI분야 세계 최고 권위자. 네이버, 카카오, 라인플러스, 쿠팡, 배달의 민족, 당근마켓, 토스를 거친 AI분야 세계 최고 권위자.</div>
        </div>
      </div>
      <div class="instructor-review-block col-6 column q-pl-lg">
        <div class="contents-header col-auto">
          <span class="contents-title">수강후기</span>
          <span class="contents-more">더보기></span>
        </div>
        <div class="instructor-review-list col">

        </div>
      </div>
    </div>
    <div class="in-progress-course-block col-4 column q-py-sm">
      <div class="contents-header col-auto">
        <span class="contents-title">진행중인 강좌</span>
        <span class="contents-more">더보기></span>
      </div>
      <div class="in-progress-course-list col row justify-around items-center no-wrap">
        <course></course>
        <course></course>
        <course></course>
        <course></course>
        <course></course>
      </div>
    </div>
    <div class="all-course-block col-4 column q-py-sm">
      <div class="contents-header col-auto">
        <span class="contents-title">전체 강좌</span>
        <span class="contents-more">더보기></span>
      </div>
      <div class="all-course-list col row justify-around items-center no-wrap">
        <course></course>
        <course></course>
        <course></course>
        <course></course>
        <course></course>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive } from '@vue/reactivity'
import InstructorHeader from "@/components/instructor/InstructorHeader"
import Course from '@/components/course/Course'

export default {
  name: 'InstructorDetailView',
  components: {
    InstructorHeader,
    Course,
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

<style scoped lang="scss">
.header {
  height: 23vh;
}

.contents {
  height: 77vh;
}

.contents-header {
  margin-bottom: 0.5vh;
}

.contents-title {
  font-size: 1.5vh;
  font-weight: bold;
}

.contents-more {
  font-size: 1.2vh;
  color: blue;
  margin-left: 0.4vw;
}

.instructor-review-list {
  background-color: lightgray;
  border-radius: 0.5vh;
}

.in-progress-course-list {
  background-color: lightgoldenrodyellow;
  border-radius: 0.5vh;
}

.all-course-list {
  background-color: lightblue;
  border-radius: 0.5vh;
}

::v-deep {
  .course {
    background-size: auto 100%;
    width: 24vh;
    height: 16vh;
  }
}
</style>