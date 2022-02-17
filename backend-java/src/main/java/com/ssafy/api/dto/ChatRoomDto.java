package com.ssafy.api.dto;

import com.ssafy.db.entity.Lecture;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ChatRoomDto {
    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public static ChatRoomDto of (Lecture lecture) {
        ChatRoomDto chatRoomDto = new ChatRoomDto();

        chatRoomDto.setRoomId(lecture.getLectureId().toString());
        chatRoomDto.setName(lecture.getLectureName());

        return chatRoomDto;
    }
}
