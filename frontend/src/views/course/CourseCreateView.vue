<template>
  <div class="course-roof">
    <div class="profile-roof"></div>
    <div class="profile-roof-cover"></div>
    <div class="profile-img text-white"></div>
    <p class="course-detail-title">{{ state.title }}</p>
    <p class="course-detail-instructor">강사 : 강태훈</p>
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
        v-model="state.subject"
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
      <q-file outlined dense bottom-slots v-model="model" label="강좌 썸네일" counter max-files="12" class="q-mb-lg">
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
    </div>
    <div class="col-1"></div>
    <div class="col-3">
      <q-card class="reservation-card q-mb-xl">
        <q-card-section class="bg-purple">
          <q-date v-model="date" />
          <q-input outlined dense v-model="text" label="수강료" bg-color="white" class="q-mt-lg" />
          <q-input outlined dense v-model="text" label="제한 인원" bg-color="white" class="q-mt-lg" />
          <q-input outlined dense v-model="text" label="강의 주기" bg-color="white" class="q-mt-lg" />
        </q-card-section>
      </q-card>
    </div>
  </div>
</template>
<script>
import { reactive, ref } from "vue"
export default {
  name: 'CourseCreate',

  setup () {
    const state = reactive({
      title: '강쉡의 요리교실',
      subject: '요리',
      description: '강태훈 강사님의 신나는 요리교실!',
    })
    return {
      state, model: ref(null), date: ref('2022/02/10')
    }
  }
}
</script>
<style>
  
</style>