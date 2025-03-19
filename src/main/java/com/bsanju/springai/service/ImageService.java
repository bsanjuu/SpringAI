package com.bsanju.springai.service;

import com.bsanju.springai.model.ImageResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class ImageService {

    @Value("${spring.ai.stability.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public ImageResponse generateImage(String prompt, int steps, int width, int height) {
        String url = "https://api.stability.ai/v2beta/stable-image/generate/sd3";

        // Construct form-data parameters
        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("prompt", prompt);
        requestBody.add("steps", steps);
        requestBody.add("width", width);
        requestBody.add("height", height);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", "Bearer " + apiKey);

        // Create the HTTP request
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> rawResponse = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);


            System.out.println("Raw Stability AI Response: " + rawResponse.getBody());

            ResponseEntity<ImageResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity, ImageResponse.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                System.err.println("Stability AI API returned unexpected status: " + response.getStatusCode());
                return null;
            }
        } catch (HttpClientErrorException e) {
            System.err.println("Stability AI API Error: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Unexpected Error: " + e.getMessage());
            return null;
        }

    }
}
