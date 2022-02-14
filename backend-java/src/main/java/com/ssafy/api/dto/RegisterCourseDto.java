package com.ssafy.api.dto;

import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.RegisterCourse;
import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


public class RegisterCourseDto {
    @Getter
    @Setter
    @ApiModel("RegisterCourseListRes")
    public static class RegisterCourseListRes {
        @ApiModelProperty(name="registerId", example="1")
        Long registerId;
        @ApiModelProperty(name="isCancel", example="0")
        Boolean isCancel;
        @ApiModelProperty(name="user", example="user object")
        User user;
        @ApiModelProperty(name="course", example="course object")
        Course course;

        public static RegisterCourseDto.RegisterCourseListRes of(RegisterCourse registerCourse) {
            RegisterCourseDto.RegisterCourseListRes res = new RegisterCourseDto.RegisterCourseListRes();

            res.setRegisterId(registerCourse.getRegisterId());
            res.setIsCancel(registerCourse.isCancel());
            res.setUser(registerCourse.getUser());
            res.setCourse(registerCourse.getCourse());
            return res;
        }
    }
}

