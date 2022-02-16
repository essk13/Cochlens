package com.ssafy.db.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.MathExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.dto.CourseDto;
import com.ssafy.db.entity.QWishlist;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.ssafy.db.entity.QCourse.course;
import static com.ssafy.db.entity.QRegisterCourse.registerCourse;

public class CourseCustomRepositoryImpl implements CourseCustomRepository {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CourseDto.CourseListRes> findCourseListByBest() {
        return jpaQueryFactory.select(Projections.constructor(CourseDto.CourseListRes.class,
                        course.courseId, course.courseName, course.courseDescription,
                        course.courseCategory, course.courseThumbnail,
                        course.courseOpenDate, course.courseCloseDate,
                        course.courseFee, course.courseWishCount, course.courseReviewCount,
                        course.courseReviewRateAverage.as("courseReviewRateAverage"), course.user.userName.as("instructorName")))
                .from(course)
                .orderBy(course.courseWishCount.desc())
                .limit(5)
                .fetch();
    }

    @Override
    public List<CourseDto.CourseListRes> findCourseListByRecent(User user) {
        return jpaQueryFactory.select(Projections.constructor(CourseDto.CourseListRes.class,
                        registerCourse.course.courseId, registerCourse.course.courseName, registerCourse.course.courseDescription,
                        registerCourse.course.courseCategory, registerCourse.course.courseThumbnail,
                        registerCourse.course.courseOpenDate, registerCourse.course.courseCloseDate,
                        registerCourse.course.courseFee, registerCourse.course.courseWishCount, registerCourse.course.courseReviewCount,
                        registerCourse.course.courseReviewRateAverage.as("courseReviewRateAverage"), registerCourse.course.user.userName.as("instructorName")))
                .from(registerCourse)
                .where(registerCourse.user.eq(user))
                .fetch();
    }

    @Override
    public List<CourseDto.CourseListRes> findCourseListByCourseName(String courseName, Pageable pageable) {
        return jpaQueryFactory.select(Projections.constructor(CourseDto.CourseListRes.class,
                        course.courseId, course.courseName, course.courseDescription,
                        course.courseCategory, course.courseThumbnail,
                        course.courseOpenDate, course.courseCloseDate,
                        course.courseFee, course.courseWishCount, course.courseReviewCount,
                        course.courseReviewRateAverage.as("courseReviewRateAverage"), course.user.userName.as("instructorName")))
                .from(course)
                .where(course.courseName.contains(courseName))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

    @Override
    public Optional<CourseDto.CourseInstructorVO> findInstructorRate(User user) {
        CourseDto.CourseInstructorVO result =
                jpaQueryFactory.select(Projections.constructor(CourseDto.CourseInstructorVO.class,
                                course.courseId.count().as("courseCount"),
                                course.courseReviewCount.sum().as("courseReviewCount"),
                                MathExpressions.round(course.courseReviewRateAverage.avg(), 1).as("courseReviewGrade")))
                        .from(course)
                        .where(course.user.eq(user))
                        .groupBy(course.user.userId)
                        .fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public List<CourseDto.CourseListRes> findAllByLiveList(User user) {
        return jpaQueryFactory.select(Projections.constructor(CourseDto.CourseListRes.class,
                        course.courseId, course.courseName, course.courseDescription,
                        course.courseCategory, course.courseThumbnail,
                        course.courseOpenDate, course.courseCloseDate,
                        course.courseFee, course.courseWishCount, course.courseReviewCount,
                        course.courseReviewRateAverage.as("courseReviewRateAverage"), course.user.userName.as("instructorName")))
                .from(course)
                .where(course.user.eq(user), Expressions.currentDate().between(course.courseOpenDate, course.courseCloseDate))
                .fetch();
    }

    @Override
    public List<CourseDto.CourseListRes> findAllByVodList(User user) {
        return jpaQueryFactory.select(Projections.constructor(CourseDto.CourseListRes.class,
                        course.courseId, course.courseName, course.courseDescription,
                        course.courseCategory, course.courseThumbnail,
                        course.courseOpenDate, course.courseCloseDate,
                        course.courseFee, course.courseWishCount, course.courseReviewCount,
                        course.courseReviewRateAverage.as("courseReviewRateAverage"), course.user.userName.as("instructorName")))
                .from(course)
                .where(course.user.eq(user), Expressions.currentDate().between(course.courseOpenDate, course.courseCloseDate).not())
                .fetch();
    }
}
