package com.ssafy.api.service;

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
    CourseRepository courseRepository;

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    RegisterCourseRepository registerCourseRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    WishlistRepository wishlistRepository;

    /**
     * create
     */
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
    public void registerCourse(User user, Course course) {
        RegisterCourse registerCourse = RegisterCourse.builder()
                .user(user)
                .course(course)
                .build();
        registerCourseRepository.save(registerCourse);
    }

    @Override
    public void registerWishlist(User user, Course course) {
        Wishlist wishlist = Wishlist.builder()
                .user(user)
                .course(course)
                .build();
        course.increaseWishCount();

        wishlistRepository.save(wishlist);
        courseRepository.save(course);
    }

    /**
     * read
     */

    @Override
    public List<CourseDto.CourseListRes> getCourseList() {
        List<CourseDto.CourseListRes> result = new ArrayList<>();
        List<Course> list = courseRepository.findAll();

        for (Course course : list) {
            CourseDto.CourseListRes courseRes = CourseDto.CourseListRes.of(course);
            result.add(courseRes);
        }
        return result;
    }

    @Override
    public Course getCourse(Long courseId) {
        return courseRepository.findById(courseId).get();
    }

    @Override
    public CourseDto.CourseInstructorVO getInstructorRate(User user) {
        return courseRepository.findInstructorRate(user).get();
    }

    @Override
    public List<CourseDto.CourseListRes> getCourseLiveList(User user) {
       return courseRepository.findAllByLiveList(user);
    }

    @Override
    public List<CourseDto.CourseListRes> getCourseVodList(User user) {
        return courseRepository.findAllByVodList(user);
    }

    @Override
    public List<CourseDto.CourseListRes> getBestCourseList() {
        return courseRepository.findByBestCourse();
    }

    @Override
    public List<CourseDto.CourseListRes> getRecentCourseList(User user) {
        return courseRepository.findByRegisterCourse(user);
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
                courseRes.setCourseWishCount(wishCount);

                result.add(courseRes);
            }
        }
        return result;
    }

    /**
     * update
     */

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

    /**
     * delete
     */

    @Override
    public void deregisterWishlist(User user, Course course) {
        Wishlist wishlist = wishlistRepository.findWishlistByUserAndCourse(user, course).get();
        wishlistRepository.delete(wishlist);
    }

    @Override
    public void deregisterCourse(User user, Course course) {
        RegisterCourse registerCourse = registerCourseRepository.findRegisterCourseByUserAndCourse(user, course).get();
        registerCourseRepository.delete(registerCourse);
    }

}
