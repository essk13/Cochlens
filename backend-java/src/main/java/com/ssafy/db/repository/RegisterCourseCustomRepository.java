package com.ssafy.db.repository;

import com.ssafy.api.dto.CourseDto;
import com.ssafy.db.entity.RegisterCourse;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.Wishlist;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface RegisterCourseCustomRepository {
    public List<CourseDto.CourseListRes> findRegisterCourseListByUser(User user);
    public List<CourseDto.CourseListRes> findWishCourseListByUser(User user);
    public Optional<RegisterCourse> findIsJoinCourseByEmailAndCourseId(String email, Long courseId);
    public Optional<Wishlist> findIsWishCourseByEmailAndCourseId(String email, Long courseId);
    public Long findJoinCountByCourseId(Long courseId);
}
