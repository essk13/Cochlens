package com.ssafy.db.repository;

import com.ssafy.api.dto.UserDto;

import java.util.List;

public interface UserCustomRepository {
    public List<UserDto.UserInstructorRes> findInstructorList();
    public List<UserDto.UserInstructorRes> findByBestInstructorList();
}
