package com.ssafy.api.controller;

import com.ssafy.api.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate template;

    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageDto message){
        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
        template.convertAndSend("/sub/chat/room/" + message.getLectureId(), message);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDto message){
        template.convertAndSend("/sub/chat/room/" + message.getLectureId(), message);
    }
}
