package com.aliensattack.core.ai.ollama;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Options for Ollama text generation
 */
@Data
@Builder
public class OllamaOptions {
    
    @JsonProperty("temperature")
    private Double temperature;
    
    @JsonProperty("top_p")
    private Double topP;
    
    @JsonProperty("top_k")
    private Integer topK;
    
    @JsonProperty("num_predict")
    private Integer numPredict;
    
    @JsonProperty("stop")
    private String[] stop;
    
    @JsonProperty("repeat_penalty")
    private Double repeatPenalty;
    
    @JsonProperty("seed")
    private Integer seed;
    
    @JsonProperty("num_ctx")
    private Integer numCtx;
    
    @JsonProperty("num_gpu")
    private Integer numGpu;
    
    @JsonProperty("num_thread")
    private Integer numThread;
}


