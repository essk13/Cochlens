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
    UserService userService;

    @Autowired
    CourseService courseService;

    @Autowired
    UserRepositorySupport userRepositorySupport;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    CourseRepository courseRepository;


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
                reviewRes.setCourse(review.getCourse());
                result.add(reviewRes);
            }
        }
        return result;
    }

    @Override
    public List<ReviewDto.ReviewListRes> getReviewListByCourseId(Long courseId){

        Course course = courseRepository.getOne(courseId);

        List<ReviewDto.ReviewListRes> result = new ArrayList<>();
        List<Review> list = reviewRepository.findAll();

        for (Review review : list) {
            if (review.getCourse() == course){
                ReviewDto.ReviewListRes reviewRes = new ReviewDto.ReviewListRes();

                reviewRes.setReviewId(review.getReviewId());
                reviewRes.setReviewDate(review.getReviewDate());
                reviewRes.setReviewGrade(review.getReviewGrade());
                reviewRes.setReviewContent(review.getReviewContent());
                reviewRes.setUser(review.getUser());
                result.add(reviewRes);
            }
        }
        return result;
    }

    @Override
    public Review createReview(User user, Long courseId, ReviewDto.ReviewInsertReq reviewInsertInfo){

        Course course = courseRepository.getOne(courseId);

        Review review = Review.builder()
                .reviewDate(reviewInsertInfo.getReviewDate())
                .reviewContent(reviewInsertInfo.getReviewContent())
                .reviewGrade(reviewInsertInfo.getReviewGrade())
                .user(user)
                .course(course)
                .build();

        return reviewRepository.save(review);
    }

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
    public void delete(Long reviewId) {
        Review review = reviewRepository.getOne(reviewId);

        reviewRepository.delete(review);
    }
}
