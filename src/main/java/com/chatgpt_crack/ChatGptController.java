package com.chatgpt_crack;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.ui.Model;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
@CrossOrigin
public class ChatGptController {
    
    private static final Logger log = LoggerFactory.getLogger(ChatGptController.class);
    private final ChatClient chatClient;

    public ChatGptController(ChatClient.Builder builder){
        this.chatClient = builder.build();
    }

    @GetMapping("")
    public String home() {
        return "index";
    }

    @HxRequest
    @PostMapping("/api/chat")
    public HtmxResponse generate(@RequestParam String message, Model model) {
        
        log.info("User message: {}", message);
       String response = chatClient.prompt()
                        .user(message)
                        .call()
                        .content();
        model.addAttribute("response",response);
        model.addAttribute("message",message);
       
        return HtmxResponse.builder()
                            .view("viewName :: responseFragment")
                            .view("recent-message-list :: messageFragment")
                            .build();
    }//end of method
    

    
}//end of class
