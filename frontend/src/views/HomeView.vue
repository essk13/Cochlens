<template>
  <div class="home">
    <!-- 상단 광고 1 -->
    <!-- <div class="home-advertisement q-pa-md">
      <q-carousel
        animated
        v-model="state.slide"
        height="100%"
        arrows
        navigation
        infinite
        class="home-advertisement-carousel shadow-4"
      >
        <q-carousel-slide :name="1" img-src="https://cdn.quasar.dev/img/mountains.jpg" />
        <q-carousel-slide :name="2" img-src="https://cdn.quasar.dev/img/parallax1.jpg" />
        <q-carousel-slide :name="3" img-src="https://cdn.quasar.dev/img/parallax2.jpg" />
        <q-carousel-slide :name="4" img-src="https://cdn.quasar.dev/img/quasar.jpg" />
      </q-carousel>
    </div> -->

    <!-- 상단 광고 2 -->
    <div class="home-advertisement shadow-4">
      <q-carousel
        animated
        v-model="state.slide"
        height="100%"
        arrows
        navigation
        infinite
      >
        <q-carousel-slide :name="1" img-src="https://cdn.quasar.dev/img/mountains.jpg" />
        <q-carousel-slide :name="2" img-src="https://cdn.quasar.dev/img/parallax1.jpg" />
        <q-carousel-slide :name="3" img-src="https://cdn.quasar.dev/img/parallax2.jpg" />
        <q-carousel-slide :name="4" img-src="https://cdn.quasar.dev/img/quasar.jpg" />
      </q-carousel>
    </div>

    <!-- 하단 컨텐츠 -->
    <div class="home-contents row q-py-sm q-px-xl">
      <div class="col-auto q-py-xl q-pl-xl full-height">
        <!-- 나의 최근 강좌 -->
        <div class="home-contents-block column">
          <div class="contents-header col-auto">
            <span class="contents-title">나의 최근 강좌</span>
            <span class="contents-more">더보기></span>
          </div>
          <!-- 강좌 목록 -->
          <div class="my-course-list col column justify-between">

            <!-- 강좌 정보 -->
            <div class="my-course row shadow-2">
              <!-- 강좌 썸네일 -->
              <div class="my-course-img col-auto">
                <img src="https://cdn.quasar.dev/img/mountains.jpg">
              </div>
              <!-- 강좌 정보 -->
              <div class="my-course-info col column">
                <div class="col-3 row">
                  <div class="col-6 text-bold">강쉡의 요리교실</div>
                  <div class="col-6 text-end text-caption text-bold">현재 진도 : 3강 / 15강</div>
                </div>
                <div class="col-6 text-caption q-pt-xs">이 강좌는 그리스 로마부터 시작되어 현대에 이르기까지</div>
                <div class="col-3 row">
                  <div class="text-start col-7 full-height">
                    <p class="text-bold text-caption q-mt-xs">강사 : 강태훈</p>
                  </div>
                  <div class="text-end col-5">
                    <span class="my-course-continue text-bold">이어서 학습하기 >></span>
                  </div>
                </div>
              </div>
            </div>

            <div class="my-course row shadow-2">
              <div class="my-course-img col-auto">
                <img src="https://cdn.quasar.dev/img/mountains.jpg">
              </div>
              <div class="my-course-info col column">
                <div class="col-3 row">
                  <div class="col-6 text-bold">강쉡의 요리교실</div>
                  <div class="col-6 text-end text-caption text-bold">현재 진도 : 3강 / 15강</div>
                </div>
                <div class="col-6 text-caption q-pt-xs">이 강좌는 그리스 로마부터 시작되어 현대에 이르기까지</div>
                <div class="col-3 row">
                  <div class="text-start col-7 full-height">
                    <p class="text-bold text-caption q-mt-xs">강사 : 강태훈</p>
                  </div>
                  <div class="text-end col-5">
                    <span class="my-course-continue text-bold">이어서 학습하기 >></span>
                  </div>
                </div>
              </div>
            </div>

            <div class="my-course row shadow-2">
              <div class="my-course-img col-auto">
                <img src="https://cdn.quasar.dev/img/mountains.jpg">
              </div>
              <div class="my-course-info col column">
                <div class="col-3 row">
                  <div class="col-6 text-bold">강쉡의 요리교실</div>
                  <div class="col-6 text-end text-caption text-bold">현재 진도 : 3강 / 15강</div>
                </div>
                <div class="col-6 text-caption q-pt-xs">이 강좌는 그리스 로마부터 시작되어 현대에 이르기까지</div>
                <div class="col-3 row">
                  <div class="text-start col-7 full-height">
                    <p class="text-bold text-caption q-mt-xs">강사 : 강태훈</p>
                  </div>
                  <div class="text-end col-5">
                    <span class="my-course-continue text-bold">이어서 학습하기 >></span>
                  </div>
                </div>
              </div>
            </div>

            <div class="my-course row shadow-2">
              <div class="my-course-img col-auto">
                <img src="https://cdn.quasar.dev/img/mountains.jpg">
              </div>
              <div class="my-course-info col column">
                <div class="col-3 row">
                  <div class="col-6 text-bold">강쉡의 요리교실</div>
                  <div class="col-6 text-end text-caption text-bold">현재 진도 : 3강 / 15강</div>
                </div>
                <div class="col-6 text-caption q-pt-xs">이 강좌는 그리스 로마부터 시작되어 현대에 이르기까지</div>
                <div class="col-3 row">
                  <div class="text-start col-7 full-height">
                    <p class="text-bold text-caption q-mt-xs">강사 : 강태훈</p>
                  </div>
                  <div class="text-end col-5">
                    <span class="my-course-continue text-bold">이어서 학습하기 >></span>
                  </div>
                </div>
              </div>
            </div>

          </div>
        </div>
      </div>
      <div class="col column full-height">
        <!-- 베스트 강사 -->
        <div class="col-6 q-px-xl q-pt-xl q-pb-sm">
          <div class="home-contents-block column">
            <div class="contents-header col-auto">
              <span class="contents-title">인기 강사</span>
              <span class="contents-more">더보기></span>
            </div>
              <!-- 베스트 강사 목록 -->
            <div class="contents-body best-instructor-list col row justify-between shadow-2">

              <!-- 베스트 강사 정보 -->
              <div class="best-instructor column">
                <!-- 베스트 강사 사진 -->
                <div class="best-instructor-img col-9">
                  <img class="shadow-4" src="https://cdn.quasar.dev/img/mountains.jpg">
                </div>
                <!-- 베스트 강사 설명 -->
                <div class="best-instructor-info col-3">
                  <p>강태훈</p>
                </div>
              </div>

              <div class="best-instructor column">
                <div class="best-instructor-img col-9">
                  <img class="shadow-4" src="https://cdn.quasar.dev/img/mountains.jpg">
                </div>
                <div class="best-instructor-info col-3">
                  <p>김성우</p>
                </div>
              </div>

              <div class="best-instructor column">
                <div class="best-instructor-img col-9">
                  <img class="shadow-4" src="https://cdn.quasar.dev/img/mountains.jpg">
                </div>
                <div class="best-instructor-info col-3">
                  <p>박지유</p>
                </div>
              </div>

              <div class="best-instructor column">
                <div class="best-instructor-img col-9">
                  <img class="shadow-4" src="https://cdn.quasar.dev/img/mountains.jpg">
                </div>
                <div class="best-instructor-info col-3">
                  <p>이수환</p>
                </div>
              </div>

              <div class="best-instructor column">
                <div class="best-instructor-img col-9">
                  <img class="shadow-4" src="https://cdn.quasar.dev/img/mountains.jpg">
                </div>
                <div class="best-instructor-info col-3">
                  <p>아이유</p>
                </div>
              </div>

            </div>
          </div>
        </div>
        <!-- 베스트 강좌 -->
        <div class="col-6 q-px-xl q-pb-xl q-pt-sm">
          <div class="home-contents-block column">
            <div class="contents-header col-auto">
              <span class="contents-title">인기 강좌</span>
              <span class="contents-more">더보기></span>
            </div>
            <!-- 베스트 강좌 목록 -->
            <div class="contents-body best-course-list col row justify-between shadow-2">

              <!-- 베스트 강좌 정보 -->
              <div class="best-course column shadow-3">
                <!-- 베스트 강좌 사진 -->
                <div class="best-course-img col-9">
                  <img class="shadow-4" src="https://cdn.quasar.dev/img/mountains.jpg">
                </div>
                <!-- 베스트 강좌 설명 -->
                <div class="best-course-info col-3 row">
                  <div class="col-8 q-pl-sm q-pt-sm">
                    <div class="text-bold">강쉡의 요리교실</div>
                  </div>
                  <div class="col-4 q-pr-sm text-end">
                    <div class="text-caption">강태훈</div>
                    <div class="text-caption">#요리</div>
                  </div>
                </div>
              </div>

              <div class="best-course column shadow-3">
                <div class="best-course-img col-9">
                  <img class="shadow-4" src="https://cdn.quasar.dev/img/mountains.jpg">
                </div>
                <div class="best-course-info col-3 row">
                  <div class="col-8 q-pl-sm q-pt-sm">
                    <div class="text-bold">강쉡의 요리교실</div>
                  </div>
                  <div class="col-4 q-pr-sm text-end">
                    <div class="text-caption">강태훈</div>
                    <div class="text-caption">#요리</div>
                  </div>
                </div>
              </div>

              <div class="best-course column shadow-3">
                <div class="best-course-img col-9">
                  <img class="shadow-4" src="https://cdn.quasar.dev/img/mountains.jpg">
                </div>
                <div class="best-course-info col-3 row">
                  <div class="col-8 q-pl-sm q-pt-sm">
                    <div class="text-bold">강쉡의 요리교실</div>
                  </div>
                  <div class="col-4 q-pr-sm text-end">
                    <div class="text-caption">강태훈</div>
                    <div class="text-caption">#요리</div>
                  </div>
                </div>
              </div>

              <div class="best-course column shadow-3">
                <div class="best-course-img col-9">
                  <img class="shadow-4" src="https://cdn.quasar.dev/img/mountains.jpg">
                </div>
                <div class="best-course-info col-3 row">
                  <div class="col-8 q-pl-sm q-pt-sm">
                    <div class="text-bold">강쉡의 요리교실</div>
                  </div>
                  <div class="col-4 q-pr-sm text-end">
                    <div class="text-caption">강태훈</div>
                    <div class="text-caption">#요리</div>
                  </div>
                </div>
              </div>

            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import router from "@/router"
import { reactive } from '@vue/reactivity'

export default {
  name: 'HomeView',
  components: {
  },

  setup() {
    // Created
    if (localStorage.getItem('JWT')) {
      console.log('login!')
    } else {
      router.push({ name: 'login' })
    }

    const state = reactive({
      slide: 1
    })

    return {
      state,
    }
  }
}
</script>

<style scoped>
.home-advertisement {
  height: 30vh;
}

.home-advertisement-carousel {
  border-radius: 24px;
}

.home-contents {
  height: 70vh;
}

.home-contents-block {
  /* background-color: rgb(187, 210, 255); */
  height: 100%;
  /* border-radius: 24px; */
  /* padding: 10px; */
}

.contents-header {
  margin-bottom: 0.5vw;
}

.contents-body {
  background-color: lightgray;
  height: 100%;
  border-radius: 0.3vw;
}

.contents-title {
  font-size: 2.2vh;
  font-weight: bold;
}

.contents-more {
  font-size: 1.2vh;
  color: blue;
  margin-left: 0.4vw;
}

/* .my-class-list {

} */

.my-course {
  background-color: lightgray;
  border-radius: 0.3vw;
  width: 48vh;
  height: 11vh;
  overflow: hidden;
}

.my-course-img {
  height: 100%;
}

.my-course-img > img {
  width: 95%;
  height: 100%;
}

.my-course-info {
  padding: 1vh;
  height: 100%;
}

.my-course-continue {
  background-color: rgb(187, 210, 255);
  padding: 0.25vh 0.75vh;
  border-radius: 1.2vh;
  /* border: solid;
  border-color: black;
  border-width: 0.1vh; */
  font-size: 1.2vh;
}

.best-instructor-list {
  padding: 4vh;
}

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

.best-instructor-info {
  text-align: center;
  font-size: 1.8vh;
  font-weight: bold;
  padding-top: 1.2vh;
}

.best-course-list {
  padding: 4vh 4.5vh;
}

.best-course {
  width: 26ch;
  height: 100%;
  border-radius: 1vh;
  overflow: hidden;
}

.best-course-img {
  overflow: hidden;
}

.best-course-img > img {
  width: 100%;
}

.best-course-info {
  background-color: white;
}
</style>