package com.ssafy.db.repository;

import com.ssafy.api.dto.ReviewDto;
import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.User;

import java.util.List;
import java.util.Optional;

public interface ReviewCustomRepository {
    Optional<Double> countReviewAverage(Course course);
    public List<ReviewDto.ReviewListRes> findAllByUser(User user);
}
