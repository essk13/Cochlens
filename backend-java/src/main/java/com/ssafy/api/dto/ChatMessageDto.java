package com.ssafy.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDto {
    private String lectureId;
    private String writer;
    private String message;
}
