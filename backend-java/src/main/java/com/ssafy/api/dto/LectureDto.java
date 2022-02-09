package com.ssafy.api.dto;

import com.ssafy.db.entity.Lecture;
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
        @ApiModelProperty(name="lectureName", example="tets")
        String lectureName;
        @ApiModelProperty(name="lectureRuntime", example="hh:mm")
        Date lectureRuntime;
        @ApiModelProperty(name="lectureThumbnail", example="thumbnail address")
        String lectureThumbnail;
        @ApiModelProperty(name="lectureDate", example="yyyy-dd-mm")
        Date lectureDate;
        @ApiModelProperty(name="lectureState", example="start")
        String lectureState;
        @ApiModelProperty(name="lectureVod", example="vod address")
        String lectureVod;
        @ApiModelProperty(name="lectureOpenTime", example="hh:mm")
        Date lectureOpenTime;
        @ApiModelProperty(name="lectureCloseTime", example="hh:mm")
        Date lectureCloseTime;
    }

    @Getter
    @Setter
    @ApiModel("LectureRes")
    public static class LectureRes {
        @ApiModelProperty(name="lectureId")
        Long lectureId;
        @ApiModelProperty(name="lectureName")
        String lectureName;
        @ApiModelProperty(name="lectureRuntime")
        Date lectureRuntime;
        @ApiModelProperty(name="lectureThumbnail")
        String lectureThumbnail;
        @ApiModelProperty(name="lectureDate")
        Date lectureDate;
        @ApiModelProperty(name="lectureState")
        String lectureState;
        @ApiModelProperty(name="lectureVod")
        String lectureVod;
        @ApiModelProperty(name="lectureOpenTime")
        Date lectureOpenTime;
        @ApiModelProperty(name="lectureCloseTime")
        Date lectureCloseTime;

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

            return res;
        }
    }

}
