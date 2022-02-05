package com.ssafy.api.controller;

import com.ssafy.api.service.UserService;
import com.ssafy.api.service.CourseService;

import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.Review;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.ReviewRepository;
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
@RequestMapping("/review")
@CrossOrigin("*")
public class ReviewController {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    @GetMapping
    public List<Map<String, Object>> list() {
        List<Map<String, Object>> result = new ArrayList<>();
        reviewRepository.findAll().forEach(reviewList -> {
            Map<String, Object> obj = new HashMap<>();
            obj.put("reviewId", reviewList.getReviewId());
            result.add(obj);
        });
        return result;
    }

    @GetMapping("/{reviewId}")
    public Map<String, Object> review(@PathVariable Long reviewId){
        var option = reviewRepository.findById(reviewId);
        if (!option.isPresent())
            return null;
        Review review = option.get();
        Map<String, Object> obj = new HashMap<>();
        obj.put("reviewId", review.getReviewId());
        obj.put("reviewDate", review.getReviewDate());
        obj.put("reviewContent", review.getReviewContent());
        obj.put("reviewGrade", review.getReviewGrade());
        obj.put("userId", review.getUser());
        obj.put("courseId", review.getCourse());
        return obj;
    }

    @DeleteMapping("/{reviewId}")
    public void delete(@PathVariable Long reviewId){
        reviewRepository.deleteById(reviewId);
    }

    @PostMapping
    public Long post(@RequestBody Map<String, Object> body) {

        Date date = new Date();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.out.println("principal : " + principal);

        User user = userService.getUserByEmail(principal.toString());

        Long courseId = Long.parseLong(body.get("courseId").toString());

        Course course = courseService.getCourseByCourseId(courseId);

        return reviewRepository.save(Review.builder()
                .reviewDate(date)
                .reviewContent(body.get("reviewContent").toString())
                .reviewGrade(Integer.parseInt(body.get("reviewGrade").toString()))
                .user(user)
                .course(course)
                .build()).getReviewId();
    }

//        this.course = course;

    @PutMapping("/{reviewId}")
    public Map<String, Object> update(@PathVariable Long reviewId, @RequestBody Map<String, Object> body) {
        var option = reviewRepository.findById(reviewId);

        if (!option.isPresent())
            return null;
        Date date = new Date();

        Review review = option.get();
        review.setReviewDate(date);
        review.setReviewContent(body.get("reviewContent").toString());
        review.setReviewGrade(Integer.parseInt(body.get("reviewGrade").toString()));
        reviewRepository.save(review);
        return Map.of("Message", "review수정");


    }
}
