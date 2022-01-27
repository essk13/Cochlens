<template>
  <div class="login-view">
    <div class="full-width full-height row justify-center items-center content-center">
      <div class="col-1"></div>
      <div class="col-5 full-height row justify-center items-center content-center">
        <img @click="clickLogin" :src="state.logo" height="250"/>
      </div>

      <!-- Input -->
      <div class="col-4 row justify-start items-center content-center">
        <div class="login-input-area">
          <p class="unlogin-title">Sing In</p>
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
            @keyup.enter="clickLogin"
          >
            <template v-slot:append>
              <q-icon
                :name="state.isPwd ? 'visibility_off' : 'visibility'"
                class="cursor-pointer"
                @click="state.isPwd = !state.isPwd"
              />
            </template>
          </q-input>

          <div class="row justify-end">
            <p class="q-pr-md">비밀번호가 기억이 안나시나요?</p>
            <a @click="move" class="text-primary text-bold">비밀번호 찾기</a>
          </div>

          <!-- Button -->
          <q-btn
            @click="clickLogin"
            class="full-width q-mb-md bg-logo-color text-bold"
          >
            로 그 인
          </q-btn>

          <div class="q-px-xl">
            <q-btn
              color="primary"
              @click="moveSingup"
              class="full-width q-mb-md"
            >회원가입</q-btn>
            <q-btn color="white" text-color="dark" class="full-width q-mb-md">구글 로그인</q-btn>
            <q-btn color="positive" class="full-width">네이버 로그인</q-btn>
          </div>
        </div>
      </div>
      <div class="col-1"></div>
    </div>
  </div>
</template>
<script>
import logo from '@/assets/logo.svg'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { reactive } from 'vue'

export default {
  name: 'LoginView',
  components: {
  },

  setup() {
    const store = useStore()
    const router = useRouter()
    const state = reactive({
      logo,
      email: '',
      password: '',
      isPwd: true
    })

    // Created
    if (localStorage.getItem('JWT')) {
      router.push({ name: 'home' })
    }

    function clickLogin() {
      store.dispatch('userLogin', { id: state.email, password: state.password })
      .then(res => {
        console.log(res)
        router.push({ name: 'home' })
      })
    }

    function moveSingup() {
      router.push({ name: 'signup' })
    }

    return {
      state, clickLogin, moveSingup,
    }
  }
}
</script>
<style>
.login-view {
  background-color: rgb(211, 239, 255);
  height: 100%;
  width: 100%;
}

.login-area {
  height: 100%;
}

.login-input-area {
  width: 60%;
}

.unlogin-title {
  font-weight: bold;
  font-size: 25px;
  text-align: end;
}

.bg-logo-color {
  background: rgb(187, 210, 255) !important;
}
</style>