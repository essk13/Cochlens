package com.ssafy.api.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.dto.ReviewDto;
import com.ssafy.api.request.SttDto;
import com.ssafy.db.entity.Lecture;
import com.ssafy.db.entity.Review;
import com.ssafy.db.entity.Stt;
import com.ssafy.db.repository.LectureRepository;
import com.ssafy.db.repository.SttRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("sttService")
public class SttServicelmpl implements SttService{

    @Autowired
    SttRepository sttRepository;

    @Autowired
    LectureRepository lectureRepository;

    @Override
    public void createStt(Long lectureId, List<SttDto.SttInsertReq> sttInsert){
        Lecture lecture = lectureRepository.getOne(lectureId);

        for(SttDto.SttInsertReq insertStt : sttInsert) {
            Stt stt = Stt.builder()
                            .sttOneId(insertStt.getSttOneId())
                            .sttRecognitionStatus(insertStt.getSttRecognitionStatus())
                            .sttOffset(insertStt.getSttOffset())
                            .sttDuration(insertStt.getSttDuration())
                            .sttDisplayText(insertStt.getSttDisplayText())
                            .lecture(lecture)
                            .build();

            sttRepository.save(stt);
        }
    }

    @Override
    public List<SttDto.SttRes>  getSttByLectureId(Long lectureId) {


        List<SttDto.SttRes> list = sttRepository.findAllByLectureId(lectureId);



        List<SttDto.SttRes> result = new ArrayList<>();

        for (SttDto.SttRes sttData : list) {
            SttDto.SttRes stt = sttData;
            result.add(stt);
        }

        return result;
    }
}
