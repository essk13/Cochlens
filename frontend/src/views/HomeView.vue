<template>
  <div class="home">
    <h1>메인 페이지</h1>
  </div>
</template>

<script>
// @ is an alias to /src

import router from "@/router"

export default {
  name: 'HomeView',
  components: {
  },

  setup() {
    // Created
    if (localStorage.getItem('JWT')) {
      console.log('login!')
    } else {
      router.push({ name: 'login' })
    }

    return {}
  }
}
</script>
