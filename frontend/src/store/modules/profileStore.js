import axios from "axios"
const BASE_URL = 'https://localhost:8443/api/v1/'

const profileStore = {
  namespaced: true,
  state: {
    component: 'home',
    takingList: null,
    wishList: null,
  },
  getters: {
  },
  mutations: {
    SET_WISHLIST({ state }, wishList) {
      state.wishList = wishList
    },

    SET_TAKINGLIST({ state }, takingList) {
      state.takingList = takingList
    }
  },

  actions: {
    getWishList({ commit }) {
      axios({
        method: 'get',
        url: `${BASE_URL}users/me/wishlist`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('JWT')}`
        },
      })
        .then((res) => {
          console.log(res)
          commit('SET_WISHLIST', res.data)
        })
        .catch((err) => {
          console.log(err)
        })
    },

    getTakingList({ commit }) {
      axios({
        method: 'get',
        url: `${BASE_URL}course/recent`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('JWT')}`
        },
      })
        .then((res) => {
          console.log(res)
          commit('SET_TAKINGLIST', res.data)
        })
        .catch((err) => {
          console.log(err)
        })
    },

    updateUser({ rootState }) {
      console.log(rootState)
      axios({
        method: 'put',
        url: `${BASE_URL}users/me`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('JWT')}`
        },
        data: {
          email: rootState.user.email,
          isCommand: rootState.user.isCommand,
          isFaceFocusing: rootState.user.isFaceFocusing,
          isSTT: rootState.user.isSTT,
          isSubtitle: rootState.user.isSubtitle,
          // 임시
          password: "asdfasdfasdf",
          profileImage: rootState.user.profileImage,
          role: rootState.user.role,
          thumbnailImage: rootState.user.thumbnailImage,
          userDescription: rootState.user.userDescription,
          userName: rootState.user.userName,
          userNickname: rootState.user.userNickname
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
      console.log('change')
      rootState.user.isSubtitle = !rootState.user.isSubtitle
      console.log(rootState.user.isSubtitle)
      dispatch('updateUser')
    },

    changeCommand({ rootState, dispatch }) {
      rootState.user.isCommand = !rootState.user.isCommand
      dispatch('updateUser')
    },

    changeFocusing({ rootState, dispatch }) {
      rootState.user.isFaceFocusing = !rootState.user.isFaceFocusing
      dispatch('updateUser')
    },
  },
}

export default profileStore