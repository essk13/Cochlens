package com.ssafy.api.service;

import com.ssafy.db.entity.Lecture;

import java.util.List;
import java.util.Map;

public interface LectureService {
    Lecture createLecture(Map<String, Object> body);
    Lecture updateLecture(Long lectureId, Map<String, Object> body);
    List<Map<String, Object>> getLectureList();
    Map<String, Object> getLectureInfo(Long courseId);
//    List<Map<String, Object>> getLectureByCourseId(Long courseId);
}
