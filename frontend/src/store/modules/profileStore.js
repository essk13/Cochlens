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
      const user = rootState.user
      axios({
        method: 'put',
        url: `${BASE_URL}users/me`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('JWT')}`
        },
        data: {
          email: user.email,
          isCommand: user.isCommand,
          isFaceFocusing: user.isFaceFocusing,
          isSTT: user.isSTT,
          isSubtitle: user.isSubtitle,
          // 임시
          password: "asdfasdfasdf",
          profileImage: user.profileImage,
          role: user.role,
          thumbnailImage: user.thumbnailImage,
          userDescription: user.userDescription,
          userName: user.userName,
          userNickname: user.userNickname
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