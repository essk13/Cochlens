package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * 유저 모델 정의.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;
    @Column(name = "email", nullable = false)
    String email;
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false)
    String password;
    @Column(name = "user_name", nullable = false)
    String userName;
    @Column(name = "user_nickname", nullable = false)
    String userNickname;
    @Column(name = "profile_image")
    String profileImage;
    @Column(name = "thumbnail_image")
    String thumbnailImage;
    @Column(name = "user_description")
    String userDescription;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    Role role;

    @Column(name = "is_subtitle")
    boolean isSubtitle;
    @Column(name = "is_command")
    boolean isCommand;
    @Column(name = "is_face_focusing")
    boolean isFaceFocusing;

    @Builder
    public User(String email, String password, String userName, String profileImage, Role role) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.profileImage = profileImage;
        this.role = role;
    }

    public User update(String userName, String profileImage){
        this.userName = userName;
        this.profileImage = profileImage;

        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }
}
