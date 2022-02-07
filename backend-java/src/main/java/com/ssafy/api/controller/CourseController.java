package com.ssafy.api.controller;

import com.ssafy.api.service.CourseService;
import com.ssafy.api.service.LectureService;
import com.ssafy.api.service.UserService;
import com.ssafy.db.entity.Course;
import com.ssafy.db.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;

import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("/course")
@CrossOrigin("*")
public class CourseController {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    @Autowired
    LectureService lectureService;

    @GetMapping
    public List<Map<String, Object>> list() {

        List<Map<String, Object>> result = courseService.getCourseList();
        return result;
    }


    @GetMapping("/{courseId}")
    public Map<String, Object> course(@PathVariable Long courseId){

        Map<String, Object> course = courseService.getCourseInfo(courseId);
        return course;
    }

    @DeleteMapping("/{courseId}")
    public void delete(@PathVariable Long courseId){
        courseRepository.deleteById(courseId);
    }

    @PostMapping
    public Long post(@RequestBody Map<String, Object> body) {

        Course course = courseService.createCourse(body);

        return Long.valueOf(1);
    }

    @PutMapping("/{courseId}")
    public Map<String, Object> update(@PathVariable Long courseId, @RequestBody Map<String, Object> body) {

        Course course = courseService.updateCourse(courseId, body);

        if (course == null){
            return Map.of("Message", "course수정실패");
        }
        return Map.of("Message", "course수정");

    }

//    강좌를 통한 강의 검색
//    @GetMapping("/{courseId}/lecture")
//    public List<Map<String, Object>> getLectureByCourseId(@PathVariable Long courseId) {
//
//        List<Map<String, Object>> result = lectureService.getLectureByCourseId(courseId);
//        return result;
//
//    }
}
