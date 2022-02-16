package com.ssafy.api.service;

import com.ssafy.api.dto.CourseDto;
import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface CourseService {
    /* create */
    Course createCourse(User user, CourseDto.CourseInsertReq courseInsertInfo);
    void registerCourse(User user, Course course);
    void registerWishlist(User user, Course course);
    /* read */
    Course getCourse(Long courseId);
    List<CourseDto.CourseAllRes> getCourseList(Pageable pageable);
    CourseDto.CourseInstructorVO getInstructorRate(User user);

    List<CourseDto.CourseListRes> getRegisterCourseList(User user);
    List<CourseDto.CourseListRes> getWishCourseList(User user);

    List<CourseDto.CourseListRes> getCourseLiveList(User user);
    List<CourseDto.CourseListRes> getCourseVodList(User user);

    List<CourseDto.CourseListRes> getBestCourseList();
    List<CourseDto.CourseListRes> getRecentCourseList(User user);
    List<CourseDto.CourseListRes> getSearchCourseList(String courseName, Pageable pageable);
    /* update */
    Course updateCourse(Long courseId, CourseDto.CourseInsertReq courseInsertInfo);
    /* delete */
    void deregisterCourse(User user, Course course);
    void deregisterWishlist(User user, Course course);

    /* check */
    Boolean findIsJoinCourseByUser(String email, Long courseId);
    Boolean findIsWishCourseByEmailAndCourseId(String email, Long courseId);

    /* count */
    Long findJoinCountByCourseId(Long courseId);
}
