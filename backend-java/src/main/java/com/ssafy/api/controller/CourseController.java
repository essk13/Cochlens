package com.ssafy.api.controller;

import com.ssafy.api.dto.CourseDto;
import com.ssafy.api.dto.LectureDto;
import com.ssafy.api.service.CourseService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Course;
import com.ssafy.db.repository.CourseRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    CourseService courseService;

    @Autowired
    CourseRepository courseRepository;

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

        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        courseService.createCourse(userDetails.getUser(), courseInsertInfo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @ApiOperation(value = "강좌들 조회", notes = "생성된 강좌 list를 조회한다.")
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
    public ResponseEntity<CourseDto.CourseRes> getCourse(@ApiParam(value="강좌 id 정보", required = true) @PathVariable Long courseId){
        Course course = courseService.getCourseByCourseId(courseId);
        List<LectureDto.LectureRes> list = null;
        return ResponseEntity.ok().body(CourseDto.CourseRes.of(course, list));
    }

    @PutMapping("/{courseId}")
    @ApiOperation(value = "강좌 수정", notes = "강좌를 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<CourseDto.CourseRes> updateCourse(@ApiParam(value="강좌 id 정보", required = true) @PathVariable Long courseId,
                                                            @ApiParam(value="강좌 id 정보", required = true) @RequestBody CourseDto.CourseInsertReq courseInsertInfo) {

        courseService.updateCourse(courseId, courseInsertInfo);
        return ResponseEntity.noContent().build();
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
        Long userId = userDetails.getUser().getUserId();
        System.out.println("joinCount0");
        courseService.registerCourse(userId, courseId);
        System.out.println("joinCount1111111111111111111111");
        return ResponseEntity.noContent().build();
    }

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

//    @DeleteMapping("/{courseId}")
//    public void delete(@PathVariable Long courseId){
//
//        courseRepository.deleteById(courseId);
//    }

//    @GetMapping("/{courseId}/review")
//    @ApiOperation(value = "강좌 리뷰 조회", notes = "강좌 리뷰 리스트를 조회한다.")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "성공"),
//            @ApiResponse(code = 401, message = "인증 실패"),
//            @ApiResponse(code = 404, message = "사용자 없음"),
//            @ApiResponse(code = 500, message = "서버 오류")
//    })
//    public ResponseEntity<?> getCourseReivew(@ApiParam(value="강좌 id 정보", required = true) @PathVariable Long courseId) {
//
//        return ResponseEntity.ok().build();
//    }




//    강좌를 통한 강의 검색
//    @GetMapping("/{courseId}/lecture")
//    public List<Map<String, Object>> getLectureByCourseId(@PathVariable Long courseId) {
//
//        List<Map<String, Object>> result = lectureService.getLectureByCourseId(courseId);
//        return result;
//
//    }
}
