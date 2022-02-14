<template>
  <!-- 상단 메뉴 -->
  <div class="row profile-upper-area q-mb-lg">
    <div class="col-6 q-pr-md">
      <p class="profile-menu-title">시간표</p>
      <div class="profile-home-menu"></div>
    </div>

    <div class="col-6 q-pl-md">
      <p class="profile-menu-title">접근성 설정</p>
      <div class="profile-home-menu">
        <!-- 자막 -->
        <div>
          <q-toggle
            v-model="state.subtitle"
            checked-icon="check"
            color="blue"
            unchecked-icon="clear"
            @click="clickSubtitle"
          />
          자막
        </div>

        <!-- 동작 명령어 -->
        <div>
          <q-toggle
            v-model="state.command"
            checked-icon="check"
            color="blue"
            unchecked-icon="clear"
            @click="clickCommand"
          />
          동작 명령어
        </div>

        <!-- 강사 얼굴 확대 -->
        <div>
          <q-toggle
            v-model="state.faceFocusing"
            checked-icon="check"
            color="blue"
            unchecked-icon="clear"
            @click="clickFocusing"
          />
          얼굴 확대
        </div>
      </div>
    </div>
  </div>

  <!-- 하단 메뉴 -->
  <div class="row profile-downer-area">
    <div class="col-6 q-pr-md">
      <div class="row">
        <p class="profile-menu-title q-mr-sm">수강 중인 강좌</p>
        <a href="">더보기</a>
      </div>
      <div v-if="state.takingList" class="profile-home-row-menu">
        <taking-course-vue
          class="q-mb-md"
          v-for="(takingItem, index) in state.takingList"
          :key="index"
          :wish-item="takingItem"
        >
        </taking-course-vue>
      </div>
      <div v-else class="profile-home-row-menu">
        수강 중인 강좌가 없습니다.
      </div>
    </div>

    <div class="col-6 q-pl-md">
      <div class="row">
        <p class="profile-menu-title q-mr-sm">내가 찜한 강좌</p>
        <a href="">더보기</a>
      </div>
      <div v-if="state.wishList" class="profile-home-row-menu">
        <taking-course-vue
          class="q-mb-md"
          v-for="(wishItem, index) in state.wishList"
          :key="index"
          :wish-item="wishItem"
        >
        </taking-course-vue>
      </div>
      <div v-else class="profile-home-row-menu">
        찜한 강좌가 없습니다.
      </div>
    </div>
  </div>
</template>
<script>
import TakingCourseVue from "./TakingCourse.vue"
import { reactive } from "vue"
import { useStore } from 'vuex'

export default {
  name: 'ProfileMain',
  components: {
    TakingCourseVue,
  },
  setup () {
    const store = useStore()
    const state = reactive({
      subtitle: store.state.user.isSubtitle,
      command: store.state.user.isCommand,
      faceFocusing: store.state.user.isFaceFocusing,
      takingList: store.state.profileStore.takingList,
      wishList: store.state.profileStore.wishList,
    })

    // Function
    function clickSubtitle() {
      console.log('sub')
      store.dispatch('profileStore/changeSubtitle')
    }
    function clickCommand() {
      console.log('com')
      store.dispatch('profileStore/changeCommand')
    }
    function clickFocusing() {
      console.log('face')
      store.dispatch('profileStore/changeFocusing')
    }

    return {
      state,
      clickSubtitle,
      clickCommand,
      clickFocusing,
    }
  }
}
</script>
<style>
  .profile-upper-area {
    width: 100%;
    height: 30%;
  }

  .profile-downer-area {
    width: 100%;
    height: 70%;
  }

  .profile-home-menu {
    height: 85%;
    width: 100%;
    padding: 25px;
    border-radius: 5px;
    background: gray;
  }

  .profile-home-row-menu {
    height: 90%;
    width: 100%;
    padding: 15px;
    border-radius: 5px;
    background: gray;
  }

  .profile-menu-title {
    font-size: 15px;
    font-weight: bold;
  }

  .course-profile {
    width: 100%;
    height: 100px;
    border-radius: 5px;
    background: rgb(75, 75, 75);
  }
</style>