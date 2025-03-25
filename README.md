Complete Guide 
https://bsanju.medium.com/implementing-spring-ai-26e4b93292df

To integrate AI capabilities into your Spring Boot application using Spring AI, follow these steps:

**1. Create a New Spring Boot Project**

- Use [Spring Initializr](https://start.spring.io/) to generate a new project.
- Add the following dependencies:
  - Spring Web
  - Spring AI OpenAI Starter

**2. Add Your OpenAI API Key**

In your `application.properties` file, include your OpenAI API key:


```properties
spring.ai.openai.api-key=your_openai_api_key
```


**3. Implement the AI Service**

Create a service to interact with the AI model:


```java
package com.example.service;

import org.springframework.ai.chat.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final ChatClient chatClient;

    public ChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String getResponse(String prompt) {
        return chatClient.call(prompt);
    }
}
```


**4. Develop a REST Controller**

Set up a REST endpoint to handle AI interactions:


```java
package com.example.controller;

import com.example.service.ChatService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiController {
    private final ChatService chatService;

    public AiController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/chat")
    public String chat(@RequestBody String prompt) {
        return chatService.getResponse(prompt);
    }
}
```


**5. Run the Application**

- Start your Spring Boot application.
- Send a POST request to `http://localhost:8080/api/ai/chat` with your prompt in the request body.

This setup enables your application to process prompts and receive AI-generated responses via the configured REST endpoint. 

