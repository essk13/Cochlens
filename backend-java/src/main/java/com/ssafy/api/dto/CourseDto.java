package com.ssafy.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.Lecture;
import com.ssafy.db.entity.Review;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

public class CourseDto {
    /**
     * Request
     */
    @Getter
    @Setter
    @ApiModel("CourseInsertReq")
    public static class CourseInsertReq {
        @ApiModelProperty(name="course name", example="test")
        String courseName;
        @ApiModelProperty(name="courseDescription", example="description")
        String courseDescription;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
        @ApiModelProperty(name="courseOpenDate", example="2022/02/10")
        Date courseOpenDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
        @ApiModelProperty(name="courseCloseDate", example="2023/02/10")
        Date courseCloseDate;
        @ApiModelProperty(name="courseCycle", example="1")
        int courseCycle;
        @ApiModelProperty(name="courseThumbnail", example="thumbnail address")
        String courseThumbnail;
        @ApiModelProperty(name="courseCategory", example="category")
        String courseCategory;
        @ApiModelProperty(name="courseLimitPeople", example="30")
        int courseLimitPeople;
        @ApiModelProperty(name="courseFee", example="100000")
        int courseFee;
        @ApiModelProperty(name="courseIntroVideo", example="intro video address")
        String courseIntroVideo;
    }

    /**
     * Response
     */

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("CourseListRes")
    public static class CourseListRes {
        @ApiModelProperty(name="courseId", example="1")
        Long courseId;
        @ApiModelProperty(name="course name", example="test")
        String courseName;
        @ApiModelProperty(name="courseDescription", example="description")
        String courseDescription;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
        @ApiModelProperty(name="courseOpenDate", example="2022/02/10")
        Date courseOpenDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
        @ApiModelProperty(name="courseCloseDate", example="2023/02/10")
        Date courseCloseDate;
        @ApiModelProperty(name="courseCycle", example="1")
        int courseCycle;
        @ApiModelProperty(name="courseThumbnail", example="thumbnail address")
        String courseThumbnail;
        @ApiModelProperty(name="courseCategory", example="category")
        String courseCategory;
        @ApiModelProperty(name="courseLimitPeople", example="30")
        int courseLimitPeople;
        @ApiModelProperty(name="courseFee", example="100000")
        int courseFee;
        @ApiModelProperty(name="instructorName", example="ssafy")
        String instructorName;
        @ApiModelProperty(name="courseReviewCount", example="10")
        int courseReviewCount;
        @ApiModelProperty(name="courseReviewRateAverage", example="4.88")
        Double courseReviewRateAverage;
        @ApiModelProperty(name="wishCount", example="10")
        int wishCount;

        public static CourseListRes of(Course course) {
            CourseListRes res = new CourseListRes();

            res.setCourseId(course.getCourseId());
            res.setCourseName(course.getCourseName());
            res.setCourseDescription(course.getCourseDescription());
            res.setCourseThumbnail(course.getCourseThumbnail());
            res.setCourseCategory(course.getCourseCategory());
            res.setInstructorName(course.getUser().getUserName());
            res.setCourseCycle(course.getCourseCycle());
            res.setCourseFee(course.getCourseFee());
            res.setCourseOpenDate(course.getCourseOpenDate());
            res.setCourseCloseDate(course.getCourseCloseDate());
            res.setCourseCategory(course.getCourseCategory());
            res.setCourseReviewCount(course.getCourseReviewCount());
            res.setCourseReviewRateAverage(course.getCourseReviewGrade());
            res.setWishCount(course.getCourseWishCount());

            return res;
        }
    }

    @Getter
    @Setter
    @ApiModel("CourseRes")
    public static class CourseRes {
        @ApiModelProperty(name="courseId", example = "1")
        Long courseId;
        @ApiModelProperty(name="courseName", example = "test")
        String courseName;
        @ApiModelProperty(name="courseDescription", example = "description")
        String courseDescription;
        @ApiModelProperty(name="courseOpenDate", example="2022/02/10")
        Date courseOpenDate;
        @ApiModelProperty(name="courseCloseDate", example="2022/02/10")
        Date courseCloseDate;
        @ApiModelProperty(name="courseCycle", example="7")
        int courseCycle;
        @ApiModelProperty(name="courseThumbnail", example="thumbnail address")
        String courseThumbnail;
        @ApiModelProperty(name="courseCategory", example="category")
        String courseCategory;
        @ApiModelProperty(name="courseLimitPeople", example="100")
        int courseLimitPeople;
        @ApiModelProperty(name="courseFee", example="30000")
        int courseFee;
        @ApiModelProperty(name="courseIntroVideo", example="intro video address")
        String courseIntroVideo;

        @ApiModelProperty(name="courseWishCount", example="10")
        int courseWishCount;
        @ApiModelProperty(name="courseReviewCount", example="10")
        int courseReviewCount;
        @ApiModelProperty(name="courseReviewRateAverage", example="4.88")
        Double courseReviewRateAverage;

        @ApiModelProperty(name="joinCount", example="10")
        int joinCount;
        @ApiModelProperty(name="lectureList")
        List<LectureDto.LectureListRes> lectureList;
        @ApiModelProperty(name="reviewList")
        List<ReviewDto.ReviewListRes> reviewList;
        @ApiModelProperty(name="instructorName", example="ssafy")
        String instructorName;

        @ApiModelProperty(name="inJoin", example="false")
        Boolean isJoin;
        @ApiModelProperty(name="isWish", example="false")
        Boolean isWish;

        public static CourseRes of(Course course, List<LectureDto.LectureListRes> lectureList, List<ReviewDto.ReviewListRes> reviewList, boolean isJoin, boolean isWish) {
            CourseRes res = new CourseRes();

            res.setCourseId(course.getCourseId());
            res.setCourseName(course.getCourseName());
            res.setCourseDescription(course.getCourseDescription());
            res.setCourseOpenDate(course.getCourseOpenDate());
            res.setCourseCloseDate(course.getCourseCloseDate());
            res.setCourseCycle(course.getCourseCycle());
            res.setCourseThumbnail(course.getCourseThumbnail());
            res.setCourseCategory(course.getCourseCategory());
            res.setCourseLimitPeople(course.getCourseLimitPeople());
            res.setCourseFee(course.getCourseFee());
            res.setCourseIntroVideo(course.getCourseIntroVideo());

            res.setCourseWishCount(course.getCourseWishCount());
            res.setCourseReviewCount(course.getCourseReviewCount());
            res.setCourseReviewRateAverage(course.getCourseReviewGrade());

            res.setLectureList(lectureList);
            res.setReviewList(reviewList);

            res.setInstructorName(course.getUser().getUserName());

            res.setIsJoin(isJoin);
            res.setIsWish(isWish);

            return res;
        }
    }

    /**
     *  Value Object
     */

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("CourseInstructorVO")
    public static class CourseInstructorVO {
        @ApiModelProperty(name="course count", example="ssafy")
        long courseCount;
        @ApiModelProperty(name="course review count", example="ssafy")
        int courseReviewCount;
        @ApiModelProperty(name="course review grade", example="ssafy")
        double courseReviewGrade;
    }
}
