package com.ssafy.api.service;

import com.ssafy.db.entity.Lecture;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.CourseRepository;
import com.ssafy.db.repository.CourseRepositorySupport;
import com.ssafy.db.repository.LectureRepository;
import lombok.var;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssafy.db.entity.Course;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service("lectureService")
public class LectureServiceImpl implements LectureService{

    @Autowired
    CourseRepositorySupport courseRepositorySupport;

    @Autowired
    UserService userService;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    LectureRepository lectureRepository;

    @Override
    public List<Map<String, Object>> getLectureList(){
        List<Map<String, Object>> result = new ArrayList<>();
        lectureRepository.findAll().forEach(lectureList -> {
            Map<String, Object> obj = new HashMap<>();
            obj.put("lectureId", lectureList.getLectureId());
            result.add(obj);
        });
        return result;
    }


    @Override
    public Lecture createLecture(Map<String, Object> body) {

        String lectureDate = body.get("lectureDate").toString();
        Integer value = Integer.parseInt(lectureDate);
        int year = value / 10000;
        int month = ((value % 10000) / 100) - 1;
        int day = value % 100;
        Date date = new GregorianCalendar(year, month, day).getTime();

        Lecture lecture = new Lecture();

        lecture.setLectureName(body.get("lectureName").toString());
        lecture.setLectureDate(date);
        lecture.setLectureState(body.get("lectureState").toString());

        return lectureRepository.save(lecture);
    }

    @Override
    public Map<String, Object> getLectureInfo(Long lectureId){
        var option = lectureRepository.findById(lectureId);

        if (!option.isPresent())
            return null;

        Lecture lecture = option.get();
        Map<String, Object> obj = new HashMap<>();
        obj.put("lectureId", lecture.getLectureId());
        obj.put("lectureCloseTime", lecture.getLectureCloseTime());
        obj.put("lectureDate", lecture.getLectureDate());
        obj.put("lectureName", lecture.getLectureName());
        obj.put("lectureOpenTime", lecture.getLectureOpenTime());
        obj.put("lectureRuntime", lecture.getLectureRuntime());
        obj.put("lectureState", lecture.getLectureState());
        obj.put("lectureThumbnail", lecture.getLectureThumbnail());
        obj.put("lectureVod", lecture.getLectureVod());
        return obj;
    }

    @Override
    public Lecture updateLecture(Long lectureId, Map<String, Object> body){

        var option = lectureRepository.findById(lectureId);

        if (!option.isPresent())
            return null;

        String lectureDate = body.get("lectureDate").toString();
        String lectureRuntime = body.get("lectureRuntime").toString();
        String lectureOpenTime = body.get("lectureOpenTime").toString();
        String lectureCloseTime = body.get("lectureCloseTime").toString();

        Integer valueDate = Integer.parseInt(lectureDate);
        Integer valueRuntime = Integer.parseInt(lectureRuntime);
        Integer valueOpenTime = Integer.parseInt(lectureOpenTime);
        Integer valueCloseTime = Integer.parseInt(lectureCloseTime);
        int yearDate = valueDate / 10000;
        int yearRuntime = valueRuntime / 10000;
        int yearOpenTime = valueOpenTime / 10000;
        int yearCloseTime = valueCloseTime / 10000;
        int monthDate = ((valueDate % 10000) / 100) - 1;
        int monthRuntime = ((valueRuntime % 10000) / 100) - 1;
        int monthOpenTime = ((valueOpenTime % 10000) / 100) - 1;
        int monthCloseTime = ((valueCloseTime % 10000) / 100) - 1;
        int dayDate = valueDate % 100;
        int dayRuntime = valueRuntime % 100;
        int dayOpenTime = valueOpenTime % 100;
        int dayCloseTime = valueCloseTime % 100;
        Date date = new GregorianCalendar(yearDate, monthDate, dayDate).getTime();
        Date dateRuntime = new GregorianCalendar(yearRuntime, monthRuntime, dayRuntime).getTime();
        Date dateOpenTime = new GregorianCalendar(yearOpenTime, monthOpenTime, dayOpenTime).getTime();
        Date dateCloseTime = new GregorianCalendar(yearCloseTime, monthCloseTime, dayCloseTime).getTime();

        Lecture lecture = option.get();
        lecture.setLectureName(body.get("lectureName").toString());
        lecture.setLectureThumbnail(body.get("lectureThumbnail").toString());
        lecture.setLectureState(body.get("lectureState").toString());
        lecture.setLectureVod(body.get("lectureVod").toString());
        lecture.setLectureDate(date);
        lecture.setLectureRuntime(dateRuntime);
        lecture.setLectureOpenTime(dateOpenTime);
        lecture.setLectureCloseTime(dateCloseTime);
        return lectureRepository.save(lecture);
    }
}
