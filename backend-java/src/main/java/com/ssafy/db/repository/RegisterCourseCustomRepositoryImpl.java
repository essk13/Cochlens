package com.ssafy.db.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.dto.CourseDto;
import com.ssafy.db.entity.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static com.ssafy.db.entity.QCourse.course;
import static com.ssafy.db.entity.QRegisterCourse.registerCourse;
import static com.ssafy.db.entity.QWishlist.wishlist;

public class RegisterCourseCustomRepositoryImpl implements RegisterCourseCustomRepository {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CourseDto.CourseListRes> findRegisterCourseListByUser(User user) {
        return jpaQueryFactory.select(Projections.constructor(CourseDto.CourseListRes.class,
                        registerCourse.course.courseId, registerCourse.course.courseName, registerCourse.course.courseDescription,
                        registerCourse.course.courseCategory, registerCourse.course.courseThumbnail,
                        registerCourse.course.courseOpenDate, registerCourse.course.courseCloseDate,
                        registerCourse.course.courseFee, registerCourse.course.courseWishCount, registerCourse.course.courseReviewCount,
                        registerCourse.course.courseReviewRateAverage.as("courseReviewRateAverage"),
                        registerCourse.user.userName.as("instructorName"),
                        course.user.userNickname.as("instructorNickname")))
                .from(registerCourse)
                .where(registerCourse.user.eq(user))
                .fetch();
    }

    @Override
    public List<CourseDto.CourseListRes> findWishCourseListByUser(User user) {
        return jpaQueryFactory.select(Projections.constructor(CourseDto.CourseListRes.class,
                        wishlist.course.courseId, wishlist.course.courseName, wishlist.course.courseDescription,
                        wishlist.course.courseCategory, wishlist.course.courseThumbnail,
                        wishlist.course.courseOpenDate, wishlist.course.courseCloseDate,
                        wishlist.course.courseFee, wishlist.course.courseWishCount, wishlist.course.courseReviewCount,
                        wishlist.course.courseReviewRateAverage.as("courseReviewRateAverage"),
                        wishlist.user.userName.as("instructorName"),
                        wishlist.user.userNickname.as("instructorNickname")))
                .from(wishlist)
                .where(wishlist.user.eq(user))
                .fetch();
    }

    @Override
    public Optional<RegisterCourse> findIsJoinCourseByEmailAndCourseId(String email, Long courseId){
        RegisterCourse registerCourse = jpaQueryFactory
                .select(QRegisterCourse.registerCourse)
                .from(QRegisterCourse.registerCourse)
                .where(QRegisterCourse.registerCourse.user.email.eq(email))
                .where(QRegisterCourse.registerCourse.course.courseId.eq(courseId)).fetchOne();
        return Optional.ofNullable(registerCourse);
    }

    @Override
    public Optional<Wishlist> findIsWishCourseByEmailAndCourseId(String email, Long courseId){
        Wishlist wishlist = jpaQueryFactory
                .select(QWishlist.wishlist)
                .from(QWishlist.wishlist)
                .where(QWishlist.wishlist.user.email.eq(email))
                .where(QWishlist.wishlist.course.courseId.eq(courseId)).fetchOne();
        return Optional.ofNullable(wishlist);
    }

    @Override
    public Long findJoinCountByCourseId(Long courseId){
        return jpaQueryFactory.select(QRegisterCourse.registerCourse)
                .from(QRegisterCourse.registerCourse)
                .where(QRegisterCourse.registerCourse.course.courseId.eq(courseId))
                .fetchCount();

    }
}
