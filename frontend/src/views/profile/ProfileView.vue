<template>
  <div class="course-roof">
    <div class="profile-roof"></div>
    <div class="profile-roof-cover"></div>
    <div class="profile-img text-white"></div>
    <div class="profile-description text-white">회원 정보</div>
  </div>

  <div class="profile-component">
    <profile-main v-if="state.home"></profile-main>
    <taking-course v-else-if="state.taking"></taking-course>
    <wish-list v-else></wish-list>
  </div>
</template>
<script>
import ProfileMain from "@/components/profile/ProfileMain"
import TakingCourse from "@/components/profile/TakingCourse"
import WishList from "@/components/profile/WishList"
import { reactive } from '@vue/reactivity'
import { useStore } from 'vuex'
import { watch } from 'vue'
import { computed } from '@vue/runtime-core'

export default {
  name: 'ProfileView',
  components: {
    ProfileMain,
    TakingCourse,
    WishList,
  },

  setup () {
    const store = useStore()
    const state = reactive({
      home: true,
      taking: false,
      wish: false,
    })

    // Watch
    watch(
      computed(() => store.state.profileStore.component),
      (newComponent, oldComponent) => {
        console.log('new', newComponent, 'old', oldComponent)
        if (newComponent === 'home') {
          state.home = true
          state.taking = false
          state.wish = false
        } else if (newComponent === 'taking') {
          state.home = false
          state.taking = true
          state.wish = false
        } else {
          state.home = false
          state.taking = false
          state.wish = true
        }
    })

    return {
      state,
    }
  }
}
</script>
<style>
  .profile-roof {
    width: 100%;
    height: 180px;
    background-image: url('@/assets/cooking_roof.jpg');
    background-size: cover;
    background-position: 20%;
  }

  .profile-roof-cover {
    width: 100%;
    height: 180px;
    background: black;
    opacity: 30%;
    position: absolute;
    top: 0;
    z-index: 1;
  }

  .profile-img {
    width: 150px;
    height: 150px;
    border-radius: 50px;
    background-image: url('@/assets/kang.png');
    background-size: cover;
    position: absolute;
    z-index: 3;
    top: 60px;
    left: 60px;
    text-align: center;
  }

  .profile-description {
    width: 205px;
    height: 85px;
    border-radius: 5px;
    background: rgb(102, 102, 102);
    position: absolute;
    top: 95px;
    left: 203px;
    text-align: center;
    z-index: 2;
  }

  .profile-component {
    height: 900px;
    width: 100%;
    padding: 60px;
  }
</style>