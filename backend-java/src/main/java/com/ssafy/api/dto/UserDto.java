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
        @ApiModelProperty(name="User Email", example="ssafy@ssafy.com")
        String email;
        @ApiModelProperty(name="User Name", example="ssafy")
        String userName;
        @ApiModelProperty(name="User Nickname", example="hotsix")
        String userNickname;
        @ApiModelProperty(name="User Description", example="notexisted")
        String userDescription;
        @ApiModelProperty(name="IsSubtitle", example="false")
        Boolean isSubtitle;
        @ApiModelProperty(name="IsCommand", example="false")
        Boolean isCommand;
        @ApiModelProperty(name="IsSTT", example="false")
        Boolean isSTT;
        @ApiModelProperty(name="IsFaceFocusing", example="false")
        Boolean isFaceFocusing;
        @ApiModelProperty(name="Role", example="User")
        Role role;
        @ApiModelProperty(name="ProfileImage", example="notexisted")
        String profileImage;
        @ApiModelProperty(name="ThumbnailImage", example="notexisted")
        String thumbnailImage;

        public static UserRes of(User user) {
            UserRes res = new UserRes();
            res.setEmail(user.getEmail());
            res.setUserName(user.getUserName());
            res.setUserNickname(user.getUserNickname());
            res.setUserDescription(user.getUserDescription());
            res.setIsSubtitle(user.isSubtitle());
            res.setIsCommand(user.isCommand());
            res.setIsSTT(user.isSTT());
            res.setIsFaceFocusing(user.isFaceFocusing());
            res.setRole(user.getRole());
            res.setProfileImage(user.getProfileImage());
            res.setThumbnailImage(user.getThumbnailImage());
            return res;
        }
    }
    /**
     * 회원 본인 정보 조회 API ([PUT] /api/v1/users/me) 요청에 대한 응답값 정의.
     */
    @Getter
    @Setter
    @ApiModel("UserPutResponse")
    public static class UserPutRes {
        @ApiModelProperty(name="User Email", example="ssafy@ssafy.com")
        String email;
        @ApiModelProperty(name="User Name", example="ssafy")
        String userName;
        @ApiModelProperty(name="User Nickname", example="hotsix")
        String userNickname;
        @ApiModelProperty(name="User Description", example="notexisted")
        String userDescription;
        @ApiModelProperty(name="IsSubtitle", example="false")
        Boolean isSubtitle;
        @ApiModelProperty(name="IsCommand", example="false")
        Boolean isCommand;
        @ApiModelProperty(name="IsSTT", example="false")
        Boolean isSTT;
        @ApiModelProperty(name="IsFaceFocusing", example="false")
        Boolean isFaceFocusing;
        @ApiModelProperty(name="Role", example="User")
        Role role;
        @ApiModelProperty(name="ProfileImage", example="notexisted")
        String profileImage;
        @ApiModelProperty(name="ThumbnailImage", example="notexisted")
        String thumbnailImage;
        @ApiModelProperty(name = "유저 Password", example = "your_password")
        String password;

        public static UserPutRes of(User user) {
            UserPutRes res = new UserPutRes();
            res.setEmail(user.getEmail());
            res.setUserName(user.getUserName());
            res.setUserNickname(user.getUserNickname());
            res.setUserDescription(user.getUserDescription());
            res.setIsSubtitle(user.isSubtitle());
            res.setIsCommand(user.isCommand());
            res.setIsSTT(user.isSTT());
            res.setIsFaceFocusing(user.isFaceFocusing());
            res.setRole(user.getRole());
            res.setProfileImage(user.getProfileImage());
            res.setThumbnailImage(user.getThumbnailImage());
            return res;
        }
    }
}
