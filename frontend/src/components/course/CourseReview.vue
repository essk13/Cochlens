<template>
  <q-item v-ripple>
    <q-item-section avatar>
      <q-avatar>
        <img src="https://cdn.quasar.dev/img/avatar2.jpg">
      </q-avatar>
    </q-item-section>

    <q-item-section>
      <q-item-label lines="1">Suzan ★5.0</q-item-label>
      <q-item-label caption lines="2">
        I'll be in your neighborhood doing errands this
        weekend.
      </q-item-label>
    </q-item-section>

    <q-item-section side top>
      1 min ago
    </q-item-section>
  </q-item>

  <q-separator inset="item" />
</template>

<script>
import { reactive } from '@vue/reactivity'

export default {
  name: 'CourseReview',
  components: {
  },

  setup() {
    const state = reactive({
    })

    return {
      state
    }
  }
}
</script>

<style scoped>
</style>