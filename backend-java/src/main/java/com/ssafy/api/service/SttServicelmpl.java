package com.ssafy.api.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.request.SttDto;
import com.ssafy.db.entity.Lecture;
import com.ssafy.db.entity.Stt;
import com.ssafy.db.repository.LectureRepository;
import com.ssafy.db.repository.SttRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Stt createStt(Long lectureId, List<SttDto.SttInsertReq> sttInsert){
        Lecture insertLecture = new Lecture();
        List<Lecture> lectureList = lectureRepository.findAll();
        for(Lecture lecture : lectureList) {
            if (lectureId == lecture.getLectureId()) {
                insertLecture = lecture;
            }
        }
        for(SttDto.SttInsertReq insertStt : sttInsert) {
            Stt stt = Stt.builder()
                            .sttOneId(insertStt.getId())
                            .sttRecognitionStatus(insertStt.getRecognitionStatus())
                            .sttOffset(insertStt.getOffset())
                            .sttDuration(insertStt.getDuration())
                            .sttDisplayText(insertStt.getDisplayText())
                            .lecture(insertLecture)
                            .build();
            sttRepository.save(stt);
        }
        Stt stt = new Stt();
        return stt;
    };

    @Override
    public List<Stt>  getSttByLectureId(Long lecutreId){
        List<Stt> sttList =  sttRepository.findAll();
        List<Stt> returnStt = new ArrayList<>();

        for(Stt stt : sttList) {
            if (lecutreId == stt.getLecture().getLectureId()) {
                returnStt.add(stt);
            }
        }
        return returnStt;
    }
}
