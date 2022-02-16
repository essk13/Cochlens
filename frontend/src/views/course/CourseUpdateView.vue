<template>
  <div class="course-roof">
    <div class="profile-roof"></div>
    <div class="profile-roof-cover"></div>
    <div class="profile-img text-white"></div>
    <p class="course-detail-title">{{ state.title }}</p>
    <p class="course-detail-instructor">강사 : {{ state.instructorName }}</p>
  </div>

  <div class="row course-detail-upper-area">
    <div class="col-8">
      <p>강좌 이름</p>
      <q-input
        outlined
        v-model="state.title"
        dense
        label="강좌 이름"
        class="q-mb-lg"
      />
      <p>강좌 분야</p>
      <q-input
        outlined
        v-model="state.category"
        dense
        label="강좌 분야"
        class="q-mb-lg"
      />
      <p>강좌 설명</p>
      <q-input
        outlined
        v-model="state.description"
        dense
        label="강좌 설명"
        class="q-mb-lg"
        type="textarea"
      />

      <p>강좌 썸네일</p>
      <q-file outlined dense bottom-slots v-model="state.thumbnail" label="강좌 썸네일" counter max-files="12" class="q-mb-lg">
        <template v-slot:after>
          <q-icon name="attach_file" />
        </template>

        <template v-slot:append>
          <q-icon v-if="model !== null" name="close" @click.stop="model = null" class="cursor-pointer" />
          <q-icon name="search" @click.stop />
        </template>

        <template v-slot:hint>
          Field hint
        </template>
      </q-file>


      <p>강좌 소개 영상</p>
      <q-file outlined dense bottom-slots v-model="model" label="강좌 소개 영상" counter max-files="12" class="q-mb-lg">
        <template v-slot:after>
          <q-icon name="attach_file" />
        </template>

        <template v-slot:append>
          <q-icon v-if="model !== null" name="close" @click.stop="model = null" class="cursor-pointer" />
          <q-icon name="search" @click.stop />
        </template>

        <template v-slot:hint>
          Field hint
        </template>
      </q-file>

      <div style="width: 100%; text-align: right;">
        <q-btn @click="clickUpdate" bg-color="blue-1" label="수정" />
      </div>
    </div>
    <div class="col-1"></div>

    <div class="col-3">
      <q-card class="reservation-card q-mb-xl">
        <q-card-section class="bg-purple">
          <p>강좌 진행 기간</p>
          <q-date v-model="state.date" range />
          <q-input outlined dense v-model="state.fee" label="수강료" bg-color="white" class="q-mt-lg" />
          <q-input outlined dense v-model="state.limit" label="제한 인원" bg-color="white" class="q-mt-lg" />
          <q-input outlined dense v-model="state.cycle" label="강의 주기" bg-color="white" class="q-mt-lg" />
        </q-card-section>
      </q-card>
    </div>
  </div>
</template>
<script>
import { reactive } from "vue"
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'CourseCreate',

  setup () {
    const store = useStore()
    const router = useRouter()
    const courseData = store.state.courseStore.courseData
    const state = reactive({
      instructorName: store.state.user.userName,
      title: courseData.courseName,
      category: courseData.courseCategory,
      description: courseData.courseDescription,
      date: { from: `${courseData.courseOpenDate}`, to: `${courseData.courseCloseDate}` },
      limit: courseData.courseLimitPeople,
      fee: courseData.courseFee,
      cycle: courseData.courseCycle,
      thumbnail: courseData.courseThumbnail,
    })

    // Function
    // 강좌 수정
    function clickUpdate() {
      store.dispatch('courseStore/updateCourse', {
        id: courseData.courseId,
        data: {
          courseName: state.title,
          courseDescription: state.description,
          courseCategory: state.category,
          courseThumbnail: state.thumbnail,
          courseIntroVideo: null,
          courseOpenDate: state.date.to,
          courseCloseDate: state.date.from,
          courseFee: state.fee,
          courseCycle: state.cycle,
          courseLimitPeople: state.limit,
        }
      })
      store.dispatch('courseStore/getCourseList')
      setTimeout(()=> { router.push({ name: 'courseDetail', params: { courseId: courseData.courseId } }) }, 300)
    }

    return {
      state,
      clickUpdate
    }
  }
}
</script>
<style>
  
</style>