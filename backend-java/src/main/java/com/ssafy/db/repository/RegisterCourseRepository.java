package com.ssafy.db.repository;

import com.ssafy.db.entity.RegisterCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterCourseRepository extends JpaRepository<RegisterCourse, Long> {
    RegisterCourse findByUserIdAndCourseId(Long userId, Long courseId);
}
