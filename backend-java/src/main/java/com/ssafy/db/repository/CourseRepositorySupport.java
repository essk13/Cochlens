package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QCourse;
import com.ssafy.db.entity.Course;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CourseRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QCourse qCourse = QCourse.course;

    public Optional<Course> findCourseByCourseId(Long courseId) {
        Course course = jpaQueryFactory.select(qCourse).from(qCourse)
                .where(qCourse.courseId.eq(courseId)).fetchOne();
        if (course == null) return Optional.empty();
        return Optional.ofNullable(course);
    }
}
