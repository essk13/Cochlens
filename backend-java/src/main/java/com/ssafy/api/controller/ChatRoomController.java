package com.ssafy.api.controller;

import com.ssafy.api.dto.ChatRoomDto;
import com.ssafy.api.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
public class ChatRoomController {
    private final LectureService lectureService;

    /**
     * 강좌 조회
     */
    @GetMapping(value = "/room")
    public ChatRoomDto getLectureRoom(@RequestParam Long lectureId) {
        return lectureService.findLectureById(lectureId);
    }
}
