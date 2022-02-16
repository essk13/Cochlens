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
        @ApiModelProperty(name="courseCategory", example="category")
        String courseCategory;
        @ApiModelProperty(name="courseThumbnail", example="thumbnail address")
        String courseThumbnail;
        @ApiModelProperty(name="courseIntroVideo", example="intro video address")
        String courseIntroVideo;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
        @ApiModelProperty(name="courseOpenDate", example="2022/02/10")
        Date courseOpenDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
        @ApiModelProperty(name="courseCloseDate", example="2023/02/10")
        Date courseCloseDate;
        @ApiModelProperty(name="courseFee", example="100000")
        int courseFee;
        @ApiModelProperty(name="courseCycle", example="1")
        int courseCycle;
        @ApiModelProperty(name="courseLimitPeople", example="30")
        int courseLimitPeople;
    }

    /**
     * Response
     */

    /**
     * Course List read
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
        @ApiModelProperty(name="courseCategory", example="category")
        String courseCategory;
        @ApiModelProperty(name="courseThumbnail", example="thumbnail address")
        String courseThumbnail;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
        @ApiModelProperty(name="courseOpenDate", example="2022/02/10")
        Date courseOpenDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
        @ApiModelProperty(name="courseCloseDate", example="2023/02/10")
        Date courseCloseDate;
        @ApiModelProperty(name="courseFee", example="100000")
        int courseFee;

        @ApiModelProperty(name="courseWishCount", example="10")
        int courseWishCount;
        @ApiModelProperty(name="courseReviewCount", example="10")
        int courseReviewCount;
        @ApiModelProperty(name="courseReviewRateAverage", example="4.88")
        Double courseReviewRateAverage;

        @ApiModelProperty(name="instructorName", example="ssafy")
        String instructorName;

        public static CourseListRes of(Course course) {
            CourseListRes res = new CourseListRes();

            res.setCourseId(course.getCourseId());
            res.setCourseName(course.getCourseName());
            res.setCourseDescription(course.getCourseDescription());
            res.setCourseCategory(course.getCourseCategory());
            res.setCourseThumbnail(course.getCourseThumbnail());

            res.setCourseOpenDate(course.getCourseOpenDate());
            res.setCourseCloseDate(course.getCourseCloseDate());

            res.setCourseFee(course.getCourseFee());
            res.setCourseWishCount(course.getCourseWishCount());
            res.setCourseReviewCount(course.getCourseReviewCount());
            res.setCourseReviewRateAverage(course.getCourseReviewRateAverage());

            res.setInstructorName(course.getUser().getUserName());

            return res;
        }
    }

    @Getter
    @Setter
    @ApiModel("CourseAllRes")
    public static class CourseAllRes {
        @ApiModelProperty(name="courseId")
        Long courseId;
        @ApiModelProperty(name="courseName")
        String courseName;
        @ApiModelProperty(name="courseDescription")
        String courseDescription;
        @ApiModelProperty(name="courseCategory")
        String courseCategory;
        @ApiModelProperty(name="courseThumbnail")
        String courseThumbnail;
        @ApiModelProperty(name="instructorName")
        String instructorName;

        public static CourseAllRes of(Course course) {
            CourseAllRes res = new CourseAllRes();
            res.setCourseId(course.getCourseId());
            res.setCourseName(course.getCourseName());
            res.setCourseDescription(course.getCourseDescription());
            res.setCourseCategory(course.getCourseCategory());
            res.setCourseThumbnail(course.getCourseThumbnail());
            res.setInstructorName(course.getUser().getUserName());

            return res;
        }
    }

    /**
     * Course detail read
     */
    @Getter
    @Setter
    @ApiModel("CourseDetailRes")
    public static class CourseDetailRes {
        @ApiModelProperty(name="courseId", example = "1")
        Long courseId;
        @ApiModelProperty(name="courseName", example = "test")
        String courseName;
        @ApiModelProperty(name="courseDescription", example = "description")
        String courseDescription;
        @ApiModelProperty(name="courseCategory", example="category")
        String courseCategory;
        @ApiModelProperty(name="courseThumbnail", example="thumbnail address")
        String courseThumbnail;
        @ApiModelProperty(name="courseIntroVideo", example="intro video address")
        String courseIntroVideo;
        @ApiModelProperty(name="courseOpenDate", example="2022/02/10")
        Date courseOpenDate;
        @ApiModelProperty(name="courseCloseDate", example="2022/02/10")
        Date courseCloseDate;
        @ApiModelProperty(name="courseFee", example="30000")
        int courseFee;
        @ApiModelProperty(name="courseCycle", example="7")
        int courseCycle;
        @ApiModelProperty(name="courseLimitPeople", example="100")
        int courseLimitPeople;

        @ApiModelProperty(name="coursejoinCount")
        int courseJoinCount;
        @ApiModelProperty(name="courseWishCount")
        int courseWishCount;
        @ApiModelProperty(name="courseReviewCount")
        int courseReviewCount;
        @ApiModelProperty(name="courseReviewRateAverage")
        Double courseReviewRateAverage;

        @ApiModelProperty(name="instructorName")
        String instructorName;
        @ApiModelProperty(name="instructorProfileImage")
        String instructorProfileImage;

        @ApiModelProperty(name="inJoin", example="false")
        Boolean isJoin;
        @ApiModelProperty(name="isWish", example="false")
        Boolean isWish;

        @ApiModelProperty(name="lectureList")
        List<LectureDto.LectureListRes> lectureList;
        @ApiModelProperty(name="reviewList")
        List<ReviewDto.ReviewListRes> reviewList;

        public static CourseDetailRes of(
                Course course, int courseJoinCount, boolean isJoin, boolean isWish,
                List<LectureDto.LectureListRes> lectureList, List<ReviewDto.ReviewListRes> reviewList) {
            CourseDetailRes res = new CourseDetailRes();

            res.setCourseId(course.getCourseId());
            res.setCourseName(course.getCourseName());
            res.setCourseDescription(course.getCourseDescription());
            res.setCourseCategory(course.getCourseCategory());
            res.setCourseThumbnail(course.getCourseThumbnail());
            res.setCourseIntroVideo(course.getCourseIntroVideo());
            res.setCourseOpenDate(course.getCourseOpenDate());
            res.setCourseCloseDate(course.getCourseCloseDate());
            res.setCourseFee(course.getCourseFee());
            res.setCourseCycle(course.getCourseCycle());
            res.setCourseLimitPeople(course.getCourseLimitPeople());

            res.setCourseJoinCount(courseJoinCount);
            res.setCourseWishCount(course.getCourseWishCount());
            res.setCourseReviewCount(course.getCourseReviewCount());
            res.setCourseReviewRateAverage(course.getCourseReviewRateAverage());

            res.setInstructorName(course.getUser().getUserName());
            res.setInstructorProfileImage(course.getUser().getProfileImage());

            res.setIsJoin(isJoin);
            res.setIsWish(isWish);

            res.setLectureList(lectureList);
            res.setReviewList(reviewList);

            return res;
        }
    }

    /**
     *  Value Object
     */

    /**
     * Course Instructor rate
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
