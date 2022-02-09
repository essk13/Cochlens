package com.ssafy.api.service;

import com.ssafy.api.dto.CourseDto;
import com.ssafy.db.entity.RegisterCourse;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.CourseRepository;
import com.ssafy.db.repository.RegisterCourseRepository;
import com.ssafy.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssafy.db.entity.Course;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("courseService")
public class CourseServiceImpl implements CourseService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    RegisterCourseRepository registerCourseRepository;

    @Override
    public Course createCourse(User user, CourseDto.CourseInsertReq courseInsertInfo) {
        Course course = new Course();

        course.setCourseName(courseInsertInfo.getCourseName());
        course.setCourseDescription(courseInsertInfo.getCourseDescription());
        course.setCourseOpenDate(courseInsertInfo.getCourseOpenDate());
        course.setCourseCycle(courseInsertInfo.getCourseCycle());
        course.setCourseThumbnail(courseInsertInfo.getCourseThumbnail());
        course.setCourseCategory(courseInsertInfo.getCourseCategory());
        course.setCourseLimitPeople(courseInsertInfo.getCourseLimitPeople());
        course.setCourseFee(courseInsertInfo.getCourseFee());
        course.setCourseIntroVideo(courseInsertInfo.getCourseIntroVideo());
        course.setUser(user);

        return courseRepository.save(course);
    }

    @Override
    public List<CourseDto.CourseListRes> getCourseList() {
        List<CourseDto.CourseListRes> result = new ArrayList<>();
        List<Course> list = courseRepository.findAll();

        for (Course course : list) {
            CourseDto.CourseListRes courseRes = new CourseDto.CourseListRes();

            courseRes.setCourseId(course.getCourseId());
            courseRes.setCourseName(course.getCourseName());
            courseRes.setCourseDescription(course.getCourseDescription());
            courseRes.setCourseThumbnail(course.getCourseThumbnail());
            courseRes.setCourseCategory(course.getCourseCategory());
            courseRes.setInstructorName(course.getUser().getUserName());

            result.add(courseRes);
        }

        return result;
    }

    @Override
    public Course getCourseByCourseId(Long courseId) {
        return courseRepository.getOne(courseId);
    }

    @Override
    public Course updateCourse(Long courseId, CourseDto.CourseInsertReq courseInsertInfo){
        Course course = courseRepository.getOne(courseId);
        Course newCourse = Course.builder()
                .courseId(course.getCourseId())
                .courseName(courseInsertInfo.getCourseName())
                .courseDescription(courseInsertInfo.getCourseDescription())
                .courseOpenDate(courseInsertInfo.getCourseOpenDate())
                .courseCycle(courseInsertInfo.getCourseCycle())
                .courseThumbnail(courseInsertInfo.getCourseThumbnail())
                .courseCategory(courseInsertInfo.getCourseCategory())
                .courseLimitPeople(courseInsertInfo.getCourseLimitPeople())
                .courseFee(course.getCourseFee())
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
    }

    @Override
    public void deregisterCourse(Long userId, Long courseId) {
    }
}

