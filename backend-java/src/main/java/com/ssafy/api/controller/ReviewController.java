//package com.ssafy.api.controller;
//
//import com.ssafy.api.service.ReviewService;
//import com.ssafy.api.service.UserService;
//import com.ssafy.api.service.CourseService;
//
//import com.ssafy.db.entity.Review;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import lombok.AllArgsConstructor;
//
//import java.util.*;
//
//@RestController
//@AllArgsConstructor
//@RequestMapping("/review")
//@CrossOrigin("*")
//public class ReviewController {
//
//    @Autowired
//    ReviewRepository reviewRepository;
//
//    @Autowired
//    UserService userService;
//
//    @Autowired
//    ReviewService reviewService;
//
//    @Autowired
//    CourseService courseService;
//
//    @GetMapping
//    public List<Map<String, Object>> list() {
//        List<Map<String, Object>> result = reviewService.getReviewList();
//        return result;
//    }
//
//    @GetMapping("/{reviewId}")
//    public Map<String, Object> review(@PathVariable Long reviewId){
//
//
//        Map<String, Object> review = reviewService.getReviewInfo(reviewId);
//        return review;
//    }
//
//    @DeleteMapping("/{reviewId}")
//    public void delete(@PathVariable Long reviewId){
//        reviewRepository.deleteById(reviewId);
//    }
//
//    @PostMapping
//    public Long post(@RequestBody Map<String, Object> body) {
//
//        Review review = reviewService.createReview(body);
//
//        return Long.valueOf(1);
//    }
//
//
//    @PutMapping("/{reviewId}")
//    public Map<String, Object> update(@PathVariable Long reviewId, @RequestBody Map<String, Object> body) {
//
//        Review review = reviewService.updateReview(reviewId, body);
//
//        if (review == null){
//            return Map.of("Message", "review수정실패");
//        }
//        return Map.of("Message", "review수정");
//    }
//}
