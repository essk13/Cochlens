package com.ssafy.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.db.entity.Lecture;
import com.ssafy.db.entity.Course;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class LectureDto {
    @Getter
    @Setter
    @ApiModel("LectureInsertReq")
    public static class LectureInsertReq {
        @ApiModelProperty(name="lectureName", example="test")
        String lectureName;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm")
        @ApiModelProperty(name="lectureRuntime", example="02:00")
        Date lectureRuntime;
        @ApiModelProperty(name="lectureThumbnail", example="thumbnail address")
        String lectureThumbnail;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
        @ApiModelProperty(name="lectureDate", example="2022/02/11")
        Date lectureDate;
        @ApiModelProperty(name="lectureState", example="start")
        String lectureState;
        @ApiModelProperty(name="lectureVod", example="vod address")
        String lectureVod;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm")
        @ApiModelProperty(name="lectureOpenTime", example="10:10")
        Date lectureOpenTime;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm")
        @ApiModelProperty(name="lectureCloseTime", example="12:10")
        Date lectureCloseTime;
    }

    @Getter
    @Setter
    @ApiModel("LectureListRes")
    public static class LectureListRes {
        @ApiModelProperty(name="lectureId", example="1")
        Long lectureId;
        @ApiModelProperty(name="lectureName", example="test")
        String lectureName;
        @ApiModelProperty(name="lectureRuntime", example="02:00")
        Date lectureRuntime;
        @ApiModelProperty(name="lectureThumbnail", example="thumbnail address")
        String lectureThumbnail;
        @ApiModelProperty(name="lectureDate", example="2022/02/11")
        Date lectureDate;
        @ApiModelProperty(name="lectureState", example="start")
        String lectureState;
        @ApiModelProperty(name="lectureOpenTime", example="10:10")
        Date lectureOpenTime;
        @ApiModelProperty(name="lectureCloseTime", example="12:10")
        Date lectureCloseTime;

        public static LectureListRes of(Lecture lecture) {
            LectureListRes res = new LectureListRes();

            res.setLectureId(lecture.getLectureId());
            res.setLectureName(lecture.getLectureName());
            res.setLectureRuntime(lecture.getLectureRuntime());
            res.setLectureThumbnail(lecture.getLectureThumbnail());
            res.setLectureDate(lecture.getLectureDate());
            res.setLectureState(lecture.getLectureState());
            res.setLectureOpenTime(lecture.getLectureOpenTime());
            res.setLectureCloseTime(lecture.getLectureCloseTime());

            return res;
        }
    }

    @Getter
    @Setter
    @ApiModel("LectureRes")
    public static class LectureRes {
        @ApiModelProperty(name="lectureId", example="1")
        Long lectureId;
        @ApiModelProperty(name="lectureName", example="test")
        String lectureName;
        @ApiModelProperty(name="lectureRuntime", example="02:00")
        Date lectureRuntime;
        @ApiModelProperty(name="lectureThumbnail", example="thumbnail address")
        String lectureThumbnail;
        @ApiModelProperty(name="lectureDate", example="2022/02/11")
        Date lectureDate;
        @ApiModelProperty(name="lectureState", example="start")
        String lectureState;
        @ApiModelProperty(name="lectureVod", example="vod address")
        String lectureVod;
        @ApiModelProperty(name="lectureOpenTime", example="10:10")
        Date lectureOpenTime;
        @ApiModelProperty(name="lectureCloseTime", example="12:10")
        Date lectureCloseTime;
        @ApiModelProperty(name="course", example="course object")
        Course course;

        public static LectureRes of(Lecture lecture) {
            LectureRes res = new LectureRes();

            res.setLectureId(lecture.getLectureId());
            res.setLectureName(lecture.getLectureName());
            res.setLectureRuntime(lecture.getLectureRuntime());
            res.setLectureThumbnail(lecture.getLectureThumbnail());
            res.setLectureDate(lecture.getLectureDate());
            res.setLectureState(lecture.getLectureState());
            res.setLectureVod(lecture.getLectureVod());
            res.setLectureOpenTime(lecture.getLectureOpenTime());
            res.setLectureCloseTime(lecture.getLectureCloseTime());
            res.setCourse(lecture.getCourse());

            return res;
        }
    }

}
