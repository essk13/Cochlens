package com.ssafy.db.repository;

import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.RegisterCourse;
import com.ssafy.db.entity.User;
import org.graalvm.compiler.nodes.calc.IntegerDivRemNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegisterCourseRepository extends JpaRepository<RegisterCourse, Long> {

//    Optional<RegisterCourse> findRegisterCourseByUserIdAndCourseId(User user, Course course);
//    Optional<RegisterCourse> findByRegisterCourseId(Long RegisterCourseId);
//    Optional<RegisterCourse> findByUserIdAndCourseId(User user, Course course);
//    Optional<RegisterCourse> findByUserIdAndCourseId(Long userId, Long courseId);
//    RegisterCourse findByUserIdAndCourseId(Long userId, Long courseId);
}
