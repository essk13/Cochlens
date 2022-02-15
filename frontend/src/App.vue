<template>
  <q-layout view="lhh LpR lff" class="shadow-2 rounded-borders">
    <q-drawer
      v-model="state.subNav"
      bordered
      :width="200"
      :breakpoint="500"
      class="sub-nav"
    >
      <q-list>
        <template v-for="(navItem, index) in state.subNavItems" :key="index">
          <q-item clickable :active="navItem.title === 'Outbox'" v-ripple @click="navItem.method">
            <q-item-section>
              {{ navItem.title }}
            </q-item-section>
            <q-item-section v-if="navItem.icon" avatar>
              <q-icon :name="navItem.icon" />
            </q-item-section>
          </q-item>
          <q-separator :key="'sep' + index" v-if="navItem.separator" />
        </template>
      </q-list>
    </q-drawer>

    <q-drawer
      v-model="state.mainNav"
      :width="200"
      :breakpoint="700"
      bordered
      class="navbar"
    >
      <q-list>
        <template v-for="(navItem, index) in state.mainNavItem" :key="index">
          <q-item clickable :active="navItem.title === 'Outbox'" v-ripple @click="navItem.method">
            <q-item-section avatar>
              <q-icon :name="navItem.icon" />
            </q-item-section>
            <q-item-section>
              {{ navItem.title }}
            </q-item-section>
          </q-item>
          <q-separator :key="'sep' + index"  v-if="navItem.separator" />
        </template>
      </q-list>
    </q-drawer>

    <q-page-container class="page" id="main-content">
      <router-view/>
    </q-page-container>
  </q-layout>
</template>

<script>
import logo from '@/assets/logo-none-title.svg'
import { reactive } from '@vue/reactivity'
import { useStore } from 'vuex'
import { computed } from '@vue/runtime-core'
import { useRouter, useRoute } from 'vue-router'
import { watch } from 'vue'

export default {
  setup() {
    const store = useStore()
    const router = useRouter()
    const route = useRoute()

    const state = reactive({
      logo,
      mainNav: true,
      mainNavItem: [
        { title: 'Cochlens', icon: 'dashboard', method: clickHome },
        { title: '내 정보', icon: 'account_circle', method: clickProfile },
        { title: '강좌 조회', icon: 'cast', method: clickCourse },
        { title: '강사 조회', icon: 'co_present', method: clickInstructor },
        { title: '설정', icon: 'settings', method: clickSetting },
        { title: '로그아웃', icon: 'close', method: clickLogout },
      ],
      subNav: false,
      subNavItems: [],
      toggle_item: false
    })

    // Created
    if (localStorage.getItem('JWT')) {
      console.log(localStorage.getItem('JWT'))
      store.dispatch('getUserData')
    } else {
      state.mainNav = false
      router.push({ name: 'login' })
    }

    // Watch
    watch(
      computed(() => store.state.user),
      (newUser, oldUser) => {
        console.log('new' + newUser, 'old' + oldUser)
        if (newUser) {
          state.mainNav = true
        } else {
          state.mainNav = false
        }
      }
    )

    watch(
      computed(() => state.subNav),
      (newSubNav, oldSubNav) => {
        console.log(newSubNav)
        console.log(oldSubNav)
        const target = document.getElementById('main-content')
        if (newSubNav) {
          target.classList.add("sub-open")
        } else {
          target.classList.remove("sub-open")
        }
      }
    )

    watch(
      computed(() => state.subNav),
      (newsubNav, oldsubNav) => {
        console.log(newsubNav, oldsubNav)
        const target = document.getElementById('main-content')
        if (!newsubNav && state.mainNav) {
          target.classList.add("main-open")
        } else {
          target.classList.remove("main-open")
        }
      }
    )

    watch(
      computed(() => route.params),
      (newParams, oldParams) => {
        console.log(newParams, oldParams)
        if (newParams.courseId) {
          state.subNavItems = [
            { title: '강좌 목록', icon: '', method: clickCourse },
            { title: '강좌 상세정보', icon: '', method: moveCourseDetail },
            { title: '강좌 리뷰', icon: '', method: moveCourseReview },
            { title: '강좌 개설', icon: '', method: moveCourseCreate },
            { title: '', icon: 'keyboard_double_arrow_left', method: subNavClose},
          ]
        } else if (newParams.instructorId) {
          state.subNavItems = [
            { title: '강사 목록', icon: '', method: clickInstructor},
            { title: '강사 프로필', icon: '', method: clickInstructorDetail},
            { title: '강사 라이브 강좌', icon: '', method: clickInstructorLive},
            { title: '강사 전체 강좌', icon: '', method: clickInstructorCourse},
            { title: '강사 리뷰', icon: '', method: clickInstructorReview},
            { title: '', icon: 'keyboard_double_arrow_left', method: subNavClose},
          ]
        }
      }
    )


    // Function
    function subNavClose() {
      state.subNav = !state.subNav
    }

    function clickHome() {
      state.subNav = false
      router.push({ name: 'home' })
    }


    function clickProfile() {
      store.dispatch('profileStore/getWishList')
      store.dispatch('profileStore/getTakingList')
      state.subNav = true
      router.push({ name: 'profile' })
      state.subNavItems = [
        { title: '프로필 홈', icon: '', method: clickProfileHome},
        { title: '수강 중인 강좌', icon: '', method: clickProfileTaking},
        { title: '내가 찜한 강좌', icon: '', method: clickProfileWish},
        { title: '', icon: 'keyboard_double_arrow_left', method: subNavClose},
      ]
    }

    function clickProfileHome() {
      store.state.profileStore.component = 'home'
    }
    function clickProfileTaking() {
      store.state.profileStore.component = 'taking'
    }
    function clickProfileWish() {
      store.state.profileStore.component = 'wish'
    }


    function clickCourse() {
      store.dispatch('courseStore/getBestCourseList')
      store.dispatch('courseStore/getCourseList')
      state.subNav = true
      router.push({ name: 'courseList' })
      state.subNavItems = [
        { title: '강좌 목록', icon: '', method: clickCourse },
        { title: '강좌 개설', icon: '', method: moveCourseCreate },
        { title: '', icon: 'keyboard_double_arrow_left', method: subNavClose},
      ]
    }

    function moveCourseDetail() {
      router.push({ name: 'courseDetail', params: { courseId: route.params.courseId } })
    }
    function moveCourseReview() {
      router.push({ name: 'courseReviewList', params: { courseId: route.params.courseId } })
    }
    function moveCourseCreate() {
      router.push({ name: 'courseCreate' })
    }


    function clickInstructor() {
      state.subNav = true
      router.push({ name: 'instructorList' })
      state.subNavItems = [
        { title: '강사 목록', icon: '', method: clickInstructor},
        { title: '', icon: 'keyboard_double_arrow_left', method: subNavClose},
      ]
    }
    function clickInstructorDetail() {
      router.push({ name: 'instructorDetail', params: { instructorId: route.params.instructorId } })
    }
    function clickInstructorLive() {
      router.push({ name: 'instructorLiveList', params: { instructorId: route.params.instructorId } })
    }
    function clickInstructorCourse() {
      router.push({ name: 'instructorCourseList', params: { instructorId: route.params.instructorId } })
    }
    function clickInstructorReview() {
      router.push({ name: 'instructorReviewList', params: { instructorId: route.params.instructorId }})
    }


    function clickSetting() {
      state.subNav = false
      router.push({ name: 'setting' })
    }

    function clickLogout() {
      state.subNav = false
      state.mainNav = false
      store.dispatch('userLogout')
      router.push({ name: 'login' })
    }

    return {
      state,
      clickHome, clickProfile, clickCourse, clickInstructor, clickSetting, clickLogout
    }
  },
  }
</script>

<style>
.navbar {
  background-color: rgb(211, 239, 255);
}

.sub-nav {
  background-color: rgb(166, 222, 255);
  margin-left: 200px !important;
}

.main-menu {
  font-size: 17px !important;
  font-weight: bold;
}

.sub-menu {
  font-size: 15px !important;
  font-weight: bold;
}

.x-btn {
  position: absolute;
  bottom: 30px;
  right: 10px;
}

.sub-open {
  padding-left: 399px !important;
}

.main-open {
  padding-left: 200px !important;
}
</style>