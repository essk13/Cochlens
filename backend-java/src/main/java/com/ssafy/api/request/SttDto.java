package com.ssafy.api.request;

import com.ssafy.api.dto.CourseDto;
import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.Lecture;
import com.ssafy.db.entity.Stt;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.ssafy.db.entity.Lecture;
import springfox.documentation.spring.web.json.Json;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


public class SttDto {

    @Getter
    @Setter
    @ApiModel("SttInsertReq")
    public static class SttInsertReq {

        @ApiModelProperty(name="stt_one_id", example="3fba53c7778842829fceacbd935a173f")
        String sttOneId;

        @ApiModelProperty(name="stt_recognitionStatus", example = "1")
        Integer sttRecognitionStatus;

        @ApiModelProperty(name="stt_offset", example = "1")
        Integer sttOffset;

        @ApiModelProperty(name="stt_duration", example = "1")
        Integer sttDuration;

        @ApiModelProperty(name="stt_display_text", example = "1")
        String sttDisplayText;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @ApiModel("SttRes")
    public static class SttRes {
        @ApiModelProperty(name="stt_one_id", example="3fba53c7778842829fceacbd935a173f")
        String sttOneId;

        @ApiModelProperty(name="stt_recognitionStatus", example = "1")
        Integer sttRecognitionStatus;

        @ApiModelProperty(name="stt_offset", example = "1")
        Integer sttOffset;

        @ApiModelProperty(name="stt_duration", example = "1")
        Integer sttDuration;

        @ApiModelProperty(name="stt_display_text", example = "1")
        String sttDisplayText;

        public static SttDto.SttRes of(Stt stt) {

            SttDto.SttRes res = new SttDto.SttRes();

            res.setSttOneId(stt.getSttOneId());
            res.setSttRecognitionStatus(stt.getSttRecognitionStatus());
            res.setSttOffset(stt.getSttOffset());
            res.setSttDuration(stt.getSttDuration());
            res.setSttDisplayText(stt.getSttDisplayText());

            return res;
        }
    }
}
