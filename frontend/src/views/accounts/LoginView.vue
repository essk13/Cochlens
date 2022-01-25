<template lang="">
  <div>
    <img @click="clickLogin" :src="state.logo" height="200"/>
    <h1>로그인 페이지</h1>
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
      logo
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
<style lang="">
</style>