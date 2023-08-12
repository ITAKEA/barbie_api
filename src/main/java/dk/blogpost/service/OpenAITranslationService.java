package dk.blogpost.service;

import dk.blogpost.dto.TranslatedPostDTO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenAITranslationService implements TranslationService {

    @Value("${OPENAI_API_ENDPOINT:}")
    private String OPENAI_API_ENDPOINT;

    @Value("${API_KEY:}")
    private String API_KEY;

    @Override
    public TranslatedPostDTO translatePostToEnglish(String title, String content) {
        String translatedTitle = translateUsingOpenAI("Translate the following Danish title to English: " + title);
        String translatedContent = translateUsingOpenAI("Translate the following Danish text to English: " + content);

        TranslatedPostDTO result = new TranslatedPostDTO();
        result.setTranslatedTitle(translatedTitle);
        result.setTranslatedContent(translatedContent);

        return result;
    }


    private String translateUsingOpenAI(String prompt) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_KEY);
        headers.set("Content-Type", "application/json");

        JSONObject requestBody = new JSONObject();
        requestBody.put("prompt", prompt);
        requestBody.put("max_tokens", 150);

        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(OPENAI_API_ENDPOINT, request, String.class);

        JSONObject jsonResponse = new JSONObject(response.getBody());
        return jsonResponse.getJSONArray("choices").getJSONObject(0).getString("text").trim();
    }

    @Override
    public String translateToEnglish(String content) {
        return translateUsingOpenAI("Translate the following Danish text to English: " + content);
    }
}

