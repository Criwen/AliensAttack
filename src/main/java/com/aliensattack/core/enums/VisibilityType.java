package com.aliensattack.core.enums;

/**
 * Defines different visibility states for units
 */
public enum VisibilityType {
    CLEAR,          // Fully visible
    CONCEALED,      // Hidden from enemies
    REVEALED,       // Just been discovered
    PARTIALLY_HIDDEN, // Partially visible (behind cover)
    HIDDEN          // Hidden from enemies
} 