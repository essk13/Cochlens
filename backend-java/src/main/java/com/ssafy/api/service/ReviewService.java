package com.ssafy.api.service;

import com.ssafy.db.entity.Review;

import java.util.List;
import java.util.Map;

public interface ReviewService {

    Review createReview(Map<String, Object> body);
    Review updateReview(Long courseId, Map<String, Object> body);
    List<Map<String, Object>> getReviewList();
    Map<String, Object> getReviewInfo(Long reviewId);
}
