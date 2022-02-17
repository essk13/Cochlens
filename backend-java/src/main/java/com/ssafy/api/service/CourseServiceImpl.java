package com.ssafy.api.service;

import com.ssafy.api.dto.CourseDto;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("courseService")
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    RegisterCourseRepository registerCourseRepository;

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
    public List<CourseDto.CourseAllRes> getCourseList(Pageable pageable) {
        List<CourseDto.CourseAllRes> result = new ArrayList<>();
        List<Course> list = courseRepository.findAll(pageable).getContent();

        for (Course course : list) {
            CourseDto.CourseAllRes courseRes = CourseDto.CourseAllRes.of(course);
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
    public List<CourseDto.CourseListRes> getRegisterCourseList(User user) {
        return registerCourseRepository.findRegisterCourseListByUser(user);
    }

    @Override
    public List<CourseDto.CourseListRes> getWishCourseList(User user) {
        return registerCourseRepository.findWishCourseListByUser(user);
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
        return courseRepository.findCourseListByBest();
    }

    @Override
    public List<CourseDto.CourseListRes> getRecentCourseList(User user) {
        return courseRepository.findCourseListByRecent(user);
    }

    @Override
    public List<CourseDto.CourseListRes> getSearchCourseList(String courseName, Pageable pageable){
        return courseRepository.findCourseListByCourseName(courseName, pageable);
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
