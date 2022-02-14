package com.ssafy.db.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.MathExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.dto.ReviewDto;
import com.ssafy.api.dto.UserDto;
import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static com.ssafy.db.entity.QReview.review;
import static com.ssafy.db.entity.QCourse.course;

public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Double> countReviewAverage(Course course) {
        double result = jpaQueryFactory.select(MathExpressions.round(review.reviewGrade.avg(), 1))
            .from(review)
            .where(review.course.eq(course))
            .groupBy(review.course)
            .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<ReviewDto.ReviewListRes> findAllByUser(User user) {
        return jpaQueryFactory.select(Projections.constructor(ReviewDto.ReviewListRes.class,
                        review.reviewId, review.reviewContent, review.reviewDate, review.reviewGrade,
                        Projections.constructor(UserDto.UserReviewRes.class, review.user.userName, review.user.userNickname, review.user.profileImage)))
                .from(review)
                .innerJoin(course).on(review.course.eq(course))
                .where(course.user.eq(user))
                .fetch();
    }
}
