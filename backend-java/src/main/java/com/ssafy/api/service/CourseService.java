package com.ssafy.api.service;

import com.ssafy.db.entity.Course;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface CourseService {
    Course getCourseByCourseId(Long courseId);
    Course createCourse(Map<String, Object> body);
    Course updateCourse(Long courseId, Map<String, Object> body);
    List<Map<String, Object>> getCourseList();
    Map<String, Object> getCourseInfo(Long courseId);
}
