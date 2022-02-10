package com.ssafy.api.controller;

import com.ssafy.api.dto.UserDto;
import com.ssafy.api.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	CourseService courseService;

	@PostMapping()
	@ApiOperation(value = "회원 가입", notes = "<strong>아이디와 패스워드</strong>를 통해 회원가입 한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
		@ApiResponse(code = 402, message = "email 중복"),
		@ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseBody> register(
			@RequestBody @ApiParam(value="회원가입 정보", required = true) UserDto.UserRegisterPostReq registerInfo) {
		User user = userService.createUser(registerInfo);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/me")
	@ApiOperation(value = "회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<UserDto.UserRes> getUserInfo(@ApiIgnore Authentication authentication) {
		/**
		 * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
		 * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
		 */
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String email = userDetails.getUsername();
		User user = userService.getUserByEmail(email);

		return ResponseEntity.status(200).body(UserDto.UserRes.of(user));
	}

	@PutMapping("/me")
	@ApiOperation(value = "회원 본인 정보 수정", notes = "로그인한 회원 본인의 정보를 수정한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<UserDto.UserPutRes> update(@ApiIgnore Authentication authentication,
												  @RequestBody @ApiParam(value = "회원 수정 정보", required = true) UserDto.UserPutRes userPutRes) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String email = userDetails.getUsername();

		User user = userService.update(email, userPutRes);

		return ResponseEntity.status(200).body(UserDto.UserPutRes.of(user));
	}

//	@PutMapping("/me")
//	@ApiOperation(value = "회원 본인 정보 수정", notes = "로그인한 회원 본인의 정보를 수정한다.")
//	@ApiResponses({
//			@ApiResponse(code = 200, message = "성공"),
//			@ApiResponse(code = 401, message = "인증 실패"),
//			@ApiResponse(code = 404, message = "사용자 없음"),
//			@ApiResponse(code = 500, message = "서버 오류")
//	})
//	public ResponseEntity<UserDto.UserRes> update(@ApiIgnore Authentication authentication,
//												  @RequestBody @ApiParam(value = "회원 수정 정보", required = true) UserDto.UserRegisterPostReq userRegisterPostReq) {
//		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
//		String email = userDetails.getUsername();
//
//		User user = userService.update(email, userRegisterPostReq);
//
//		return ResponseEntity.status(200).body(UserDto.UserRes.of(user));
//	}

	@DeleteMapping("/me")
	@ApiOperation(value = "회원 본인 정보 삭제", notes = "로그인한 회원을 탈퇴한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<?> delete(@ApiIgnore Authentication authentication) {
		/**
		 * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
		 * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
		 */
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String email = userDetails.getUsername();
		userService.delete(email);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/instructor")
	@ApiOperation(value = "강사 정보 조회", notes = "강사 목록을 조회한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<?> getInstructorList() {

		return ResponseEntity.status(200).build();
	}


	@GetMapping("/review")
	@ApiOperation(value = "강사 리뷰 조회", notes = "강사 리뷰 목록을 조회한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<?> getInstructorReivew() {

		return ResponseEntity.status(200).build();
	}

//	@GetMapping("/course")
//	public List<Map<String, Object>> getCourseByUserEmail(@ApiIgnore Authentication authentication) {
//		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
//		String email = userDetails.getUsername();
//
//		List<Map<String, Object>> result = courseService.getCourseByUserEmail(email);
//
//		return result;
//
//	}

}