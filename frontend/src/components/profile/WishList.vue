<template>
  <p class="profile-menu-title">내가 찜한 강좌</p>
  <div v-if="state.wishCourseList" class="row justify-between q-gutter-xl">
    <course-item
      v-for="wishCourse in state.wishCourseList"
      :key="wishCourse.courseId"
      :course-item="wishCourse"
    >
    </course-item>
  </div>
  <div v-else>찜한 강좌가 없습니다.</div>
</template>

<script>
import CourseItem from '@/components/course/CourseItem'
import { reactive } from '@vue/reactivity'
import { useStore } from 'vuex'
import { watchEffect } from 'vue'

export default {
  name: 'WishList',
  components: {
    CourseItem,
  },

  setup () {
    const store = useStore()
    const state = reactive({
      wishCourseList: store.state.courseStore.wishList
    })

    watchEffect(() => {
      state.wishCourseList = store.state.courseStore.wishList
    })

    return {
      state
    }
  },
}
</script>

<style scoped lang="scss">
::v-deep {
  .course-item {
    background-size: auto 100%;
    width: 26vh;
    height: 18vh;
  }
}
</style>