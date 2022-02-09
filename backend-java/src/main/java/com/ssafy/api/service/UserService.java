package com.ssafy.api.service;

import com.ssafy.api.dto.UserDto;
import com.ssafy.db.entity.User;

import java.util.Map;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {
	User createUser(UserDto.UserRegisterPostReq userRegisterInfo);
	User getUserByEmail(String email);
	User update(String email, UserDto.UserRegisterPostReq userRegisterPostReq);
	void delete(String email);
}
