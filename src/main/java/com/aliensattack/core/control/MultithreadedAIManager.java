package com.aliensattack.core.control;

import com.aliensattack.core.interfaces.IBrain;
import com.aliensattack.core.model.GameContext;
import com.aliensattack.actions.interfaces.IAction;
import lombok.extern.log4j.Log4j2;
import lombok.Data;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Multithreaded AI manager for concurrent AI processing
 */
@Log4j2
public class MultithreadedAIManager {
    
    private static final int DEFAULT_THREAD_POOL_SIZE = 4;
    private static final int DEFAULT_MAX_CONCURRENT_AI = 8;
    private static final long DEFAULT_AI_TIMEOUT_MS = 5000;
    
    private final ExecutorService aiExecutor;
    private final int maxConcurrentAI;
    private final long aiTimeoutMs;
    private final AtomicInteger activeAICount;
    
    // Performance metrics
    private final AtomicInteger totalAIDecisions = new AtomicInteger(0);
    private final AtomicInteger successfulDecisions = new AtomicInteger(0);
    private final AtomicInteger failedDecisions = new AtomicInteger(0);
    private final AtomicInteger timeoutDecisions = new AtomicInteger(0);
    
    public MultithreadedAIManager() {
        this(DEFAULT_THREAD_POOL_SIZE, DEFAULT_MAX_CONCURRENT_AI, DEFAULT_AI_TIMEOUT_MS);
    }
    
    public MultithreadedAIManager(int threadPoolSize, int maxConcurrentAI, long aiTimeoutMs) {
        this.aiExecutor = Executors.newFixedThreadPool(threadPoolSize);
        this.maxConcurrentAI = maxConcurrentAI;
        this.aiTimeoutMs = aiTimeoutMs;
        this.activeAICount = new AtomicInteger(0);
        
        log.info("MultithreadedAIManager initialized with {} threads, max concurrent AI: {}, timeout: {}ms", 
                threadPoolSize, maxConcurrentAI, aiTimeoutMs);
    }
    
    /**
     * Process AI decisions for multiple units concurrently
     */
    public List<AIDecisionResult> processAIDecisions(List<IBrain> aiBrains, GameContext context) {
        if (aiBrains.isEmpty()) {
            return new ArrayList<>();
        }
        
        log.debug("Processing AI decisions for {} units", aiBrains.size());
        
        // Limit concurrent AI processing
        int maxConcurrent = Math.min(maxConcurrentAI, aiBrains.size());
        Semaphore semaphore = new Semaphore(maxConcurrent);
        
        List<CompletableFuture<AIDecisionResult>> futures = aiBrains.stream()
            .map(brain -> processAIDecisionAsync(brain, context, semaphore))
            .collect(Collectors.toList());
        
        // Wait for all AI decisions to complete
        try {
            CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0])
            );
            
            // Wait with timeout
            allFutures.get(aiTimeoutMs * 2, TimeUnit.MILLISECONDS);
            
        } catch (TimeoutException e) {
            log.warn("AI decision processing timed out after {}ms", aiTimeoutMs * 2);
        } catch (Exception e) {
            log.error("Error during AI decision processing", e);
        }
        
        // Collect results
        List<AIDecisionResult> results = new ArrayList<>();
        for (CompletableFuture<AIDecisionResult> future : futures) {
            try {
                if (future.isDone()) {
                    AIDecisionResult result = future.get();
                    results.add(result);
                } else {
                    // Handle incomplete futures
                    results.add(new AIDecisionResult(null, AIDecisionStatus.TIMEOUT, "Decision timed out"));
                    timeoutDecisions.incrementAndGet();
                }
            } catch (Exception e) {
                log.error("Error getting AI decision result", e);
                results.add(new AIDecisionResult(null, AIDecisionStatus.FAILED, e.getMessage()));
                failedDecisions.incrementAndGet();
            }
        }
        
        totalAIDecisions.addAndGet(results.size());
        log.debug("Completed AI decisions: {}/{} successful", 
                results.stream().filter(r -> r.getStatus() == AIDecisionStatus.SUCCESS).count(),
                results.size());
        
        return results;
    }
    
    /**
     * Process single AI decision asynchronously
     */
    private CompletableFuture<AIDecisionResult> processAIDecisionAsync(
            IBrain brain, GameContext context, Semaphore semaphore) {
        
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Acquire semaphore to limit concurrency
                if (!semaphore.tryAcquire(aiTimeoutMs, TimeUnit.MILLISECONDS)) {
                    return new AIDecisionResult(brain, AIDecisionStatus.TIMEOUT, "Semaphore acquisition timeout");
                }
                
                try {
                    activeAICount.incrementAndGet();
                    
                    // Process AI decision with timeout
                    CompletableFuture<Optional<IAction>> decisionFuture = CompletableFuture.supplyAsync(() -> {
                        try {
                            return brain.selectAction(context);
                        } catch (Exception e) {
                            log.error("Error in AI decision for brain: {}", brain.getBrainId(), e);
                            throw new RuntimeException(e);
                        }
                    }, aiExecutor);
                    
                    Optional<IAction> actionOpt = decisionFuture.get(aiTimeoutMs, TimeUnit.MILLISECONDS);
                    
                    if (actionOpt.isPresent()) {
                        IAction action = actionOpt.get();
                        successfulDecisions.incrementAndGet();
                        return new AIDecisionResult(brain, AIDecisionStatus.SUCCESS, "Decision successful", action);
                    } else {
                        failedDecisions.incrementAndGet();
                        return new AIDecisionResult(brain, AIDecisionStatus.FAILED, "No action decided");
                    }
                    
                } finally {
                    activeAICount.decrementAndGet();
                }
                
            } catch (TimeoutException e) {
                timeoutDecisions.incrementAndGet();
                return new AIDecisionResult(brain, AIDecisionStatus.TIMEOUT, "AI decision timeout");
            } catch (Exception e) {
                failedDecisions.incrementAndGet();
                log.error("Error processing AI decision for brain: {}", brain.getBrainId(), e);
                return new AIDecisionResult(brain, AIDecisionStatus.FAILED, e.getMessage());
            } finally {
                semaphore.release();
            }
        }, aiExecutor);
    }
    
    /**
     * Process AI decisions in batches
     */
    public List<AIDecisionResult> processAIDecisionsInBatches(
            List<IBrain> aiBrains, GameContext context, int batchSize) {
        
        List<AIDecisionResult> allResults = new ArrayList<>();
        
        for (int i = 0; i < aiBrains.size(); i += batchSize) {
            int endIndex = Math.min(i + batchSize, aiBrains.size());
            List<IBrain> batch = aiBrains.subList(i, endIndex);
            
            log.debug("Processing AI batch {}/{} with {} units", 
                    (i / batchSize) + 1, (aiBrains.size() + batchSize - 1) / batchSize, batch.size());
            
            List<AIDecisionResult> batchResults = processAIDecisions(batch, context);
            allResults.addAll(batchResults);
            
            // Small delay between batches to prevent overwhelming
            if (endIndex < aiBrains.size()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
        
        return allResults;
    }
    
    /**
     * Get AI processing statistics
     */
    public AIProcessingStatistics getStatistics() {
        return new AIProcessingStatistics(
            totalAIDecisions.get(),
            successfulDecisions.get(),
            failedDecisions.get(),
            timeoutDecisions.get(),
            activeAICount.get(),
            maxConcurrentAI
        );
    }
    
    /**
     * Shutdown the AI manager
     */
    public void shutdown() {
        aiExecutor.shutdown();
        try {
            if (!aiExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                aiExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            aiExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        log.info("MultithreadedAIManager shutdown");
    }
    
    @Data
    public static class AIDecisionResult {
        private final IBrain brain;
        private final AIDecisionStatus status;
        private final String message;
        private final IAction action;
        
        public AIDecisionResult(IBrain brain, AIDecisionStatus status, String message) {
            this(brain, status, message, null);
        }
        
        public AIDecisionResult(IBrain brain, AIDecisionStatus status, String message, IAction action) {
            this.brain = brain;
            this.status = status;
            this.message = message;
            this.action = action;
        }
        
        public boolean isSuccessful() {
            return status == AIDecisionStatus.SUCCESS;
        }
    }
    
    public enum AIDecisionStatus {
        SUCCESS,
        FAILED,
        TIMEOUT
    }
    
    @Data
    public static class AIProcessingStatistics {
        private final int totalDecisions;
        private final int successfulDecisions;
        private final int failedDecisions;
        private final int timeoutDecisions;
        private final int activeAI;
        private final int maxConcurrentAI;
        
        public double getSuccessRate() {
            return totalDecisions > 0 ? (double) successfulDecisions / totalDecisions : 0.0;
        }
        
        public double getTimeoutRate() {
            return totalDecisions > 0 ? (double) timeoutDecisions / totalDecisions : 0.0;
        }
        
        public double getConcurrencyUsage() {
            return (double) activeAI / maxConcurrentAI;
        }
    }
}
