package com.ssafy.api.request;

import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.Lecture;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import springfox.documentation.spring.web.json.Json;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


public class SttDto {

    @Getter
    @Setter
    @ApiModel("SttDto")
    public static class SttInsertReq {

        @ApiModelProperty(name="stt_one_id", example="3fba53c7778842829fceacbd935a173f")
        String id;

        @ApiModelProperty(name="stt_recognitionStatus", example = "1")
        Integer recognitionStatus;

        @ApiModelProperty(name="stt_offset", example = "1")
        Integer offset;

        @ApiModelProperty(name="stt_duraton", example = "1")
        Integer duration;

        @ApiModelProperty(name="stt_dispalyText", example = "1")
        String displayText;

    }



    @Getter
    @Setter
    @ApiModel("SttRes")
    public static class SttRes {
        @ApiModelProperty(name="자막 Id", example="3fba53c7778842829fceacbd935a173f")
        String id;
        @ApiModelProperty(name="강의 Id", example ="1")
        Long lectureid;
        @ApiModelProperty(name="자막 내용", example = "{}")
        Json stt;


    }
}
