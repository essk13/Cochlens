package com.ssafy.api.service;

import com.ssafy.api.dto.CourseDto;
import com.ssafy.api.dto.ReviewDto;
import com.ssafy.db.entity.Review;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.ReviewRepository;
import com.ssafy.db.repository.CourseRepository;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRepositorySupport;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssafy.db.entity.Course;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("reviewService")
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    UserRepositorySupport userRepositorySupport;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    CourseRepository courseRepository;

    /*
        create
    */

    @Override
    public Review createReview(User user, Course course, ReviewDto.ReviewInsertReq reviewInsertInfo){
        Review review = Review.builder()
                .reviewDate(reviewInsertInfo.getReviewDate())
                .reviewContent(reviewInsertInfo.getReviewContent())
                .reviewGrade(reviewInsertInfo.getReviewGrade())
                .user(user)
                .course(course)
                .build();

        return reviewRepository.save(review);
    }

    /*
        read
    */

    @Override
    public List<ReviewDto.ReviewListRes> getReviewListByEmail(String email){

        User user = userRepositorySupport.findUserByEmail(email).get();

        List<ReviewDto.ReviewListRes> result = new ArrayList<>();
        List<Review> list = reviewRepository.findAll();

        for (Review review : list) {
            if (review.getUser() == user){
                ReviewDto.ReviewListRes reviewRes = new ReviewDto.ReviewListRes();

                reviewRes.setReviewId(review.getReviewId());
                reviewRes.setReviewDate(review.getReviewDate());
                reviewRes.setReviewGrade(review.getReviewGrade());
                reviewRes.setReviewContent(review.getReviewContent());
                result.add(reviewRes);
            }
        }
        return result;
    }

    @Override
    public List<ReviewDto.ReviewListRes> getReviewListByCourse(Course course) {
        List<Review> list = reviewRepository.findAllByCourse(course);

        List<ReviewDto.ReviewListRes> result = new ArrayList<>();

        for (Review review : list) {
            ReviewDto.ReviewListRes reviewRes = ReviewDto.ReviewListRes.of(review);
            result.add(reviewRes);
        }
        return result;
    }

    /*
        update
    */

    @Override
    public Review updateReview(Long reviewId, ReviewDto.ReviewInsertReq reviewInsertInfo){
        Review review =  reviewRepository.getOne(reviewId);

        Review newReview = Review.builder()
                .reviewDate(reviewInsertInfo.getReviewDate())
                .reviewContent(reviewInsertInfo.getReviewContent())
                .reviewGrade(reviewInsertInfo.getReviewGrade())
                .user(review.getUser())
                .course(review.getCourse())
                .build();

        return reviewRepository.save(newReview);

    }

    @Override
    public void updateReviewGrade(Course course) {
        double reviewGrade = reviewRepository.countReviewAverage(course).get();
        course.setCourseReviewGrade(reviewGrade);
        courseRepository.save(course);
        courseRepository.flush();
    }

    /*
        delete
    */

    @Override
    public void delete(Long reviewId) {
        Review review = reviewRepository.getOne(reviewId);

        reviewRepository.delete(review);
    }
}
