package com.ssafy.api.service;

import com.ssafy.db.entity.Course;

public interface CourseService {
    Course getCourseByCourseId(Long courseId);

}
