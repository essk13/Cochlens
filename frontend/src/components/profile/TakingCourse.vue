<template>
  <!-- 강좌 정보 -->
  <div @click="clickCourse" class="my-course row shadow-2">
    <!-- 강좌 썸네일 -->
    <div class="my-course-img col-auto">
      <img src="https://cdn.quasar.dev/img/mountains.jpg">
      <!-- <img :src="state.thumbnailImageUrl"> -->
    </div>
    <!-- 강좌 정보 -->
    <div class="my-course-info col column">
      <div class="col-3 row">
        <div class="col-6 text-bold">{{ props.takingCourseItem.courseName }} <span class="text-caption">★{{ props.takingCourseItem.courseReviewRateAverage }}</span></div>
        <div class="col-6 text-right text-caption text-bold">현재 진도 : 3강 / 15강</div>
      </div>
      <div class="col-6 text-caption q-pt-xs">{{ props.takingCourseItem.courseDescription }}</div>
      <div class="col-3 row">
        <div class="text-left col-7 full-height">
          <p class="text-bold text-caption q-mt-xs">강사 : {{ props.takingCourseItem.instructorName }} | #{{ props.takingCourseItem.courseCategory }}</p>
        </div>
        <div class="text-right col-5">
          <span class="my-course-continue text-bold">이어서 학습하기 >></span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive, computed } from '@vue/reactivity'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'TakingCourse',
  props: {
    takingCourseItem: Object,
  },
  setup (props) {
    const store = useStore()
    const router = useRouter()
    const state = reactive({
      thumbnailImageUrl: computed(() => props.takingCourseItem.courseThumbnail),
    })

    // 강좌 상세 정보로 이동
    function clickCourse(){
      store.dispatch('courseStore/getCourseDetail', props.takingCourseItem.courseId)
      .then(() => {
        router.push({ name: 'courseDetail', params: { courseId: props.takingCourseItem.courseId }})
      })
    }

    return {
      state,
      props,
      clickCourse,
    }
  },
}
</script>

<style scoped>
.my-course {
  background-color: rgb(255, 255, 238);
  border-radius: 0.3vw;
  overflow: hidden;
}

.my-course:hover {
  box-shadow: 0 5px 10px rgb(0 0 0 / 70%);
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