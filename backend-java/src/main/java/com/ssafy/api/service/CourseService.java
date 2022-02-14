package com.ssafy.api.service;

import com.ssafy.api.dto.CourseDto;
import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.User;

import java.util.List;
import java.util.Map;

public interface CourseService {
    /* create */
    Course createCourse(User user, CourseDto.CourseInsertReq courseInsertInfo);
    void registerCourse(User user, Course course);
    void registerWishlist(User user, Course course);
    /* read */
    Course getCourse(Long courseId);
    List<CourseDto.CourseListRes> getCourseList();
    CourseDto.CourseRes getCourseByCourseId(Long courseId, String email);
    CourseDto.CourseInstructorVO getInstructorRate(User user);

    List<CourseDto.CourseListRes> getCourseLiveList(User user);
    List<CourseDto.CourseListRes> getCourseVodList(User user);

    List<CourseDto.CourseListRes> getBestCourseList();
    List<CourseDto.CourseListRes> getRecentCourseList(Long userId);
    List<CourseDto.CourseListRes> getSearchCourseList(String courseName);
    /* update */
    Course updateCourse(Long courseId, CourseDto.CourseInsertReq courseInsertInfo);
    /* delete */
    void deregisterCourse(Long userId, Long courseId);
    void deregisterWishlist(Long userId, Long courseId);

}
