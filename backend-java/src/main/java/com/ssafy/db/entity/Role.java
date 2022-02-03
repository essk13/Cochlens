package com.ssafy.db.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER", "사용자"),
    INSTRUCTOR("ROLE_INSTRUCTOR", "강사"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String key;
    private final String title;
}
