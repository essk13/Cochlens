package com.ssafy.api.dto;

import com.ssafy.db.entity.Lecture;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomDto {
    private Long roomId;
    private String name;

    public static ChatRoomDto of (Lecture lecture) {
        ChatRoomDto chatRoomDto = new ChatRoomDto();

        chatRoomDto.setRoomId(lecture.getLectureId());
        chatRoomDto.setName(lecture.getLectureName());

        return chatRoomDto;
    }
}
