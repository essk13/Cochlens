package com.ssafy.db.repository;

import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.Review;

import java.util.List;
import java.util.Optional;

public interface CustomizedReviewRepository {
    Optional<Double> countReviewAverage(Course course);
}
