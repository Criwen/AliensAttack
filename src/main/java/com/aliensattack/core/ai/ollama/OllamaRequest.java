package com.aliensattack.core.ai.ollama;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Request model for Ollama text generation API
 */
@Data
@Builder
public class OllamaRequest {
    
    @JsonProperty("model")
    private String model;
    
    @JsonProperty("prompt")
    private String prompt;
    
    @JsonProperty("stream")
    private boolean stream;
    
    @JsonProperty("options")
    private OllamaOptions options;
    
    @JsonProperty("format")
    private String format;
    
    @JsonProperty("keep_alive")
    private String keepAlive;
    
    @JsonProperty("images")
    private List<String> images;
    
    @JsonProperty("raw")
    private boolean raw;
    
    @JsonProperty("template")
    private String template;
    
    @JsonProperty("context")
    private List<Integer> context;
    
    @JsonProperty("system")
    private String system;
    
    @JsonProperty("mirostat")
    private Integer mirostat;
    
    @JsonProperty("mirostat_tau")
    private Double mirostatTau;
    
    @JsonProperty("mirostat_eta")
    private Double mirostatEta;
    
    @JsonProperty("num_ctx")
    private Integer numCtx;
    
    @JsonProperty("num_gqa")
    private Integer numGqa;
    
    @JsonProperty("num_gpu")
    private Integer numGpu;
    
    @JsonProperty("num_thread")
    private Integer numThread;
    
    @JsonProperty("repeat_last_n")
    private Integer repeatLastN;
    
    @JsonProperty("repeat_penalty")
    private Double repeatPenalty;
    
    @JsonProperty("seed")
    private Integer seed;
    
    @JsonProperty("stop")
    private List<String> stop;
    
    @JsonProperty("tfs_z")
    private Double tfsZ;
    
    @JsonProperty("num_predict")
    private Integer numPredict;
    
    @JsonProperty("top_k")
    private Integer topK;
    
    @JsonProperty("top_p")
    private Double topP;
    
    @JsonProperty("temperature")
    private Double temperature;
}
