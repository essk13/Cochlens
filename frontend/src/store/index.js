import { createStore } from 'vuex'
import axios from 'axios'

// modules
import classroomStore from '@/store/modules/classroomStore'
import instructorStore from '@/store/modules/instructorStore'
import profileStore from '@/store/modules/profileStore'

const BASE_URL = 'https://localhost:8443/api/v1/'

export default createStore({
  state: {
    user: null,
  },
  getters: {
  },
  mutations: {
    SET_USER( state ) {
      state.user = 'loginUser'
    },
    DEL_USER( state ) {
      state.user = null
    }
  },
  actions: {
    userLogin({ commit }, data) {
      axios({
        method: 'post',
        url: `${BASE_URL}auth/login`,
        data: data
      })
        .then(res => {
          console.log(res)
          alert('환영합니다.')
          const token = res.data.accessToken
          localStorage.setItem('JWT', token)
          commit('SET_USER')
        })
        .catch(err => {
          console.log(err)
          alert('Err')
        })
    },
    userLogout({ commit }) {
      commit('DEL_USER')
    },
    userSignup(data) {
      console.log(data)
      axios({
        method: 'post',
        url: `${BASE_URL}users`,
        data: data
      })
        .then(res => {
          console.log(res)
          alert('회원가입이 완료되었습니다.')
        })
        .catch(err => {
          console.log(err)
          alert('Err')
        })
    },
    set_user({ commit }) {
      commit('SET_USER')
    }
  },
  modules: {
    classroomStore: classroomStore,
    instructorStore: instructorStore,
    profileStore: profileStore
  }
})
