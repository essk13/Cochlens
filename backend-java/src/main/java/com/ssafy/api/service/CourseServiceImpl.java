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

        courseRepository.save(course);
        return course;
//        return courseRepository.save(course);

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
                .courseCloseDate(courseInsertInfo.getCourseCloseDate())
                .courseCycle(courseInsertInfo.getCourseCycle())
                .courseThumbnail(courseInsertInfo.getCourseThumbnail())
                .courseCategory(courseInsertInfo.getCourseCategory())
                .courseLimitPeople(courseInsertInfo.getCourseLimitPeople())
                .courseFee(courseInsertInfo.getCourseFee())
                .courseIntroVideo(courseInsertInfo.getCourseIntroVideo())
                .user(course.getUser())
                .build();

        courseRepository.save(newCourse);

        System.out.println("return = " + newCourse);

        return newCourse;
//        return courseRepository.save(newCourse);
    }

    @Override
    public Map<String, Object> registerCourse(Long userId, Long courseId) {
//    public void registerCourse(Long userId, Long courseId) {
        User user = userRepository.getOne(userId);
        Course course = courseRepository.getOne(courseId);
        RegisterCourse registerCourse = RegisterCourse.builder()
                .user(user)
                .course(course)
                .isCancel(true)
                .build();
        registerCourseRepository.save(registerCourse);

        List<Map<String, Object>> result = new ArrayList<>();
        registerCourseRepository.findAll().forEach(registerCourseList -> {
            Map<String, Object> obj = new HashMap<>();
            Course courseCheck = registerCourseList.getCourse();
            if (courseCheck == course){
                obj.put("courseId", registerCourseList.getRegisterId());
                result.add(obj);
            }
        });

        int joinCount = result.size();
        boolean isJoin = registerCourse.isCancel();

        Map<String, Object> returnValue = new HashMap<String, Object>();
        returnValue.put("joinCount", joinCount);
        returnValue.put("isJoin", isJoin);

        return returnValue;
    }

    @Override
//    public void deregisterCourse(Long userId, Long courseId) {
    public Map<String, Object> deregisterCourse(Long userId, Long courseId) {
        User user = userRepository.getOne(userId);
        Course course = courseRepository.getOne(courseId);

        registerCourseRepository.findAll().forEach(registerCourseList -> {
            Course courseCheck = registerCourseList.getCourse();
            User userCheck = registerCourseList.getUser();
            if (courseCheck == course && userCheck == user){
                registerCourseRepository.delete(registerCourseList);
            }
        });

        List<Map<String, Object>> result = new ArrayList<>();
        registerCourseRepository.findAll().forEach(registerCourseList -> {
            Map<String, Object> obj = new HashMap<>();
            Course courseCheck = registerCourseList.getCourse();
            if (courseCheck == course){
                obj.put("courseId", registerCourseList.getRegisterId());
                result.add(obj);
            }
        });

        int joinCount = result.size();
        boolean isJoin = false;

        Map<String, Object> returnValue = new HashMap<String, Object>();
        returnValue.put("joinCount", joinCount);
        returnValue.put("isJoin", isJoin);

        return returnValue;
    }
}
