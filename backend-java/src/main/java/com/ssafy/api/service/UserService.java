package com.ssafy.api.service;

import com.ssafy.api.dto.CourseDto;
import com.ssafy.api.dto.RegisterCourseDto;
import com.ssafy.api.dto.UserDto;
import com.ssafy.api.dto.WishlistDto;
import com.ssafy.db.entity.User;

import java.util.List;
import java.util.Map;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {
	User createUser(UserDto.UserRegisterPostReq userRegisterInfo);
	User getUserByEmail(String email);
	//	User update(String email, UserDto.UserRegisterPutReq userRegisterPutReq);
	User update(String email, UserDto.UserPutRes userPutRes);
	void delete(String email);

	List<UserDto.UserRes> getInstructorList();


	List<WishlistDto.WishlistRes> getWishlist(String email);

	List<UserDto.UserRes> getBestInstructorList();
	UserDto.UserRes getInstructorInfo(Long userId);
}
