package com.aliensattack.core.ai.ollama;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Message model for Ollama chat API
 */
@Data
@Builder
public class OllamaMessage {
    
    @JsonProperty("role")
    private String role;
    
    @JsonProperty("content")
    private String content;
    
    @JsonProperty("images")
    private String[] images;
}


