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
    }
  },
  actions: {
    userLogin({ commit }) {
      commit('SET_USER')
    }
  },
  modules: {
    classroomStore: classroomStore,
    instructorStore: instructorStore,
    profileStore: profileStore
  }
})
