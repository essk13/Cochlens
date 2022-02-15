package com.ssafy.db.repository;

import com.ssafy.api.dto.CourseDto;
import com.ssafy.db.entity.User;

import java.util.List;
import java.util.Optional;

public interface CourseCustomRepository {
    public List<CourseDto.CourseListRes> findByRegisterCourse(User user);
    public List<CourseDto.CourseListRes> findByBestCourse();
    public Optional<CourseDto.CourseInstructorVO> findInstructorRate(User user);
    public List<CourseDto.CourseListRes> findAllByLiveList(User user);
    public List<CourseDto.CourseListRes> findAllByVodList(User user);
}
