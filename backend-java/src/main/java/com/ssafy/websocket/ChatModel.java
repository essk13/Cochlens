package com.ssafy.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class ChatModel {
    // 강의 Id
    private Long lectureId;

    // 수강생(강사) 이름
    private String userName;

    // 채팅 내용
    private String content;
}
