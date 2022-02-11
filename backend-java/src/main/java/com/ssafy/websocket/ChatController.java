package com.ssafy.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final SimpMessagingTemplate template;

    @MessageMapping("/chat")
    public void sendMessage(ChatModel chatModel) {
        Long lectureId = chatModel.getLectureId();
        log.debug(chatModel.toString());
        template.convertAndSend("/topic/lecture", chatModel);
    }
}
