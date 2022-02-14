package com.ssafy.api.service;

import com.ssafy.api.request.SttDto;
import com.ssafy.db.entity.Stt;
import com.ssafy.db.entity.Lecture;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public interface SttService {
    Stt createStt(Long lectureId, List<SttDto.SttInsertReq> sttInsert);
    List<Stt> getSttByLectureId(Long lecutreId);

}
