<template>
  <div class="course-roof">
    <div class="course-roof-img" id="course-roof"></div>
    <div class="profile-roof-cover"></div>
    <div class="course-img text-white" id="course-img"></div>
    <p class="course-detail-title">{{ state.courseData.courseName }}</p>
    <p class="course-detail-instructor">강사 : {{ state.courseData.instructorName }}</p>
  </div>

  <div class="row course-detail-upper-area">
    <div class="col-7">
      <!-- 강좌 소개 -->
      <div class="q-mb-xl">
        <p class="profile-menu-title">강좌 소개</p>
        <p>
          {{ state.courseData.courseDescription }}
        </p>
      </div>

      <!-- 커리큘럼 -->
      <div>
        <div class="row justify-between item-center content-center">
          <p class="profile-menu-title q-mb-none">커리큘럼</p>
          <q-btn v-if="state.courseData.instructorName === state.userName" @click="state.isDialog = true" color="blue" label="강의 생성" dense />
          <q-btn v-else @click="state.isReview = true" color="blue" label="리뷰 작성" dense />
        </div>
        <lecture-item
          v-for="lecture in state.lectureList"
          :key="lecture.lectureId"
          :lecture-item="lecture"
        ></lecture-item>
      </div>
    </div>
    <div class="col-1"></div>

    <div class="col-4 q-pa-md">
      <div class="reserve">
        <!-- 수강신청 -->
        <q-card class="reservation-card q-mb-xl">
          <q-card-section class="bg-purple text-white">
            <div class="text-h6">수강신청</div>
            <div class="text-subtitle2">수강료: {{ state.courseData.courseFee }}</div>
            <div class="text-subtitle2">강좌 주기: {{ state.courseData.courseCycle }}</div>
            <div class="text-subtitle2">시작일: {{ state.courseData.courseOpenDate }} / 종료일: {{ state.courseData.courseCloseDate }}</div>
            <div class="text-subtitle2">제한인원: {{ state.courseData.courseJoinCount }} / {{ state.courseData.courseLimitPeople }}</div>
          </q-card-section>

          <q-card-actions align="around">
            <div>
              <q-btn v-if="state.courseData.instructorName === state.userName" @click="moveCourseUpdate" flat>강좌 수정</q-btn>
              <q-btn v-else-if="state.courseData.courseJoinCount === state.courseData.courseLimitPeople && !state.courseData.isJoin" disable flat>신청마감</q-btn>
              <q-btn v-else-if="!state.courseData.isJoin" @click="clickRegister" flat>신청하기</q-btn>
              <q-btn v-else-if="state.courseData.isJoin" @click="clickDeregister" flat>수강중인 강좌</q-btn>
            </div>
            <div>
              <q-btn v-if="!state.courseData.isWish" @click="clickWish" flat>찜 하기 ({{ state.courseData.courseWishCount }})</q-btn>
              <q-btn v-else @click="clickUnwish" flat>찜한 강좌 ({{ state.courseData.courseWishCount }})</q-btn>
            </div>
          </q-card-actions>
        </q-card>

        <!-- 강좌 리뷰 -->
        <q-card class="reservation-card">
          <q-card-section class="bg-purple text-white">
            <div class="row">
              <p class="text-h6 q-mr-sm q-mb-none">강좌 리뷰</p>
              <a @click="moveCourseReview">더보기</a>
            </div>
            <div class="text-subtitle2">by John Doe</div>
          </q-card-section>
          <q-list v-if="state.reviewList" bordered class="course-review-preview rounded-borders">
            <course-review
              v-for="(reviewItem, index) in state.reviewList"
              :key="index"
              :course-review="reviewItem"
            ></course-review>
          </q-list>
          <div v-else>리뷰가 없습니다.</div>
        </q-card>
      </div>
    </div>
  </div>

  <!-- 강의 생성 모달 -->
  <q-dialog v-model="state.isDialog" persistent>
    <q-card style="min-width: 350px">
      <q-card-section>
        <div class="text-h6">강의 생성</div>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <p class="q-mb-xs">강의 명</p>
        <q-input outlined dense v-model="state.lectureName" label="Outlined" class="q-mb-md" />

        <p class="q-mb-xs">강의 종류</p>
        <q-select outlined dense v-model="state.lectureState" :options="state.options" label="Outlined" class="q-mb-md" />

        <p class="q-mb-xs">강의 날짜 / 시간</p>
        <div class="row items-start">
          <q-date v-model="state.lectureDate" color="purple" class="q-mb-md" />

          <div class="q-gutter-sm row">
            <q-input filled v-model="state.lectureOpenTime" mask="time" :rules="['time']" label="시작 시간">
              <template v-slot:append>
                <q-icon name="access_time" class="cursor-pointer">
                  <q-popup-proxy cover transition-show="scale" transition-hide="scale">
                    <q-time v-model="state.lectureOpenTime">
                      <div class="row items-center justify-end">
                        <q-btn v-close-popup label="Close" color="primary" flat />
                      </div>
                    </q-time>
                  </q-popup-proxy>
                </q-icon>
              </template>
            </q-input>

            <q-input filled v-model="state.lectureCloseTime" mask="time" :rules="['time']" label="종료 시간">
              <template v-slot:append>
                <q-icon name="access_time" class="cursor-pointer">
                  <q-popup-proxy cover transition-show="scale" transition-hide="scale">
                    <q-time v-model="state.lectureCloseTime">
                      <div class="row items-center justify-end">
                        <q-btn v-close-popup label="Close" color="primary" flat />
                      </div>
                    </q-time>
                  </q-popup-proxy>
                </q-icon>
              </template>
            </q-input>

          </div>
        </div>
      </q-card-section>

      <q-card-actions align="right" class="text-primary">
        <q-btn flat label="강의 생성" v-close-popup @click="createLecture" />
        <q-btn flat label="취소" v-close-popup />
      </q-card-actions>
    </q-card>
  </q-dialog>

  <!-- 리뷰 생성 모달 -->
  <q-dialog v-model="state.isReview" persistent>
    <q-card style="min-width: 350px">
      <q-card-section>
        <div class="text-h6">리뷰 생성</div>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <p class="q-mb-xs">리뷰</p>
        <q-input outlined dense v-model="state.reviewContent" label="Outlined" class="q-mb-md" />
        <p class="q-mb-xs">평점</p>
          <q-rating
            v-model="state.reviewRate"
            size="2em"
            :max="10"
            color="primary"
            class="q-mb-md"
          />
      </q-card-section>

      <q-card-actions align="right" class="text-primary">
        <q-btn flat label="리뷰 작성" v-close-popup @click="createReview" />
        <q-btn flat label="취소" v-close-popup />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script>
import LectureItem from '@/components/course/LectureItem'
import CourseReview from '@/components/course/CourseReview'
import { reactive } from '@vue/reactivity'
import { useStore } from 'vuex'
import { useRoute, useRouter } from 'vue-router'
import { onMounted, watchEffect } from '@vue/runtime-core'

export default {
  name: 'CourseDetailView',
  components: {
    CourseReview,
    LectureItem,
  },
  setup() {
    const store = useStore()
    const router = useRouter()
    const route = useRoute()
    const optionContent = {
      '라이브': 'BEFORE',
      'VOD': 'VOD'
    }
    let today = new Date()
    const url = 'wss://' + location.host + '/groupcall'
    store.dispatch('courseStore/setWs', url)

    const state = reactive({
      name: 'test',
      room: '',
      courseData: store.state.courseStore.courseData,
      lectureList: store.state.courseStore.courseData.lectureList,
      reviewList: store.state.courseStore.courseData.reviewList,
      userName: store.state.user.userName,

      isDialog: false,
      dialogText: 'click',
      lectureCloseTime: `${today.getHours().toString().padStart(2, '0')}:${today.getMinutes().toString().padStart(2, '0')}`,
      lectureDate: `${today.getFullYear()}/${(today.getMonth() + 1).toString().padStart(2, '0')}/${today.getDate().toString().padStart(2, '0')}`,
      lectureName: '',
      lectureOpenTime: `${today.getHours().toString().padStart(2, '0')}:${today.getMinutes().toString().padStart(2, '0')}`,
      lectureRuntime: '02:00',
      lectureState: 'BEFORE',
      lectureThumbnail: null,
      lectureVod: null,
      options: ['라이브', 'VOD'],

      isReview: false,
      reviewContent: '',
      reviewRate: 5,
    })

    console.log(store.state.courseStore.courseData.courseJoinCount)
    console.log(store.state.courseStore.courseData.courseWishCount)
    console.log(store.state.courseStore.courseData.isJoin)
    console.log(store.state.courseStore.courseData.isWish)

    // Mounted
    onMounted(() => {
      setImage()
    })

    // Watch
    watchEffect(() => {
      state.courseData = store.state.courseStore.courseData
      setImage()
      changeList()
    })
    watchEffect(() => {
      state.lectureList = store.state.courseStore.courseData.lectureList
    })

    // Function
    // 강좌, 강사 사진 설정
    function setImage() {
      const courseImg = document.getElementById('profile-img')
      const courseRoofImg = document.getElementById('profile-roof')
      if (state.courseData.instructorProfileImage) {
        courseImg.style.backgroundImage = `url(${state.courseData.instructorProfileImage})`
      } else {
        courseImg.style.backgroundImage = `url('/img/kang.968d430f.png')`
      }

      if (state.courseData.courseThumbnail) {
        courseRoofImg.style.backgroundImage = `url(${state.courseData.courseThumbnail})`
      } else {
        courseRoofImg.style.backgroundImage = `url('/img/cooking_roof.c34b6973.jpg')`
      }
    }
    // Function
    function changeList() {
      state.lectureList = store.state.courseStore.courseData.lectureList
      state.reviewList = store.state.courseStore.courseData.reviewList
    }
    // 리뷰 페이지 이동
    function moveCourseReview() {
      router.push({ name: 'courseReviewList', params: { courseId: route.params.courseId } })
    }
    // 수강 신청
    function clickRegister() {
      state.courseData.isJoin = true
      state.courseData.courseJoinCount += 1
      store.dispatch('courseStore/registerCourse', state.courseData.courseId)
    }
    // 수강 취소
    function clickDeregister() {
      state.courseData.isJoin = false
      state.courseData.courseJoinCount -= 1
      store.dispatch('courseStore/deregisterCourse', state.courseData.courseId)
    }
    // 찜 추가
    function clickWish() {
      state.courseData.isWish = true
      state.courseData.courseWishCount += 1
      store.dispatch('courseStore/wishCourse', state.courseData.courseId)
    }
    // 찜 취소
    function clickUnwish() {
      state.courseData.isWish = false
      state.courseData.courseWishCount -= 1
      store.dispatch('courseStore/unwishCourse', state.courseData.courseId)
    }
    // 강좌 수정
    function moveCourseUpdate() {
      router.push({ name: 'courseUpdate', params: { courseId: state.courseData.courseId }})
    }

    // 강의 생성
    function createLecture() {
      store.dispatch('courseStore/createLecture', {
        id: state.courseData.courseId,
        data: {
          lectureCloseTime: state.lectureCloseTime,
          lectureDate: state.lectureDate,
          lectureName: state.lectureName,
          lectureOpenTime: state.lectureOpenTime,
          lectureRuntime: state.lectureRuntime,
          lectureState: optionContent[state.lectureState],
          lectureThumbnail: state.lectureThumbnail,
          lectureVod: state.lectureVod,
        }
      })
    }
    // 강의 시작
    function startLecture() {
      let message = {
        id : 'joinRoom',
        name : 'Instructor',
        room : 'testroom',
      }
      store.dispatch('courseStore/register', message)
      router.push({ name: 'liveLecture', params: { courseId: route.params.courseId, lectureId: '001' } })
    }
    // 강의 참가
    function joinLecture() {
      let message = {
        id : 'joinRoom',
        name : state.name,
        room : 'testroom',
      }
      store.dispatch('courseStore/register', message)
      router.push({ name: 'liveLecture', params: { courseId: route.params.courseId, lectureId: '001' } })
    }

    // 리뷰 작성
    function createReview() {
      store.dispatch('courseStore/createReview', {
        id: state.courseData.courseId,
        data: {
          reviewContent: state.reviewContent,
          reviewDate: `${today.getFullYear()}/${(today.getMonth() + 1).toString().padStart(2, '0')}/${today.getDate().toString().padStart(2, '0')}`,
          reviewRate: state.reviewRate
        }
      })
      state.reviewContent = ''
      state.reviewRate = 5
    }

    return {
      state, url,
      clickRegister,
      clickDeregister,
      clickWish,
      clickUnwish,
      createLecture,
      moveCourseUpdate,
      startLecture,
      joinLecture,
      createReview,
      moveCourseReview,
    }
  }
}
</script>

<style>
.course-roof {
  position: sticky;
  top: 0;
}

.course-roof-img {
  width: 100%;
  height: 180px;
  background-image: url('@/assets/cooking_roof.jpg');
  background-size: cover;
  background-position: 20%;
}

.course-img {
  width: 150px;
  height: 150px;
  border-radius: 50px;
  background-image: url('@/assets/kang.png');
  background-size: cover;
  position: absolute;
  z-index: 3;
  top: 60px;
  left: 60px;
}

.course-detail-title {
  position: absolute;
  top: 100px;
  left: 220px;
  font-size: 30px;
  font-weight: bold;
  color: white;
  z-index: 2;
}

.course-detail-instructor {
  position: absolute;
  top: 145px;
  left: 225px;
  font-size: 15px;
  color: white;
  z-index: 2;
}

.course-detail-upper-area {
  padding: 60px 60px 0 60px;
}

.reservation-card {
  width: 100%;
}

.course-detail-review {
  width: 100%;
}

.reserve {
  position: -webkit-sticky;
  position: sticky !important;
  top: 255px;
}

.course-review-preview {
  background-color: rgb(255, 255, 238);
}
</style>