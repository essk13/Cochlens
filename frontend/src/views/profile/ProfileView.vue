<template>
  <div>
    <div class="profile-roof"></div>
    <div class="profile-img text-white">프로필 사진</div>
    <div class="profile-description text-white">회원 정보</div>
    <profile-main></profile-main>
    <taking-class></taking-class>
    <wish-list></wish-list>
  </div>
</template>
<script>
import ProfileMain from "@/components/profile/ProfileMain"
import TakingClass from "@/components/profile/TakingClass"
import WishList from "@/components/profile/WishList"

export default {
  name: 'ProfileView',
  components: {
    ProfileMain,
    TakingClass,
    WishList,
  },
}
</script>
<style>
  .profile-roof {
    width: 100%;
    height: 180px;
    background: gray;
  }

  .profile-img {
    width: 150px;
    height: 150px;
    border-radius: 50px;
    background: rgb(63, 63, 63);
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
  }
</style>