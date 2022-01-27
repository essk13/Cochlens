<template>
  <!-- 상단 메뉴 -->
  <div class="row profile-upper-area q-px-xl q-mb-xl">
    <div class="col-6 q-pa-md">
      <p>시간표</p>
      <div class="profile-home-menu"></div>
    </div>
    <div class="col-6  q-pa-md">
      <p>접근성 설정</p>
      <div class="profile-home-menu"></div>
    </div>
  </div>

  <!-- 하단 메뉴 -->
  <div class="row profile-downer-area q-px-xl">
    <div class="col-6 q-pa-md">
      <p>수강중인 강좌</p>
      <div class="profile-home-menu"></div>
    </div>
    <div class="col-6  q-pa-md">
      <p>내가 찜한 강좌</p>
      <div class="profile-home-menu"></div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'ProfileMain',
}
</script>
<style>
  .profile-upper-area {
    width: 100%;
    height: 30%;
  }

  .profile-downer-area {
    width: 100%;
    height: 70%;
  }

  .profile-home-menu {
    height: 100%;
    width: 100%;
    border-radius: 5px;
    background: gray;
  }
</style>