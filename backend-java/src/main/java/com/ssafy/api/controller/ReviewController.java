package com.ssafy.api.controller;

import com.ssafy.api.dto.ReviewDto;
import com.ssafy.api.service.ReviewService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;


@RestController
@AllArgsConstructor
@RequestMapping("/review")
@CrossOrigin("*")
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    /*
        create
    */

    /*
        read
    */

    /*
        update
    */

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
        return ResponseEntity.ok().build();
    }

    /*
        delete
    */

    public ResponseEntity<?> delete(@ApiParam(value="리뷰 id 정보", required = true) @PathVariable Long reviewId) {

        reviewService.delete(reviewId);
        return ResponseEntity.noContent().build();
    }

}
