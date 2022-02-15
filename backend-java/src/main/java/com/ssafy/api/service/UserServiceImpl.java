package com.ssafy.api.service;

import com.ssafy.api.dto.*;
import com.ssafy.common.exception.handler.BusinessException;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.ssafy.common.exception.handler.ErrorCode.DUPLICATE_RESOURCE;
import static com.ssafy.common.exception.handler.ErrorCode.MEMBER_NOT_FOUND;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	WishlistRepository wishlistRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	/**
	 * create
	 */
	@Override
	public User createUser(UserDto.UserRegisterPostReq userRegisterInfo) {
		if (userRepository.findByEmail(userRegisterInfo.getEmail()).isPresent()) {
			throw new BusinessException(DUPLICATE_RESOURCE);
		}
		User user = User.builder()
				.email(userRegisterInfo.getEmail())
				.password(passwordEncoder.encode(userRegisterInfo.getPassword()))
				.userName(userRegisterInfo.getUserName())
				.role(userRegisterInfo.getRole())
				.build();
		user.setUserNickname(userRegisterInfo.getUserNickname());
		return userRepository.save(user);
	}

	/**
	 * read
	 */

	@Override
	public User getUserById(Long userId) {
		return userRepository.findById(userId).get();
	}

	@Override
	public User getUserByEmail(String email) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		User user = userRepository.findByEmail(email).get();

		return user;
	}

	@Override
	public List<WishlistDto.WishlistRes> getWishlist(String email) {
		User user = userRepository.findByEmail(email).get();

		List<WishlistDto.WishlistRes> result = new ArrayList<>();
		List<Wishlist> list = wishlistRepository.findAll();

		for (Wishlist wishlist : list) {
			if (user == wishlist.getUser()){
				WishlistDto.WishlistRes wishlistRes = new WishlistDto.WishlistRes();
				wishlistRes.setCourse(wishlist.getCourse());
				result.add(wishlistRes);
			}
		}
		return result;
	}

	@Override
	public List<UserDto.UserInstructorRes> getInstructorList(Pageable pageable) {
		List<UserDto.UserInstructorRes> result = userRepository.findInstructorList(pageable);
		return result;
	}

	@Override
	public List<UserDto.UserInstructorRes> getBestInstructorList(){
		return userRepository.findInstructorListByBest();
	}

	@Override
	public List<UserDto.UserInstructorRes> getSearchInstructorList(String instructorName, Pageable pageable) {
		return userRepository.findInstructorListByUserNickname(instructorName, pageable);
	}

	/**
	 * update
	 */

	@Override
	public User updateUser(String email, UserDto.UserPutReq userPutReq) {
		User user = getUserByEmail(email);

		user.setEmail(userPutReq.getEmail());
		user.setUserName(userPutReq.getUserName());
		user.setUserNickname(userPutReq.getUserNickname());
		user.setProfileImage(userPutReq.getProfileImage());
		user.setThumbnailImage(userPutReq.getThumbnailImage());
		user.setUserDescription(userPutReq.getUserDescription());

		return userRepository.save(user);
	}

	/**
	 * delete
	 */

	@Override
	public void delete(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));
		userRepository.delete(user);
	}
}
