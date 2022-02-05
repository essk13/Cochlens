<template>
  <div>
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

    <div class="profile-roof"></div>
    <div class="profile-roof-cover"></div>
    <div class="profile-img text-white"></div>
    <div class="profile-description text-white">회원 정보</div>

    <div class="profile-component">
      <profile-main v-if="state.isHome"></profile-main>
      <taking-course v-else-if="state.isTaking"></taking-course>
      <wish-list v-else></wish-list>
    </div>
  </div>
</template>
<script>
import ProfileMain from "@/components/profile/ProfileMain"
import TakingCourse from "@/components/profile/TakingCourse"
import WishList from "@/components/profile/WishList"
import { reactive } from '@vue/reactivity'
import { useStore } from 'vuex'
import { watch } from 'vue'
import { computed } from '@vue/runtime-core'

export default {
  name: 'ProfileView',
  components: {
    ProfileMain,
    TakingCourse,
    WishList,
  },

  setup () {
    const store = useStore()
    const state = reactive({
      isHome: true,
      isTaking: false,
      isWish: false,
      drawer: true,
      drawerItems: [
        { title: '프로필 홈', icon: '', method: clickProfileHome},
        { title: '수강 중인 강좌', icon: '', method: clickProfileTaking},
        { title: '내가 찜한 강좌', icon: '', method: clickProfileWish}
      ],
    })

    // Watch
    watch(
      computed(() => store.state.drawer),
      (newDrawer, oldDrawer) => {
        console.log('new', newDrawer, 'old', oldDrawer)
        state.drawer = !state.drawer
    })

    // Function
    function clickProfileHome() {
      state.isHome = true
      state.isTaking = false
      state.isWish = false
    }

    function clickProfileTaking() {
      state.isHome = false
      state.isTaking = true
      state.isWish = false
    }

    function clickProfileWish() {
      state.isHome = false
      state.isTaking = false
      state.isWish = true
    }

    return {
      state,
      clickProfileHome, clickProfileTaking, clickProfileWish
    }
  }
}
</script>
<style>
  .profile-roof {
    width: 100%;
    height: 180px;
    background-image: url('@/assets/cooking_roof.jpg');
    background-size: cover;
    background-position: 20%;
  }

  .profile-roof-cover {
    width: 100%;
    height: 180px;
    background: black;
    opacity: 30%;
    position: absolute;
    top: 0;
    z-index: 1;
  }

  .profile-img {
    width: 150px;
    height: 150px;
    border-radius: 50px;
    background-image: url('@/assets/kang.png');
    background-size: cover;
    position: absolute;
    z-index: 3;
    top: 60px;
    left: 60px;
    text-align: center;
  }

  .profile-description {
    width: 205px;
    height: 85px;
    border-radius: 5px;
    background: rgb(102, 102, 102);
    position: absolute;
    top: 95px;
    left: 203px;
    text-align: center;
    z-index: 2;
  }

  .profile-component {
    height: 900px;
    width: 100%;
    padding: 60px;
  }
</style>