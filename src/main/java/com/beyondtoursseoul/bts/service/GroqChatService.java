package com.beyondtoursseoul.bts.service;

import com.beyondtoursseoul.bts.dto.AiChatRequest;
import com.beyondtoursseoul.bts.dto.AiChatResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class GroqChatService {

    private final RestClient restClient;

    @Value("${groq.api.key:}")
    private String apiKey;

    @Value("${groq.api.model:llama-3.1-8b-instant}")
    private String model;

    public GroqChatService(@Value("${groq.api.base-url:https://api.groq.com/openai/v1}") String baseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public AiChatResponse chat(AiChatRequest request) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("GROQ_API_KEY가 설정되어 있지 않습니다.");
        }

        if (request == null || request.getMessage() == null || request.getMessage().isBlank()) {
            throw new IllegalStateException("message는 필수입니다.");
        }

        GroqChatResponse response = restClient.post()
                .uri("/chat/completions")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + apiKey)
                .body(createRequestBody(request))
                .retrieve()
                .body(GroqChatResponse.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()
                || response.getChoices().get(0).getMessage() == null) {
            throw new IllegalStateException("Groq 응답이 올바르지 않습니다.");
        }

        return new AiChatResponse(response.getChoices().get(0).getMessage().getContent(), model);
    }

    private Map<String, Object> createRequestBody(AiChatRequest request) {
        String language = request.getLanguage() == null || request.getLanguage().isBlank()
                ? "ko"
                : request.getLanguage();

        return Map.of(
                "model", model,
                "temperature", 0.7,
                "max_tokens", 800,
                "messages", List.of(
                        Map.of(
                                "role", "system",
                                "content", createSystemPrompt(language)
                        ),
                        Map.of(
                                "role", "user",
                                "content", request.getMessage()
                        )
                )
        );
    }

    private String createSystemPrompt(String language) {
        return """
                You are Beyond Tours Seoul's AI travel assistant.
                Help foreign visitors explore Seoul with practical, friendly, and accurate recommendations.
                Answer in the requested language: %s.
                If you are not sure about real-time availability, tell the user to check official sources.
                """.formatted(language);
    }

    @Getter
    @NoArgsConstructor
    private static class GroqChatResponse {
        private List<Choice> choices;
    }

    @Getter
    @NoArgsConstructor
    private static class Choice {
        private Message message;
    }

    @Getter
    @NoArgsConstructor
    private static class Message {
        private String role;

        @JsonProperty("content")
        private String content;
    }
}
