<template>
  <p>이름</p>
  <q-input outlined v-model="state.userName" label="이름" dense />
  <p class="q-mt-md">닉네임</p>
  <q-input outlined v-model="state.userNickname" label="닉네임" dense />
  <p v-if="state.role === 'INSTRUCTOR'" class="q-mt-md">소개</p>
  <q-input v-if="state.role === 'INSTRUCTOR'" outlined v-model="state.userDescription" label="닉네임" type="textarea" />

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

  <p class="q-mt-md">비밀번호 확인</p>
  <q-input
    v-model="state.password"
    outlined
    dense
    bg-color="white"
    :type="state.isPwd ? 'password' : 'text'"
    label="비밀번호"
    lazy-rules
    :rules="[ val => val && val.length > 7 || '비밀번호는 8자리 이상입니다.']"
  >
    <template v-slot:append>
      <q-icon
        :name="state.isPwd ? 'visibility_off' : 'visibility'"
        class="cursor-pointer"
        @click="state.isPwd = !state.isPwd"
      />
    </template>
  </q-input>

  <q-btn @click="clickUpdate" bg-color="blue-1" label="수정" style="right: 0;" />
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
      userName: userData.userName,
      userNickname: userData.userNickname,
      userDescription: userData.userDescription,
      profileImage: userData.profileImage,
      thumbnailImage: userData.thumbnailImage,
      password: '',
      isPwd: true,
      role: userData.role,
    })

    // Function
    function clickUpdate() {
      console.log(store.state.user.userName)
      store.state.user.userName = state.userName
      store.state.user.userNickname = state.userNickname
      store.state.user.userDescription = state.userDescription
      store.state.user.profileImage = state.profileImage
      store.state.user.thumbnailImage = state.thumbnailImage
      store.dispatch('profileStore/updateUser')
      store.state.profileStore.component = 'home'
    }

    return {
      state, clickUpdate
    }
  }
}
</script>

<style>

</style>