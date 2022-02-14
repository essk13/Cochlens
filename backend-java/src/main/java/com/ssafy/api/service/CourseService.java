package com.ssafy.api.service;

import com.ssafy.api.dto.CourseDto;
import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.User;

import java.util.List;
import java.util.Map;

public interface CourseService {
    Course createCourse(User user, CourseDto.CourseInsertReq courseInsertInfo);
    List<CourseDto.CourseListRes> getCourseList();
    CourseDto.CourseRes getCourseByCourseId(Long courseId, String email);
    Course updateCourse(Long courseId, CourseDto.CourseInsertReq courseInsertInfo);
    void registerCourse(Long userId, Long courseId);
    void deregisterCourse(Long userId, Long courseId);
    void registerWishlist(Long userId, Long courseId);
    void deregisterWishlist(Long userId, Long courseId);
    List<CourseDto.CourseListRes> getBestCourseList();

    List<CourseDto.CourseListRes> getRecentCourseList(Long userId);
    List<CourseDto.CourseListRes> getSearchCourseList(String courseName);

}