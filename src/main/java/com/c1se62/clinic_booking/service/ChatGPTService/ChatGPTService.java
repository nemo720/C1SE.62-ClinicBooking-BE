package com.c1se62.clinic_booking.service.ChatGPTService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Service
public class ChatGPTService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getChatGPTResponse(String prompt) {
        String apiUrl = "https://api.openai.com/v1/chat/completions";

        // Tạo dữ liệu yêu cầu
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", new Object[]{
                Map.of("role", "user", "content", prompt)
        });

        // Tạo header cho yêu cầu
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // Tạo đối tượng HttpEntity
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // Gửi yêu cầu POST đến API của OpenAI
        Map<String, Object> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, Map.class).getBody();

        // Kiểm tra và trả về phản hồi
        if (response != null && response.containsKey("choices")) {
            Map<String, Object> choice = (Map<String, Object>) ((List<?>) response.get("choices")).get(0);
            return (String) ((Map<String, Object>) choice.get("message")).get("content");
        } else {
            return "Không nhận được phản hồi từ ChatGPT.";
        }
    }
}
