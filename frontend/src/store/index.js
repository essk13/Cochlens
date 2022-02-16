import { createStore } from 'vuex'
import axios from 'axios'

// modules
import courseStore from '@/store/modules/courseStore'
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
    SET_USER( state, data ) {
      state.user = data
    },
    DEL_USER( state ) {
      state.user = null
    }
  },
  actions: {
    userLogin({ commit, dispatch }, data) {
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
        })
        .catch(err => {
          commit('SET_USER')
          console.log(err)
          alert('Err')
          // test
          localStorage.setItem('JWT', 'test')
          commit('SET_USER', 'TEST_USER')
        })
    },

    userLogout({ commit }) {
      localStorage.removeItem('JWT')
      commit('DEL_USER')
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
        url: `${BASE_URL}main`,
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
          alert('err')
        })
    }
  },
  modules: {
    courseStore: courseStore,
    instructorStore: instructorStore,
    profileStore: profileStore
  }
})
