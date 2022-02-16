package com.ssafy.websocket;

import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final SimpMessagingTemplate template;

    @MessageMapping("/chat/{lectureId}")
    public void sendMessage(ChatModel chatModel,
    @DestinationVariable String lectureId
    ) {
        log.debug(chatModel.toString());

        String webSocketDefault = "/topic/";
        String webSocketPath = webSocketDefault + lectureId;
        template.convertAndSend(webSocketPath, chatModel);
    }
}
