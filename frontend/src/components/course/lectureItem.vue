<template>
<div class="course-detail-curriculum row justfiy-between">
  <dir class="row">
    <div class="course-detail-thumbnail"></div>
    <div>
      <p class="curriculum-title">{{ props.lectureItem.lectureName }}</p>
      <p class="curriculum-description">
        강사 : {{ instructor }}<br>
        날짜 : {{ props.lectureItem.lectureDate }} / 길이 : {{ props.lectureItem.lectureRuntime }}
      </p>
    </div>
  </dir>
  <q-btn v-if="!props.lectureItem.lectureState && state.userName === state.instructorName" @click="startLecture" color="purple">강의 시작</q-btn>
  <q-btn v-else-if="props.lectureItem.lectureState === 'live' && !state.userName === state.instructorName" @click="joinLecture" color="purple">강의 참가</q-btn>
  <q-btn v-else-if="props.lectureItem.lectureState === 'done' && state.userName === state.instructorName" @click="uploadVOD" color="purple">VOD 등록</q-btn>
  <q-btn v-else-if="props.lectureItem.lectureState === 'done' && !state.userName === state.instructorName" disable color="purple">종 료</q-btn>
  <q-btn v-else @click="watchVOD" color="purple">VOD</q-btn>
</div>
</template>

<script>
import { reactive } from '@vue/reactivity'
import { useRoute, useRouter } from 'vue-router'
import { useStore } from 'vuex'
export default {
  name: 'LectureItem',
  components: {
  },
  props: {
    lectureItem: Object,
  },
  setup(props) {
    const store = useStore()
    const router = useRouter()
    const route = useRoute()
    const state = reactive({
      instructor: store.state.courseStore.courseData.instructorName,
      userName: store.state.user.userName
    })

    // 강의 시작
    function startLecture() {
      let message = {
        id : 'joinRoom',
        name : 'Instructor',
        room : props.lectureItem.lectureName,
      }
      store.dispatch('courseStore/register', message)
      router.push({ name: 'liveLecture', params: { courseId: route.params.courseId, lectureId: props.lectureItem.lectureId } })
    }
    // 강의 참가
    function joinLecture() {
      let message = {
        id : 'joinRoom',
        name : store.state.user.userNickname,
        room : props.lectureItem.lectureName,
      }
      store.dispatch('courseStore/register', message)
      router.push({ name: 'liveLecture', params: { courseId: route.params.courseId, lectureId: props.lectureItem.lectureId } })
    }
    // VOD 등록
    function uploadVOD() {
    }
    // VOD 시청
    function watchVOD() {
    }

    return {
      state,
      startLecture,
      joinLecture,
      uploadVOD,
      watchVOD
    }
  }
}
</script>

<style>
.course-detail-thumbnail {
  width: 200px;
  height: 130px;
  border-radius: 5px;
  background-image: url('@/assets/cooking.jpg');
  background-size: cover;
}

.curriculum-title {
  font-size: 20px;
  font-weight: 500;
  color: white;
  margin: 10px;
}

.curriculum-description {
  font-size: 15px;
  font-weight: 200;
  color: white;
  margin: 10px;
}
</style>