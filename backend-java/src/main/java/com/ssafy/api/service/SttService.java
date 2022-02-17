package com.ssafy.api.service;

import com.ssafy.api.request.SttDto;
import com.ssafy.db.entity.Stt;
import com.ssafy.db.entity.Lecture;
import org.springframework.beans.factory.annotation.Autowired;
import springfox.documentation.spring.web.json.Json;

import java.util.List;
import java.util.Map;

public interface SttService {
    void createStt(Long lectureId, List<SttDto.SttInsertReq> sttInsert);
    List<SttDto.SttRes> getSttByLectureId(Long lectureId);

}
