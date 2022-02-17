package com.ssafy.db.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.dto.CourseDto;
import com.ssafy.api.dto.ReviewDto;
import com.ssafy.api.dto.UserDto;
import com.ssafy.api.request.SttDto;

import static com.ssafy.db.entity.QCourse.course;
import static com.ssafy.db.entity.QReview.review;
import static com.ssafy.db.entity.QStt.stt;
import com.ssafy.db.entity.Stt;
import com.ssafy.db.entity.User;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class SttCustomRepositoryImpl implements SttCustomRepository{
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public List<SttDto.SttRes> findAllByLectureId(Long lectureId){
        return jpaQueryFactory.select(Projections.constructor(SttDto.SttRes.class,
                stt.sttOneId, stt.sttRecognitionStatus, stt.sttOffset,
                stt.sttDuration, stt.sttDisplayText))
                .from(stt)
                .where(stt.lecture.lectureId.eq(lectureId))
                .fetch();
    }
}