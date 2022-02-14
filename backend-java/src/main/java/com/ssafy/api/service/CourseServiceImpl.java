package com.ssafy.api.service;

import com.google.common.primitives.Ints;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.dto.CourseDto;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.*;

@Service("courseService")
public class CourseServiceImpl implements CourseService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    RegisterCourseRepository registerCourseRepository;

    @Autowired
    RegisterCourseRepositorySupport registerCourseRepositorySupport;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    WishlistRepository wishlistRepository;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public Course createCourse(User user, CourseDto.CourseInsertReq courseInsertInfo) {


        Course course = Course.builder()
                .courseName(courseInsertInfo.getCourseName())
                .courseDescription(courseInsertInfo.getCourseDescription())
                .courseOpenDate(courseInsertInfo.getCourseOpenDate())
                .courseCloseDate(courseInsertInfo.getCourseCloseDate())
                .courseCycle(courseInsertInfo.getCourseCycle())
                .courseThumbnail(courseInsertInfo.getCourseThumbnail())
                .courseCategory(courseInsertInfo.getCourseCategory())
                .courseLimitPeople(courseInsertInfo.getCourseLimitPeople())
                .courseFee(courseInsertInfo.getCourseFee())
                .courseIntroVideo(courseInsertInfo.getCourseIntroVideo())
                .user(user)
                .build();

        return courseRepository.save(course);

    }

    @Override
    public List<CourseDto.CourseListRes> getCourseList() {
        List<CourseDto.CourseListRes> result = new ArrayList<>();
        List<Course> list = courseRepository.findAll();
        List<Wishlist> wish = wishlistRepository.findAll();
        List<Review> reviewData = reviewRepository.findAll();


        for (Course course : list) {

//            찜목록 개수
            int wishCount = 0;

            for (Wishlist wishlist : wish) {
                if ( course == wishlist.getCourse()) {
                    wishCount += 1;
                }
            }

//            리뷰 평점 및 개수
            int reviewCount = 0;

            int reviewGradeSum = 0;

            for (Review review : reviewData) {
                if ( course == review.getCourse()) {
                    reviewCount += 1;
                    reviewGradeSum += review.getReviewGrade();
                }
            }
            Double reviewRateAverage = 0.0;
            if (reviewCount != 0){
//                소수점 둘째자리까지 표시
                reviewRateAverage = Math.round(Double.valueOf(reviewGradeSum / reviewCount) * 100) / 100.0;
            }

            CourseDto.CourseListRes courseRes = new CourseDto.CourseListRes();

            courseRes.setCourseId(course.getCourseId());
            courseRes.setCourseName(course.getCourseName());
            courseRes.setCourseDescription(course.getCourseDescription());
            courseRes.setCourseThumbnail(course.getCourseThumbnail());
            courseRes.setCourseCategory(course.getCourseCategory());
            courseRes.setInstructorName(course.getUser().getUserName());
            courseRes.setCourseCycle(course.getCourseCycle());
            courseRes.setCourseFee(course.getCourseFee());
            courseRes.setCourseOpenDate(course.getCourseOpenDate());
            courseRes.setCourseCloseDate(course.getCourseCloseDate());
            courseRes.setCourseReviewCount(reviewCount);
            courseRes.setCourseReviewRateAverage(reviewRateAverage);
            courseRes.setWishCount(wishCount);

            result.add(courseRes);
        }
        return result;
    }

    @Override
    public CourseDto.CourseRes getCourseByCourseId(Long courseId, String email) {
        Course course = courseRepository.getOne(courseId);
        User user = userService.getUserByEmail(email);
        List<Wishlist> wishlist = wishlistRepository.findAll();
        List<RegisterCourse> registerCourseList = registerCourseRepository.findAll();

//        찜 사람 수
        int wishCount = 0;
//        본인 찜 유무
        boolean isWish = false;
        for (Wishlist wishData : wishlist) {
            if(wishData.getCourse() == course){
                wishCount += 1;
                if(wishData.getUser() == user){
                    isWish = true;
                }
            }
        }

//        수강 사람 수
        int joinCount = 0;
//        본인 수강 유무
        boolean isJoin = false;

        for (RegisterCourse registerData : registerCourseList) {
            if(registerData.getCourse() == course){
                joinCount += 1;
                if(registerData.getUser() == user){
                    isJoin = true;
                }
            }
        }

        List<Lecture> lectureList = new ArrayList<>();
        List<Review> reviewList = new ArrayList<>();

        List<Lecture> lectureListData = lectureRepository.findAll();
        List<Review> reviewListData = reviewRepository.findAll();

        for (Lecture lectureData : lectureListData) {
            if(lectureData.getCourse() == course){
                lectureList.add(lectureData);
            }
        }

        int courseReviewCount = 0;
        int courseReviewGradeSum = 0;


        Double courseReviewRateAverage = 0.0;

        for (Review reviewData : reviewListData) {
            if(reviewData.getCourse() == course){
                reviewList.add(reviewData);
                courseReviewCount += 1;
                courseReviewGradeSum += reviewData.getReviewGrade();
            }
        }

        if (courseReviewCount != 0){
//                소수점 둘째자리까지 표시
            courseReviewRateAverage = Math.round(Double.valueOf(courseReviewGradeSum / courseReviewCount) * 100) / 100.0;
        }

        CourseDto.CourseRes courseRes = new CourseDto.CourseRes();

        courseRes.setCourseId(course.getCourseId());
        courseRes.setCourseName(course.getCourseName());
        courseRes.setCourseDescription(course.getCourseDescription());
        courseRes.setCourseThumbnail(course.getCourseThumbnail());
        courseRes.setCourseCategory(course.getCourseCategory());
        courseRes.setInstructorName(course.getUser().getUserName());
        courseRes.setCourseCycle(course.getCourseCycle());
        courseRes.setCourseFee(course.getCourseFee());
        courseRes.setCourseOpenDate(course.getCourseOpenDate());
        courseRes.setCourseCloseDate(course.getCourseCloseDate());
        courseRes.setCourseCategory(course.getCourseCategory());
        courseRes.setCourseReviewCount(courseReviewCount);
        courseRes.setCourseReviewRateAverage(courseReviewRateAverage);
        courseRes.setWishCount(wishCount);
        courseRes.setJoinCount(joinCount);
        courseRes.setLectureList(lectureList);
        courseRes.setReviewList(reviewList);
        courseRes.setInstructorName(course.getUser().getUserName());
        courseRes.setIsWish(isWish);
        courseRes.setIsJoin(isJoin);

        System.out.println(course.getUser().getUserId());
        System.out.println(course.getUser().getUserName());
        System.out.println(course.getUser().getEmail());

        return courseRes;

    }



    @Override
    public Course updateCourse(Long courseId, CourseDto.CourseInsertReq courseInsertInfo){
        Course course = courseRepository.getOne(courseId);
        Course newCourse = Course.builder()
                .courseId(course.getCourseId())
                .courseName(courseInsertInfo.getCourseName())
                .courseDescription(courseInsertInfo.getCourseDescription())
                .courseOpenDate(courseInsertInfo.getCourseOpenDate())
                .courseCloseDate(courseInsertInfo.getCourseCloseDate())
                .courseCycle(courseInsertInfo.getCourseCycle())
                .courseThumbnail(courseInsertInfo.getCourseThumbnail())
                .courseCategory(courseInsertInfo.getCourseCategory())
                .courseLimitPeople(courseInsertInfo.getCourseLimitPeople())
                .courseFee(courseInsertInfo.getCourseFee())
                .courseIntroVideo(courseInsertInfo.getCourseIntroVideo())
                .user(course.getUser())
                .build();


        return courseRepository.save(newCourse);
    }

    @Override
    public void registerCourse(Long userId, Long courseId) {
        User user = userRepository.getOne(userId);
        Course course = courseRepository.getOne(courseId);
        RegisterCourse registerCourse = RegisterCourse.builder()
                .user(user)
                .course(course)
                .build();
        registerCourseRepository.save(registerCourse);

        //이 강좌 수강인원
//        List<Map<String, Object>> result = new ArrayList<>();
//        registerCourseRepository.findAll().forEach(registerCourseList -> {
//            Map<String, Object> obj = new HashMap<>();
//            Course courseCheck = registerCourseList.getCourse();
//            if (courseCheck == course){
//                obj.put("courseId", registerCourseList.getRegisterId());
//                result.add(obj);
//            }
//        });
//        Integer joinCount = result.size();
    }

    @Override
    public void deregisterCourse(Long userId, Long courseId) {
        User user = userRepository.getOne(userId);
        Course course = courseRepository.getOne(courseId);

        List<RegisterCourse> list = registerCourseRepository.findAll();

        for (RegisterCourse registerCourse : list) {
            if (user == registerCourse.getUser() && course == registerCourse.getCourse()){
                registerCourseRepository.delete(registerCourse);
                break;
            }
        }

//        JPA썼음
//        RegisterCourse registerCourse = jpaQueryFactory.select(qRegisterCourse).from(qRegisterCourse)
//        .where(qRegisterCourse.user.eq(user)).where(qRegisterCourse.course.eq(course)).fetchOne();
//        registerCourseRepository.delete(registerCourse);
    }

    @Override
    public void registerWishlist(Long userId, Long courseId) {
        User user = userRepository.getOne(userId);
        Course course = courseRepository.getOne(courseId);

        Wishlist wishlist = Wishlist.builder()
                .user(user)
                .course(course)
                .build();

        wishlistRepository.save(wishlist);
    }

    @Override
    public void deregisterWishlist(Long userId, Long courseId) {
        User user = userRepository.getOne(userId);
        Course course = courseRepository.getOne(courseId);

        List<Wishlist> list = wishlistRepository.findAll();

        for (Wishlist wishlist : list) {
            if (user == wishlist.getUser() && course == wishlist.getCourse()){
                wishlistRepository.delete(wishlist);
                break;
            }
        }

//        JPA썼음
//        Wishlist wishlist = jpaQueryFactory.select(qWishlist).from(qWishlist)
//                .where(qWishlist.user.eq(user)).where(qWishlist.course.eq(course)).fetchOne();
//        wishlistRepository.delete(wishlist);
    }

    @Override
    public List<CourseDto.CourseListRes> getBestCourseList() {

        int courseSize = courseRepository.findAll().size();

        int[] arr = new int[courseSize + 1];

        List<Wishlist> list = wishlistRepository.findAll();
        for (Wishlist wishlist : list) {
            arr[Math.toIntExact(wishlist.getCourse().getCourseId())] += 1;
        }

        List<CourseDto.CourseListRes> result = new ArrayList<>();

        int count = Math.min(arr.length, 5);

        for (int i = 0; i < count; i++) {
//            찜 개수 중 가장 큰 값
            int max = Arrays.stream(arr).max().getAsInt();

//            max 값을 가진 index 찾기(강좌 번호)
            int maxIndex = Ints.indexOf(arr, max);

            arr[maxIndex] = 0;

            if (maxIndex == 0) break;

            Course course = courseRepository.getOne(Long.valueOf(maxIndex));
            CourseDto.CourseListRes courseRes = new CourseDto.CourseListRes();

            //            리뷰 평점 및 개수
            int reviewCount = 0;

            int reviewGradeSum = 0;

            List<Review> reviewDate = reviewRepository.findAll();
            for (Review review : reviewDate) {
                if ( course == review.getCourse()) {
                    reviewCount += 1;
                    reviewGradeSum += review.getReviewGrade();
                }
            }
            Double reviewRateAverage = 0.0;
            if (reviewCount != 0){
//                소수점 둘째자리까지 표시
                reviewRateAverage = Math.round(Double.valueOf(reviewGradeSum / reviewCount) * 100) / 100.0;
            }

            courseRes.setCourseId(course.getCourseId());
            courseRes.setCourseName(course.getCourseName());
            courseRes.setCourseDescription(course.getCourseDescription());
            courseRes.setCourseThumbnail(course.getCourseThumbnail());
            courseRes.setCourseCategory(course.getCourseCategory());
            courseRes.setInstructorName(course.getUser().getUserName());
            courseRes.setCourseCycle(course.getCourseCycle());
            courseRes.setCourseFee(course.getCourseFee());
            courseRes.setCourseOpenDate(course.getCourseOpenDate());
            courseRes.setCourseCloseDate(course.getCourseCloseDate());
            courseRes.setCourseCategory(course.getCourseCategory());
            courseRes.setCourseReviewCount(reviewCount);
            courseRes.setCourseReviewRateAverage(reviewRateAverage);
            courseRes.setWishCount(max);

            result.add(courseRes);
        }
        return result;
    }
    @Override
    public List<CourseDto.CourseListRes> getRecentCourseList(Long userId){
        User user = userRepository.getOne(userId);

        List<RegisterCourse> list = registerCourseRepository.findAll();

        List<CourseDto.CourseListRes> result = new ArrayList<>();

        for (RegisterCourse registerCourseList : list) {
            if (registerCourseList.getUser() == user){

                CourseDto.CourseListRes courseRes = new CourseDto.CourseListRes();


                Course course = registerCourseList.getCourse();

                //            찜목록 개수
                int wishCount = 0;

                List<Wishlist> wish = wishlistRepository.findAll();
                for (Wishlist wishlist : wish) {
                    if ( course == wishlist.getCourse()) {
                        wishCount += 1;
                    }
                }
                //            리뷰 평점 및 개수
                int reviewCount = 0;

                int reviewGradeSum = 0;

                List<Review> reviewDate = reviewRepository.findAll();
                for (Review review : reviewDate) {
                    if ( course == review.getCourse()) {
                        reviewCount += 1;
                        reviewGradeSum += review.getReviewGrade();
                    }
                }
                Double reviewRateAverage = 0.0;
                if (reviewCount != 0){
//                소수점 둘째자리까지 표시
                    reviewRateAverage = Math.round(Double.valueOf(reviewGradeSum / reviewCount) * 100) / 100.0;
                }

                courseRes.setCourseId(course.getCourseId());
                courseRes.setCourseName(course.getCourseName());
                courseRes.setCourseDescription(course.getCourseDescription());
                courseRes.setCourseThumbnail(course.getCourseThumbnail());
                courseRes.setCourseCategory(course.getCourseCategory());
                courseRes.setInstructorName(course.getUser().getUserName());
                courseRes.setCourseCycle(course.getCourseCycle());
                courseRes.setCourseFee(course.getCourseFee());
                courseRes.setCourseOpenDate(course.getCourseOpenDate());
                courseRes.setCourseCloseDate(course.getCourseCloseDate());
                courseRes.setCourseCategory(course.getCourseCategory());
                courseRes.setCourseReviewCount(reviewCount);
                courseRes.setCourseReviewRateAverage(reviewRateAverage);
                courseRes.setWishCount(wishCount);


                result.add(courseRes);
            }
        }
        return result;
    }

    @Override
    public List<CourseDto.CourseListRes> getSearchCourseList(String courseName){
        List<Course> courseList = courseRepository.findAll();
        List<Wishlist> wish = wishlistRepository.findAll();
        List<Review> reviewData = reviewRepository.findAll();

        List<CourseDto.CourseListRes> result = new ArrayList<>();

        for (Course courseData : courseList) {
            if(courseData.getCourseName().contains(courseName)){

                //            찜목록 개수
                int wishCount = 0;

                for (Wishlist wishlist : wish) {
                    if ( courseData == wishlist.getCourse()) {
                        wishCount += 1;
                    }
                }

//            리뷰 평점 및 개수
                int reviewCount = 0;

                int reviewGradeSum = 0;

                for (Review review : reviewData) {
                    if ( courseData == review.getCourse()) {
                        reviewCount += 1;
                        reviewGradeSum += review.getReviewGrade();
                    }
                }
                Double reviewRateAverage = 0.0;
                if (reviewCount != 0){
//                소수점 둘째자리까지 표시
                    reviewRateAverage = Math.round(Double.valueOf(reviewGradeSum / reviewCount) * 100) / 100.0;
                }

                CourseDto.CourseListRes courseRes = new CourseDto.CourseListRes();

                courseRes.setCourseId(courseData.getCourseId());
                courseRes.setCourseName(courseData.getCourseName());
                courseRes.setCourseDescription(courseData.getCourseDescription());
                courseRes.setCourseThumbnail(courseData.getCourseThumbnail());
                courseRes.setCourseCategory(courseData.getCourseCategory());
                courseRes.setInstructorName(courseData.getUser().getUserName());
//                courseRes.setCourseCycle(courseData.getCourseCycle());
                courseRes.setCourseFee(courseData.getCourseFee());
                courseRes.setCourseOpenDate(courseData.getCourseOpenDate());
                courseRes.setCourseCloseDate(courseData.getCourseCloseDate());
                courseRes.setCourseReviewCount(reviewCount);
                courseRes.setCourseReviewRateAverage(reviewRateAverage);
                courseRes.setWishCount(wishCount);

                result.add(courseRes);
            }
        }
        return result;
    }

}
