package com.aliensattack.core.data;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Concealment Level Data - XCOM2 Stealth System
 * Represents different levels of concealment and their properties
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConcealmentLevel {
    
    private String name;
    private int duration; // Duration in milliseconds
    private double strength; // Concealment strength multiplier
    private int energyCost; // Energy cost to maintain
    private boolean requiresCover; // Whether cover is required
}
