<template lang="">
  <div>
    <input @click="register()" type="button" name="join" value="Join!">
  </div>
</template>

<script>
import { reactive } from '@vue/reactivity'

export default {
  name: 'CourseDetailView',
  components: {},
  setup() {
    const state = reactive({
    })
    function register() {
      name = document.getElementById('name').value;
      let room = document.getElementById('roomName').value;

      document.getElementById('room-header').innerText = 'ROOM ' + room;
      document.getElementById('join').style.display = 'none';
      document.getElementById('room').style.display = 'block';

      let message = {
        id : 'joinRoom',
        name : name,
        room : room,
      }
      sendMessage(message);
    }

    return {
      state, register,
    }
  }
}
</script>

<style>
  
</style>