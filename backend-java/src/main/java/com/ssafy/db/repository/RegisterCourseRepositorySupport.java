package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.*;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RegisterCourseRepositorySupport {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    RegisterCourseRepository registerCourseRepository;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QRegisterCourse qRegisterCourse = QRegisterCourse.registerCourse;

//        public Optional<RegisterCourse> findRegisterCourseByUserIdAndCourseId(Long userId, Long courseId) {
//
//        User user = userRepository.getOne(userId);
//        Course course = courseRepository.getOne(courseId);
//
//        RegisterCourse registerCourse = jpaQueryFactory.select(qRegisterCourse).from(qRegisterCourse)
//                .where(qRegisterCourse.user.eq(user)).where(qRegisterCourse.course.eq(course)).fetchOne();
//
//        if(registerCourse == null) return Optional.empty();
//        return Optional.ofNullable(registerCourse);
//
//    }
//    public RegisterCourse findRegisterCourseByUserIdAndCourseId(Long userId, Long courseId) {
//
//        User user = userRepository.getOne(userId);
//        Course course = courseRepository.getOne(courseId);
//
//        RegisterCourse registerCourse = jpaQueryFactory.select(qRegisterCourse).from(qRegisterCourse)
//                .where(qRegisterCourse.user.eq(user)).where(qRegisterCourse.course.eq(course)).fetchOne();
//        RegisterCourse registerCourse = new RegisterCourse();
//        return registerCourse;
//
//    }
}
