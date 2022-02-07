<template lang="">
  <div>
    <input onclick="register(); return false;" type="button" name="join" value="Join!">
  </div>
</template>

<script>
import { reactive } from '@vue/reactivity'

export default {
  name: 'HomeView',
  components: {
  },

  setup() {
    const state = reactive({
    })

    return {
      state,
    }
  }
}
</script>

<style>
  
</style>