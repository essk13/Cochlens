package com.ssafy.common.auth.dto;

import com.ssafy.db.entity.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String userName;
    private String email;
    private String userNickname;
    private String userDescription;
    private Boolean isSubtitle;
    private Boolean isCommand;
    private Boolean isSTT;
    private Boolean isFaceFocusing;
    private String profileImage;

    public SessionUser(User user) {
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.userNickname = user.getUserNickname();
        this.userDescription = user.getUserDescription();
        this.isSubtitle = user.isSubtitle();
        this.isCommand = user.isCommand();
        this.isSTT = user.isSTT();
        this.isFaceFocusing = user.isFaceFocusing();
        this.profileImage = user.getProfileImage();
    }
}
