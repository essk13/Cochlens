package com.ssafy.db.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.MathExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.dto.UserDto;
import com.ssafy.db.entity.QUser;
import com.ssafy.db.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.ssafy.db.entity.QUser.user;
import static com.ssafy.db.entity.QCourse.course;

/**
 * 유저 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class UserCustomRepositoryImpl implements UserCustomRepository {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    public Optional<User> findUserByEmail(String email) {
        User user = jpaQueryFactory.select(QUser.user).from(QUser.user)
                .where(QUser.user.email.eq(email)).fetchOne();
        if(user == null) return Optional.empty();
        return Optional.ofNullable(user);
    }

    @Override
    public List<UserDto.UserInstructorRes> findInstructorList(Pageable pageable) {
        return jpaQueryFactory.select(Projections.constructor(UserDto.UserInstructorRes.class,
                        user.userId, user.email, user.userName, user.userNickname, user.profileImage,
                        course.courseId.count().as("courseCount"),
                        course.courseReviewCount.sum().as("courseReviewCount"),
                        MathExpressions.round(course.courseReviewRateAverage.avg(), 1).as("courseReviewRateAverage")))
                .from(user)
                .innerJoin(course)
                .on(user.eq(course.user))
                .groupBy(course.user)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

    @Override
    public List<UserDto.UserInstructorRes> findInstructorListByBest() {
        return jpaQueryFactory.select(Projections.constructor(UserDto.UserInstructorRes.class,
                        user.userId, user.email, user.userName, user.userNickname, user.profileImage,
                        course.courseId.count().as("courseCount"),
                        course.courseReviewCount.sum().as("courseReviewCount"),
                        MathExpressions.round(course.courseReviewRateAverage.avg(), 1).as("courseReviewRateAverage")))
                .from(user)
                .innerJoin(course)
                .on(user.eq(course.user))
                .groupBy(course.user)
                .orderBy(course.courseId.count().desc())
                .limit(5)
                .fetch();
    }

    @Override
    public List<UserDto.UserInstructorRes> findInstructorListByUserNickname(String instructorName, Pageable pageable) {
        return jpaQueryFactory.select(Projections.constructor(UserDto.UserInstructorRes.class,
                        user.userId, user.email, user.userName, user.userNickname, user.profileImage,
                        course.courseId.count().as("courseCount"),
                        course.courseReviewCount.sum().as("courseReviewCount"),
                        MathExpressions.round(course.courseReviewRateAverage.avg(), 1).as("courseReviewRateAverage")))
                .from(user)
                .innerJoin(course)
                .on(user.eq(course.user))
                .where(user.userNickname.contains(instructorName))
                .groupBy(course.user)
                .orderBy(course.courseId.count().desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }


}
