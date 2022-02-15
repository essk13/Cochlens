package com.ssafy.db.repository;

import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.RegisterCourse;
import com.ssafy.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegisterCourseRepository extends JpaRepository<RegisterCourse, Long> {
    Optional<RegisterCourse> findRegisterCourseByUserAndCourse(User user, Course course);
}
