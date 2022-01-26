<template>
  <div class="login-view">
    <div class="full-width full-height row justify-center items-center content-center">
      <div class="col-6 full-height row justify-center items-center content-center">
        <img @click="clickLogin" :src="state.logo" height="250"/>
      </div>

      <!-- Input -->
      <div class="col-6 row justify-start items-center content-center">
        <div class="login-input-area">
          <p class="unlogin-title">Sing In</p>
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

          <div class="row justify-end">
            <p class="q-pr-md">비밀번호가 기억이 안나시나요?</p>
            <a @click="move" class="text-primary text-bold">비밀번호 찾기</a>
          </div>

          <div class="q-px-xl">
            <q-btn
              color="primary"
              class="full-width q-mb-md"
              @click="moveSingup"
            >회원가입</q-btn>
            <q-btn color="white" text-color="dark" class="full-width q-mb-md">구글 로그인</q-btn>
            <q-btn color="positive" class="full-width">네이버 로그인</q-btn>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import logo from '@/assets/logo.svg'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { ref } from 'vue'

export default {
  name: 'LoginView',
  components: {
  },

  setup() {
    const store = useStore()
    const router = useRouter()
    const state = {
      logo,
    }

    const email = ref('')
    const password = ref('')
    const isPwd = ref(true)

    function clickLogin() {
      store.dispatch('userLogin')
      router.push({ name: 'home' })
    }

    function moveSingup() {
      router.push({ name: 'signup' })
    }

    return {
      state, clickLogin, moveSingup,
      email, password, isPwd,
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
</style>