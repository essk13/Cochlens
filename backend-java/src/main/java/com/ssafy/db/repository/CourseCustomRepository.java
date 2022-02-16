package com.ssafy.db.repository;

import com.ssafy.api.dto.CourseDto;
import com.ssafy.db.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CourseCustomRepository {
    public List<CourseDto.CourseListRes> findCourseListByBest();
    public List<CourseDto.CourseListRes> findCourseListByRecent(User user);
    public List<CourseDto.CourseListRes> findCourseListByCourseName(String courseName, Pageable pageable);
    public Optional<CourseDto.CourseInstructorVO> findInstructorRate(User user);
    public List<CourseDto.CourseListRes> findAllByLiveList(User user);
    public List<CourseDto.CourseListRes> findAllByVodList(User user);
}
