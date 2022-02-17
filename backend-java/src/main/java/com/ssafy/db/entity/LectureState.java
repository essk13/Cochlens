package com.ssafy.db.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LectureState {
    BEFORE("LECTURE_BEFORE", "before"),
    LIVE("LECTURE_LIVE", "live"),
    VOD("LECTURE_VOD", "vod");

    private final String key;
    private final String title;
}
