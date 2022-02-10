package com.ssafy.api.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.dto.UserDto;
import com.ssafy.common.exception.handler.BusinessException;
import com.ssafy.common.exception.handler.ErrorCode;
import com.ssafy.db.entity.QUser;
import com.ssafy.db.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRepositorySupport;

import java.util.Optional;

import static com.ssafy.common.exception.handler.ErrorCode.DUPLICATE_RESOURCE;
import static com.ssafy.common.exception.handler.ErrorCode.MEMBER_NOT_FOUND;
import static java.util.Optional.empty;

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

	@Autowired
	JPAQueryFactory jpaQueryFactory;

	@Override
	public User createUser(UserDto.UserRegisterPostReq userRegisterInfo) {
		if(userRepository.findByEmail(userRegisterInfo.getEmail()) != userRepository.findByEmail("notexisted")){
			userRepository.findByEmail("notexisted").orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));
		}
//		userRepository.findByEmail(userRegisterInfo.getEmail()).ifPresent(() -> new BusinessException(DUPLICATE_RESOURCE));
//		userRepository.findByEmail(userRegisterInfo.getEmail()).orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));

//		QUser qUser = QUser.user;
//		String email = userRegisterInfo.getEmail();
//		User userCheck = jpaQueryFactory.select(qUser).from(qUser)
//					.where(qUser.email.eq(email)).fetchOne();
//		if(userCheck != null) return null;

		User user = new User();

		user.setEmail(userRegisterInfo.getEmail());
		user.setPassword(passwordEncoder.encode(userRegisterInfo.getPassword()));
		user.setUserName(userRegisterInfo.getName());
		user.setRole(Role.USER);

//		-------------

//		User user = User.builder()
//				.email(userRegisterInfo.getEmail())
//				.password(passwordEncoder.encode(userRegisterInfo.getPassword()))
//				.userName(userRegisterInfo.getName())
//				.role(Role.USER)
//				.build();

		user.setUserNickname(userRegisterInfo.getNickname());
		// 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.

		return userRepository.save(user);
	}
//	@Override
//	public User createUser(UserDto.UserRegisterPostReq userRegisterInfo) {
//		userRepository.findByEmail(userRegisterInfo.getEmail()).orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));
//		User user = User.builder()
//				.email(userRegisterInfo.getEmail())
//				.password(passwordEncoder.encode(userRegisterInfo.getPassword()))
//				.userName(userRegisterInfo.getName())
//				.role(Role.USER)
//				.build();
//
//		user.setUserNickname(userRegisterInfo.getNickname());
//		// 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
//		return userRepository.save(user);
//	}

	@Override
	public User getUserByEmail(String email) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		User user = userRepositorySupport.findUserByEmail(email).get();
		return user;
	}

	@Override
	public User update(String email, UserDto.UserPutRes userPutRes) {

		String emailCheck = userPutRes.getEmail();

		QUser qUser = QUser.user;

		User userCheck = jpaQueryFactory.select(qUser).from(qUser)
				.where(qUser.email.eq(emailCheck)).fetchOne();
//		입력한 이메일이 DB에 있을 때
		if(userCheck != null && !email.equals(emailCheck)) return null;

//		입력한 이메일이 DB에 없거나, 현재 계정의 이메일과 같을 경우
		User user = userRepositorySupport.findUserByEmail(email).get();

		user.setEmail(userPutRes.getEmail());
		user.setUserName(userPutRes.getUserName());
		user.setUserNickname(userPutRes.getUserNickname());
		user.setUserName(userPutRes.getProfileImage());
		user.setUserName(userPutRes.getThumbnailImage());
		user.setPassword(passwordEncoder.encode(userPutRes.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public void delete(String email) {
		User user = userRepositorySupport.findUserByEmail(email).get();
		userRepository.delete(user);
	}

}
