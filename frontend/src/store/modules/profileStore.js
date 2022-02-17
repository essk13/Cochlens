import axios from "axios"
const BASE_URL = 'https://i6d102.p.ssafy.io:8443/api/v1/'

const profileStore = {
  namespaced: true,
  state: {
    component: 'home',
    takingList: [],
    wishList: [],
  },
  getters: {
  },
  mutations: {
    UPDATE_USER({ rootState }, data) {
      rootState.user.userName = data.userName
      rootState.user.userNickname = data.userNickname
      rootState.user.profileImage = data.profileImage
      rootState.user.thumbnailImage = data.thumbnailImage
      rootState.user.userDescription = data.userDescription
    },
    SET_WISHLIST(state, wishList) {
      state.wishList = wishList
    },
    SET_TAKINGLIST(state, takingList) {
      state.takingList = takingList
    },
  },

  actions: {
    // 찜, 수강 목록 조회
    getUserCourse({ commit }) {
      axios({
        method: 'get',
        url: `${BASE_URL}users/profile`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('JWT')}`
        },
      })
        .then((res) => {
          console.log(res)
          commit('SET_TAKINGLIST', res.data.registerCourseList)
          commit('SET_WISHLIST', res.data.wishCourseList)
        })
        .catch((err) => {
          console.log(err)
        })
    },

    // 회원정보 수정
    updateUser({ commit }, data) {
      axios({
        method: 'put',
        url: `${BASE_URL}users/me`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('JWT')}`
        },
        data: data
      })
        .then((res) => {
          console.log(res)
          commit('UPDATE_USER', res.data)
        })
        .catch((err) => {
          console.log(err)
        })
    },

    // 회원 탈퇴
    removeId() {
      axios({
        method: 'delete',
        url: `${BASE_URL}users/me`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('JWT')}`
        }
      })
    },

    // 접근성 설정 변경
    changeOption({ rootState }) {
      const user = rootState.user
      axios({
        method: 'put',
        url: '',
        headers: {
          Authorization: `Bearer ${localStorage.getItem('JWT')}`
        },
        data: {
          isSubtitle: user.isSubtitle,
          isCommand: user.isCommand,
          isFaceFocusing: user.isFaceFocusing,
        }
      })
        .then((res) => {
          console.log(res)
        })
        .catch((err) => {
          console.log(err)
        })
    },

    changeSubtitle({ rootState, dispatch }) {
      rootState.user.isSubtitle = !rootState.user.isSubtitle
      dispatch('changeOption')
    },
    changeCommand({ rootState, dispatch }) {
      rootState.user.isCommand = !rootState.user.isCommand
      dispatch('changeOption')
    },
    changeFocusing({ rootState, dispatch }) {
      rootState.user.isFaceFocusing = !rootState.user.isFaceFocusing
      dispatch('changeOption')
    },
  },
}

export default profileStore