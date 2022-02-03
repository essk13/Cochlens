package com.ssafy.common.auth.dto;

import com.ssafy.db.entity.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String nickname;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.picture = user.getPicture();
    }
}
