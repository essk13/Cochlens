<template>
  <div @click="moveInstructorDetail" class="instructor-item">
    <div class="instructor-profile-img shadow-2">
      <img src="https://cdn.quasar.dev/img/mountains.jpg">
      <!-- <img :src="state.profileImageUrl"> -->
    </div>
    <div class="instructor-profile-info shadow-2">
      <div class="instructor-name q-mb-xs text-bold">{{ props.instructor.userNickname }}</div>
      <div>이메일 : {{ props.instructor.email }}</div>
      <div>강좌 수 : {{ props.instructor.courseCount }}</div>
      <div>별점 : ★{{ props.instructor.courseReviewRateAverage }} ({{ props.instructor.courseReviewCount }})</div>
    </div>
  </div>
</template>

<script>
import { reactive, computed } from '@vue/reactivity'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'InstructorItem',
  components: {
  },
  props: {
    instructor: Object,
  },
  setup(props) {
    const store = useStore()
    const router = useRouter()
    const state = reactive({
      profileImageUrl: computed(() => props.instructor.profileImage)
    })

    // 강사 상세 정보로 이동
    function moveInstructorDetail() {
      store.dispatch('instructorStore/getInstructorDetail', props.instructor.userId)
      .then(() => {
        router.push({ name: 'instructorDetail', params: { instructorId: props.instructor.userId }})
      })
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
.instructor-item {
  position: relative;
  width: 380px;
  height: 150px;
}

.instructor-item:hover > div {
  box-shadow: 0 5px 10px rgb(0 0 0 / 70%);
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
  left: 50px;
  z-index: 1;
  background-color: rgba(0, 0, 0, 0.6);
  border-radius: 8px;
  width: 300px;
  height: 130px;
  padding: 14px 0px 0px 115px;
  color: white;
}

.instructor-name {
  font-size: 2vh;
}
</style>