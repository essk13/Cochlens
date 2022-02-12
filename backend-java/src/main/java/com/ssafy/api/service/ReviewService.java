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
    Review createReview(User user, Long courseId, ReviewDto.ReviewInsertReq reviewInsertInfo);
    Review updateReview(Long reviewId, ReviewDto.ReviewInsertReq reviewInsertInfo);
    List<ReviewDto.ReviewListRes> getReviewListByCourseId(Long courseId);
    List<ReviewDto.ReviewListRes> getReviewListByEmail(String email);
    void delete(Long reviewId);
}
