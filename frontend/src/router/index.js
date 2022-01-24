import { createRouter, createWebHistory } from 'vue-router'
// accounts
import LoginView from '../views/accounts/LoginView'
import SignupView from '../views/accounts/SignupView'

// Menu Bar
import HomeView from '../views/HomeView'
import SettingView from '../views/SettingView'
import ProfileView from '../views/profile/ProfileView'
import ClassList from '../views/classroom/ClassList'
import InstructorList from '../views/classroom/InstructorList'
import InstructorProfileView from '../views/instructor/InstructorProfileView'


const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/setting',
    name: 'setting',
    component: SettingView
  },
  {
    path: '/profile',
    name: 'profile',
    component: ProfileView
  },
  {
    path: '/classlist',
    name: 'classlist',
    component: ClassList
  },
  {
    path: '/instructorlist',
    name: 'instructorlist',
    component: InstructorList
  },
  {
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
