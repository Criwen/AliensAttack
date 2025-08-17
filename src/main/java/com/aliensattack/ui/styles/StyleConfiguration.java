package com.aliensattack.ui.styles;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration loader for UI styles
 * Loads style settings from properties files and provides easy access to configuration values
 */
public class StyleConfiguration {
    
    private static final Logger log = LogManager.getLogger(StyleConfiguration.class);
    
    private static final String UI_PROPERTIES_FILE = "ui.properties";
    private static final String STYLE_PROPERTIES_FILE = "style.properties";
    
    @Getter
    private static final StyleConfiguration instance = new StyleConfiguration();
    
    private final Properties uiProperties;
    private final Properties styleProperties;
    
    private StyleConfiguration() {
        uiProperties = new Properties();
        styleProperties = new Properties();
        loadProperties();
    }
    
    /**
     * Load properties from configuration files
     */
    private void loadProperties() {
        try {
            // Load UI properties
            try (InputStream input = getClass().getClassLoader().getResourceAsStream(UI_PROPERTIES_FILE)) {
                if (input != null) {
                    uiProperties.load(input);
                    log.info("Loaded UI properties from {}", UI_PROPERTIES_FILE);
                } else {
                    log.warn("UI properties file not found: {}", UI_PROPERTIES_FILE);
                }
            }
            
            // Load style properties
            try (InputStream input = getClass().getClassLoader().getResourceAsStream(STYLE_PROPERTIES_FILE)) {
                if (input != null) {
                    styleProperties.load(input);
                    log.info("Loaded style properties from {}", STYLE_PROPERTIES_FILE);
                } else {
                    log.debug("Style properties file not found: {}", STYLE_PROPERTIES_FILE);
                }
            }
            
        } catch (IOException e) {
            log.error("Error loading style configuration: {}", e.getMessage(), e);
        }
    }
    
    /**
     * Get string property value
     * @param key property key
     * @param defaultValue default value if property not found
     * @return property value
     */
    public String getString(String key, String defaultValue) {
        String value = uiProperties.getProperty(key);
        if (value == null) {
            value = styleProperties.getProperty(key);
        }
        return value != null ? value : defaultValue;
    }
    
    /**
     * Get integer property value
     * @param key property key
     * @param defaultValue default value if property not found
     * @return property value
     */
    public int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(getString(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            log.warn("Invalid integer value for property {}: {}", key, getString(key, ""));
            return defaultValue;
        }
    }
    
    /**
     * Get boolean property value
     * @param key property key
     * @param defaultValue default value if property not found
     * @return property value
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        String value = getString(key, String.valueOf(defaultValue));
        return Boolean.parseBoolean(value);
    }
    
    /**
     * Get color property value
     * @param key property key
     * @param defaultValue default color if property not found
     * @return color value
     */
    public Color getColor(String key, Color defaultValue) {
        String colorHex = getString(key, null);
        if (colorHex != null && colorHex.startsWith("#")) {
            try {
                return Color.decode(colorHex);
            } catch (NumberFormatException e) {
                log.warn("Invalid color value for property {}: {}", key, colorHex);
            }
        }
        return defaultValue;
    }
    
    /**
     * Get font property value
     * @param keyPrefix property key prefix for font properties
     * @param defaultValue default font if properties not found
     * @return font value
     */
    public Font getFont(String keyPrefix, Font defaultValue) {
        String fontName = getString(keyPrefix + ".name", defaultValue.getFontName());
        int fontSize = getInt(keyPrefix + ".size", defaultValue.getSize());
        int fontStyle = getInt(keyPrefix + ".style", defaultValue.getStyle());
        
        try {
            return new Font(fontName, fontStyle, fontSize);
        } catch (Exception e) {
            log.warn("Error creating font for property {}: {}", keyPrefix, e.getMessage());
            return defaultValue;
        }
    }
    
    /**
     * Get current active style from configuration
     * @return active style identifier
     */
    public String getActiveStyle() {
        return getString("ui.style.active", "default");
    }
    
    /**
     * Check if style caching is enabled
     * @return true if caching is enabled
     */
    public boolean isStyleCachingEnabled() {
        return getBoolean("ui.style.cache.enabled", true);
    }
    
    /**
     * Get style cache size
     * @return cache size
     */
    public int getStyleCacheSize() {
        return getInt("ui.style.cache.size", 500);
    }
    
    /**
     * Check if hover effects are enabled
     * @return true if hover effects are enabled
     */
    public boolean areHoverEffectsEnabled() {
        return getBoolean("ui.style.hover.enabled", true);
    }
    
    /**
     * Get hover effect duration
     * @return hover duration in milliseconds
     */
    public int getHoverDuration() {
        return getInt("ui.style.hover.duration", 150);
    }
    
    /**
     * Reload configuration from files
     */
    public void reload() {
        loadProperties();
        log.info("Style configuration reloaded");
    }
    
    /**
     * Get all available style themes
     * @return array of style theme identifiers
     */
    public String[] getAvailableThemes() {
        return new String[]{"scifi"};
    }
}
