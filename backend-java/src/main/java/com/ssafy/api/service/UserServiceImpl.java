package com.ssafy.api.service;

import com.google.common.primitives.Ints;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.dto.*;
import com.ssafy.common.exception.handler.BusinessException;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
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
	RegisterCourseRepository registerCourseRepository;

	@Autowired
	WishlistRepository wishlistRepository;

	@Autowired
	ReviewRepository reviewRepository;

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
				.userName(userRegisterInfo.getName())
				.role(userRegisterInfo.getRole())
				.build();
		user.setUserNickname(userRegisterInfo.getNickname());
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
	public List<UserDto.UserInstructorRes> getInstructorList() {
		List<UserDto.UserInstructorRes> result = userRepository.findInstructorList();
		return result;
	}

	/*
		수정 필요
	 */
	@Override
	public UserDto.UserRes getInstructorInfo(Long userId){
		User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));

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

//		userRes.setCourseCount(courseCount);
//		userRes.setCourseReviewCount(courseReviewCount);
//		userRes.setCourseReviewRateAverage(courseReviewRateAverage);
//		userRes.setCourseReviewList(courseReviewList);
//		userRes.setLiveOpenCourseList(liveOpenCourseList);
//		userRes.setVodOpenCourseList(vodOpenCourseList);
		return userRes;
	}

	/*
		수정 필요
	 */
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

//			userListRes.setCourseCount(courseCount);
//			userListRes.setCourseReviewCount(courseReviewCount);
//			userListRes.setCourseReviewRateAverage(courseReviewRateAverage);

			result.add(userListRes);
		}
		return result;
	}

	/**
	 * update
	 */

	/*
		수정 필요
	 */
	@Override
	public User update(String email, UserDto.UserPutRes userPutRes) {
		String emailCheck = userPutRes.getEmail();

//		입력한 이메일이 DB에 있고 현재 계정의 이메일과 다를 경우
		if(userRepository.findByEmail(emailCheck) != userRepository.findByEmail("") && !email.equals(emailCheck)) return null;
//		if(userCheck != null && !email.equals(emailCheck)) return null;

//		입력한 이메일이 DB에 없거나, 현재 계정의 이메일과 같을 경우
		User user = userRepository.findByEmail(email).get();

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

	/**
	 * delete
	 */

	@Override
	public void delete(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));
		userRepository.delete(user);
	}
}
