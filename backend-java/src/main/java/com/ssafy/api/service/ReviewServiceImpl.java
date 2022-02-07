package com.ssafy.api.service;

import com.ssafy.db.entity.Review;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.CourseRepository;
import com.ssafy.db.repository.CourseRepositorySupport;
import com.ssafy.db.repository.LectureRepository;
import com.ssafy.db.repository.ReviewRepository;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssafy.db.entity.Course;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service("reviewService")
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    @Autowired
    ReviewRepository reviewRepository;

    @Override
    public List<Map<String, Object>> getReviewList(){
        List<Map<String, Object>> result = new ArrayList<>();
        reviewRepository.findAll().forEach(reviewList -> {
            Map<String, Object> obj = new HashMap<>();
            obj.put("reviewId", reviewList.getReviewId());
            result.add(obj);
        });
        return result;
    }

    @Override
    public Review createReview(Map<String, Object> body){

        Date date = new Date();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.getUserByEmail(principal.toString());

        Long courseId = Long.parseLong(body.get("courseId").toString());

        Course course = courseService.getCourseByCourseId(courseId);

        Review review = new Review();

        review.setReviewDate(date);
        review.setReviewContent(body.get("reviewContent").toString());
        review.setReviewGrade(Integer.parseInt(body.get("reviewGrade").toString()));
        review.setUser(user);
        review.setCourse(course);

        return reviewRepository.save(review);
    }

    @Override
    public Review updateReview(Long reviewId, Map<String, Object> body) {

        var option = reviewRepository.findById(reviewId);

        if (!option.isPresent())
            return null;
        Date date = new Date();

        Review review = option.get();
        review.setReviewDate(date);
        review.setReviewContent(body.get("reviewContent").toString());
        review.setReviewGrade(Integer.parseInt(body.get("reviewGrade").toString()));

        return reviewRepository.save(review);
    }

    @Override
    public Map<String, Object> getReviewInfo(Long reviewId){
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
}
