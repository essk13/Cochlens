package com.ssafy.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.db.entity.Review;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;



public class ReviewDto {
    /**
     * Request
     */

    @Getter
    @Setter
    @ApiModel("ReviewInsertReq")
    public static class ReviewInsertReq {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
        @ApiModelProperty(name="reviewDate", example="2022/02/10")
        Date reviewDate;
        @ApiModelProperty(name="review rate", example="5")
        int reviewRate;
        @ApiModelProperty(name="review content", example="reviewContent")
        String reviewContent;
    }

    /**
     * Response
     */

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("ReviewListRes")
    public static class ReviewListRes {
        @ApiModelProperty(name="reviewId", example="1")
        Long reviewId;
        @ApiModelProperty(name="reviewDate", example="2022/02/10")
        Date reviewDate;
        @ApiModelProperty(name="reviewRate", example="5")
        int reviewRate;
        @ApiModelProperty(name="reviewContent", example="reviewContent")
        String reviewContent;
        @ApiModelProperty(name="author", example="user object")
        UserDto.UserReviewRes author;

        public static ReviewListRes of(Review review) {
            ReviewListRes res = new ReviewListRes();
            res.setReviewId(review.getReviewId());
            res.setReviewDate(review.getReviewDate());
            res.setReviewRate(review.getReviewRate());
            res.setReviewContent(review.getReviewContent());
            res.setAuthor(UserDto.UserReviewRes.of(review.getUser()));

            return res;
        }
    }
}
