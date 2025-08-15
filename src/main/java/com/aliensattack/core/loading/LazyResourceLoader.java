package com.aliensattack.core.loading;

import lombok.extern.log4j.Log4j2;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Lazy loading system for game resources
 */
@Log4j2
public class LazyResourceLoader {
    
    private static final int DEFAULT_THREAD_POOL_SIZE = 4;
    private static final int DEFAULT_MAX_CONCURRENT_LOADS = 10;
    
    private final Map<String, LazyResource<?>> resources;
    private final ExecutorService executorService;
    private final int maxConcurrentLoads;
    private final AtomicInteger activeLoads;
    
    public LazyResourceLoader() {
        this(DEFAULT_THREAD_POOL_SIZE, DEFAULT_MAX_CONCURRENT_LOADS);
    }
    
    public LazyResourceLoader(int threadPoolSize, int maxConcurrentLoads) {
        this.resources = new ConcurrentHashMap<>();
        this.executorService = Executors.newFixedThreadPool(threadPoolSize);
        this.maxConcurrentLoads = maxConcurrentLoads;
        this.activeLoads = new AtomicInteger(0);
        
        log.info("LazyResourceLoader initialized with {} threads, max concurrent loads: {}", 
                threadPoolSize, maxConcurrentLoads);
    }
    
    /**
     * Register a lazy resource
     */
    public <T> void registerResource(String resourceId, Supplier<T> loader, ResourceType type) {
        LazyResource<T> resource = new LazyResource<>(resourceId, loader, type);
        resources.put(resourceId, resource);
        log.debug("Registered lazy resource: {} of type {}", resourceId, type);
    }
    
    /**
     * Get resource, loading it if necessary
     */
    @SuppressWarnings("unchecked")
    public <T> T getResource(String resourceId, Class<T> resourceType) {
        LazyResource<?> resource = resources.get(resourceId);
        if (resource == null) {
            log.warn("Resource not found: {}", resourceId);
            return null;
        }
        
        return (T) resource.getResource();
    }
    
    /**
     * Preload resource asynchronously
     */
    public <T> CompletableFuture<T> preloadResource(String resourceId, Class<T> resourceType) {
        LazyResource<?> resource = resources.get(resourceId);
        if (resource == null) {
            return CompletableFuture.failedFuture(
                new IllegalArgumentException("Resource not found: " + resourceId)
            );
        }
        
        if (activeLoads.get() >= maxConcurrentLoads) {
            log.debug("Max concurrent loads reached, queuing resource: {}", resourceId);
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(100); // Small delay to avoid overwhelming
                    return preloadResource(resourceId, resourceType).get();
                } catch (Exception e) {
                    throw new RuntimeException("Failed to preload resource: " + resourceId, e);
                }
            }, executorService);
        }
        
        activeLoads.incrementAndGet();
        
        return CompletableFuture.supplyAsync(() -> {
            try {
                T result = resourceType.cast(resource.getResource());
                log.debug("Preloaded resource: {}", resourceId);
                return result;
            } finally {
                activeLoads.decrementAndGet();
            }
        }, executorService);
    }
    
    /**
     * Preload multiple resources
     */
    public CompletableFuture<Void> preloadResources(String... resourceIds) {
        CompletableFuture<?>[] futures = new CompletableFuture[resourceIds.length];
        
        for (int i = 0; i < resourceIds.length; i++) {
            futures[i] = preloadResource(resourceIds[i], Object.class);
        }
        
        return CompletableFuture.allOf(futures);
    }
    
    /**
     * Check if resource is loaded
     */
    public boolean isResourceLoaded(String resourceId) {
        LazyResource<?> resource = resources.get(resourceId);
        return resource != null && resource.isLoaded();
    }
    
    /**
     * Get loading statistics
     */
    public LoadingStatistics getStatistics() {
        int totalResources = resources.size();
        int loadedResources = (int) resources.values().stream()
            .filter(LazyResource::isLoaded)
            .count();
        int pendingResources = totalResources - loadedResources;
        
        return new LoadingStatistics(
            totalResources,
            loadedResources,
            pendingResources,
            activeLoads.get(),
            maxConcurrentLoads
        );
    }
    
    /**
     * Clear all resources
     */
    public void clear() {
        int size = resources.size();
        resources.clear();
        log.info("Cleared {} lazy resources", size);
    }
    
    /**
     * Shutdown the loader
     */
    public void shutdown() {
        executorService.shutdown();
        log.info("LazyResourceLoader shutdown");
    }
    
    @Data
    public static class LoadingStatistics {
        private final int totalResources;
        private final int loadedResources;
        private final int pendingResources;
        private final int activeLoads;
        private final int maxConcurrentLoads;
        
        public double getLoadingProgress() {
            return totalResources > 0 ? (double) loadedResources / totalResources : 0.0;
        }
        
        public double getConcurrencyUsage() {
            return (double) activeLoads / maxConcurrentLoads;
        }
    }
    
    public enum ResourceType {
        TEXTURE,
        MODEL,
        SOUND,
        CONFIGURATION,
        AI_BEHAVIOR,
        COMBAT_DATA,
        MISSION_DATA
    }
    
    /**
     * Internal class representing a lazy resource
     */
    private static class LazyResource<T> {
        private final String resourceId;
        private final Supplier<T> loader;
        private final ResourceType type;
        private volatile T resource;
        private volatile boolean loaded = false;
        private volatile long loadTime = 0;
        
        public LazyResource(String resourceId, Supplier<T> loader, ResourceType type) {
            this.resourceId = resourceId;
            this.loader = loader;
            this.type = type;
        }
        
        public synchronized T getResource() {
            if (!loaded) {
                long startTime = System.currentTimeMillis();
                try {
                    resource = loader.get();
                    loaded = true;
                    loadTime = System.currentTimeMillis() - startTime;
                    log.debug("Loaded resource {} in {}ms", resourceId, loadTime);
                } catch (Exception e) {
                    log.error("Failed to load resource: {}", resourceId, e);
                    throw new RuntimeException("Failed to load resource: " + resourceId, e);
                }
            }
            return resource;
        }
        
        public boolean isLoaded() {
            return loaded;
        }
        
        public long getLoadTime() {
            return loadTime;
        }
    }
}
