package com.aliensattack.core.ai.ollama;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Request model for Ollama chat completion API
 */
@Data
@Builder
public class OllamaChatRequest {
    
    @JsonProperty("model")
    private String model;
    
    @JsonProperty("messages")
    private List<OllamaMessage> messages;
    
    @JsonProperty("stream")
    private boolean stream;
    
    @JsonProperty("options")
    private OllamaOptions options;
    
    @JsonProperty("format")
    private String format;
    
    @JsonProperty("keep_alive")
    private String keepAlive;
    
    @JsonProperty("raw")
    private boolean raw;
    
    @JsonProperty("template")
    private String template;
    
    @JsonProperty("context")
    private List<Integer> context;
    
    @JsonProperty("system")
    private String system;
}


