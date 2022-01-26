<template>
  <div class="singup-view">
    <div class="full-width full-height row justify-center items-center content-center">
      <div class="col-6 full-height row justify-center items-center content-center">
        <img @click="clickSignup" :src="state.logo" height="250"/>
      </div>

      <!-- Input -->
      <div class="col-6 row justify-start items-center content-center">
        <div class="login-input-area">
          <p class="unlogin-title">Sing Up</p>
          <!-- E-mail -->
          <q-input
            outlined
            bg-color="white"
            v-model="email"
            label="이메일"
            lazy-rules
            :rules="[ val => val && val.length > 4 || '이메일이 올바르지 않습니다.']"
            class="q-pb-lg"
          />

          <!-- Name -->
          <q-input
            outlined
            bg-color="white"
            v-model="name"
            label="이름"
            lazy-rules
            :rules="[ val => val && val.length > 1 || '이름이 올바르지 않습니다.']"
            class="q-pb-lg"
          />

          <!-- Nickname -->
          <q-input
            outlined
            bg-color="white"
            v-model="nickname"
            label="닉네임"
            lazy-rules
            :rules="[ val => val && val.length > 1 || '별명이 올바르지 않습니다.']"
            class="q-pb-lg"
          />

          <!-- Password -->
          <q-input
            v-model="password"
            outlined
            bg-color="white"
            :type="isPwd ? 'password' : 'text'"
            label="비밀번호"
            lazy-rules
            :rules="[ val => val && val.length > 7 || '비밀번호는 8자리 이상입니다.']"
          >
            <template v-slot:append>
              <q-icon
                :name="isPwd ? 'visibility_off' : 'visibility'"
                class="cursor-pointer"
                @click="isPwd = !isPwd"
              />
            </template>
          </q-input>

          <!-- Password Check -->
          <q-input
            v-model="passwordCheck"
            outlined
            bg-color="white"
            :type="isPwdC ? 'password' : 'text'"
            label="비밀번호 확인"
            lazy-rules
            :rules="[ val => val && val == password || '비밀번호가 일치하지 않습니다.']"
          >
            <template v-slot:append>
              <q-icon
                :name="isPwdC ? 'visibility_off' : 'visibility'"
                class="cursor-pointer"
                @click="isPwdC = !isPwdC"
              />
            </template>
          </q-input>

          <div class="row justify-end">
            <p class="q-pr-md q-pb-none">이미 아이디가 있으신가요?</p>
            <a @click="moveLogin" class="text-primary text-bold">로그인하러 가기</a>
          </div>

          <div class="row">
            <div class="col-6 q-pr-sm">
              <q-btn color="white" text-color="dark" class="full-width q-mb-md">구글 로그인</q-btn>
            </div>
            <div class="col-6 q-pl-sm">
              <q-btn color="positive" class="full-width">네이버 로그인</q-btn>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import logo from '@/assets/logo.svg'
import { ref } from 'vue'
// import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'LoginView',
  components: {
  },

  setup() {
    // const store = useStore()
    const router = useRouter()
    const state = {
      logo,
    }

    const email = ref('')
    const name = ref('')
    const nickname = ref('')
    const password = ref('')
    const passwordCheck = ref('')
    const isPwd = ref(true)
    const isPwdC = ref(true)

    function moveLogin() {
      router.push({ name: 'login' })
    }

    return {
      state, moveLogin,
      email, name, nickname, password, passwordCheck, isPwd, isPwdC
    }
  }
}
</script>
<style>
.singup-view {
  background-color: rgb(211, 239, 255);
  height: 100%;
  width: 100%;
}
</style>