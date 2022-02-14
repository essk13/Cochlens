package com.ssafy.api.service;

import com.google.common.primitives.Ints;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.dto.*;
import com.ssafy.common.exception.handler.BusinessException;
import com.ssafy.common.exception.handler.ErrorCode;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.ssafy.common.exception.handler.ErrorCode.DUPLICATE_RESOURCE;
import static com.ssafy.common.exception.handler.ErrorCode.MEMBER_NOT_FOUND;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

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
	CourseRepository courseRepository;

	@Autowired
	RegisterCourseRepository registerCourseRepository;

	@Autowired
	WishlistRepository wishlistRepository;

	@Autowired
	ReviewRepository reviewRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JPAQueryFactory jpaQueryFactory;

	@Override
	public User createUser(UserDto.UserRegisterPostReq userRegisterInfo) {

		if(userRepository.findByEmail(userRegisterInfo.getEmail()) != userRepository.findByEmail("")){
			userRepository.findByEmail("").orElseThrow(() -> new BusinessException(DUPLICATE_RESOURCE));
		}
//		userRepository.findByEmail(userRegisterInfo.getEmail()).ifPresent(() -> new BusinessException(DUPLICATE_RESOURCE));
//		userRepository.findByEmail(userRegisterInfo.getEmail()).orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));
//		userRepository.findByEmail(userRegisterInfo.getEmail()).orElseThrow(() -> new BusinessException(DUPLICATE_RESOURCE));

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
//
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

//		User userCheck = userRepositorySupport.findUserByEmail(emailCheck).get();

//		QUser qUser = QUser.user;
//
//		User userCheck = jpaQueryFactory.select(qUser).from(qUser)
//				.where(qUser.email.eq(emailCheck)).fetchOne();


//		입력한 이메일이 DB에 있고 현재 계정의 이메일과 다를 경우
		if(userRepository.findByEmail(emailCheck) != userRepository.findByEmail("") && !email.equals(emailCheck)) return null;
//		if(userCheck != null && !email.equals(emailCheck)) return null;

//		입력한 이메일이 DB에 없거나, 현재 계정의 이메일과 같을 경우
		User user = userRepositorySupport.findUserByEmail(email).get();

		user.setEmail(userPutRes.getEmail());
		user.setUserName(userPutRes.getUserName());
		user.setUserNickname(userPutRes.getUserNickname());
		user.setProfileImage(userPutRes.getProfileImage());
		user.setThumbnailImage(userPutRes.getThumbnailImage());
		user.setUserDescription(userPutRes.getUserDescription());
		user.setPassword(passwordEncoder.encode(userPutRes.getPassword()));
		user.setCommand(userPutRes.getIsCommand());
		user.setRole(userPutRes.getRole());
		user.setFaceFocusing(userPutRes.getIsFaceFocusing());
		user.setSubtitle(userPutRes.getIsSubtitle());
		user.setSTT(userPutRes.getIsSTT());

		return userRepository.save(user);
	}

	@Override
	public void delete(String email) {
		User user = userRepositorySupport.findUserByEmail(email).get();
		userRepository.delete(user);
	}


	@Override
	public List<WishlistDto.WishlistRes> getWishlist(String email) {
		User user = userRepositorySupport.findUserByEmail(email).get();

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
	public List<UserDto.UserRes> getInstructorList(){

		List<User> list = userRepository.findAll();

		List<UserDto.UserRes> result = new ArrayList<>();

		for (User user : list) {
			if (user.getRole() == Role.INSTRUCTOR){
				UserDto.UserRes userListRes = new UserDto.UserRes();

//				userListRes.setEmail(user.getEmail());
				userListRes.setUserName(user.getUserName());
				userListRes.setUserNickname(user.getUserNickname());
				userListRes.setUserDescription(user.getUserDescription());
//				userListRes.setIsSubtitle(user.isSubtitle());
//				userListRes.setIsCommand(user.isCommand());
//				userListRes.setIsSTT(user.isSTT());
//				userListRes.setIsFaceFocusing(user.isFaceFocusing());
//				userListRes.setRole(user.getRole());
				userListRes.setProfileImage(user.getProfileImage());
				userListRes.setThumbnailImage(user.getThumbnailImage());
				result.add(userListRes);
			}
		}
		return result;
	}

	@Override
	public List<UserDto.UserRes> getBestInstructorList(){

		int userSize = userRepository.findAll().size();


		int[] arr = new int[userSize + 1];
		List<Wishlist> list = wishlistRepository.findAll();
		for (Wishlist wishlist : list) {
			arr[Math.toIntExact(wishlist.getUser().getUserId())] += 1;
		}

		List<UserDto.UserRes> result = new ArrayList<>();

		int count = Math.min(arr.length, 5);


		for (int i = 0; i < count; i++) {
			int max = Arrays.stream(arr).max().getAsInt();

//            max 값을 가진 index 찾기(강좌 번호)
			int maxIndex = Ints.indexOf(arr, max);
//			int maxIndex = Arrays.asList(arr).indexOf(max);

			arr[maxIndex] = 0;

			if (maxIndex == 0) break;

			User user = userRepository.getOne(Long.valueOf(maxIndex));



			int courseCount = 0;

			int courseReviewCount = 0;

			int courseReviewRateSum = 0;

			Double courseReviewRateAverage = 0.0;

			List<Review> reviewList = reviewRepository.findAll();
			List<Course> courseList = courseRepository.findAll();
			for (Course course : courseList) {
				if ( user == course.getUser()) {
					courseCount += 1;

					for  (Review review : reviewList){
						if(course == review.getCourse()){
							courseReviewCount += 1;
							courseReviewRateSum += review.getReviewGrade();
						}
					}
				}
			}

			if (courseReviewCount != 0){
//                소수점 둘째자리까지 표시
				courseReviewRateAverage = Math.round(Double.valueOf(courseReviewRateSum / courseReviewCount) * 100) / 100.0;
			}


			UserDto.UserRes userListRes = new UserDto.UserRes();

			userListRes.setUserId(user.getUserId());
			userListRes.setEmail(user.getEmail());
			userListRes.setUserName(user.getUserName());
			userListRes.setUserNickname(user.getUserNickname());
//			userListRes.setUserDescription(user.getUserDescription());
//				userListRes.setIsSubtitle(user.isSubtitle());
//				userListRes.setIsCommand(user.isCommand());
//				userListRes.setIsSTT(user.isSTT());
//				userListRes.setIsFaceFocusing(user.isFaceFocusing());
//				userListRes.setRole(user.getRole());
			userListRes.setProfileImage(user.getProfileImage());
//			userListRes.setThumbnailImage(user.getThumbnailImage());

			userListRes.setCourseCount(courseCount);
			userListRes.setCourseReviewCount(courseReviewCount);
			userListRes.setCourseReviewRateAverage(courseReviewRateAverage);

			result.add(userListRes);
		}
		return result;
	}

	@Override
	public UserDto.UserRes getInstructorInfo(Long userId){

		User user = userRepository.getOne(userId);

		int courseCount = 0;

		int courseReviewCount = 0;

		int courseReviewRateSum = 0;

		Double courseReviewRateAverage = 0.0;


		List<Review> courseReviewList = new ArrayList<>();
		List<Course> liveOpenCourseList = new ArrayList<>();
		List<Course> vodOpenCourseList = new ArrayList<>();

		Date date = new Date();

		List<Review> reviewList = reviewRepository.findAll();
		List<Course> courseList = courseRepository.findAll();
		for (Course course : courseList) {
			if ( user == course.getUser()) {
				courseCount += 1;
				if (date.after(course.getCourseOpenDate()) && date.before(course.getCourseCloseDate())){
					liveOpenCourseList.add(course);
				}
				else {
					vodOpenCourseList.add(course);
				}
				for  (Review review : reviewList){
					if(course == review.getCourse()){
						courseReviewCount += 1;
						courseReviewRateSum += review.getReviewGrade();
						courseReviewList.add(review);
					}
				}
			}
		}

		if (courseReviewCount != 0){
//                소수점 둘째자리까지 표시
			courseReviewRateAverage = Math.round(Double.valueOf(courseReviewRateSum / courseReviewCount) * 100) / 100.0;
		}


		UserDto.UserRes userRes = new UserDto.UserRes();

		userRes.setUserId(user.getUserId());
		userRes.setEmail(user.getEmail());
		userRes.setUserName(user.getUserName());
		userRes.setUserNickname(user.getUserNickname());
		userRes.setUserDescription(user.getUserDescription());
//		userRes.setIsSubtitle(user.isSubtitle());
//		userRes.setIsCommand(user.isCommand());
//		userRes.setIsSTT(user.isSTT());
//		userRes.setIsFaceFocusing(user.isFaceFocusing());
//		userRes.setRole(user.getRole());
		userRes.setProfileImage(user.getProfileImage());
		userRes.setThumbnailImage(user.getThumbnailImage());

		userRes.setCourseCount(courseCount);
		userRes.setCourseReviewCount(courseReviewCount);
		userRes.setCourseReviewRateAverage(courseReviewRateAverage);
		userRes.setCourseReviewList(courseReviewList);
		userRes.setLiveOpenCourseList(liveOpenCourseList);
		userRes.setVodOpenCourseList(vodOpenCourseList);
		return userRes;

	}
}
