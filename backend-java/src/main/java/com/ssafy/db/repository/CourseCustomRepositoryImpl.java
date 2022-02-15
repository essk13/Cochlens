package com.ssafy.db.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.MathExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.dto.CourseDto;
import com.ssafy.db.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static com.ssafy.db.entity.QCourse.course;
import static com.ssafy.db.entity.QRegisterCourse.registerCourse;

public class CourseCustomRepositoryImpl implements CourseCustomRepository {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CourseDto.CourseListRes> findByRegisterCourse(User user) {
        return jpaQueryFactory.select(Projections.bean(CourseDto.CourseListRes.class,
                        registerCourse.course.courseId, registerCourse.course.courseName, registerCourse.course.courseDescription,
                        registerCourse.course.courseThumbnail, registerCourse.course.courseCategory,
                        registerCourse.course.courseOpenDate, registerCourse.course.courseCloseDate, registerCourse.course.courseFee,
                        registerCourse.course.user.userName.as("instructorName"), registerCourse.course.courseReviewCount,
                        registerCourse.course.courseReviewGrade.as("courseReviewRateAverage"), registerCourse.course.courseWishCount))
                .from(registerCourse)
                .where(registerCourse.user.eq(user))
                .fetch();
    }

    @Override
    public List<CourseDto.CourseListRes> findByBestCourse() {
        return jpaQueryFactory.select(Projections.bean(CourseDto.CourseListRes.class,
                        course.courseId, course.courseName, course.courseDescription, course.courseThumbnail,
                        course.courseCategory, course.courseOpenDate, course.courseCloseDate,
                        course.courseFee, course.user.userName.as("instructorName"),
                        course.courseReviewCount, course.courseReviewGrade.as("courseReviewRateAverage"), course.courseWishCount))
                .from(course)
                .orderBy(course.courseWishCount.desc())
                .limit(5)
                .fetch();
    }

    @Override
    public Optional<CourseDto.CourseInstructorVO> findInstructorRate(User user) {
        CourseDto.CourseInstructorVO result =
                jpaQueryFactory.select(Projections.constructor(CourseDto.CourseInstructorVO.class,
                                course.courseId.count().as("courseCount"),
                                course.courseReviewCount.sum().as("courseReviewCount"),
                                MathExpressions.round(course.courseReviewGrade.avg(), 1).as("courseReviewGrade")))
                        .from(course)
                        .where(course.user.eq(user))
                        .groupBy(course.user.userId)
                        .fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public List<CourseDto.CourseListRes> findAllByLiveList(User user) {
        return jpaQueryFactory.select(Projections.bean(CourseDto.CourseListRes.class,
                        course.courseId, course.courseName, course.courseDescription, course.courseThumbnail,
                        course.courseCategory, course.courseOpenDate, course.courseCloseDate,
                        course.courseFee, course.user.userName.as("instructorName"),
                        course.courseReviewCount, course.courseReviewGrade.as("courseReviewRateAverage"), course.courseWishCount))
                .from(course)
                .where(course.user.eq(user), Expressions.currentDate().between(course.courseOpenDate, course.courseCloseDate))
                .fetch();
    }

    @Override
    public List<CourseDto.CourseListRes> findAllByVodList(User user) {
        return jpaQueryFactory.select(Projections.bean(CourseDto.CourseListRes.class,
                        course.courseId, course.courseName, course.courseDescription, course.courseThumbnail,
                        course.courseCategory, course.courseOpenDate, course.courseCloseDate,
                        course.courseFee, course.user.userName.as("instructorName"),
                        course.courseReviewCount, course.courseReviewGrade.as("courseReviewRateAverage"), course.courseWishCount))
                .from(course)
                .where(course.user.eq(user), Expressions.currentDate().between(course.courseOpenDate, course.courseCloseDate).not())
                .fetch();
    }
}
