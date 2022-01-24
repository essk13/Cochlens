<template lang="">
  <div>
    <img :src="logo" height="200"/>
    <h1>로그인 페이지</h1>
  </div>
</template>
<script>
import logo from '@/assets/logo.svg'

export default {
  name: 'LoginView',
  components: {
  },

  data () {
    return {
      logo
    }
  }
}
</script>
<style lang="">
</style>