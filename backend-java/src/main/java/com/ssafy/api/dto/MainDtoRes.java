package com.ssafy.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel("MainDtoRes")
public class MainDtoRes {
    @ApiModelProperty(name="register courselist")
    List<CourseDto.CourseListRes> registerCourseList;
    @ApiModelProperty(name="instructor list")
    List<UserDto.UserInstructorRes> instructorList;
    @ApiModelProperty(name="course list")
    List<CourseDto.CourseListRes> courseList;

    public static MainDtoRes of(
            List<CourseDto.CourseListRes> registerCourseList,
            List<UserDto.UserInstructorRes> instructorList,
            List<CourseDto.CourseListRes> courseList) {
        MainDtoRes mainDtoRes = new MainDtoRes();

        mainDtoRes.setRegisterCourseList(registerCourseList);
        mainDtoRes.setInstructorList(instructorList);
        mainDtoRes.setCourseList(courseList);

        return mainDtoRes;
    }
}
