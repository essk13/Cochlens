package com.ssafy.api.dto;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Role;
import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

public class UserDto {
    /**
     * 유저 로그인 API ([POST] /api/v1/auth/login) 요청에 필요한 리퀘스트 바디 정의.
     */
    @Getter
    @Setter
    @ApiModel("UserLoginPostRequest")
    public static class UserLoginPostReq {
        @ApiModelProperty(name="유저 email", example="ssafy@ssafy.com")
        String email;
        @ApiModelProperty(name="유저 Password", example="your_password")
        String password;
    }

    /**
     * 유저 회원가입 API ([POST] /api/v1/users) 요청에 필요한 리퀘스트 바디 정의.
     */
    @Getter
    @Setter
    @ApiModel("UserRegisterPostRequest")
    public static class UserRegisterPostReq {
        @ApiModelProperty(name="유저 email", example="ssafy@ssafy.com")
        String email;
        @ApiModelProperty(name="이름", example="ssafy")
        String name;
        @ApiModelProperty(name="닉네임", example="hotsix")
        String nickname;
        @ApiModelProperty(name="유저 Password", example="your_password")
        String password;
    }

    /**
     * 유저 로그인 API ([POST] /api/v1/auth) 요청에 대한 응답값 정의.
     */
    @Getter
    @Setter
    @ApiModel("UserLoginPostResponse")
    public static class UserLoginPostRes extends BaseResponseBody {
        @ApiModelProperty(name="JWT 인증 토큰", example="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN...")
        String accessToken;

        public static UserLoginPostRes of(Integer statusCode, String message, String accessToken) {
            UserLoginPostRes res = new UserLoginPostRes();
            res.setStatusCode(statusCode);
            res.setMessage(message);
            res.setAccessToken(accessToken);
            return res;
        }
    }

    /**
     * 회원 본인 정보 조회 API ([GET] /api/v1/users/me) 요청에 대한 응답값 정의.
     */
    @Getter
    @Setter
    @ApiModel("UserResponse")
    public static class UserRes{
        @ApiModelProperty(name="User ID")
        String email;
        @ApiModelProperty(name="User Name")
        String userName;
        @ApiModelProperty(name="User Nickname")
        String userNickname;
        @ApiModelProperty(name="User Description")
        String userDescription;
        @ApiModelProperty(name="IsSubtitle")
        Boolean isSubtitle;
        @ApiModelProperty(name="IsCommand")
        Boolean isCommand;
        @ApiModelProperty(name="IsTts")
        Boolean isTTS;
        @ApiModelProperty(name="IsFaceFocusing")
        Boolean isFaceFocusing;
        @ApiModelProperty(name="Role")
        Role role;
        @ApiModelProperty(name="ProfileImage")
        String profileImage;

        public static UserRes of(User user) {
            UserRes res = new UserRes();
            res.setEmail(user.getEmail());
            res.setUserName(user.getUserName());
            res.setUserNickname(user.getUserNickname());
            res.setUserDescription(user.getUserDescription());
            res.setIsSubtitle(user.isSubtitle());
            res.setIsCommand(user.isCommand());
            res.setIsTTS(user.isTTS());
            res.setIsFaceFocusing(user.isFaceFocusing());
            res.setRole(user.getRole());
            res.setProfileImage(user.getProfileImage());
            return res;
        }
    }
}
