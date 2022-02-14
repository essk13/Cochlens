package com.ssafy.api.controller;

import com.ssafy.api.dto.LectureDto;
import com.ssafy.api.service.CourseService;
import com.ssafy.api.service.LectureService;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.Lecture;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@Api(value = "강의 API", tags = {"Lecture"})
@RestController
@RequestMapping("/api/v1/lecture")
public class LectureController {

    @Autowired
    LectureService lectureService;

    @Autowired
    CourseService courseService;

    /*
        create
    */

    @PostMapping("/{courseId}")
    @ApiOperation(value = "강의 생성", notes = "강의를 생성한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> createLecture(@ApiParam(value="강좌 id 정보", required = true) @PathVariable Long courseId,
            @RequestBody @ApiParam(value="강의 생성 정보", required = true) LectureDto.LectureInsertReq lectureInsertInfo) {
        Course course = courseService.getCourse(courseId);
        lectureService.createLecture(course, lectureInsertInfo);
        return ResponseEntity.ok().build();
    }

    /*
        read
    */

    @GetMapping("/{lectureId}")
    @ApiOperation(value = "강의 조회", notes = "생성된 강의를 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<LectureDto.LectureRes> getLecture(@ApiParam(value="강의 id 정보", required = true) @PathVariable Long lectureId) {
        Lecture lecture = lectureService.getLectureInfo(lectureId);
        return ResponseEntity.ok().body(LectureDto.LectureRes.of(lecture));
    }

    /*
        update
    */

    @PutMapping("/{lectureId}")
    @ApiOperation(value = "강의 수정", notes = "강의를 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<LectureDto.LectureRes> updateLecture(@ApiParam(value="강의 id 정보", required = true) @PathVariable Long lectureId,
                                                               @ApiParam(value="강의 수정 정보", required = true) @RequestBody LectureDto.LectureInsertReq lectureInsertInfo) {
        Lecture lecture = lectureService.updateLecture(lectureId, lectureInsertInfo);

        return ResponseEntity.ok().body(LectureDto.LectureRes.of(lecture));
    }

    @PutMapping("/{lectureId}/open")
    @ApiOperation(value = "강의 라이브 시작", notes = "강의라이브를 시작한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> openLecture() {
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{lectureId}/close")
    @ApiOperation(value = "강의 라이브 종료", notes = "강의라이브를 종료한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> closeLecture() {
        return ResponseEntity.ok().build();
    }

    /*
        delete
    */

//    @DeleteMapping("/{lectureId}")
//    public void delete(@PathVariable Long lectureId){
//        lectureRepository.deleteById(lectureId);
//    }

}
