<template>
  <div class="singup-view">
    <div class="full-width full-height row justify-center items-center content-center">
      <div class="col-1"></div>
      <div class="col-5 full-height row justify-center items-center content-center">
        <img @click="clickSignup" :src="state.logo" height="250"/>
      </div>

      <!-- Input -->
      <div class="col-4 row justify-start items-center content-center">
        <div class="login-input-area">
          <p class="unlogin-title">Sing Up</p>
          <!-- E-mail -->
          <q-input
            outlined
            dense
            bg-color="white"
            v-model="state.email"
            label="이메일"
            lazy-rules
            :rules="[ val => val && val.length > 4 || '이메일이 올바르지 않습니다.']"
            class="q-pb-lg"
          />

          <!-- Name -->
          <q-input
            outlined
            dense
            bg-color="white"
            v-model="state.name"
            label="이름"
            lazy-rules
            :rules="[ val => val && val.length > 1 || '이름이 올바르지 않습니다.']"
            class="q-pb-lg"
          />

          <!-- Nickname -->
          <q-input
            outlined
            dense
            bg-color="white"
            v-model="state.nickname"
            label="닉네임"
            lazy-rules
            :rules="[ val => val && val.length > 1 || '별명이 올바르지 않습니다.']"
            class="q-pb-lg"
          />

          <!-- Password -->
          <q-input
            v-model="state.password"
            outlined
            dense
            bg-color="white"
            :type="state.isPwd ? 'password' : 'text'"
            label="비밀번호"
            lazy-rules
            :rules="[ val => val && val.length > 7 || '비밀번호는 8자리 이상입니다.']"
          >
            <template v-slot:append>
              <q-icon
                :name="state.isPwd ? 'visibility_off' : 'visibility'"
                class="cursor-pointer"
                @click="state.isPwd = !state.isPwd"
              />
            </template>
          </q-input>

          <!-- Password Check -->
          <q-input
            v-model="state.passwordCheck"
            outlined
            dense
            bg-color="white"
            :type="state.isPwdC ? 'password' : 'text'"
            label="비밀번호 확인"
            @keyup.enter="clickSingup"
            lazy-rules
            :rules="[ val => val && val == state.password || '비밀번호가 일치하지 않습니다.']"
          >
            <template v-slot:append>
              <q-icon
                :name="state.isPwdC ? 'visibility_off' : 'visibility'"
                class="cursor-pointer"
                @click="state.isPwdC = !state.isPwdC"
              />
            </template>
          </q-input>


          <!-- Button -->
          <q-btn
            @click="clickSingup"
            class="full-width q-mb-md bg-logo-color text-bold"
          >
            회 원 가 입
          </q-btn>

          <div class="row justify-end">
            <p class="q-pr-md q-pb-none">이미 아이디가 있으신가요?</p>
            <a @click="moveLogin" class="text-primary text-bold">로그인하러 가기</a>
          </div>

          <div class="row">
            <div class="col-6 q-pr-sm">
              <q-btn color="white" text-color="dark" class="full-width q-mb-md">
                <img :src="state.google" height="20" class="q-mr-sm">
                구글 로그인
              </q-btn>
            </div>
            <div class="col-6 q-pl-sm">
              <q-btn color="positive" class="full-width">
                <img :src="state.naver" height="18" class="q-mr-sm">
                네이버 로그인
              </q-btn>
            </div>
          </div>
        </div>
      </div>
      <div class="col-1"></div>
    </div>
  </div>
</template>
<script>
import logo from '@/assets/logo.svg'
import google from '@/assets/google.svg'
import naver from '@/assets/naver.svg'
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'LoginView',
  components: {
  },

  setup() {
    const store = useStore()
    const router = useRouter()
    const state = reactive({
      logo,
      google,
      naver,
      email: '',
      name: '',
      nickname: '',
      password: '',
      passwordCheck: '',
      isPwd: true,
      isPwdC: true
    })

    // Created
    if (localStorage.getItem('JWT')) {
      router.push({ name: 'home' })
    }

    function moveLogin() {
      router.push({ name: 'login' })
    }

    function clickSingup() {
      store.dispatch('userSignup',
        { email: state.email, name: state.name, nickname: state.nickname, password: state.password }
      )
      .then(res => {
        console.log(res)
        router.push({ name: 'login' })
      })
    }

    return {
      state, moveLogin, clickSingup
    }
  }
}
</script>
<style>
.singup-view {
  background-color: rgb(211, 239, 255);
  height: 100vh;
  width: 100%;
}
</style>