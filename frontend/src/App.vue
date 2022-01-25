<template>
  <v-app>
    <v-navigation-drawer
      width="200"
      class="navbar"
      v-model="state.mainNav"
    >
      <v-list
        nav
        dense
      >
        <v-list-item-group
          v-model="state.group"
          active-class="deep-purple--text text--accent-4"
        >
          <v-list-item>
            <v-list-item-icon>
              <v-icon>mdi-home</v-icon>
            </v-list-item-icon>
            <router-link to="/" class="menu" @click="state.drawer = false">Cochlens</router-link>
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
            <router-link to="/setting" class="menu" @click="state.drawer = false">설정</router-link>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>

    <v-navigation-drawer
      v-model="state.drawer"
      class="sub-nav"
      width="250"
    >
        <v-btn-toggle
          v-model="state.toggle_item"
          mandatory
        >
          <v-btn
            v-for="item in state.drawerItems"
            :key="item"
            block
            variant="none"
            class="ma-0 pa-0"
            :rounded="0"
          >
            {{ item }}
          </v-btn>
        </v-btn-toggle>

        <v-btn
          variant="none"
        >
          <v-icon @click="state.drawer = false">mdi-arrow-collapse-left</v-icon>
        </v-btn>

    </v-navigation-drawer>

    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>
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
      drawer: false,
      drawerItems: [],
      group: null,
      mainNav: true,
      toggle_item: false
    })

    // Created
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
    function clickProfile() {
      state.drawer = true
      state.drawerItems = [
        '프로필', '수강 중인 강좌', '내가 찜한 강좌'
      ]
    }

    function clickClass() {
      state.drawer = true
      state.drawerItems = [
        '강의 목록', '라이브 강좌', '인기 강좌'
      ]
    }

    function clickInstructor() {
      state.drawer = true
      state.drawerItems = [
        '강사 목록', '강사 프로필', '전체 강좌', '강사 리뷰'
      ]
    }

    return {
      state, clickProfile, clickClass, clickInstructor
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

.menu {
  font-size: 18px;
}
</style>