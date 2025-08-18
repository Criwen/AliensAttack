package com.aliensattack.core.ai.ollama;

import com.aliensattack.core.config.GameConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.log4j.Log4j2;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * HTTP client for communicating with Ollama API
 * Handles model inference, chat completions, and model management
 */
@Log4j2
public class OllamaApiClient {
    
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String baseUrl;
    private final int timeoutSeconds;
    private final int maxRetries;
    private final long retryDelayMs;
    
    public OllamaApiClient() {
        this.baseUrl = GameConfig.getString("ollama.api.base.url", "http://localhost:11434");
        this.timeoutSeconds = GameConfig.getInt("ollama.api.timeout.seconds", 30);
        this.maxRetries = GameConfig.getInt("ollama.api.max.retries", 3);
        this.retryDelayMs = GameConfig.getLong("ollama.api.retry.delay.ms", 1000);
        
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(timeoutSeconds, TimeUnit.SECONDS)
                .readTimeout(timeoutSeconds, TimeUnit.SECONDS)
                .writeTimeout(timeoutSeconds, TimeUnit.SECONDS)
                .build();
        
        this.objectMapper = new ObjectMapper();
    }
    
    /**
     * Generate text completion using Ollama
     */
    public CompletableFuture<String> generateCompletion(OllamaRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return executeWithRetry(() -> generateCompletionSync(request));
            } catch (Exception e) {
                log.error("Failed to generate completion: {}", e.getMessage());
                throw new RuntimeException("Ollama API call failed", e);
            }
        });
    }
    
    /**
     * Generate chat completion using Ollama
     */
    public CompletableFuture<String> generateChatCompletion(OllamaChatRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return executeWithRetry(() -> generateChatCompletionSync(request));
            } catch (Exception e) {
                log.error("Failed to generate chat completion: {}", e.getMessage());
                throw new RuntimeException("Ollama API call failed", e);
            }
        });
    }
    
    /**
     * Check if Ollama is available and responding
     */
    public boolean isAvailable() {
        try {
            Request request = new Request.Builder()
                    .url(baseUrl + "/api/tags")
                    .get()
                    .build();
            
            try (Response response = httpClient.newCall(request).execute()) {
                return response.isSuccessful();
            }
        } catch (Exception e) {
            log.debug("Ollama availability check failed: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Get available models from Ollama
     */
    public CompletableFuture<JsonNode> getAvailableModels() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return executeWithRetry(this::getAvailableModelsSync);
            } catch (Exception e) {
                log.error("Failed to get available models: {}", e.getMessage());
                throw new RuntimeException("Failed to get Ollama models", e);
            }
        });
    }
    
    /**
     * Synchronous completion generation
     */
    private String generateCompletionSync(OllamaRequest request) throws IOException {
        String jsonRequest = objectMapper.writeValueAsString(request);
        
        RequestBody body = RequestBody.create(
                jsonRequest, 
                MediaType.get("application/json; charset=utf-8")
        );
        
        Request httpRequest = new Request.Builder()
                .url(baseUrl + "/api/generate")
                .post(body)
                .build();
        
        try (Response response = httpClient.newCall(httpRequest).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("HTTP " + response.code() + ": " + response.message());
            }
            
            String responseBody = response.body().string();
            JsonNode jsonResponse = objectMapper.readTree(responseBody);
            
            return jsonResponse.get("response").asText();
        }
    }
    
    /**
     * Synchronous chat completion generation
     */
    private String generateChatCompletionSync(OllamaChatRequest request) throws IOException {
        String jsonRequest = objectMapper.writeValueAsString(request);
        
        RequestBody body = RequestBody.create(
                jsonRequest, 
                MediaType.get("application/json; charset=utf-8")
        );
        
        Request httpRequest = new Request.Builder()
                .url(baseUrl + "/api/chat")
                .post(body)
                .build();
        
        try (Response response = httpClient.newCall(httpRequest).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("HTTP " + response.code() + ": " + response.message());
            }
            
            String responseBody = response.body().string();
            JsonNode jsonResponse = objectMapper.readTree(responseBody);
            
            return jsonResponse.get("message").get("content").asText();
        }
    }
    
    /**
     * Synchronous model listing
     */
    private JsonNode getAvailableModelsSync() throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + "/api/tags")
                .get()
                .build();
        
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("HTTP " + response.code() + ": " + response.message());
            }
            
            String responseBody = response.body().string();
            return objectMapper.readTree(responseBody);
        }
    }
    
    /**
     * Execute operation with retry logic
     */
    private <T> T executeWithRetry(RetryableOperation<T> operation) throws Exception {
        Exception lastException = null;
        
        for (int attempt = 0; attempt <= maxRetries; attempt++) {
            try {
                return operation.execute();
            } catch (Exception e) {
                lastException = e;
                if (attempt < maxRetries) {
                    log.debug("Attempt {} failed, retrying in {} ms: {}", 
                            attempt + 1, retryDelayMs, e.getMessage());
                    Thread.sleep(retryDelayMs);
                }
            }
        }
        
        throw lastException;
    }
    
    /**
     * Functional interface for retryable operations
     */
    @FunctionalInterface
    private interface RetryableOperation<T> {
        T execute() throws Exception;
    }
    
    /**
     * Close HTTP client resources
     */
    public void close() {
        if (httpClient != null) {
            httpClient.dispatcher().executorService().shutdown();
            httpClient.connectionPool().evictAll();
        }
    }
}


