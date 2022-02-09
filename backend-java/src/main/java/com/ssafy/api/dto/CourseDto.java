package com.ssafy.api.dto;

import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.Lecture;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

public class CourseDto {
    @Getter
    @Setter
    @ApiModel("CourseInsertReq")
    public static class CourseInsertReq {
        @ApiModelProperty(name="course name", example="tets")
        String courseName;
        @ApiModelProperty(name="courseDescription", example="description")
        String courseDescription;
        @ApiModelProperty(name="courseOpenDate", example="yyyy-mm-dd")
        Date courseOpenDate;
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

    @Getter
    @Setter
    @ApiModel("CourseListRes")
    public static class CourseListRes {
        @ApiModelProperty(name="courseId")
        Long courseId;
        @ApiModelProperty(name="courseName")
        String courseName;
        @ApiModelProperty(name="courseDescription")
        String courseDescription;
        @ApiModelProperty(name="courseThumbnail")
        String courseThumbnail;
        @ApiModelProperty(name="courseCategory")
        String courseCategory;
        @ApiModelProperty(name="instructorName")
        String instructorName;

        public static CourseListRes of(Course course) {
            CourseListRes res = new CourseListRes();

            res.setCourseId(course.getCourseId());
            res.setCourseName(course.getCourseName());
            res.setCourseDescription(course.getCourseDescription());
            res.setCourseThumbnail(course.getCourseThumbnail());
            res.setCourseCategory(course.getCourseCategory());
            res.setInstructorName(course.getUser().getUserName());

            return res;
        }
    }

    @Getter
    @Setter
    @ApiModel("CourseRes")
    public static class CourseRes {
        @ApiModelProperty(name="courseId")
        Long courseId;
        @ApiModelProperty(name="courseName")
        String courseName;
        @ApiModelProperty(name="courseDescription")
        String courseDescription;
        @ApiModelProperty(name="courseOpenDate")
        Date courseOpenDate;
        @ApiModelProperty(name="courseCloseDate")
        Date courseCloseDate;
        @ApiModelProperty(name="courseCycle")
        int courseCycle;
        @ApiModelProperty(name="courseThumbnail")
        String courseThumbnail;
        @ApiModelProperty(name="courseCategory")
        String courseCategory;
        @ApiModelProperty(name="courseLimitPeople")
        int courseLimitPeople;
        @ApiModelProperty(name="courseFee")
        int courseFee;
        @ApiModelProperty(name="courseIntroVideo")
        String courseIntroVideo;

        @ApiModelProperty(name="instructorName")
        String instructorName;

        @ApiModelProperty(name="lectureList")
        List<LectureDto.LectureRes> lectureList;
//        @ApiModelProperty(name="reviewList")
//        List<Review>

        public static CourseRes of(Course course, List<LectureDto.LectureRes> lectureList) {
            CourseRes res = new CourseRes();

            res.setCourseId(course.getCourseId());
            res.setCourseName(course.getCourseName());
            res.setCourseDescription(course.getCourseDescription());
            res.setCourseOpenDate(course.getCourseOpenDate());
            res.setCourseCycle(course.getCourseCycle());
            res.setCourseThumbnail(course.getCourseThumbnail());
            res.setCourseCategory(course.getCourseCategory());
            res.setCourseLimitPeople(course.getCourseLimitPeople());
            res.setCourseFee(course.getCourseFee());
            res.setCourseIntroVideo(course.getCourseIntroVideo());
            res.setInstructorName(course.getUser().getUserName());
            res.setLectureList(lectureList);

            return res;
        }
    }
}
