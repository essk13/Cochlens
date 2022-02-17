<template>
  <q-item v-ripple>
    <q-item-section avatar>
      <q-avatar>
        <img src="https://cdn.quasar.dev/img/avatar2.jpg">
        <!-- <img :src="state.profileImageUrl"> -->
      </q-avatar>
    </q-item-section>

    <q-item-section>
      <!-- <q-item-label lines="1">Suzan ★5.0</q-item-label> -->
      <q-item-label lines="1">{{ props.courseReview.author.userNickname }} ★{{ props.courseReview.reviewRate }}</q-item-label>
      <!-- <q-item-label caption lines="2">
        I'll be in your neighborhood doing errands this
        weekend.
      </q-item-label> -->
      <q-item-label caption lines="2">
        {{ props.courseReview.reviewContent }}
      </q-item-label>
    </q-item-section>

    <!-- <q-item-section side top>
      1 min ago
    </q-item-section> -->
    <q-item-section side top>
      {{ props.courseReview.reviewDate }}
    </q-item-section>
  </q-item>

  <q-separator inset="item" />
</template>

<script>
import { reactive, computed } from '@vue/reactivity'

export default {
  name: 'CourseReview',
  components: {
  },
  props: {
    courseReview: Object,
  },
  setup(props) {
    const state = reactive({
      profileImageUrl: computed(() => props.courseReview.author.profileImage)
    })

    return {
      state, props,
    }
  }
}
</script>

<style scoped>
.q-item-type {
  position: static;
}

.q-avatar {
  position: static;
}
</style>