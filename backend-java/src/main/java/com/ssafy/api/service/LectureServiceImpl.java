package com.ssafy.api.service;

import com.ssafy.api.dto.LectureDto;
import com.ssafy.db.entity.Lecture;
import com.ssafy.db.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.*;

@Service("lectureService")
public class LectureServiceImpl implements LectureService{
    @Autowired
    LectureRepository lectureRepository;

    @Override
    public Lecture createLecture(LectureDto.LectureInsertReq lectureInsertInfo) {

//        String lectureDate = body.get("lectureDate").toString();
//        Integer value = Integer.parseInt(lectureDate);
//        int year = value / 10000;
//        int month = ((value % 10000) / 100) - 1;
//        int day = value % 100;
//        Date date = new GregorianCalendar(year, month, day).getTime();
//
        Lecture lecture = new Lecture();
//
//        lecture.setLectureName(body.get("lectureName").toString());
//        lecture.setLectureDate(date);
//        lecture.setLectureState(body.get("lectureState").toString());

        return lectureRepository.save(lecture);
    }

    @Override
    public List<LectureDto.LectureRes> getLectureList(String email){
        List<LectureDto.LectureRes> result = new ArrayList<>();
//        lectureRepository.findAll().forEach(lectureList -> {
//            Map<String, Object> obj = new HashMap<>();
//            obj.put("lectureId", lectureList.getLectureId());
//            result.add(obj);
//        });
        return result;
    }

    @Override
    public Lecture getLectureInfo(Long lectureId) {
        return lectureRepository.getOne(lectureId);
    }

    @Override
    public Lecture updateLecture(Long lectureId, LectureDto.LectureInsertReq lectureInsertInfo) {
        Lecture lecture = lectureRepository.getOne(lectureId);

//        String lectureDate = body.get("lectureDate").toString();
//        String lectureRuntime = body.get("lectureRuntime").toString();
//        String lectureOpenTime = body.get("lectureOpenTime").toString();
//        String lectureCloseTime = body.get("lectureCloseTime").toString();
//
//        Integer valueDate = Integer.parseInt(lectureDate);
//        Integer valueRuntime = Integer.parseInt(lectureRuntime);
//        Integer valueOpenTime = Integer.parseInt(lectureOpenTime);
//        Integer valueCloseTime = Integer.parseInt(lectureCloseTime);
//        int yearDate = valueDate / 10000;
//        int yearRuntime = valueRuntime / 10000;
//        int yearOpenTime = valueOpenTime / 10000;
//        int yearCloseTime = valueCloseTime / 10000;
//        int monthDate = ((valueDate % 10000) / 100) - 1;
//        int monthRuntime = ((valueRuntime % 10000) / 100) - 1;
//        int monthOpenTime = ((valueOpenTime % 10000) / 100) - 1;
//        int monthCloseTime = ((valueCloseTime % 10000) / 100) - 1;
//        int dayDate = valueDate % 100;
//        int dayRuntime = valueRuntime % 100;
//        int dayOpenTime = valueOpenTime % 100;
//        int dayCloseTime = valueCloseTime % 100;
//        Date date = new GregorianCalendar(yearDate, monthDate, dayDate).getTime();
//        Date dateRuntime = new GregorianCalendar(yearRuntime, monthRuntime, dayRuntime).getTime();
//        Date dateOpenTime = new GregorianCalendar(yearOpenTime, monthOpenTime, dayOpenTime).getTime();
//        Date dateCloseTime = new GregorianCalendar(yearCloseTime, monthCloseTime, dayCloseTime).getTime();

//        lecture.setLectureName(body.get("lectureName").toString());
//        lecture.setLectureThumbnail(body.get("lectureThumbnail").toString());
//        lecture.setLectureState(body.get("lectureState").toString());
//        lecture.setLectureVod(body.get("lectureVod").toString());
//        lecture.setLectureDate(date);
//        lecture.setLectureRuntime(dateRuntime);
//        lecture.setLectureOpenTime(dateOpenTime);
//        lecture.setLectureCloseTime(dateCloseTime);
        return lectureRepository.save(lecture);
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
