package com.ssafy.db.repository;

import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewCustomRepository {
    List<Review> findAllByCourse(Course course);
}

