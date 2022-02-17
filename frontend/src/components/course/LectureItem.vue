<template>
  <div class="course-detail-curriculum row justfiy-between">
    <div class="row">
      <div class="course-detail-thumbnail"></div>
      <div>
        <p class="curriculum-title">{{ props.lectureItem.lectureName }}</p>
        <p class="curriculum-description">
          강사 : {{ state.instructor }}<br>
          날짜 : {{ props.lectureItem.lectureDate }} / 길이 : {{ props.lectureItem.lectureRuntime }}
        </p>
      </div>
    </div>
    <div>
      <q-btn v-if="props.lectureItem.lectureState == 'before' && state.userName === state.instructor" @click="startLecture" color="purple" label="강의 시작" />
      <q-btn v-else-if="props.lectureItem.lectureState == 'before' && !(state.userName === state.instructor)" color="purple" disable label="시작 예정" />
      <q-btn v-else-if="props.lectureItem.lectureState === 'live' && !(state.userName === state.instructor)" @click="joinLecture" color="purple" label="강의 참가" />
      <q-btn v-else @click="watchVOD" color="purple" label="VOD" />
    </div>
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
      props,
      startLecture,
      joinLecture,
      uploadVOD,
      watchVOD
    }
  }
}
</script>

<style>
.course-detail-curriculum {
  width: 100%;
  height: 130px;
  border-radius: 5px;
  margin-bottom: 20px;
  background: gray;
}

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