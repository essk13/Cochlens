<template>
  <!-- 강좌 정보 -->
  <div @click="clickCourse" class="course-item column justify-end shadow-3">
    <!-- 강좌 설명 -->
    <div class="course-info row">
      <div class="col-8 q-pl-sm q-pt-xs">
        <div class="text-bold">강쉡의 요리교실</div>
        <div class="course-rate text-caption">★5.0</div>
      </div>
      <div class="col-4 q-pr-sm text-right">
        <div class="text-bold text-caption">강태훈</div>
        <div class="text-bold text-caption">#요리조리</div>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive } from '@vue/reactivity'
import { useRouter } from 'vue-router'


export default {
  name: 'CourseItem',
  components: {
  },

  setup() {
    const router = useRouter()
    const state = reactive({
      id: '001'
    })

    function clickCourse(){
      router.push({ name: 'courseDetail', params: { courseId: state.id }})
    }

    return {
      state, clickCourse
    }
  }
}
</script>

<style scoped>
.course-item {
  background-image: url("https://cdn.quasar.dev/img/mountains.jpg");
  background-position: center;
  border-radius: 1vh;
  overflow: hidden;
}

.course-img {
  overflow: hidden;
}

.course-img > img {
  width: 100%;
}

.course-info {
  background-color: rgba(256, 256, 256, 0.5);
  height: 4.2vh;
}

.course-rate {
  margin: -0.3vh 0 0 0.2vh;
}
</style>