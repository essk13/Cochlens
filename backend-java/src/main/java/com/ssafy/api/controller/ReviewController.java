package com.ssafy.api.controller;

import com.ssafy.api.dto.CourseDto;
import com.ssafy.api.dto.LectureDto;
import com.ssafy.api.dto.ReviewDto;
import com.ssafy.api.service.ReviewService;
import com.ssafy.api.service.UserService;
import com.ssafy.api.service.CourseService;

import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.Review;
import com.ssafy.db.repository.ReviewRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

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
    ReviewService reviewService;

    @Autowired
    CourseService courseService;


    @PutMapping("/{reviewId}")
    @ApiOperation(value = "리뷰 수정", notes = "리뷰를 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<ReviewDto.ReviewRes> updateReview(@ApiParam(value="리뷰 id 정보", required = true) @PathVariable Long reviewId,
                                                            @ApiParam(value="리뷰 수정 정보", required = true) @RequestBody ReviewDto.ReviewInsertReq reviewInsertInfo) {

        reviewService.updateReview(reviewId, reviewInsertInfo);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> delete(@ApiParam(value="리뷰 id 정보", required = true) @PathVariable Long reviewId) {

        reviewService.delete(reviewId);
        return ResponseEntity.noContent().build();
    }


}
