package com.ssafy.api.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ChatMessageDto {
    private String roomId;
    private String message;
    private String writer;
}
