package com.ssafy.api.dto;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Role;
import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class UserDto {
    /**
     * Request
     */

    /**
     * 유저 회원가입 API ([POST] /api/v1/users) 요청에 필요한 리퀘스트 바디 정의.
     */
    @Getter
    @Setter
    @ApiModel("UserRegisterPostRequest")
    public static class UserRegisterPostReq {
        @ApiModelProperty(name="유저 email", example="ssafy@ssafy.com")
        String email;
        @ApiModelProperty(name="유저 Password", example="your_password")
        String password;
        @ApiModelProperty(name="이름", example="ssafy")
        String userName;
        @ApiModelProperty(name="닉네임", example="hotsix")
        String userNickname;
    }

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

    @Getter
    @Setter
    @ApiModel("UserPutRes")
    public static class UserPutReq {
        @ApiModelProperty(name="User Email", example="ssafy@ssafy.com")
        String email;
        @ApiModelProperty(name="User Name", example="ssafy")
        String userName;
        @ApiModelProperty(name="User Nickname", example="hotsix")
        String userNickname;
        @ApiModelProperty(name="ProfileImage", example="notexisted")
        String profileImage;
        @ApiModelProperty(name="ThumbnailImage", example="notexisted")
        String thumbnailImage;
        @ApiModelProperty(name="User Description", example="notexisted")
        String userDescription;

        public static UserPutReq of(User user) {
            UserPutReq res = new UserPutReq();
            res.setEmail(user.getEmail());
            res.setUserName(user.getUserName());
            res.setUserNickname(user.getUserNickname());
            res.setProfileImage(user.getProfileImage());
            res.setThumbnailImage(user.getThumbnailImage());
            res.setUserDescription(user.getUserDescription());

            return res;
        }
    }

    @Getter
    @Setter
    @ApiModel("UserAccessPutReq")
    public static class UserAccessPutReq {
        @ApiModelProperty(name="IsSubtitle", example="false")
        Boolean isSubtitle;
        @ApiModelProperty(name="IsCommand", example="false")
        Boolean isCommand;
        @ApiModelProperty(name="IsFaceFocusing", example="false")
        Boolean isFaceFocusing;

        public static UserAccessPutReq of(User user) {
            UserAccessPutReq res = new UserAccessPutReq();
            res.setIsSubtitle(user.isSubtitle());
            res.setIsCommand(user.isCommand());
            res.setIsFaceFocusing(user.isFaceFocusing());

            return res;
        }
    }

    /**
     * Response
     */

    /**
     * 유저 로그인 API ([POST] /api/v1/auth) 요청에 대한 응답값 정의.
     */
    @Getter
    @Setter
    @ApiModel("UserLoginPostRes")
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
    @ApiModel("UserRes")
    public static class UserRes{
        @ApiModelProperty(name="User Id", example="1")
        Long userId;
        @ApiModelProperty(name="User Email", example="ssafy@ssafy.com")
        String email;
        @ApiModelProperty(name="User Name", example="ssafy")
        String userName;
        @ApiModelProperty(name="User Nickname", example="hotsix")
        String userNickname;
        @ApiModelProperty(name="ProfileImage", example="notexisted")
        String profileImage;
        @ApiModelProperty(name="IsSubtitle", example="false")
        Boolean isSubtitle;
        @ApiModelProperty(name="IsCommand", example="false")
        Boolean isCommand;
        @ApiModelProperty(name="IsFaceFocusing", example="false")
        Boolean isFaceFocusing;
        @ApiModelProperty(name="Role", example="User")
        Role role;

        public static UserRes of(User user) {
            UserRes res = new UserRes();
            res.setUserId(user.getUserId());
            res.setEmail(user.getEmail());
            res.setUserName(user.getUserName());
            res.setUserNickname(user.getUserNickname());
            res.setProfileImage(user.getProfileImage());
            res.setIsSubtitle(user.isSubtitle());
            res.setIsCommand(user.isCommand());
            res.setIsFaceFocusing(user.isFaceFocusing());
            res.setRole(user.getRole());

            return res;
        }
    }

    @Getter
    @Setter
    @ApiModel("UserProfileRes")
    public static class UserProfileRes {
        @ApiModelProperty(name="Thumbnail Image")
        String thumbnailImage;
        @ApiModelProperty(name="register course list")
        List<CourseDto.CourseListRes> registerCourseList;
        @ApiModelProperty(name="wish course list")
        List<CourseDto.CourseListRes> wishCourseList;

        public static UserProfileRes of(
                String thumbnailImage,
                List<CourseDto.CourseListRes> registerCourseList,
                List<CourseDto.CourseListRes> wishCourseList) {
            UserProfileRes res = new UserProfileRes();
            res.setThumbnailImage(thumbnailImage);
            res.setRegisterCourseList(registerCourseList);
            res.setWishCourseList(wishCourseList);

            return res;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("UserInstructorRes")
    public static class UserInstructorRes {
        @ApiModelProperty(name="User Id", example="1")
        Long userId;
        @ApiModelProperty(name="User Email", example="ssafy@ssafy.com")
        String email;
        @ApiModelProperty(name="User Name", example="ssafy")
        String userName;
        @ApiModelProperty(name="User Nickname", example="hotsix")
        String userNickname;
        @ApiModelProperty(name="ProfileImage", example="notexisted")
        String profileImage;
        @ApiModelProperty(name="Course Count", example="1")
        long courseCount;
        @ApiModelProperty(name="Course Review Count", example="1")
        int courseReviewCount;
        @ApiModelProperty(name="Course Reivew RateAvg", example="4.1")
        double courseReviewRateAverage;

        public static UserInstructorRes of(User user, int courseCount, int courseReviewCount, double courseReviewRateAverage) {
            UserInstructorRes res = new UserInstructorRes();

            res.setUserId(user.getUserId());
            res.setUserName(user.getUserName());
            res.setEmail(user.getEmail());
            res.setUserNickname(user.getUserNickname());
            res.setProfileImage(user.getProfileImage());
            res.setCourseCount(courseCount);
            res.setCourseReviewCount(courseReviewCount);
            res.setCourseReviewRateAverage(courseReviewRateAverage);

            return res;
        }
    }

    @Getter
    @Setter
    @ApiModel("InstructorDetailRes")
    public static class InstructorDetailRes{
        @ApiModelProperty(name="User Id", example="1")
        Long userId;
        @ApiModelProperty(name="User Email", example="ssafy@ssafy.com")
        String email;
        @ApiModelProperty(name="User Name", example="ssafy")
        String userName;
        @ApiModelProperty(name="User Nickname", example="hotsix")
        String userNickname;
        @ApiModelProperty(name="User Description", example="notexisted")
        String userDescription;
        @ApiModelProperty(name="ProfileImage", example="notexisted")
        String profileImage;
        @ApiModelProperty(name="ThumbnailImage", example="notexisted")
        String thumbnailImage;

        @ApiModelProperty(name="courseCount", example="10")
        long courseCount;
        @ApiModelProperty(name="courseReviewCount", example="10")
        int courseReviewCount;
        @ApiModelProperty(name="courseReviewRateAverage", example="4.88")
        Double courseReviewRateAverage;

        @ApiModelProperty(name="courseReviewList", example="course_review_list")
        List<ReviewDto.ReviewListRes> courseReviewList;
        @ApiModelProperty(name="liveOpenCourseList", example="live_open_course_list")
        List<CourseDto.CourseListRes> liveOpenCourseList;
        @ApiModelProperty(name="vodOpenCourseList", example="vod_open_course_list")
        List<CourseDto.CourseListRes> vodOpenCourseList;


        public static InstructorDetailRes of(
                User user, long courseCount, int courseReviewCount, double courseReviewRateAverage,
                List<ReviewDto.ReviewListRes> courseReviewList, List<CourseDto.CourseListRes> liveOpenCourseList, List<CourseDto.CourseListRes> vodOpenCourseList) {
            InstructorDetailRes res = new InstructorDetailRes();

            res.setUserId(user.getUserId());
            res.setEmail(user.getEmail());
            res.setUserName(user.getUserName());
            res.setUserNickname(user.getUserNickname());
            res.setUserDescription(user.getUserDescription());
            res.setProfileImage(user.getProfileImage());
            res.setThumbnailImage(user.getThumbnailImage());

            res.setCourseCount(courseCount);
            res.setCourseReviewCount(courseReviewCount);
            res.setCourseReviewRateAverage(courseReviewRateAverage);

            res.setCourseReviewList(courseReviewList);
            res.setLiveOpenCourseList(liveOpenCourseList);
            res.setVodOpenCourseList(vodOpenCourseList);

            return res;
        }
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("UserReviewRes")
    public static class UserReviewRes {
        @ApiModelProperty(name="User Id")
        Long userId;
        @ApiModelProperty(name="User Name")
        String userName;
        @ApiModelProperty(name="User Nickname")
        String userNickname;
        @ApiModelProperty(name="ProfileImage")
        String profileImage;

        public static UserReviewRes of(User user) {
            UserReviewRes res = new UserReviewRes();
            res.setUserId(user.getUserId());
            res.setUserName(user.getUserName());
            res.setUserNickname(user.getUserNickname());
            res.setProfileImage(user.getProfileImage());

            return res;
        }
    }
}
