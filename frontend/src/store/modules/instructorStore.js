import axios from "axios"
const BASE_URL = 'https://localhost:8443/api/v1/'

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
    instructorList: [{
      "courseCount": 10,
      "courseReviewCount": 10,
      "courseReviewList": "course_review_list",
      "courseReviewRateAverage": 4.88,
      "email": "ssafy@ssafy.com",
      "liveOpenCourseList": "live_open_course_list",
      "profileImage": "notexisted",
      "thumbnailImage": "notexisted",
      "userDescription": "notexisted",
      "userId": 0,
      "userName": "ssafy",
      "userNickname": "hotsix",
      "vodOpenCourseList": "vod_open_course_list"
    },],  // 강사 조회 목록
    instructorDetail: {
      "courseCount": 10,
      "courseReviewCount": 10,
      "courseReviewList": "course_review_list",
      "courseReviewRateAverage": 4.88,
      "email": "ssafy@ssafy.com",
      "isCommand": false,
      "isFaceFocusing": false,
      "isSTT": false,
      "isSubtitle": false,
      "liveOpenCourseList": "live_open_course_list",
      "profileImage": "notexisted",
      "role": "User",
      "thumbnailImage": "notexisted",
      "userDescription": "notexisted",
      "userId": 0,
      "userName": "ssafy",
      "userNickname": "hotsix",
      "vodOpenCourseList": "vod_open_course_list"
    },  // 강사 상세 정보
  },
  getters: {
  },
  mutations: {
    // 강사 목록 설정
    SET_INSTRUCTOR_LIST(state, instructorList) {
      state.instructorList = instructorList
    },

    // 강사 상세 정보 설정
    SET_INSTRUCTOR_DETAIL(state, instructorDetail) {
      state.instructorDetail = instructorDetail
    }
  },
  actions: {
    // 강사 목록 조회
    getInstructorList({ commit }) {
      return new Promise((resolve, reject) => {
        axios({
          method: 'get',
          url: `${BASE_URL}users/instructor`,
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