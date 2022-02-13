package com.ssafy.api.controller;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.request.SttDto;
import com.ssafy.api.service.SttService;
import com.ssafy.api.service.SttServicelmpl;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.QLecture;
import com.ssafy.db.entity.Stt;
import com.ssafy.db.repository.SttRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "STT 테스트", tags = {"STT."})
@RestController
@RequestMapping(value = "/api/v1/STT")
public class SttController {
    @Autowired
    SttService sttService;

    @Autowired
    SttRepository sttRepository;

    @PostMapping(path = "/{lectureId}")
    @ApiOperation(value = "STT 생성", notes = "강의 번호와 Json 파일을 통해 저장한다. ")
    //	파라미터 값 설명
    @ApiResponses({
            @ApiResponse(code = 200, message = "우선들어오긴함"),
            @ApiResponse(code = 401, message = "전달 실패"),
    })
    public ResponseEntity SttPost(
                                  @ApiParam(value = "강좌 생성 정보", required = true)
                                  @PathVariable Long lectureId,
                                  @RequestBody
                                  @ApiParam(value = "자막 생성 정보", required = true)
                                  List<SttDto.SttInsertReq> sttInsert)
    {
        sttService.createStt(lectureId, sttInsert);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "강좌에 자막이 저장되었습니다"));
    }

    @GetMapping(path = "/{lectureId}")
    @ApiOperation(value = "STT 자막 반환", notes = "해당 강좌의 자막을 반환한다.")
    public ResponseEntity<List<Stt>> SttGet(@PathVariable Long lectureId)
    {

        List<Stt> returnStt = sttService.getSttByLectureId(lectureId);
        return ResponseEntity.ok().body(returnStt);
    }
}
