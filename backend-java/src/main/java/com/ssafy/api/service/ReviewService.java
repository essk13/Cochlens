package com.ssafy.api.service;

import com.ssafy.api.dto.CourseDto;
import com.ssafy.api.dto.ReviewDto;
import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.Review;
import com.ssafy.db.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ReviewService {
    /* create */
    Review createReview(User user, Course course, ReviewDto.ReviewInsertReq reviewInsertInfo);
    /* read */
    List<ReviewDto.ReviewListRes> getReviewListByCourse(Course course);
    List<ReviewDto.ReviewListRes> getReviewListByUser(User user);
    /* update */
    Review updateReview(Long reviewId, ReviewDto.ReviewInsertReq reviewInsertInfo);
    void updateReviewGrade(Course course);
    /* delete */
    void deleteReview(Long reviewId);
}
