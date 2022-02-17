<template>
  <p class="profile-menu-title">수강 중인 강좌</p>
  <div v-if="state.takingCourseList" class="row justify-between q-gutter-xl">
    <course-item
      v-for="takingCourse in state.takingCourseList"
      :key="takingCourse.courseId"
      :course-item="takingCourse"
    >
    </course-item>
  </div>
  <div v-else>수강 중인 강좌가 없습니다.</div>
</template>

<script>
import CourseItem from '@/components/course/CourseItem'
import { reactive } from '@vue/reactivity'
import { useStore } from 'vuex'
import { watchEffect } from 'vue'

export default {
  name: 'TakingCourseList',
  components: {
    CourseItem,
  },

  setup () {
    const store = useStore()
    const state = reactive({
      takingCourseList: store.state.profileStore.takingList
    })

    watchEffect(() => {
      state.takingCourseList = store.state.profileStore.takingList
    })

    return {
      state
    }
  },
}
</script>

<style scoped lang="scss">
.profile-course-item{
  width: 94%;
  height: 150px;
  margin-bottom: 50px;
  border-radius: 5px;
  background: gray;
}

::v-deep {
  .course-item {
    background-size: auto 100%;
    width: 26vh;
    height: 18vh;
  }
}
</style>