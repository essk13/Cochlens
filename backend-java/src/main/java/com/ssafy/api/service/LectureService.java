package com.ssafy.api.service;

import com.ssafy.api.dto.LectureDto;
import com.ssafy.db.entity.Lecture;

import java.util.List;

public interface LectureService {
    Lecture createLecture(LectureDto.LectureInsertReq lectureInsertInfo);
    List<LectureDto.LectureRes> getLectureList(String email);
    Lecture getLectureInfo(Long courseId);
    Lecture updateLecture(Long lectureId, LectureDto.LectureInsertReq lectureInsertInfo);
//    List<Map<String, Object>> getLectureByCourseId(Long courseId);
}
