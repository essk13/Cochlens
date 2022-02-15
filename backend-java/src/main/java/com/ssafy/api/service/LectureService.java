package com.ssafy.api.service;

import com.ssafy.api.dto.LectureDto;
import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.Lecture;

import java.util.List;

public interface LectureService {
    /* create */
    Lecture createLecture(Course course, LectureDto.LectureInsertReq lectureInsertInfo);
    /* read */
    List<LectureDto.LectureListRes> getLectureList(Course course);
    Lecture getLectureInfo(Long courseId);
    /* update */
    Lecture updateLecture(Long lectureId, LectureDto.LectureInsertReq lectureInsertInfo);
    void openLecture(Long lectureId);
    void closeLecture(Long lectureId);
    /* delete */
//    List<Map<String, Object>> getLectureByCourseId(Long courseId);
}