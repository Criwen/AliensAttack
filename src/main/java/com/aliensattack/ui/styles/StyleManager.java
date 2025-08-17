package com.aliensattack.ui.styles;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Centralized style manager for AliensAttack UI
 * Handles style switching, caching, and provides factory methods for styled components
 */
public class StyleManager {
    
    private static final Logger log = LogManager.getLogger(StyleManager.class);
    
    @Getter
    private static final StyleManager instance = new StyleManager();
    
    private final Map<String, IStyle> availableStyles;
    private final Map<String, IStyle> styleCache;
    private IStyle currentStyle;
    
    private StyleManager() {
        availableStyles = new ConcurrentHashMap<>();
        styleCache = new ConcurrentHashMap<>();
        initializeStyles();
    }
    
    /**
     * Initialize all available styles
     */
    private void initializeStyles() {
        registerStyle(new SciFiStyle());
        
        // Set default style to Professional Sci-Fi
        currentStyle = availableStyles.get("scifi");
        log.info("Style manager initialized with {} styles", availableStyles.size());
    }
    
    /**
     * Register a new style
     * @param style the style to register
     */
    public void registerStyle(IStyle style) {
        availableStyles.put(style.getStyleId(), style);
        log.debug("Registered style: {}", style.getStyleId());
    }
    
    /**
     * Get all available styles
     * @return collection of available styles
     */
    public Collection<IStyle> getAvailableStyles() {
        return availableStyles.values();
    }
    
    /**
     * Get style by ID
     * @param styleId the style identifier
     * @return the style or null if not found
     */
    public IStyle getStyle(String styleId) {
        return availableStyles.get(styleId);
    }
    
    /**
     * Get current active style
     * @return current style
     */
    public IStyle getCurrentStyle() {
        return currentStyle;
    }
    
    /**
     * Switch to a different style
     * @param styleId the style identifier to switch to
     * @return true if switch was successful
     */
    public boolean switchStyle(String styleId) {
        IStyle newStyle = availableStyles.get(styleId);
        if (newStyle == null) {
            log.warn("Style not found: {}", styleId);
            return false;
        }
        
        IStyle oldStyle = currentStyle;
        currentStyle = newStyle;
        
        log.info("Switched from style '{}' to '{}'", 
            oldStyle != null ? oldStyle.getStyleId() : "none", 
            newStyle.getStyleId());
        
        return true;
    }
    
    /**
     * Apply current style to a component
     * @param component the component to style
     */
    public void applyCurrentStyle(Object component) {
        if (currentStyle == null) {
            log.warn("No current style set");
            return;
        }
        
        try {
            if (component instanceof javax.swing.JFrame) {
                currentStyle.applyToWindow((javax.swing.JFrame) component);
            } else if (component instanceof javax.swing.JPanel) {
                currentStyle.applyToPanel((javax.swing.JPanel) component);
            } else if (component instanceof javax.swing.JButton) {
                currentStyle.applyToButton((javax.swing.JButton) component);
            } else if (component instanceof javax.swing.JLabel) {
                currentStyle.applyToLabel((javax.swing.JLabel) component);
            } else if (component instanceof javax.swing.JTextArea) {
                currentStyle.applyToTextArea((javax.swing.JTextArea) component);
            } else if (component instanceof javax.swing.JTextField) {
                currentStyle.applyToTextField((javax.swing.JTextField) component);
            } else if (component instanceof javax.swing.JComboBox) {
                currentStyle.applyToComboBox((javax.swing.JComboBox<?>) component);
            } else {
                log.debug("Component type not supported for styling: {}", component.getClass().getSimpleName());
            }
        } catch (Exception e) {
            log.error("Error applying style to component: {}", e.getMessage(), e);
        }
    }
    
    /**
     * Apply current style to multiple components
     * @param components the components to style
     */
    public void applyCurrentStyleToAll(Object... components) {
        for (Object component : components) {
            applyCurrentStyle(component);
        }
    }
    
    /**
     * Get cached style or create new instance
     * @param styleId the style identifier
     * @return cached or new style instance
     */
    public IStyle getCachedStyle(String styleId) {
        return styleCache.computeIfAbsent(styleId, this::getStyle);
    }
    
    /**
     * Clear style cache
     */
    public void clearCache() {
        styleCache.clear();
        log.debug("Style cache cleared");
    }
    
    /**
     * Get style statistics
     * @return map with style statistics
     */
    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("availableStyles", availableStyles.size());
        stats.put("cachedStyles", styleCache.size());
        stats.put("currentStyle", currentStyle != null ? currentStyle.getStyleId() : "none");
        stats.put("cacheEnabled", true);
        return stats;
    }
}
