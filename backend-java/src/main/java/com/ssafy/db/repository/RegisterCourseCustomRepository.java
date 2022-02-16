package com.ssafy.db.repository;

import com.ssafy.api.dto.CourseDto;
import com.ssafy.db.entity.User;

import java.util.List;

public interface RegisterCourseCustomRepository {
    public List<CourseDto.CourseListRes> findRegisterCourseListByUser(User user);
    public List<CourseDto.CourseListRes> findWishCourseListByUser(User user);
    public Boolean findIsJoinCourseByEmailAndCourseId(String email, Long courseId);
    public Boolean findIsWishCourseByEmailAndCourseId(String email, Long courseId);
    public Long findJoinCountByCourseId(Long courseId);
}
