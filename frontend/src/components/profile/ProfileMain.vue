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
        <div class="q-mb-md">
          <q-btn-toggle
            v-model="state.subtitle"
            class="my-custom-toggle"
            padding="0px 8px"
            no-caps
            rounded
            unelevated
            toggle-color="primary"
            color="white"
            text-color="primary"
            :options="[
              {label: 'ON', value: 'on'},
              {label: 'OFF', value: 'off'}
            ]"
          />
          자막
        </div>

        <!-- 동작 명령어 -->
        <div class="q-my-md">
          <q-btn-toggle
            v-model="state.command"
            class="my-custom-toggle"
            padding="0px 8px"
            no-caps
            rounded
            unelevated
            toggle-color="primary"
            color="white"
            text-color="primary"
            :options="[
              {label: 'ON', value: 'on'},
              {label: 'OFF', value: 'off'}
            ]"
          />
          동작 명령어
        </div>

        <!-- 문자 음성 변환 -->
        <div class="q-my-md">
          <q-btn-toggle
            v-model="state.tts"
            class="my-custom-toggle"
            padding="0px 8px"
            no-caps
            rounded
            unelevated
            toggle-color="primary"
            color="white"
            text-color="primary"
            :options="[
              {label: 'ON', value: 'on'},
              {label: 'OFF', value: 'off'}
            ]"
          />
          문자 음성 변환
        </div>

        <!-- 강사 얼굴 확대 -->
        <div class="q-mt-md">
          <q-btn-toggle
            v-model="state.faceFocusing"
            class="my-custom-toggle"
            padding="0px 8px"
            no-caps
            rounded
            unelevated
            toggle-color="primary"
            color="white"
            text-color="primary"
            :options="[
              {label: 'ON', value: 'on'},
              {label: 'OFF', value: 'off'}
            ]"
          />
          얼굴 확대
        </div>
      </div>
    </div>
  </div>

  <!-- 하단 메뉴 -->
  <div class="row profile-downer-area">
    <div class="col-6 q-pr-md">
      <dir class="row">
        <p class="profile-menu-title q-mr-sm">수강 중인 강좌</p>
        <a href="">더보기</a>
      </dir>
      <div class="profile-home-row-menu">
        <div
          class="course-profile q-mb-md"
          v-for="course in state.takingCourseList"
          :key="course"
        ></div>
      </div>
    </div>

    <div class="col-6 q-pl-md">
      <dir class="row">
        <p class="profile-menu-title q-mr-sm">내가 찜한 강좌</p>
        <a href="">더보기</a>
      </dir>
      <div class="profile-home-row-menu">
        <div
          class="course-profile q-mb-md"
          v-for="wcourse in state.wishCourseList"
          :key="wcourse"
        ></div>
      </div>
    </div>
  </div>
</template>
<script>
import { reactive } from "vue"

export default {
  name: 'ProfileMain',
  setup () {
    const state = reactive({
      subtitle: 'on',
      command: 'on',
      tts: 'on',
      faceFocusing: 'on',
      takingCourseList: [1, 2, 3, 4],
      wishCourseList: [1, 2, 3, 4]
    })
    return {
      state
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