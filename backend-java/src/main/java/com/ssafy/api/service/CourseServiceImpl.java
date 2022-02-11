package com.ssafy.api.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.dto.CourseDto;
import com.ssafy.db.entity.QRegisterCourse;
import com.ssafy.db.entity.RegisterCourse;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.CourseRepository;
import com.ssafy.db.repository.RegisterCourseRepository;
import com.ssafy.db.repository.RegisterCourseRepositorySupport;
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

    @Autowired
    RegisterCourseRepositorySupport registerCourseRepositorySupport;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QRegisterCourse qRegisterCourse = QRegisterCourse.registerCourse;

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


        return courseRepository.save(newCourse);
    }

    @Override
    public void registerCourse(Long userId, Long courseId) {
        User user = userRepository.getOne(userId);
        Course course = courseRepository.getOne(courseId);
//        System.out.println("joinCount1");
        RegisterCourse registerCourse = RegisterCourse.builder()
                .user(user)
                .course(course)
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
        Integer joinCount = result.size();
    }

    @Override
    public void deregisterCourse(Long userId, Long courseId) {
        User user = userRepository.getOne(userId);
        Course course = courseRepository.getOne(courseId);

//        JPA썼음
        RegisterCourse registerCourse = jpaQueryFactory.select(qRegisterCourse).from(qRegisterCourse)
        .where(qRegisterCourse.user.eq(user)).where(qRegisterCourse.course.eq(course)).fetchOne();
//        RegisterCourse registerCourse = registerCourseRepository.findByUserIdAndCourseId(userId, courseId).get();

//        RegisterCourse registerCourse = registerCourseRepository.findByUserIdAndCourseId(userId, courseId).get();
        registerCourseRepository.delete(registerCourse);
    }
}
