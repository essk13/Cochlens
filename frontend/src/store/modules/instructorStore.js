import axios from "axios"
const BASE_URL = 'https://i6d102.p.ssafy.io:8443/'

// JWT 토큰 header 생성
const setHeader = function () {
  const token = localStorage.getItem('JWT')
  const header = {
    Authorization: `Bearer ${token}`
  }
  return header
}

const instructorStore = {
  namespaced: true,
  state: {
    instructorList: [],  // 강사 조회 목록
    bestInstructorList: [],  // 인기 강사 조회 목록
    instructorDetail: [],  // 강사 상세 정보
  },
  getters: {
  },
  mutations: {
    // 강사 목록 설정
    SET_INSTRUCTOR_LIST(state, instructorList) {
      if (instructorList != []) {
        state.instructorList = instructorList
      }
    },

    // 인기 강사 목록 설정
    SET_BEST_INSTRUCTOR_LIST(state, bestInstructorList) {
      if (bestInstructorList != []) {
        state.bestInstructorList = bestInstructorList
      }
    },

    // 강사 상세 정보 설정
    SET_INSTRUCTOR_DETAIL(state, instructorDetail) {
      state.instructorDetail = instructorDetail
    }
  },
  actions: {
    // 강사 목록 조회
    getInstructorList({ commit }, data) {
      return new Promise((resolve, reject) => {
        axios({
          method: 'get',
          url: `${BASE_URL}users/instructor?page=${data.page}&size=${data.size}`,
          headers: setHeader()
        })
        .then(res => {
          commit('SET_INSTRUCTOR_LIST', res.data)
          resolve(res)
        })
        .catch(err => {
          console.log(err)
          reject(err)
        })
      })
    },

    // 인기 강사 목록 조회
    getBestInstructorList({ commit }) {
      return new Promise((resolve, reject) => {
        axios({
          method: 'get',
          url: `${BASE_URL}users/instructor/best`,
          headers: setHeader()
        })
        .then(res => {
          commit('SET_BEST_INSTRUCTOR_LIST', res.data)
          resolve(res)
        })
        .catch(err => {
          console.log(err)
          reject(err)
        })
      })
    },

    // 강사 검색
    searchInstructor({ commit }, data) {
      return new Promise((resolve, reject) => {
        axios({
          method: 'get',
          url: `${BASE_URL}users/instructor/search?instructorName=${data.text}&page=${data.page}&size=${data.size}`,
          headers: setHeader()
        })
        .then(res => {
          commit('SET_INSTRUCTOR_LIST', res.data)
          resolve(res)
        })
        .catch(err => {
          console.log(err)
          reject(err)
        })
      })
    },

    // 강사 상세 정보 조회
    getInstructorDetail({ commit }, instructorId) {
      return new Promise((resolve, reject) => {
        axios({
          method: 'get',
          url: `${BASE_URL}users/${instructorId}`,
          headers: setHeader()
        })
        .then(res => {
          commit('SET_INSTRUCTOR_DETAIL', res.data)
          resolve(res)
        })
        .catch(err => {
          console.log(err)
          reject(err)
        })
      })
    }
  },
}

export default instructorStore