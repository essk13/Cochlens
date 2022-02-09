<template>
  <div class="course-roof">
    <div class="profile-roof"></div>
    <div class="profile-roof-cover"></div>
    <div class="profile-img text-white"></div>
    <p class="course-detail-title">강쉡의 요리교실</p>
    <p class="course-detail-instructor">강사 : 강태훈</p>
  </div>

  <div class="row course-detail-upper-area">
    <div class="col-7">
      <!-- 강좌 소개 -->
      <div class="q-mb-xl">
        <p class="profile-menu-title">강좌 소개</p>
        <p>
          강태훈 강사님의 신나는 요리교실. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다.  이것은 샘플 텍스트입니다.
          <br>
          이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 
          이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다. 이것은 샘플 텍스트입니다.
        </p>
      </div>

      <!-- 커리큘럼 -->
      <div>
        <dir class="row">
          <p class="profile-menu-title">커리큘럼</p>
        </dir>
        <div class="course-detail-curriculum row">
          <div class="course-detail-thumbnail"></div>
          <div>
            <p class="curriculum-title">제 1강 요리란 무엇인가? 강쉡이 알려드립니다!</p>
            <p class="curriculum-description">
              강사 : 강태훈<br>
              요리에 대한 모든 것! 강쉡이 하나부터 열까지 다 알려드립니다! 초보에게 강추!<br>
              별점 : 10/10 (10000+) / 길이 : 1H
            </p>
          </div>
        </div>
        <div class="course-detail-curriculum row">
          <div class="course-detail-thumbnail"></div>
          <div>
            <p class="curriculum-title">제 2강 닭 한마리 완전 정복</p>
            <p class="curriculum-description">
              강사 : 강태훈<br>
            </p>
          </div>
          <q-btn color="purple">강의 시작하기</q-btn>
        </div>
        <div class="course-detail-curriculum"></div>
        <div class="course-detail-curriculum"></div>
        <div class="course-detail-curriculum"></div>
        <div class="course-detail-curriculum"></div>
        <div class="course-detail-curriculum"></div>
        <div class="course-detail-curriculum"></div>
        <div class="course-detail-curriculum"></div>
        <div class="course-detail-curriculum"></div>
      </div>
    </div>
    <div class="col-1"></div>

    <div class="col-4 q-pa-md">
      <div class="reserve">
        <!-- 수강신청 -->
        <q-card class="reservation-card q-mb-xl">
          <q-card-section class="bg-purple text-white">
            <div class="text-h6">수강신청</div>
            <div class="text-subtitle2">by John Doe</div>
          </q-card-section>

          <q-card-actions align="around">
            <q-btn flat>신청하기</q-btn>
            <q-btn flat>찜 하기</q-btn>
          </q-card-actions>
        </q-card>

        <!-- 강좌 리뷰 -->
        <q-card class="reservation-card">
          <q-card-section class="bg-purple text-white">
            <div class="row">
              <p class="text-h6 q-mr-sm q-mb-none">강좌 리뷰</p>
              <a @click="moveCourseReview">더보기</a>
            </div>
            <div class="text-subtitle2">by John Doe</div>
          </q-card-section>

          <q-card-actions align="around">
            <div class="course-detail-review">이것은 리뷰 입니다</div>
            <div class="course-detail-review">이것은 리뷰 입니다</div>
            <div class="course-detail-review">이것은 리뷰 입니다</div>
            <div class="course-detail-review">이것은 리뷰 입니다</div>
            <div class="course-detail-review">이것은 리뷰 입니다</div>
            <div class="course-detail-review">이것은 리뷰 입니다</div>
            <div class="course-detail-review">이것은 리뷰 입니다</div>
            <div class="course-detail-review">이것은 리뷰 입니다</div>
            <div class="course-detail-review">이것은 리뷰 입니다</div>
            <div class="course-detail-review">이것은 리뷰 입니다</div>
            <div class="course-detail-review">이것은 리뷰 입니다</div>
            <div class="course-detail-review">이것은 리뷰 입니다</div>
            <div class="course-detail-review">이것은 리뷰 입니다</div>
            <div class="course-detail-review">이것은 리뷰 입니다</div>
            <div class="course-detail-review">이것은 리뷰 입니다</div>
            <div class="course-detail-review">이것은 리뷰 입니다</div>
            <div class="course-detail-review">이것은 리뷰 입니다</div>
          </q-card-actions>
        </q-card>
      </div>
    </div>
  </div>
</template>
<script>
import router from '@/router'

export default {
  name: 'CourseDetailView',

  setup () {

    // Function
    function moveCourseReview() {
      router.push({ name: 'courseReview' })
    }

    return {
      moveCourseReview
    }
  }
}
</script>
<style>
  .course-roof {
    position: sticky;
    top: 0;
  }

  .course-detail-title {
    position: absolute;
    top: 100px;
    left: 220px;
    font-size: 30px;
    font-weight: bold;
    color: white;
    z-index: 2;
  }

  .course-detail-instructor {
    position: absolute;
    top: 145px;
    left: 225px;
    font-size: 15px;
    color: white;
    z-index: 2;
  }

  .course-detail-upper-area {
    padding: 60px 60px 0 60px;
  }

  .reservation-card {
    width: 100%;
  }

  .course-detail-review {
    width: 100%;
  }

  .course-detail-thumbnail {
    width: 200px;
    height: 130px;
    border-radius: 5px;
    background-image: url('@/assets/cooking.jpg');
    background-size: cover;
  }

  .course-detail-curriculum {
    width: 100%;
    height: 130px;
    border-radius: 5px;
    margin-bottom: 20px;
    background: gray;
  }

  .curriculum-title {
    font-size: 20px;
    font-weight: 500;
    color: white;
    margin: 10px;
  }

  .curriculum-description {
    font-size: 15px;
    font-weight: 200;
    color: white;
    margin: 10px;
  }

  .reserve {
    position: -webkit-sticky;
    position: sticky !important;
    top: 255px;
  }
</style>