package com.beyondtoursseoul.bts.controller;

import com.beyondtoursseoul.bts.dto.AiChatRequest;
import com.beyondtoursseoul.bts.dto.AiChatResponse;
import com.beyondtoursseoul.bts.service.GroqChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AiChatController {

    private final GroqChatService groqChatService;

    @PostMapping("/chat")
    public AiChatResponse chat(@RequestBody AiChatRequest request) {
        return groqChatService.chat(request);
    }
}
