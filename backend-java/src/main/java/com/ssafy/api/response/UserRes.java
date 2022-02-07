package com.ssafy.api.response;

import com.ssafy.db.entity.Role;
import com.ssafy.db.entity.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원 본인 정보 조회 API ([GET] /api/v1/users/me) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("UserResponse")
public class UserRes{
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
