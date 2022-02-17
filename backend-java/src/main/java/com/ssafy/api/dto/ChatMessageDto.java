package com.ssafy.api.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class ChatMessageDto {
    private String roomId;
    private String message;
    private String writer;
}
