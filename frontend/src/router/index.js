import { createRouter, createWebHistory } from 'vue-router'
// accounts
import LoginView from '../views/accounts/LoginView'
import SignupView from '../views/accounts/SignupView'

// Menu Bar
import HomeView from '../views/HomeView'
import ProfileView from '../views/profile/ProfileView'

import CourseDetailView from '../views/course/CourseDetailView'
import CourseListView from '../views/course/CourseListView'
import CourseReviewListView from '../views/course/CourseReviewListView'
import CourseCreateView from '../views/course/CourseCreateView'
import CourseUpdateView from '../views/course/CourseUpdateView'
import LiveLectureView from '../views/course/LiveLectureView'
import ChatView from '../views/course/ChatView'

import InstructorListView from '../views/instructor/InstructorListView'
import InstructorDetailView from '../views/instructor/InstructorDetailView'
import InstructorLiveListView from '../views/instructor/InstructorLiveListView'
import InstructorCourseListView from '../views/instructor/InstructorCourseListView'
import InstructorReviewListView from '../views/instructor/InstructorReviewListView'
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
  { // 강좌 목록
    path: '/course',
    name: 'courseList',
    component: CourseListView
  },
  { // 강좌 상세 페이지
    path: '/course/:courseId',
    name: 'courseDetail',
    component: CourseDetailView
  },
  { // 강좌 리뷰
    path: '/course/:courseId/review',
    name: 'courseReviewList',
    component: CourseReviewListView
  },
  { // 강좌 생성
    path: '/course/create',
    name: 'courseCreate',
    component: CourseCreateView
  },
  { // 강좌 수정
    path: '/course/update/:courseId',
    name: 'courseUpdate',
    component: CourseUpdateView
  },
  { // 라이브 강의실
    path: '/course/:courseId/:lectureId',
    name: 'liveLecture',
    component: LiveLectureView
  },
  { // 채팅
    path: '/chat/:lectureId',
    name: 'chat',
    component: ChatView
  },

  // 강사
  { // 강사 조회(검색)
    path: '/instructor',
    name: 'instructorList',
    component: InstructorListView
  },
  { // 강사 프로필 페이지
    path: '/instructor/:instructorId',
    name: 'instructorDetail',
    component: InstructorDetailView
  },
  { // 강사 라이브 강좌 목록 페이지
    path: '/instructor/:instructorId/live',
    name: 'instructorLiveList',
    component: InstructorLiveListView
  },
  { // 강사 전체 강좌 목록 페이지
    path: '/instructor/:instructorId/course',
    name: 'instructorCourseList',
    component: InstructorCourseListView
  },
  { // 강사 리뷰 목록 페이지
    path: '/instructor/:instructorId/review',
    name: 'instructorReviewList',
    component: InstructorReviewListView
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
