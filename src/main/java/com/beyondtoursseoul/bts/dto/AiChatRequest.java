package com.beyondtoursseoul.bts.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AiChatRequest {
    private String message;
    private String language;
}
