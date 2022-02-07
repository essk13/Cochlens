package com.ssafy.api.service;

import com.ssafy.db.entity.User;
import com.ssafy.db.repository.CourseRepository;
import com.ssafy.db.repository.CourseRepositorySupport;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssafy.db.entity.Course;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service("courseService")
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepositorySupport courseRepositorySupport;

    @Autowired
    UserService userService;

    @Autowired
    CourseRepository courseRepository;


    @Override
    public Course getCourseByCourseId(Long courseId) {
        Course course = courseRepositorySupport.findCourseByCourseId(courseId).get();
        return course;
    }

    @Override
    public List<Map<String, Object>> getCourseList(){
        List<Map<String, Object>> result = new ArrayList<>();
        courseRepository.findAll().forEach(courseList -> {
            Map<String, Object> obj = new HashMap<>();
            obj.put("courseId", courseList.getCourseId());
            result.add(obj);
        });
        return result;
    }

    @Override
    public Course createCourse(Map<String, Object> body){
        String open_date = body.get("courseOpenDate").toString();
        Integer value = Integer.parseInt(open_date);
        int year = value / 10000;
        int month = ((value % 10000) / 100) - 1;
        int day = value % 100;
        Date date = new GregorianCalendar(year, month, day).getTime();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.getUserByEmail(principal.toString());

        Course course = new Course();

        course.setCourseName(body.get("courseName").toString());
        course.setCourseOpenDate(date);
        course.setCourseCycle(Integer.parseInt(body.get("courseCycle").toString()));
        course.setCourseThumbnail(body.get("courseThumbnail").toString());
        course.setCourseIntroVideo(body.get("courseIntroVideo").toString());
        course.setCourseDescription(body.get("courseDescription").toString());
        course.setCourseCategory(body.get("courseCategory").toString());
        course.setCourseLimitPeople(Integer.parseInt(body.get("courseLimitPeople").toString()));
        course.setCourseFee(Integer.parseInt(body.get("courseFee").toString()));
        course.setUser(user);

        return courseRepository.save(course);
    }

    @Override
    public Map<String, Object> getCourseInfo(Long courseId){

        var option = courseRepository.findById(courseId);
        if (!option.isPresent())
            return null;

        Course course = option.get();
        Map<String, Object> obj = new HashMap<>();
        obj.put("courseId", course.getCourseId());
        obj.put("courseCategory", course.getCourseCategory());
        obj.put("courseCloseDate", course.getCourseCloseDate());
        obj.put("courseCycle", course.getCourseCycle());
        obj.put("courseDescription", course.getCourseDescription());
        obj.put("courseFee", course.getCourseFee());
        obj.put("courseIntroVideo", course.getCourseIntroVideo());
        obj.put("courseLimitPeople", course.getCourseLimitPeople());
        obj.put("courseName", course.getCourseName());
        obj.put("courseOpenDate", course.getCourseOpenDate());
        obj.put("courseThumbnail", course.getCourseThumbnail());
        obj.put("user", course.getUser());
        return obj;
    }

    @Override
    public Course updateCourse(Long courseId, Map<String, Object> body){

        var option = courseRepository.findById(courseId);

        System.out.println("option : " + option);

        if (!option.isPresent())
            return null;

        String open_date = body.get("courseOpenDate").toString();
        Integer value = Integer.parseInt(open_date);
        int year = value / 10000;
        int month = ((value % 10000) / 100) - 1;
        int day = value % 100;
        Date date = new GregorianCalendar(year, month, day).getTime();


        String close_date = body.get("courseCloseDate").toString();
        Integer value_close = Integer.parseInt(close_date);
        int year_close = value_close / 10000;
        int month_close = ((value_close % 10000) / 100) - 1;
        int day_close = value_close % 100;
        Date date_close = new GregorianCalendar(year_close, month_close, day_close).getTime();

        Course course = option.get();
        System.out.println("course : " + course);
        course.setCourseName(body.get("courseName").toString());
        course.setCourseOpenDate(date);
        course.setCourseCloseDate(date_close);
        course.setCourseCycle(Integer.parseInt(body.get("courseCycle").toString()));
        course.setCourseThumbnail(body.get("courseThumbnail").toString());
        course.setCourseIntroVideo(body.get("courseIntroVideo").toString());
        course.setCourseDescription(body.get("courseDescription").toString());
        course.setCourseCategory(body.get("courseCategory").toString());
        course.setCourseLimitPeople(Integer.parseInt(body.get("courseLimitPeople").toString()));
        course.setCourseFee(Integer.parseInt(body.get("courseFee").toString()));

        return courseRepository.save(course);
    }
}

