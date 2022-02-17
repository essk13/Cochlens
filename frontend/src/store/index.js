import { createStore } from 'vuex'
import axios from 'axios'
import createPersistedState from 'vuex-persistedstate';

// modules
import courseStore from '@/store/modules/courseStore'
import instructorStore from '@/store/modules/instructorStore'
import profileStore from '@/store/modules/profileStore'

const BASE_URL = 'https://i6d102.p.ssafy.io:8443/'

export default createStore({
  plugins: [createPersistedState()],
  state: {
    user: null,
  },
  getters: {
  },
  mutations: {
    SET_USER( state, data ) {
      state.user = data
    },
    DEL_USER( state ) {
      state.user = null
    }
  },
  actions: {
    userLogin({ commit, dispatch }, data) {
      return new Promise((resolve, reject) => {
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
            dispatch('getUserData')
            resolve(res)
          })
          .catch(err => {
            commit('SET_USER')
            console.log(err)
            alert('아이디나 비밀번호를 확인해주세요.')
            reject(err)
          })
      })
    },

    userLogout({ commit }) {
      return new Promise((resolve) => {
        localStorage.removeItem('JWT')
        localStorage.removeItem('vuex')
        commit('DEL_USER')
        .then(resolve())
      })
    },

    userSignup({ state }, data) {
      console.log(state, data)
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

    getUserData({ commit }) {
      axios({
        method: 'get',
        url: `${BASE_URL}users/me`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('JWT')}`
        }
      })
        .then(res => {
          commit('SET_USER', res.data)
        })
        .catch(err => {
          console.log(err)
          alert('err')
        })
    },

    // 홈 화면 데이터 조회
    getHomeData({ commit }) {
      axios({
        method: 'get',
        url: `${BASE_URL}users/main`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('JWT')}`
        }
      })
        .then(res => {
          commit('profileStore/SET_TAKINGLIST', res.data.registerCourseList)
          commit('instructorStore/SET_BEST_INSTRUCTOR_LIST', res.data.instructorList)
          commit('courseStore/SET_BEST_COURSE_LIST', res.data.courseList)
        })
        .catch(err => {
          console.log(err)
        })
    }
  },
  modules: {
    courseStore: courseStore,
    instructorStore: instructorStore,
    profileStore: profileStore
  }
})
