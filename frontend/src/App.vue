<template>
  <v-app>
    <!-- Main Nav -->
    <v-navigation-drawer
      width="200"
      class="navbar pa-3"
      v-model="state.mainNav"
    >
      <v-list
        nav
        dense
      >
        <v-list-item
          v-for="item in state.mainNavItem"
          @click="item.method"
          :key="item.title"
          link
        >
          <v-list-item-icon class="mr-2">
            <img v-if="item.title == 'Cochlens'" :src="state.logo" height="20" alt="logo">
            <v-icon v-else>{{ item.icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title class="main-menu">{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
      <v-btn
          variant="none"
          class="menu-close"
        >
          <v-icon @click="clickLogout" class="text-lg-h5">mdi-window-close</v-icon>
        </v-btn>
    </v-navigation-drawer>

    <!-- Sub Nav -->
    <v-navigation-drawer
      v-model="state.drawer"
      class="sub-nav"
      width="250"
    >
      <v-list
        nav
        dense
      >
        <v-list-item>
          메뉴
        </v-list-item>
        <hr color="balck">

        <v-list-item
          v-for="item in state.drawerItems"
          @click="item.method"
          :key="item.title"
          link
        >
          <v-list-item-content>
            <v-list-item-title class="sub-menu">{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>

        <v-btn
          variant="none"
          class="menu-close"
        >
          <v-icon @click="state.drawer = false" class="text-lg-h4">mdi-chevron-double-left</v-icon>
        </v-btn>

    </v-navigation-drawer>

    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>
import logo from '@/assets/logo-none-title.svg'
import { reactive } from '@vue/reactivity'
import { useStore } from 'vuex'
import { computed } from '@vue/runtime-core'
import { useRouter } from 'vue-router'
import { watch } from 'vue'


export default {
  setup() {
    const store = useStore()
    const router = useRouter()

    const state = reactive({
      logo,
      drawer: false,
      drawerItems: [],
      group: null,
      mainNav: true,
      mainNavItem: [
        { title: 'Cochlens', icon: 'mdi-view-dashboard', method: clickHome },
        { title: '내 정보', icon: 'mdi-account-circle', method: clickProfile },
        { title: '강좌 조회', icon: 'mdi-cast-education', method: clickClass },
        { title: '강사 조회', icon: 'mdi-clipboard-account', method: clickInstructor },
        { title: '설정', icon: 'mdi-cog', method: clickSetting },
      ],
      toggle_item: false
    })

    // Created
    if (localStorage.getItem('JWT')) {
      store.dispatch('set_user')
    } else {
      router.push({ name: 'login' })
    }

    const user = computed(() => store.state.user)
    console.log(user.value)
    if (user.value == null) {
      state.mainNav = false
      router.push({ name: 'login' })
    } else {
      state.mainNav = true
    }

    // Watch
    watch(
      computed(() => store.state.user),
      (newUser, oldUser) => {
        console.log('new', newUser, 'old', oldUser)
        if (newUser != null) {
          state.mainNav = true
      }
    })

    // Function
    function clickHome() {
      state.drawer = false
      router.push({ name: 'home' })
    }

    function clickProfile() {
      state.drawer = true
      router.push({ name: 'profile' })
      state.drawerItems = [
        { title: '프로필', icon: '', method: ''},
        { title: '수강 중인 강좌', icon: '', method: ''},
        { title: '내가 찜한 강좌', icon: '', method: ''}
      ]
    }

    function clickClass() {
      state.drawer = true
      router.push({ name: 'courselist' })
      state.drawerItems = [
        { title: '강의 목록', icon: '', method: ''},
        { title: '라이브 강좌', icon: '', method: ''},
        { title: '인기 강좌', icon: '', method: moveCourse},
      ]
    }

    function moveCourse() {
      router.push({ name: 'course' })
    }

    function clickInstructor() {
      state.drawer = true
      router.push({ name: 'instructorlist' })
      state.drawerItems = [
        { title: '강사 목록', icon: '', method: ''},
        { title: '강사 프로필', icon: '', method: ''},
        { title: '전체 강좌', icon: '', method: ''},
        { title: '강사 리뷰', icon: '', method: ''},
      ]
    }

    function clickSetting() {
      state.drawer = false
      router.push({ name: 'setting' })
    }

    function clickLogout() {
      state.drawer = false
      state.mainNav = false
      localStorage.removeItem('JWT')
      store.dispatch('userLogout')
      router.push({ name: 'login' })
    }

    return {
      state,
      clickHome, clickProfile, clickClass, clickInstructor, clickSetting, clickLogout
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
}

.main-menu {
  font-size: 17px !important;
  font-weight: bold;
}

.sub-menu {
  font-size: 15px !important;
  font-weight: bold;
}

.menu-close {
  position: absolute;
  bottom: 30px;
  right: 10px;
}
</style>