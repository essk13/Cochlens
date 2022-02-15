package com.ssafy.api.service;

import com.ssafy.api.dto.UserDto;
import com.ssafy.api.dto.WishlistDto;
import com.ssafy.db.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {
	/* create */
	User createUser(UserDto.UserRegisterPostReq userRegisterInfo);
	/* read */
	User getUserById(Long userId);
	User getUserByEmail(String email);
	List<WishlistDto.WishlistRes> getWishlist(String email);
	List<UserDto.UserInstructorRes> getInstructorList(Pageable pageable);
	List<UserDto.UserInstructorRes> getBestInstructorList();
	List<UserDto.UserInstructorRes> getSearchInstructorList(String instructorName, Pageable pageable);
	/* update */
	User updateUser(String email, UserDto.UserPutReq userPutReq);
	/* delete */
	void delete(String email);

}
