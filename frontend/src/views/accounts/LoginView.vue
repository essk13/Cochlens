<template>
  <div class="login-view">
    <v-container class="login-area">
      <v-row class="login-area">
        <!-- LOGO -->
        <v-col
          cols="6"
          class="d-flex justify-center align-center login-area"
        >
          <img @click="clickLogin" :src="state.logo" height="250"/>
        </v-col>

        <!-- Input Login -->
        <v-col
          cols="6"
          class="d-flex align-center login-area"
        >
          <div class="login-input-area">
            <h3>Sing In</h3>
            <q-input outlined v-model="text" label="Outlined" />
            <q-input standout v-model="text" label="Standout" />

            <div>
              <p>비밀번호가 기억이 안나시나요?</p>
              <a href="">비밀번호 찾기</a>
            </div>
          </div>
        </v-col>
      </v-row>
    </v-container>
    <div>

    </div>
  </div>
</template>
<script>
import logo from '@/assets/logo.svg'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'LoginView',
  components: {
  },

  setup() {
    const store = useStore()
    const router = useRouter()

    const state = {
      logo,
      email: '',
      password: '',
      emailRules: [v => v.length <= 50 || 'Max 50 characters'],
      passwordRules: [v => v.trim().split(' ').length <= 30 || 'Max 30 words'],
    }

    function clickLogin() {
      store.dispatch('userLogin')
      router.push({ name: 'home' })
    }

    return {
      state, clickLogin
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
</style>