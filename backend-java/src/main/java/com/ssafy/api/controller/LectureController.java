package com.ssafy.api.controller;

import com.ssafy.api.service.LectureService;
import com.ssafy.api.service.UserService;


import com.ssafy.db.entity.Lecture;
import com.ssafy.db.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;

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

    }

    @GetMapping("/{lectureId}")
    public Map<String, Object> lecture(@PathVariable Long lectureId){

        Map<String, Object> lecture = lectureService.getLectureInfo(lectureId);
        return lecture;
    }

    @DeleteMapping("/{lectureId}")
    public void delete(@PathVariable Long lectureId){
        lectureRepository.deleteById(lectureId);
    }

    @PostMapping
    public Long post(@RequestBody Map<String, Object> body) {


        Lecture lecture = lectureService.createLecture(body);

        return Long.valueOf(1);
    }

    @PutMapping("/{lectureId}")
    public Map<String, Object> update(@PathVariable Long lectureId, @RequestBody Map<String, Object> body) {

        Lecture lecture = lectureService.updateLecture(lectureId, body);

        if (lecture == null){
            return Map.of("Message", "lecture수정실패");
        }
        return Map.of("Message", "lecture수정");
    }
}
