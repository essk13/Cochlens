package com.ssafy.api.controller;

import com.ssafy.api.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessageSendingOperations template;
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    @MessageMapping(value = "/chat/enter")
    public void enter(@DestinationVariable ChatMessageDto message) {
        log.info(message.toString());
        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "/chat/message")
    public void message(@DestinationVariable ChatMessageDto message) {
        log.info(message.toString());
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}
