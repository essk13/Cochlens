<template>
  <!-- 베스트 강사 정보 -->
  <div class="best-instructor column">
    <!-- 베스트 강사 사진 -->
    <div class="best-instructor-img col-9">
      <img @click="moveInstructorDetail" class="shadow-4" src="https://cdn.quasar.dev/img/mountains.jpg">
      <!-- <img class="shadow-4" :src="state.profileImageUrl"> -->
    </div>
    <!-- 베스트 강사 설명 -->
    <div class="best-instructor-info col-3">
      <span>강태훈</span>
      <!-- <span>{{ props.bestInstructor.userNickname }}</span> -->
      <span class="best-instructor-rate">★5.0</span>
      <!-- <span class="best-instructor-rate">★{{  }}</span> -->
    </div>
  </div>
</template>

<script>
import { reactive, computed } from '@vue/reactivity'
// import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'BestInstructor',
  components: {
  },
  props: {
    bestInstructor: Object,
  },
  setup(props) {
    // const store = useStore()
    const router = useRouter()
    const state = reactive({
      id: 1,
      profileImageUrl: computed(() => props.bestInstructor.profileImage)
    })

    // 강사 상세 정보로 이동
    function moveInstructorDetail() {
      // store.dispatch('instructorStore/getInstructorDetail', props.bestInstructor.userId)
      // .then(() => {
      //   router.push({ name: 'instructorDetail', params: { instructorId: props.bestInstructor.userId }})
      // })
      router.push({ name: 'instructorDetail', params: { instructorId: state.id }})
    }

    return {
      state,
      props,
      moveInstructorDetail,
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

.best-instructor-img > img:hover {
  box-shadow: 0 5px 10px rgb(0 0 0 / 70%);
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