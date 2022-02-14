package com.ssafy.db.repository;

import com.querydsl.core.types.dsl.MathExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.ssafy.db.entity.QReview.review;

public class CustomizedReviewRepositoryImpl implements CustomizedReviewRepository {
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
}
