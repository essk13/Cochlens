<template>
  <v-app>
    <v-navigation-drawer
      width="200"
    >
      <v-list
        nav
        dense
      >
        <v-list-item-group
          v-model="group"
          active-class="deep-purple--text text--accent-4"
        >
          <v-list-item>
            <v-list-item-icon>
              <v-icon>mdi-home</v-icon>
            </v-list-item-icon>
            <router-link to="/" class="menu" @click="drawer = false">Cochlens</router-link>
          </v-list-item>

          <v-list-item>
            <v-list-item-icon>
              <v-icon>mdi-account</v-icon>
            </v-list-item-icon>
            <router-link to="/profile" class="menu" @click="clickProfile">내 정보</router-link>
          </v-list-item>

          <v-list-item>
            <v-list-item-icon>
              <v-icon>mdi-account</v-icon>
            </v-list-item-icon>
            <router-link to="/classlist" class="menu" @click="clickClass">강좌 조회</router-link>
          </v-list-item>

          <v-list-item>
            <v-list-item-icon>
              <v-icon>mdi-account</v-icon>
            </v-list-item-icon>
            <router-link to="/instructorlist" class="menu" @click="clickInstructor">강사 조회</router-link>
          </v-list-item>

          <v-list-item>
            <v-list-item-icon>
              <v-icon>mdi-account</v-icon>
            </v-list-item-icon>
            <router-link to="/setting" class="menu" @click="drawer = false">설정</router-link>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>

    <v-navigation-drawer
      v-model="drawer"
      width="250"
    >
      <v-list
        nav
        dense
      >
        <v-list-item-group
          v-model="group"
          active-class="deep-purple--text text--accent-4"
        >
          <v-list-item>
            <v-list-item-icon>
              <v-icon>mdi-home</v-icon>
            </v-list-item-icon>
            <router-link to="/" class="menu">Cochlens</router-link>
          </v-list-item>

          <v-list-item v-for="item in drawerItems" :key="item">
            <h3>{{ item }}</h3>
          </v-list-item>


          <v-list-item>
            <v-list-item-icon>
              <v-icon @click="drawer = false">mdi-arrow-collapse-left</v-icon>
            </v-list-item-icon>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>

    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>
// import { reactive } from '@vue/reactivity'

export default {
  props: {
  },

  data: () => ({
      drawer: false,
      group: null,
      drawerItems: [],
    }),

  methods: {
    clickProfile () {
      this.drawer = true
      this.drawerItems = [
        '프로필', '수강 중인 강좌', '내가 찜한 강좌'
      ]
    },
    clickClass () {
      this.drawer = true
      this.drawerItems = [
        '강의 목록', '라이브 강좌', '인기 강좌'
      ]
    },
    clickInstructor () {
      this.drawer = true
      this.drawerItems = [
        '강사 목록', '강사 프로필', '전체 강좌', '강사 리뷰'
      ]
    },
  }

  // setup() {
  //   const state = reactive({
  //     drawer: false,
  //     group: null,
  //     isLogin: false,
  //     isProfile: false,
  //     isClass: false,
  //     isInstructor: false
  //   })

  //   const clickProfile = () => {
  //     state.drawer = !state.drawer
  //   }

  //   return {
  //     state, clickProfile
  //   }
  // },
  }
</script>

<style>
.menu {
  font-size: 18px;
}
</style>