package com.beyondtoursseoul.bts.service.translation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class GoogleTranslationService implements TranslationService {

    private static final String GOOGLE_TRANSLATE_URL =
            "https://translation.googleapis.com/language/translate/v2";

    private final RestClient restClient;

    @Value("${google.translation.api.key}")
    private String apiKey;

    public GoogleTranslationService() {
        this.restClient = RestClient.builder().baseUrl(GOOGLE_TRANSLATE_URL).build();
    }

    @Override
    public String translate(String text, String sourceLang, String targetLang) {
        if (text == null || text.isBlank()) return "";

        try {
            GoogleTranslationResponse response = restClient.get()
                    .uri(uriBuilder -> uriBuilder.queryParam("q", text)
                            .queryParam("source", sourceLang)
                            .queryParam("target", targetLang)
                            .queryParam("key", apiKey)
                            .build())
                    .retrieve().body(GoogleTranslationResponse.class);

            if (response != null && response.getData() != null && !response.getData().getTranslations().isEmpty()) {
                return response.getData().getTranslations().get(0).getTranslatedText();
            }
        } catch (Exception e) {
            log.info("번역 실패: {}", e.getMessage());
        }

        return text;
    }

    @Override
    public List<String> translateBatch(List<String> texts, String sourceLang, String targetLang) {
        if (texts == null || texts.isEmpty()) return List.of();

        try {
            Map<String, Object> requestBody = Map.of(
                    "q", texts,
                    "source", sourceLang,
                    "target", targetLang,
                    "format", "text"
            );

            GoogleTranslationResponse response = restClient.post()
                    .uri(uriBuilder ->
                            uriBuilder.queryParam("key", apiKey).build())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(requestBody)
                    .retrieve()
                    .body(GoogleTranslationResponse.class);

            if (response != null && response.getData() != null) {
                List<String> textList = response.getData()
                        .getTranslations()
                        .stream()
                        .map(translation -> translation.getTranslatedText())
                        .toList();
                return textList;
            }
        } catch (Exception e) {
            log.error("Google API 호출 실패: {}", e.getMessage());
        }
        
        // [수정] 실패 시 원본 대신 빈 리스트 반환하여 호출 측에서 감지하도록 함
        return List.of();
    }

    @Getter
    @NoArgsConstructor
    private static class GoogleTranslationResponse {
        private Data data;

        @Getter
        @NoArgsConstructor
        private static class Data {
            private List<Translation> translations;
        }

        @Getter
        @NoArgsConstructor
        private static class Translation {
            private String translatedText;
        }
    }
}
