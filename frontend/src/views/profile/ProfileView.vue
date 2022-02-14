<template>
  <div class="course-roof">
    <div class="profile-roof" id="profile-roof"></div>
    <div class="profile-roof-cover"></div>
    <div class="profile-img text-white" id="profile-img"></div>
    <div class="profile-description q-py-sm">
      <div class="row items-end">
        <p class="q-mr-sm q-my-none text-bold text-white nickname">{{ state.userData.userNickname }}</p>
        <p class="q-my-none text-blue-grey-1">{{ state.userData.role }}</p>
      </div>
      <p class="q-mr-sm q-mt-xs q-mb-none text-blue-grey-1">{{ state.userData.userName }} / {{ state.userData.email }}</p>
      <a @click="moveUpdate" class="q-mr-sm q-mt-xs q-mb-none text-blue-1">프로필 수정</a>
    </div>
  </div>

  <div class="profile-component">
    <profile-main v-if="state.home"></profile-main>
    <taking-course-list v-else-if="state.taking"></taking-course-list>
    <wish-list v-if="state.wishList"></wish-list>
    <profile-update v-if="state.update"></profile-update>
  </div>
</template>
<script>
import ProfileMain from "@/components/profile/ProfileMain"
import TakingCourseList from "@/components/profile/TakingCourseList"
import WishList from "@/components/profile/WishList"
import ProfileUpdate from "@/components/profile/ProfileUpdate"
import { reactive } from '@vue/reactivity'
import { useStore } from 'vuex'
import { watch } from 'vue'
import { computed, onMounted } from '@vue/runtime-core'

export default {
  name: 'ProfileView',
  components: {
    ProfileMain,
    TakingCourseList,
    WishList,
    ProfileUpdate,
  },

  setup () {
    const store = useStore()
    const state = reactive({
      home: true,
      taking: false,
      wish: false,
      update: false,
      userData: store.state.user
    })

    // Mounted
    onMounted(() => {
      store.dispatch('getWishList')
      store.dispatch('getTakingList')
      const profileImg = document.getElementById('profile-img')
      const profileRoof = document.getElementById('profile-roof')
      console.log(state.userData.profileImage)
      console.log(state.userData.thumbnailImage)
      if (state.userData.profileImage) {
        profileImg.style.backgroundImage = `url(${state.userData.profileImage})`
      } else {
        profileImg.style.backgroundImage = `url('/img/kang.968d430f.png')`
      }

      if (state.userData.thumbnailImage) {
        profileRoof.style.backgroundImage = `url(${state.userData.thumbnailImage})`
      } else {
        profileRoof.style.backgroundImage = `url('/img/cooking_roof.c34b6973.jpg')`
      }
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
          state.update = false
        } else if (newComponent === 'taking') {
          state.home = false
          state.taking = true
          state.wish = false
          state.update = false
        } else if (newComponent === 'wish') {
          state.home = false
          state.taking = false
          state.wish = true
          state.update = false
        } else {
          state.home = false
          state.taking = false
          state.wish = false
          state.update = true
        }
    })

    // Function
    function moveUpdate() {
      store.state.profileStore.component = 'update'
    }

    return {
      state, moveUpdate
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
  }

  .profile-description {
    width: 205px;
    height: 85px;
    border-radius: 5px;
    background: rgb(0, 0, 0, 0.7);
    position: absolute;
    top: 95px;
    left: 203px;
    z-index: 2;
    padding-left: 20px;
  }

  .profile-component {
    /* height: 900px; */
    width: 100%;
    padding: 60px;
  }

  .nickname {
    font-size: 20px;
  }
</style>