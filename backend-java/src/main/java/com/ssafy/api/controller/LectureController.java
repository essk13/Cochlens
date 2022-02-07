package com.ssafy.api.controller;

import com.ssafy.api.service.LectureService;
import com.ssafy.api.service.UserService;


import com.ssafy.db.entity.Lecture;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.LectureRepository;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("/lecture")
@CrossOrigin("*")
public class LectureController {

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    UserService userService;

    @Autowired
    LectureService lectureService;

    @GetMapping
    public List<Map<String, Object>> list() {

        List<Map<String, Object>> result = lectureService.getLectureList();
        return result;

//        List<Map<String, Object>> result = new ArrayList<>();
//        lectureRepository.findAll().forEach(lectureList -> {
//            Map<String, Object> obj = new HashMap<>();
//            obj.put("lectureId", lectureList.getLectureId());
//            result.add(obj);
//        });
//        return result;
    }

    @GetMapping("/{lectureId}")
    public Map<String, Object> lecture(@PathVariable Long lectureId){

        Map<String, Object> lecture = lectureService.getLectureInfo(lectureId);
        return lecture;
//        var option = lectureRepository.findById(lectureId);
//        if (!option.isPresent())
//            return null;
//        Lecture lecture = option.get();
//        Map<String, Object> obj = new HashMap<>();
//        obj.put("lectureId", lecture.getLectureId());
//        obj.put("lectureCloseTime", lecture.getLectureCloseTime());
//        obj.put("lectureDate", lecture.getLectureDate());
//        obj.put("lectureName", lecture.getLectureName());
//        obj.put("lectureOpenTime", lecture.getLectureOpenTime());
//        obj.put("lectureRuntime", lecture.getLectureRuntime());
//        obj.put("lectureState", lecture.getLectureState());
//        obj.put("lectureThumbnail", lecture.getLectureThumbnail());
//        obj.put("lectureVod", lecture.getLectureVod());
//        return obj;
    }

    @DeleteMapping("/{lectureId}")
    public void delete(@PathVariable Long lectureId){
        lectureRepository.deleteById(lectureId);
    }

    @PostMapping
    public Long post(@RequestBody Map<String, Object> body) {


        Lecture lecture = lectureService.createLecture(body);

        return Long.valueOf(1);
//        String lectureDate = body.get("lectureDate").toString();
//        Integer value = Integer.parseInt(lectureDate);
//        int year = value / 10000;
//        int month = ((value % 10000) / 100) - 1;
//        int day = value % 100;
//        Date date = new GregorianCalendar(year, month, day).getTime();
//
//        return lectureRepository.save(Lecture.builder()
//                .lectureName(body.get("lectureName").toString())
//                .lectureDate(date)
//                .lectureState(body.get("lectureState").toString())
//                .build()).getLectureId();
    }

    @PutMapping("/{lectureId}")
    public Map<String, Object> update(@PathVariable Long lectureId, @RequestBody Map<String, Object> body) {

        Lecture lecture = lectureService.updateLecture(lectureId, body);

        if (lecture == null){
            return Map.of("Message", "lecture수정실패");
        }
        return Map.of("Message", "lecture수정");
//        var option = lectureRepository.findById(lectureId);
//
//        if (!option.isPresent())
//            return null;
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
//
//        Lecture lecture = option.get();
//        lecture.setLectureName(body.get("lectureName").toString());
//        lecture.setLectureThumbnail(body.get("lectureThumbnail").toString());
//        lecture.setLectureState(body.get("lectureState").toString());
//        lecture.setLectureVod(body.get("lectureVod").toString());
//        lecture.setLectureDate(date);
//        lecture.setLectureRuntime(dateRuntime);
//        lecture.setLectureOpenTime(dateOpenTime);
//        lecture.setLectureCloseTime(dateCloseTime);
//        lectureRepository.save(lecture);
//        return Map.of("Message", "lecture수정");


    }
}
