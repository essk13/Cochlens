package com.ssafy.api.controller;

import com.ssafy.api.service.CourseService;
import com.ssafy.api.service.UserService;
import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.CourseRepository;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @GetMapping
    public List<Map<String, Object>> list() {

        List<Map<String, Object>> result = courseService.getCourseList();
        return result;
//        List<Map<String, Object>> result = new ArrayList<>();
//        courseRepository.findAll().forEach(courseList -> {
//            Map<String, Object> obj = new HashMap<>();
//            obj.put("courseId", courseList.getCourseId());
//            result.add(obj);
//        });
//        return result;
    }

    @GetMapping("/{courseId}")
    public Map<String, Object> course(@PathVariable Long courseId){

        Map<String, Object> course = courseService.getCourseInfo(courseId);
        return course;
//        var option = courseRepository.findById(courseId);
//        if (!option.isPresent())
//            return null;
//        Course course = option.get();
//        Map<String, Object> obj = new HashMap<>();
//        obj.put("courseId", course.getCourseId());
//        obj.put("courseCategory", course.getCourseCategory());
//        obj.put("courseCloseDate", course.getCourseCloseDate());
//        obj.put("courseCycle", course.getCourseCycle());
//        obj.put("courseDescription", course.getCourseDescription());
//        obj.put("courseFee", course.getCourseFee());
//        obj.put("courseIntroVideo", course.getCourseIntroVideo());
//        obj.put("courseLimitPeople", course.getCourseLimitPeople());
//        obj.put("courseName", course.getCourseName());
//        obj.put("courseOpenDate", course.getCourseOpenDate());
//        obj.put("courseThumbnail", course.getCourseThumbnail());
//        obj.put("user", course.getUser());
//        return obj;
    }

    @DeleteMapping("/{courseId}")
    public void delete(@PathVariable Long courseId){
        courseRepository.deleteById(courseId);
    }

    @PostMapping
    public Long post(@RequestBody Map<String, Object> body) {

        Course course = courseService.createCourse(body);

        return Long.valueOf(1);
//        String open_date = body.get("courseOpenDate").toString();
//        Integer value = Integer.parseInt(open_date);
//        int year = value / 10000;
//        int month = ((value % 10000) / 100) - 1;
//        int day = value % 100;
//        Date date = new GregorianCalendar(year, month, day).getTime();
//
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        System.out.println("principal : " + principal);
//
//        User user = userService.getUserByEmail(principal.toString());
//
//        return courseRepository.save(Course.builder()
//            .courseName(body.get("courseName").toString())
//            .courseOpenDate(date)
//            .courseCycle(Integer.parseInt(body.get("courseCycle").toString()))
//            .courseThumbnail(body.get("courseThumbnail").toString())
//            .courseIntroVideo(body.get("courseIntroVideo").toString())
//            .courseDescription(body.get("courseDescription").toString())
//            .courseCategory(body.get("courseCategory").toString())
//            .courseLimitPeople(Integer.parseInt(body.get("courseLimitPeople").toString()))
//            .courseFee(Integer.parseInt(body.get("courseFee").toString()))
//            .user(user)
//            .build()).getCourseId();
    }

    @PutMapping("/{courseId}")
    public Map<String, Object> update(@PathVariable Long courseId, @RequestBody Map<String, Object> body) {

        Course course = courseService.updateCourse(courseId, body);

        if (course == null){
            return Map.of("Message", "course수정실패");
        }
        return Map.of("Message", "course수정");


//        var option = courseRepository.findById(courseId);
//
//        if (!option.isPresent())
//            return null;
//        String open_date = body.get("courseOpenDate").toString();
//        Integer value = Integer.parseInt(open_date);
//        int year = value / 10000;
//        int month = ((value % 10000) / 100) - 1;
//        int day = value % 100;
//        Date date = new GregorianCalendar(year, month, day).getTime();
//
//
//        String close_date = body.get("courseCloseDate").toString();
//        Integer value_close = Integer.parseInt(close_date);
//        int year_close = value_close / 10000;
//        int month_close = ((value_close % 10000) / 100) - 1;
//        int day_close = value_close % 100;
//        Date date_close = new GregorianCalendar(year_close, month_close, day_close).getTime();
//
//        Course course = option.get();
//        course.setCourseName(body.get("courseName").toString());
//        course.setCourseOpenDate(date);
//        course.setCourseCloseDate(date_close);
//        course.setCourseCycle(Integer.parseInt(body.get("courseCycle").toString()));
//        course.setCourseThumbnail(body.get("courseThumbnail").toString());
//        course.setCourseIntroVideo(body.get("courseIntroVideo").toString());
//        course.setCourseDescription(body.get("courseDescription").toString());
//        course.setCourseCategory(body.get("courseCategory").toString());
//        course.setCourseLimitPeople(Integer.parseInt(body.get("courseLimitPeople").toString()));
//        course.setCourseFee(Integer.parseInt(body.get("courseFee").toString()));
//        courseRepository.save(course);
//        return Map.of("Message", "course수정");
    }
}
