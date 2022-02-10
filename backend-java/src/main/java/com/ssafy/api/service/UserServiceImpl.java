package com.ssafy.api.service;

import com.ssafy.api.dto.UserDto;
import com.ssafy.common.exception.handler.BusinessException;
import com.ssafy.common.exception.handler.ErrorCode;
import com.ssafy.db.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRepositorySupport;

import static com.ssafy.common.exception.handler.ErrorCode.MEMBER_NOT_FOUND;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	UserRepositorySupport userRepositorySupport;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public User createUser(UserDto.UserRegisterPostReq userRegisterInfo) {
		userRepository.findByEmail(userRegisterInfo.getEmail()).orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));
		User user = User.builder()
				.email(userRegisterInfo.getEmail())
				.password(passwordEncoder.encode(userRegisterInfo.getPassword()))
				.userName(userRegisterInfo.getName())
				.role(Role.USER)
				.build();

		user.setUserNickname(userRegisterInfo.getNickname());
		// 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
		return userRepository.save(user);
	}

	@Override
	public User getUserByEmail(String email) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		User user = userRepositorySupport.findUserByEmail(email).get();
		return user;
	}

	@Override
	public User update(String email, UserDto.UserRegisterPostReq userRegisterPostReq) {
		User user = userRepositorySupport.findUserByEmail(email).get();
		user.setUserName(userRegisterPostReq.getName());
		user.setUserNickname(userRegisterPostReq.getNickname());

		return userRepository.save(user);
	}

	@Override
	public void delete(String email) {
		User user = userRepositorySupport.findUserByEmail(email).get();
		userRepository.delete(user);
	}

}
