package com.ssafy.api.service;

import com.ssafy.db.repository.CourseRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssafy.db.entity.Course;
import org.springframework.stereotype.Service;

@Service("courseService")
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepositorySupport courseRepositorySupport;

    @Override
    public Course getCourseByCourseId(Long courseId) {
        Course course = courseRepositorySupport.findCourseByCourseId(courseId).get();
        return course;
    }
}

