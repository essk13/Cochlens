package com.ssafy.api.service;

import com.ssafy.db.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRepositorySupport;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.Optional;

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
	public User createUser(UserRegisterPostReq userRegisterInfo) {
		User user = new User();
		user.setEmail(userRegisterInfo.getEmail());
		user.setUserName(userRegisterInfo.getName());
		user.setUserNickname(userRegisterInfo.getNickname());
		user.setRole(Role.USER);
		// 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
		user.setPassword(passwordEncoder.encode(userRegisterInfo.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public User getUserByEmail(String email) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		User user = userRepositorySupport.findUserByEmail(email).get();
		return user;
	}

	@Transactional
	public int update(String email, Map<String, Object> body) {
		Optional<User> oUser = userRepository.findByEmail(email);


		if(!oUser.isPresent())
			return 0;

		User user = oUser.get();
		user.setUserName(body.get("userName").toString());
		user.setUserNickname(body.get("userNickname").toString());
		user.setUserDescription(body.get("userDescription").toString());
		user.setEmail(body.get("email").toString());
		user.setSubtitle(Boolean.parseBoolean(body.get("isSubtitle").toString()));
		user.setCommand(Boolean.parseBoolean(body.get("isCommand").toString()));
		user.setTTS(Boolean.parseBoolean(body.get("isTTS").toString()));
		user.setFaceFocusing(Boolean.parseBoolean(body.get("isFaceFocusing").toString()));
		user.setRole(Role.valueOf(body.get("role").toString()));
		user.setProfileImage(body.get("profileImage").toString());

		System.out.println(body.get("password").toString());
		user.setPassword(passwordEncoder.encode(body.get("password").toString()));

		userRepository.save(user);
		return 1;
	}

	@Transactional
	public int delete(String email) {
		Optional<User> oUser = userRepository.findByEmail(email);
		if(oUser.isPresent()) {
			userRepository.delete(oUser.get());
			return 1;
		}
		return 0;
	}

}
