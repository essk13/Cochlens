import { createRouter, createWebHistory } from 'vue-router'
// accounts
import LoginView from '../views/accounts/LoginView'
import SignupView from '../views/accounts/SignupView'

// Menu Bar
import HomeView from '../views/HomeView'
import ProfileView from '../views/profile/ProfileView'

import CourseDetailView from '../views/course/CourseDetailView'
import CourseListView from '../views/course/CourseListView'
import LectureListView from '../views/course/LectureListView'
import CourseReviewView from '../views/course/CourseReviewView'
import CourseCreateView from '../views/course/CourseCreateView'

import InstructorListView from '../views/instructor/InstructorListView'
import InstructorDetailView from '../views/instructor/InstructorDetailView'
import SettingView from '../views/SettingView'


const routes = [
  // 홈
  { // 메인 페이지
    path: '/',
    name: 'home',
    component: HomeView
  },

  // 사용자 인증
  { // 로그인
    path: '/accounts/login',
    name: 'login',
    component: LoginView
  },
  { // 회원가입
    path: '/accounts/signup',
    name: 'signup',
    component: SignupView
  },

  // 프로필
  { // 프로필 페이지
    path: '/profile',
    name: 'profile',
    component: ProfileView
  },

  // 강좌
  { // 강좌 상세 페이지
    path: '/course',
    name: 'course',
    component: CourseDetailView
  },
  { // 강좌 조회
    path: '/courselist',
    name: 'courselist',
    component: CourseListView
  },
  { // 강의실 페이지
    path: '/lecture',
    name: 'lecture',
    component: LectureListView
  },
  { // 강좌 리뷰
    path: '/course/review',
    name: 'courseReview',
    component: CourseReviewView
  },
  { // 강좌 생성
    path: '/course/create',
    name: 'courseCreate',
    component: CourseCreateView
  },

  // 강사
  { // 강사 조회(검색)
    path: '/instructorlist',
    name: 'instructorlist',
    component: InstructorListView
  },
  { // 강사 프로필 페이지
    path: '/instructor',
    name: 'instructor',
    component: InstructorDetailView
  },

  { // 설정 페이지
    path: '/setting',
    name: 'setting',
    component: SettingView
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
