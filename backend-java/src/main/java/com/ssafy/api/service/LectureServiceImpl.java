package com.ssafy.api.service;

import com.ssafy.api.dto.LectureDto;
import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.Lecture;
import com.ssafy.db.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.*;

@Service("lectureService")
public class LectureServiceImpl implements LectureService{
    @Autowired
    LectureRepository lectureRepository;

    /*
        create
    */

    @Override
    public Lecture createLecture(Course course, LectureDto.LectureInsertReq lectureInsertInfo) {
        Lecture lecture = Lecture.builder()
                .lectureName(lectureInsertInfo.getLectureName())
                .lectureRuntime(lectureInsertInfo.getLectureRuntime())
                .lectureThumbnail(lectureInsertInfo.getLectureThumbnail())
                .lectureDate(lectureInsertInfo.getLectureDate())
                .lectureState(lectureInsertInfo.getLectureState())
                .lectureVod(lectureInsertInfo.getLectureVod())
                .lectureOpenTime(lectureInsertInfo.getLectureOpenTime())
                .lectureCloseTime(lectureInsertInfo.getLectureCloseTime())
                .course(course)
                .build();

        return lectureRepository.save(lecture);
    }

    /*
        read
    */

    @Override
    public List<LectureDto.LectureListRes> getLectureList(Course course){
        List<Lecture> list = lectureRepository.findAllByAndCourse(course);
        List<LectureDto.LectureListRes> result = new ArrayList<>();

        for (Lecture lecture : list) {
            LectureDto.LectureListRes lectureListRes = LectureDto.LectureListRes.of(lecture);

            result.add(lectureListRes);
        }

        return result;
    }

    @Override
    public Lecture getLectureInfo(Long lectureId) {
        return lectureRepository.getOne(lectureId);
    }

    /*
        update
    */

    @Override
    public Lecture updateLecture(Long lectureId, LectureDto.LectureInsertReq lectureInsertInfo) {
        Lecture lecture = lectureRepository.getOne(lectureId);

        Lecture newLecture = Lecture.builder()
                .lectureId(lecture.getLectureId())
                .lectureState(lectureInsertInfo.getLectureState())
                .lectureVod(lectureInsertInfo.getLectureVod())
                .lectureRuntime(lectureInsertInfo.getLectureRuntime())
                .lectureCloseTime(lectureInsertInfo.getLectureCloseTime())
                .lectureDate(lectureInsertInfo.getLectureDate())
                .lectureName(lectureInsertInfo.getLectureName())
                .lectureOpenTime(lectureInsertInfo.getLectureOpenTime())
                .lectureThumbnail(lectureInsertInfo.getLectureThumbnail())
                .course(lecture.getCourse())
                .build();

        return lectureRepository.save(newLecture);
    }

//    @Override
//    public List<Map<String, Object>> getLectureByCourseId(Long courseId) {
//        Course course = courseService.getCourseByCourseId(courseId);
//        List<Map<String, Object>> result = new ArrayList<>();
//        lectureRepository.findAll().forEach(lectureList -> {
//            Map<String, Object> obj = new HashMap<>();
//            if (lectureList.getCourse() == course){
//                obj.put("lectureId", lectureList.getLectureId());
//                result.add(obj);
//            }
//        });
//        return result;
//    }
}
