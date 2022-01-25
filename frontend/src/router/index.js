import { createRouter, createWebHistory } from 'vue-router'
// accounts
import LoginView from '../views/accounts/LoginView'
import SignupView from '../views/accounts/SignupView'

// Menu Bar
import HomeView from '../views/HomeView'
import SettingView from '../views/SettingView'
import ProfileView from '../views/profile/ProfileView'
import ClassView from '../views/classroom/ClassView'
import VODView from '../views/classroom/VODView'
import ClassListView from '../views/classroom/ClassListView'
import InstructorListView from '../views/instructor/InstructorListView'
import InstructorProfileView from '../views/instructor/InstructorProfileView'


const routes = [
  { // 메인 페이지
    path: '/',
    name: 'home',
    component: HomeView
  },
  { // 설정 페이지
    path: '/setting',
    name: 'setting',
    component: SettingView
  },
  { // 프로필 페이지
    path: '/profile',
    name: 'profile',
    component: ProfileView
  },
  { // 강의실 페이지
    path: '/classroom',
    name: 'classroom',
    component: ClassView
  },
  { // 강의실 페이지
    path: '/vod',
    name: 'vod',
    component: VODView
  },
  { // 강좌 조회(검색)
    path: '/classlist',
    name: 'classlist',
    component: ClassListView
  },
  { // 강사 조회(검색)
    path: '/instructorlist',
    name: 'instructorlist',
    component: InstructorListView
  },
  { // 강사 프로필 페이지
    path: '/instructorprofile',
    name: 'instructorprofile',
    component: InstructorProfileView
  },

  // accounts
  {
    path: '/accounts/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/accounts/signup',
    name: 'signup',
    component: SignupView
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
