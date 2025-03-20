package com.bsanju.springai.controller;

import com.bsanju.springai.model.ImageResponse;
import com.bsanju.springai.service.ChatService;
import com.bsanju.springai.service.ImageService;
import com.bsanju.springai.service.RecipeService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class AiController {

    private final ChatService chatService;
    private final ImageService imageService;
    private final RecipeService recipeService;

    @Autowired
    public AiController(ChatService chatService, ImageService imageService, RecipeService recipeService) {
        this.chatService = chatService;
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("ask-ai")
    public String getResponse(@RequestParam String prompt) {
        return chatService.getResponse(prompt);
    }

    @GetMapping("ask-ai-options")
    public String getResponseOptions(@RequestParam String prompt) {
        return chatService.getResponseOptions(prompt);
    }

    @PostMapping("generate-image")
    public ResponseEntity<?> generateImages(@RequestParam String prompt,
                                            @RequestParam(defaultValue = "50") int steps,
                                            @RequestParam(defaultValue = "1024") int width,
                                            @RequestParam(defaultValue = "1024") int height) {
        try {
            ImageResponse imageResponse = imageService.generateImage(prompt, steps, width, height);

            if (imageResponse == null || imageResponse.getArtifacts() == null || imageResponse.getArtifacts().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error: Image generation failed. Check API key and endpoint.");
            }

            return ResponseEntity.ok(imageResponse.getArtifacts().stream()
                    .map(ImageResponse.Artifact::getUrl)
                    .toList());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }


    @GetMapping("recipe-creator")
    public String recipeCreator(@RequestParam String ingredients,
                                @RequestParam(defaultValue = "any") String cuisine,
                                @RequestParam(defaultValue = "") String dietaryRestriction) {
        return recipeService.createRecipe(ingredients, cuisine, dietaryRestriction);
    }
}
