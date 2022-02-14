package com.ssafy.api.controller;

import com.ssafy.api.dto.CourseDto;
import com.ssafy.api.dto.LectureDto;
import com.ssafy.api.dto.ReviewDto;
import com.ssafy.api.service.CourseService;
import com.ssafy.api.service.LectureService;
import com.ssafy.api.service.ReviewService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.User;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

@Api(value = "강좌 API", tags = {"Course"})
@RestController
@RequestMapping("/api/v1/course")
public class CourseController {
    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    @Autowired
    LectureService lectureService;

    @Autowired
    ReviewService reviewService;

    /*
        create
    */

    @PostMapping
    @ApiOperation(value = "강좌 생성", notes = "강좌를 생성한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> createCourse(@ApiIgnore Authentication authentication,
                                                                    @RequestBody @ApiParam(value="강좌 생성 정보", required = true) CourseDto.CourseInsertReq courseInsertInfo) {
        System.out.println(authentication);
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        String email = userDetails.getUsername();
        User user = userService.getUserByEmail(email);
        courseService.createCourse(user, courseInsertInfo);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{courseId}/register")
    @ApiOperation(value = "강좌 수강 신청", notes = "강좌 수강 신청한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> registerCourse(@ApiIgnore Authentication authentication,
                                                                     @ApiParam(value="강좌 id 정보", required = true) @PathVariable Long courseId) {

        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        Course course = courseService.getCourse(courseId);
        courseService.registerCourse(userDetails.getUser(), course);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{courseId}/wish")
    @ApiOperation(value = "강좌 찜 목록 추가", notes = "강좌 찜 목록 추가한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> registerWishlist(@ApiIgnore Authentication authentication,
                                                                       @ApiParam(value="강좌 id 정보", required = true) @PathVariable Long courseId) {

        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        Course course = courseService.getCourse(courseId);
        courseService.registerWishlist(userDetails.getUser(), course);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{courseId}/review")
    @ApiOperation(value = "강좌 리뷰 작성", notes = "강좌 리뷰를 작성한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> createReview(@ApiIgnore Authentication authentication,
                                                                   @ApiParam(value="강좌 id 정보", required = true) @PathVariable Long courseId,
                                                                   @RequestBody @ApiParam(value="리뷰 생성 정보", required = true) ReviewDto.ReviewInsertReq reviewInsertInfo) {
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        Course course = courseService.getCourse(courseId);
        reviewService.createReview(userDetails.getUser(), course, reviewInsertInfo);
        reviewService.updateReviewGrade(course);
        return ResponseEntity.ok().build();
    }

    /*
        read
    */

    @GetMapping
    @ApiOperation(value = "강좌 목록 조회", notes = "생성된 강좌 list를 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<List<CourseDto.CourseListRes>> getCourseList() {
        List<CourseDto.CourseListRes> list = courseService.getCourseList();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{courseId}")
    @ApiOperation(value = "강좌 상세 조회", notes = "생성된 강좌를 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<CourseDto.CourseRes> getCourse(@ApiIgnore Authentication authentication,
            @ApiParam(value="강좌 id 정보", required = true) @PathVariable Long courseId){
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        String email = userDetails.getUsername();

        User user = userService.getUserByEmail(email);
        Course course = courseService.getCourse(courseId);
        List<LectureDto.LectureListRes> lectureList = lectureService.getLectureList(course);
        List<ReviewDto.ReviewListRes> reviewList = reviewService.getReviewListByCourse(course);
        boolean isWish = false;
        boolean isJoin = false;

        return ResponseEntity.ok().body(CourseDto.CourseRes.of(course, lectureList, reviewList, isJoin, isWish));
    }

    @GetMapping("/recent")
    @ApiOperation(value = "수강 중인 강좌 조회", notes = "수강 중인 강좌 list를 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<List<CourseDto.CourseListRes>> getRecentCourseList(@ApiIgnore Authentication authentication) {

        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        Long userId = userDetails.getUser().getUserId();

        List<CourseDto.CourseListRes> list = courseService.getRecentCourseList(userId);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/best")
    @ApiOperation(value = "베스트 강좌 조회", notes = "베스트 강좌 list를 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<List<CourseDto.CourseListRes>> getBestCourseList() {
//        찜이 가장 많은 강좌를 최대 5개까지 가져옴
        List<CourseDto.CourseListRes> list = courseService.getBestCourseList();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/search")
    @ApiOperation(value = "강좌 검색 조회", notes = "강좌 검색 list를 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<List<CourseDto.CourseListRes>> getSearchCourseList(@RequestParam String courseName) {
        List<CourseDto.CourseListRes> list = courseService.getSearchCourseList(courseName);
        return ResponseEntity.ok().body(list);
    }

    /*
        update
    */

    @PutMapping("/{courseId}")
    @ApiOperation(value = "강좌 수정", notes = "강좌를 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<CourseDto.CourseRes> updateCourse(@ApiParam(value="강좌 id 정보", required = true) @PathVariable Long courseId,
                                                            @ApiParam(value="강좌 수정 정보", required = true) @RequestBody CourseDto.CourseInsertReq courseInsertInfo) {

        courseService.updateCourse(courseId, courseInsertInfo);
        return ResponseEntity.ok().build();
    }


    /*
        delete
    */

    @DeleteMapping("/{courseId}/deregister")
    @ApiOperation(value = "강좌 수강 신청 취소", notes = "강좌 수강 신청을 취소한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> deregisterCourse(@ApiIgnore Authentication authentication,
                                              @ApiParam(value="강좌 id 정보", required = true) @PathVariable Long courseId) {
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        Long userId = userDetails.getUser().getUserId();
        courseService.deregisterCourse(userId, courseId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{courseId}/wish")
    @ApiOperation(value = "강좌 찜 취소", notes = "강좌 찜 취소한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> deregisterWishlist(@ApiIgnore Authentication authentication,
                                                                       @ApiParam(value="강좌 id 정보", required = true) @PathVariable Long courseId) {
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        Long userId = userDetails.getUser().getUserId();
        courseService.deregisterWishlist(userId, courseId);
        return ResponseEntity.noContent().build();
    }
}
