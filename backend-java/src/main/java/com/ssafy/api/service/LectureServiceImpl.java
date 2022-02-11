package com.ssafy.api.service;

import com.ssafy.api.dto.CourseDto;
import com.ssafy.api.dto.LectureDto;
import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.Lecture;
import com.ssafy.db.repository.CourseRepository;
import com.ssafy.db.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.*;

@Service("lectureService")
public class LectureServiceImpl implements LectureService{
    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    CourseRepository courseRepository;

    @Override
    public Lecture createLecture(LectureDto.LectureInsertReq lectureInsertInfo) {
        Course course = courseRepository.getOne(lectureInsertInfo.getCourseId());

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

    @Override
    public List<LectureDto.LectureRes> getLectureList(String email){

        List<LectureDto.LectureRes> result = new ArrayList<>();
        List<Lecture> list = lectureRepository.findAll();

        for (Lecture lecture : list) {
            LectureDto.LectureRes lectureRes = new LectureDto.LectureRes();

            lectureRes.setLectureId(lecture.getLectureId());
            lectureRes.setLectureDate(lecture.getLectureDate());
            lectureRes.setLectureName(lecture.getLectureName());
            lectureRes.setLectureVod(lecture.getLectureVod());
            lectureRes.setLectureState(lecture.getLectureState());
            lectureRes.setLectureRuntime(lecture.getLectureRuntime());
            lectureRes.setLectureOpenTime(lecture.getLectureOpenTime());
            lectureRes.setLectureCloseTime(lecture.getLectureCloseTime());
            lectureRes.setLectureThumbnail(lecture.getLectureThumbnail());
            lectureRes.setCourse(lecture.getCourse());
            result.add(lectureRes);
        }

        return result;
    }

    @Override
    public Lecture getLectureInfo(Long lectureId) {
        return lectureRepository.getOne(lectureId);
    }

    @Override
    public Lecture updateLecture(Long lectureId, LectureDto.LectureInsertReq lectureInsertInfo) {
        Lecture lecture = lectureRepository.getOne(lectureId);

        Lecture newLecture = Lecture.builder()
                .lectureId(lecture.getLectureId())
                .lectureState(lecture.getLectureState())
                .lectureVod(lecture.getLectureVod())
                .lectureRuntime(lecture.getLectureRuntime())
                .lectureCloseTime(lecture.getLectureCloseTime())
                .lectureDate(lecture.getLectureDate())
                .lectureName(lecture.getLectureName())
                .lectureOpenTime(lecture.getLectureOpenTime())
                .lectureThumbnail(lecture.getLectureThumbnail())
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
