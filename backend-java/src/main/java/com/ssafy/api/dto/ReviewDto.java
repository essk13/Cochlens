package com.ssafy.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.Review;
import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;



public class ReviewDto {
    /*
        Request
     */

    @Getter
    @Setter
    @ApiModel("ReviewInsertReq")
    public static class ReviewInsertReq {
        @ApiModelProperty(name="reviewContent", example="reviewContent")
        String reviewContent;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
        @ApiModelProperty(name="reviewDate", example="2022/02/10")
        Date reviewDate;
        @ApiModelProperty(name="reviewGrade", example="5")
        int reviewGrade;
    }

    /*
        Response
     */

    @Getter
    @Setter
    @ApiModel("ReviewListRes")
    public static class ReviewListRes {
        @ApiModelProperty(name="reviewId", example="1")
        Long reviewId;
        @ApiModelProperty(name="reviewContent", example="reviewContent")
        String reviewContent;
        @ApiModelProperty(name="reviewDate", example="2022/02/10")
        Date reviewDate;
        @ApiModelProperty(name="reviewGrade", example="5")
        int reviewGrade;
        @ApiModelProperty(name="user", example="user object")
        UserDto.UserReviewRes userReviewRes;

        public static ReviewListRes of(Review review) {
            ReviewListRes res = new ReviewListRes();

            res.setReviewId(review.getReviewId());
            res.setUserReviewRes(UserDto.UserReviewRes.of(review.getUser()));
            res.setReviewDate(review.getReviewDate());
            res.setReviewContent(review.getReviewContent());
            res.setReviewGrade(review.getReviewGrade());

            return res;
        }
    }

    @Getter
    @Setter
    @ApiModel("ReviewRes")
    public static class ReviewRes {

        @ApiModelProperty(name="reviewId")
        Long reviewId;
        @ApiModelProperty(name="reviewDate")
        Date reviewDate;
        @ApiModelProperty(name="reviewContent")
        String reviewContent;
        @ApiModelProperty(name="reviewGrade")
        int reviewGrade;
        @ApiModelProperty(name="user object")
        User user;
        @ApiModelProperty(name="course object")
        Course course;

        public static ReviewRes of(Review review) {
            ReviewRes res = new ReviewRes();

            res.setReviewId(review.getReviewId());
            res.setReviewDate(review.getReviewDate());
            res.setReviewContent(review.getReviewContent());
            res.setReviewGrade(review.getReviewGrade());
            res.setUser(review.getUser());
            res.setCourse(review.getCourse());

            return res;
        }
    }
}
