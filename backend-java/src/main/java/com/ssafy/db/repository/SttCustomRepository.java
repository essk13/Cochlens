package com.ssafy.db.repository;


import com.ssafy.api.request.SttDto;
import com.ssafy.db.entity.Stt;

import java.util.List;

public interface SttCustomRepository {
    public List<SttDto.SttRes> findAllByLectureId(Long lectureId);
}

