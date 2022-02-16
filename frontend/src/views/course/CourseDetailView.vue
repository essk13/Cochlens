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
          <q-btn @click="state.isDialog = true" color="blue" label="강의 생성" dense />
        </div>
        <lecture-item
          v-for="lecture in state.lectureList"
          :key="lecture.lectureId"
          :lecture-item="lecture"
        ></lecture-item>
        <div class="course-detail-curriculum"></div>
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
              <q-btn v-if="state.courseData.courseJoinCount === state.courseData.courseLimitPeople && !state.courseData.isJoin" @click="clickRegister" flat>신청마감</q-btn>
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

  <!-- Dialog -->
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
          <q-date v-model="state.lectureDate" mask="YYYY-MM-DD HH:mm" color="purple" class="q-mb-md" />

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
</template>

<script>
import LectureItem from '@/components/course/lectureItem'
import { reactive } from '@vue/reactivity'
import { useStore } from 'vuex'
import { useRoute, useRouter } from 'vue-router'
import CourseReview from '@/components/course/CourseReview'
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
    let today = new Date()
    const url = 'wss://' + location.host + '/groupcall'
    store.dispatch('courseStore/setWs', url)

    const state = reactive({
      name: 'test',
      room: '',
      courseData: store.state.courseStore.courseData,
      lectureList: store.state.courseStore.courseData.lectureList,
      reviewList: store.state.courseStore.courseData.reviewList,

      isDialog: false,
      dialogText: 'click',
      lectureCloseTime: `${today.getHours().toString().padStart(2, '0')}:${today.getMinutes().toString().padStart(2, '0')}`,
      lectureDate: `${today.getFullYear()}/${(today.getMonth() + 1).toString().padStart(2, '0')}/${today.getDate().toString().padStart(2, '0')}`,
      lectureName: '',
      lectureOpenTime: `${today.getHours().toString().padStart(2, '0')}:${today.getMinutes().toString().padStart(2, '0')}`,
      lectureState: 'before',
      lectureThumbnail: null,
      lectureVod: null,
      options: ['라이브', 'VOD'],
    })

    console.log('lectureItem')
    console.log(state.lectureList[0])

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
      store.dispatch('registerCourse')
    }
    // 수강 취소
    function clickDeregister() {
      state.courseData.isJoin = false
      state.courseData.courseJoinCount -= 1
      store.dispatch('deregisterCourse')
    }
    // 찜 추가
    function clickWish() {
      state.courseData.isWish = true
      state.courseData.courseWishCount += 1
      store.dispatch('wishCourse')
    }
    // 찜 취소
    function clickUnwish() {
      state.courseData.isWish = false
      state.courseData.courseWishCount -= 1
      store.dispatch('unwishCourse')
    }

    // 강의 생성
    function createLecture() {
      store.dispatch('courseStore/createLecture', {
        id: state.courseData.courseId,
        data: {
          lectureCloseTime: "12:10",
          lectureDate: "2022/02/11",
          lectureName: state.lectuerName,
          lectureOpenTime: "10:10",
          lectureRuntime: "02:00",
          lectureState: state.lectureState,
          lectureThumbnail: "thumbnail address",
          lectureVod: "vod address"
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

    return {
      state, url,
      clickRegister,
      clickDeregister,
      clickWish,
      clickUnwish,
      createLecture,
      startLecture,
      joinLecture,
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

.course-detail-curriculum {
  width: 100%;
  height: 130px;
  border-radius: 5px;
  margin-bottom: 20px;
  background: gray;
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