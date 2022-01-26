import { createStore } from 'vuex'

// modules
import classroomStore from '@/store/modules/classroomStore'
import instructorStore from '@/store/modules/instructorStore'
import profileStore from '@/store/modules/profileStore'

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
    userLogin({ commit }) {
      commit('SET_USER')
    },
    userLogout({ commit }) {
      commit('DEL_USER')
    }
  },
  modules: {
    classroomStore: classroomStore,
    instructorStore: instructorStore,
    profileStore: profileStore
  }
})
