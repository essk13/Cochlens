<template>
  <p>이름</p>
  <q-input outlined v-model="state.userName" label="이름" dense />

  <p class="q-mt-md">닉네임</p>
  <q-input outlined v-model="state.userNickname" label="닉네임" dense />

  <p class="q-mt-md">소개</p>
  <q-input outlined v-model="state.userDescription" label="닉네임" type="textarea" />

  <p class="q-mt-md">프로필 사진</p>
  <q-file outlined dense bottom-slots v-model="state.profileImage" label="프로필 사진" counter max-files="12">
    <template v-slot:after>
      <q-icon name="attach_file" />
    </template>

    <template v-slot:append>
      <q-icon v-if="profileImage !== null" name="close" @click.stop="profileImage = null" class="cursor-pointer" />
      <q-icon name="search" @click.stop />
    </template>

    <template v-slot:hint>
      프로필 사진은 정사각형 형태를 추천합니다.
    </template>
  </q-file>

  <p class="q-mt-md">배경 사진</p>
  <q-file outlined dense bottom-slots v-model="state.thumbnailImage" label="배경 사진" counter max-files="12">
    <template v-slot:after>
      <q-icon name="attach_file" />
    </template>

    <template v-slot:append>
      <q-icon v-if="thumbnailImage !== null" name="close" @click.stop="thumbnailImage = null" class="cursor-pointer" />
      <q-icon name="search" @click.stop />
    </template>
  </q-file>

  <div style="width: 100%; text-align: right;">
    <q-btn @click="clickUpdate" bg-color="blue-1" label="수정" class="q-mr-sm" />
    <q-btn @click="removeId" bg-color="red" label="회원탈퇴" />
  </div>
</template>

<script>
import { reactive } from '@vue/reactivity'
import { useStore } from 'vuex'
export default {
  name: 'ProfileUpdate',
  setup() {
    const store = useStore()
    const userData = store.state.user
    const state = reactive({
      role: userData.role,
      userName: userData.userName,
      userNickname: userData.userNickname,
      profileImage: userData.profileImage,
      thumbnailImage: userData.thumbnailImage,
      userDescription: userData.userDescription,
    })

    // Function
    // 회원정보 수정
    function clickUpdate() {
      store.dispatch('profileStore/updateUser', {
        email: userData.email,
        userName: state.userName,
        userNickname: state.userNickname,
        profileImage: state.profileImage,
        thumbnailImage: state.thumbnailImage,
        userDescription: state.userDescription,
      })
      store.state.profileStore.component = 'home'
    }
    // 회원 탈퇴
    function removeId() {
      store.dispatch('removeId')
      store.dispatch('userLogout')
    }

    return {
      state,
      clickUpdate,
      removeId,
    }
  }
}
</script>

<style>

</style>