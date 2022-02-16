package com.ssafy.db.repository;

import com.ssafy.api.dto.UserDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserCustomRepository {
    public List<UserDto.UserInstructorRes> findInstructorList(Pageable pageable);
    public List<UserDto.UserInstructorRes> findInstructorListByBest();
    public List<UserDto.UserInstructorRes> findInstructorListByUserNickname(String instructorName, Pageable pageable);
}
