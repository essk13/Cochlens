<template>
  <div class="chat-list">
    <div v-for="(chat, index) in state.chatList" :key="index">
      <h6 class="q-ma-none">
        {{ chat.userName }}
      </h6>
      <p>{{ chat.content }}</p>
    </div>
  </div>
  <div>
    <q-input filled v-model="state.text" label="Label" :dense="dense">
      <template v-slot:after>
        <q-btn @click="sendChat" round dense flat icon="send" />
      </template>
    </q-input>
  </div>
</template>

<script>
import SockJS from 'sockjs-client'
import Stomp from 'webstomp-client'

import { reactive } from '@vue/reactivity'
export default {
  name: 'ChatView',
  
  setup() {
    const state = reactive({
      text: '',
      chatList: [],
      stompClient: null,
    })

    // Function
    function connect() {
      var socket = new SockJS('/cochlens')
      state.stompClient = Stomp.over(socket)

      state.stompClient.connect({}, () => {
          subscribeChat()
      })
    }

    const subscribeChat = () => {
      state.stompClient.subscribe(`/topic/lecture`, res => {
        const chatMsg = JSON.parse(res.body)
        state.chatList.push(chatMsg)
      })
    }

    function sendChat() {
      console.log('send')
      // 전달할 객체
      if (state.stompClient) {
        const msg = {
          lectureId: 102,
          userName: 'Mr.깡',
          content: state.text
        }
        state.stompClient.send(`/app/chat`, JSON.stringify(msg), {})
        state.text = ''
      }
    }

    connect()

    return { 
      state,
      connect, sendChat
    }
  }
}
</script>

<style>
.chat-list {
  width: 400px;
  height: 900px;
  background: skyblue;
}
</style>