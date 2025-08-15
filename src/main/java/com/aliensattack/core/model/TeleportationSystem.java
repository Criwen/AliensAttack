package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.*;
import java.time.LocalDateTime;

/**
 * Extended Teleportation System - XCOM 2 Tactical Combat
 * Implements comprehensive teleportation mechanics with visual effects, sound effects,
 * failure mechanics, and interdiction systems
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeleportationSystem {
    
    private String systemId;
    private Map<String, TeleportInstance> activeTeleports;
    private List<TeleportVisualEffect> visualEffects;
    private List<TeleportSoundEffect> soundEffects;
    private List<TeleportFailure> failureEvents;
    private Map<String, TeleportInterdiction> activeInterdictions;
    private Map<String, Integer> teleportStatistics;
    private boolean isSystemActive;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeleportInstance {
        private String teleportId;
        private String casterId;
        private Position startPosition;
        private Position targetPosition;
        private TeleportType type;
        private int range;
        private double successChance;
        private boolean isSuccessful;
        private LocalDateTime startTime;
        private LocalDateTime completionTime;
        private int duration;
        private int currentDuration;
        private List<String> effects;
        private String description;
        private double energyCost;
        private int cooldown;
        private int currentCooldown;
    }
    
    public enum TeleportType {
        INSTANT_TELEPORT,       // Instant teleportation
        PHASED_TELEPORT,        // Phased teleportation with delay
        LINKED_TELEPORT,        // Teleport linked to another unit
        GROUP_TELEPORT,         // Teleport multiple units
        CONDITIONAL_TELEPORT,   // Teleport with conditions
        REVERSE_TELEPORT,       // Reverse teleportation
        DIMENSIONAL_TELEPORT,   // Dimensional teleportation
        QUANTUM_TELEPORT,       // Quantum teleportation
        PSIONIC_TELEPORT,       // Psionic teleportation
        TECHNICAL_TELEPORT      // Technical teleportation
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeleportVisualEffect {
        private String effectId;
        private String effectName;
        private VisualEffectType type;
        private String targetId;
        private Position position;
        private int duration;
        private int currentDuration;
        private boolean isActive;
        private LocalDateTime startTime;
        private List<String> visualElements;
        private String description;
        private int intensity;
        private String color;
        private String pattern;
        private double opacity;
        private String animation;
    }
    
    public enum VisualEffectType {
        TELEPORT_AURA,          // Teleportation aura
        SPATIAL_DISTORTION,     // Spatial distortion effect
        DIMENSIONAL_RIFT,       // Dimensional rift visualization
        QUANTUM_FLUX,           // Quantum flux effect
        PSIONIC_PORTAL,         // Psionic portal effect
        ENERGY_DISCHARGE,       // Energy discharge effect
        SPATIAL_TEAR,           // Spatial tear effect
        DIMENSIONAL_WAVE,       // Dimensional wave effect
        QUANTUM_PULSE,          // Quantum pulse effect
        PSIONIC_RESONANCE       // Psionic resonance effect
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeleportSoundEffect {
        private String effectId;
        private String effectName;
        private SoundEffectType type;
        private String targetId;
        private Position position;
        private int duration;
        private int currentDuration;
        private boolean isActive;
        private LocalDateTime startTime;
        private List<String> soundElements;
        private String description;
        private int volume;
        private String frequency;
        private String pattern;
        private double pitch;
        private String modulation;
    }
    
    public enum SoundEffectType {
        SPATIAL_DISTORTION,     // Spatial distortion sound
        DIMENSIONAL_RUMBLE,     // Dimensional rumble
        QUANTUM_HARMONICS,      // Quantum harmonics
        PSIONIC_RESONANCE,      // Psionic resonance
        ENERGY_DISCHARGE,       // Energy discharge sound
        SPATIAL_TEAR,           // Spatial tear sound
        DIMENSIONAL_WAVE,       // Dimensional wave sound
        QUANTUM_PULSE,          // Quantum pulse sound
        PSIONIC_PORTAL,         // Psionic portal sound
        TELEPORT_ECHO           // Teleportation echo
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeleportFailure {
        private String failureId;
        private String teleportId;
        private String casterId;
        private FailureType type;
        private String reason;
        private int severity;
        private LocalDateTime failureTime;
        private Position failurePosition;
        private List<String> consequences;
        private boolean isResolved;
        private String resolution;
    }
    
    public enum FailureType {
        SPATIAL_INSTABILITY,    // Spatial instability
        ENERGY_INSUFFICIENCY,   // Insufficient energy
        INTERDICTION_BLOCK,     // Blocked by interdiction
        RANGE_EXCEEDED,         // Range exceeded
        TARGET_INVALID,         // Invalid target
        COOLDOWN_ACTIVE,        // Cooldown still active
        PSIONIC_INTERFERENCE,   // Psionic interference
        TECHNICAL_MALFUNCTION,  // Technical malfunction
        DIMENSIONAL_LOCK,       // Dimensional lock
        QUANTUM_DISRUPTION      // Quantum disruption
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeleportInterdiction {
        private String interdictionId;
        private String sourceId;
        private InterdictionType type;
        private int range;
        private int duration;
        private int currentDuration;
        private boolean isActive;
        private LocalDateTime startTime;
        private Position centerPosition;
        private List<String> affectedUnits;
        private double successChance;
        private String description;
        private int energyCost;
        private String activationCondition;
    }
    
    public enum InterdictionType {
        SPATIAL_LOCK,           // Spatial lock field
        DIMENSIONAL_BARRIER,    // Dimensional barrier
        QUANTUM_DISRUPTION,     // Quantum disruption field
        PSIONIC_JAMMING,        // Psionic jamming
        ENERGY_DISRUPTION,      // Energy disruption
        SPATIAL_ANCHOR,         // Spatial anchor
        DIMENSIONAL_TETHER,     // Dimensional tether
        QUANTUM_ENTANGLEMENT,   // Quantum entanglement
        PSIONIC_BINDING,        // Psionic binding
        TECHNICAL_JAMMING       // Technical jamming
    }
    
    /**
     * Initialize teleportation system
     */
    public boolean initializeSystem(String systemId) {
        this.systemId = systemId;
        this.isSystemActive = true;
        
        this.activeTeleports = new HashMap<>();
        this.visualEffects = new ArrayList<>();
        this.soundEffects = new ArrayList<>();
        this.failureEvents = new ArrayList<>();
        this.activeInterdictions = new HashMap<>();
        this.teleportStatistics = new HashMap<>();
        
        // Initialize statistics
        initializeStatistics();
        
        return true;
    }
    
    /**
     * Initialize teleport statistics
     */
    private void initializeStatistics() {
        teleportStatistics.put("total_teleports", 0);
        teleportStatistics.put("successful_teleports", 0);
        teleportStatistics.put("failed_teleports", 0);
        teleportStatistics.put("interdicted_teleports", 0);
        teleportStatistics.put("visual_effects_created", 0);
        teleportStatistics.put("sound_effects_created", 0);
    }
    
    /**
     * Attempt teleportation
     */
    public boolean attemptTeleport(String casterId, Position startPosition, Position targetPosition, 
                                  TeleportType type, int range, double energyCost) {
        // Check if teleport is within range
        if (!isWithinRange(startPosition, targetPosition, range)) {
            createFailureEvent(null, casterId, FailureType.RANGE_EXCEEDED, "Target position exceeds teleport range");
            return false;
        }
        
        // Check for interdiction
        if (isInterdicted(startPosition, targetPosition)) {
            createFailureEvent(null, casterId, FailureType.INTERDICTION_BLOCK, "Teleport blocked by interdiction field");
            teleportStatistics.put("interdicted_teleports", teleportStatistics.get("interdicted_teleports") + 1);
            return false;
        }
        
        // Check cooldown
        if (isOnCooldown(casterId)) {
            createFailureEvent(null, casterId, FailureType.COOLDOWN_ACTIVE, "Teleport ability on cooldown");
            return false;
        }
        
        // Calculate success chance
        double successChance = calculateSuccessChance(casterId, startPosition, targetPosition, type);
        
        // Attempt teleport
        boolean success = Math.random() < successChance;
        
        // Create teleport instance
        TeleportInstance teleport = TeleportInstance.builder()
            .teleportId("TP_" + System.currentTimeMillis())
            .casterId(casterId)
            .startPosition(startPosition)
            .targetPosition(targetPosition)
            .type(type)
            .range(range)
            .successChance(successChance)
            .isSuccessful(success)
            .startTime(LocalDateTime.now())
            .duration(calculateTeleportDuration(type))
            .currentDuration(calculateTeleportDuration(type))
            .effects(new ArrayList<>())
            .description("Teleport: " + type.name())
            .energyCost(energyCost)
            .cooldown(calculateCooldown(type))
            .currentCooldown(0)
            .build();
        
        activeTeleports.put(teleport.getTeleportId(), teleport);
        
        // Update statistics
        teleportStatistics.put("total_teleports", teleportStatistics.get("total_teleports") + 1);
        
        if (success) {
            teleportStatistics.put("successful_teleports", teleportStatistics.get("successful_teleports") + 1);
            teleport.setCompletionTime(LocalDateTime.now());
            
            // Create success effects
            createTeleportEffects(teleport, true);
        } else {
            teleportStatistics.put("failed_teleports", teleportStatistics.get("failed_teleports") + 1);
            
            // Create failure effects
            createTeleportEffects(teleport, false);
            
            // Create failure event
            createFailureEvent(teleport.getTeleportId(), casterId, FailureType.SPATIAL_INSTABILITY, "Teleport failed due to spatial instability");
        }
        
        return success;
    }
    
    /**
     * Check if target is within range
     */
    private boolean isWithinRange(Position start, Position target, int range) {
        double distance = calculateDistance(start, target);
        return distance <= range;
    }
    
    /**
     * Calculate distance between positions
     */
    private double calculateDistance(Position start, Position target) {
        int dx = target.getX() - start.getX();
        int dy = target.getY() - start.getY();
        int dh = target.getHeight() - start.getHeight();
        
        return Math.sqrt(dx * dx + dy * dy + dh * dh);
    }
    
    /**
     * Check if teleport is interdicted
     */
    private boolean isInterdicted(Position start, Position target) {
        for (TeleportInterdiction interdiction : activeInterdictions.values()) {
            if (interdiction.isActive() && isPositionInInterdictionRange(start, target, interdiction)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Check if positions are in interdiction range
     */
    private boolean isPositionInInterdictionRange(Position start, Position target, TeleportInterdiction interdiction) {
        double startDistance = calculateDistance(start, interdiction.getCenterPosition());
        double targetDistance = calculateDistance(target, interdiction.getCenterPosition());
        
        return startDistance <= interdiction.getRange() || targetDistance <= interdiction.getRange();
    }
    
    /**
     * Check if caster is on cooldown
     */
    private boolean isOnCooldown(String casterId) {
        return activeTeleports.values().stream()
            .anyMatch(teleport -> teleport.getCasterId().equals(casterId) && teleport.getCurrentCooldown() > 0);
    }
    
    /**
     * Calculate teleport success chance
     */
    private double calculateSuccessChance(String casterId, Position start, Position target, TeleportType type) {
        double baseChance = 0.8;
        
        // Add type-specific bonuses
        switch (type) {
            case INSTANT_TELEPORT:
                baseChance += 0.1;
                break;
            case PHASED_TELEPORT:
                baseChance += 0.05;
                break;
            case LINKED_TELEPORT:
                baseChance += 0.15;
                break;
            case GROUP_TELEPORT:
                baseChance -= 0.1;
                break;
            case CONDITIONAL_TELEPORT:
                baseChance -= 0.05;
                break;
            default:
                break;
        }
        
        // Add distance penalties
        double distance = calculateDistance(start, target);
        if (distance > 10) {
            baseChance -= 0.1;
        }
        
        return Math.min(1.0, Math.max(0.0, baseChance));
    }
    
    /**
     * Calculate teleport duration
     */
    private int calculateTeleportDuration(TeleportType type) {
        return switch (type) {
            case INSTANT_TELEPORT -> 1;
            case PHASED_TELEPORT -> 3;
            case LINKED_TELEPORT -> 2;
            case GROUP_TELEPORT -> 4;
            case CONDITIONAL_TELEPORT -> 2;
            default -> 1;
        };
    }
    
    /**
     * Calculate teleport cooldown
     */
    private int calculateCooldown(TeleportType type) {
        return switch (type) {
            case INSTANT_TELEPORT -> 3;
            case PHASED_TELEPORT -> 2;
            case LINKED_TELEPORT -> 4;
            case GROUP_TELEPORT -> 6;
            case CONDITIONAL_TELEPORT -> 3;
            default -> 3;
        };
    }
    
    /**
     * Create teleport effects
     */
    private void createTeleportEffects(TeleportInstance teleport, boolean isSuccess) {
        if (isSuccess) {
            // Create success visual effect
            TeleportVisualEffect visualEffect = TeleportVisualEffect.builder()
                .effectId("VE_" + teleport.getTeleportId())
                .effectName("Successful Teleport")
                .type(VisualEffectType.TELEPORT_AURA)
                .targetId(teleport.getCasterId())
                .position(teleport.getTargetPosition())
                .duration(teleport.getDuration())
                .currentDuration(teleport.getDuration())
                .isActive(true)
                .startTime(LocalDateTime.now())
                .visualElements(Arrays.asList("Teleport aura", "Spatial distortion", "Success glow"))
                .description("Visual effect for successful teleport")
                .intensity(75)
                .color("Blue")
                .pattern("Pulsing")
                .opacity(0.8)
                .animation("Fade in")
                .build();
            
            visualEffects.add(visualEffect);
            
            // Create success sound effect
            TeleportSoundEffect soundEffect = TeleportSoundEffect.builder()
                .effectId("SE_" + teleport.getTeleportId())
                .effectName("Successful Teleport")
                .type(SoundEffectType.TELEPORT_ECHO)
                .targetId(teleport.getCasterId())
                .position(teleport.getTargetPosition())
                .duration(teleport.getDuration())
                .currentDuration(teleport.getDuration())
                .isActive(true)
                .startTime(LocalDateTime.now())
                .soundElements(Arrays.asList("Teleport echo", "Success resonance", "Spatial harmony"))
                .description("Sound effect for successful teleport")
                .volume(70)
                .frequency("High")
                .pattern("Echo")
                .pitch(1.2)
                .modulation("Harmonic")
                .build();
            
            soundEffects.add(soundEffect);
        } else {
            // Create failure visual effect
            TeleportVisualEffect visualEffect = TeleportVisualEffect.builder()
                .effectId("VE_" + teleport.getTeleportId())
                .effectName("Failed Teleport")
                .type(VisualEffectType.SPATIAL_DISTORTION)
                .targetId(teleport.getCasterId())
                .position(teleport.getStartPosition())
                .duration(3)
                .currentDuration(3)
                .isActive(true)
                .startTime(LocalDateTime.now())
                .visualElements(Arrays.asList("Spatial distortion", "Failure flash", "Energy discharge"))
                .description("Visual effect for failed teleport")
                .intensity(50)
                .color("Red")
                .pattern("Flickering")
                .opacity(0.6)
                .animation("Flicker")
                .build();
            
            visualEffects.add(visualEffect);
            
            // Create failure sound effect
            TeleportSoundEffect soundEffect = TeleportSoundEffect.builder()
                .effectId("SE_" + teleport.getTeleportId())
                .effectName("Failed Teleport")
                .type(SoundEffectType.SPATIAL_DISTORTION)
                .targetId(teleport.getCasterId())
                .position(teleport.getStartPosition())
                .duration(3)
                .currentDuration(3)
                .isActive(true)
                .startTime(LocalDateTime.now())
                .soundElements(Arrays.asList("Spatial distortion", "Failure crackle", "Energy discharge"))
                .description("Sound effect for failed teleport")
                .volume(80)
                .frequency("Low")
                .pattern("Crackle")
                .pitch(0.8)
                .modulation("Dissonant")
                .build();
            
            soundEffects.add(soundEffect);
        }
        
        // Update statistics
        teleportStatistics.put("visual_effects_created", teleportStatistics.get("visual_effects_created") + 1);
        teleportStatistics.put("sound_effects_created", teleportStatistics.get("sound_effects_created") + 1);
    }
    
    /**
     * Create failure event
     */
    private void createFailureEvent(String teleportId, String casterId, FailureType type, String reason) {
        TeleportFailure failure = TeleportFailure.builder()
            .failureId("TF_" + System.currentTimeMillis())
            .teleportId(teleportId)
            .casterId(casterId)
            .type(type)
            .reason(reason)
            .severity(calculateFailureSeverity(type))
            .failureTime(LocalDateTime.now())
            .failurePosition(null) // Would be set based on context
            .consequences(Arrays.asList("Teleport failed", "Energy wasted", "Cooldown triggered"))
            .isResolved(false)
            .resolution("")
            .build();
        
        failureEvents.add(failure);
    }
    
    /**
     * Calculate failure severity
     */
    private int calculateFailureSeverity(FailureType type) {
        return switch (type) {
            case SPATIAL_INSTABILITY -> 30;
            case ENERGY_INSUFFICIENCY -> 20;
            case INTERDICTION_BLOCK -> 40;
            case RANGE_EXCEEDED -> 25;
            case TARGET_INVALID -> 35;
            case COOLDOWN_ACTIVE -> 15;
            case PSIONIC_INTERFERENCE -> 45;
            case TECHNICAL_MALFUNCTION -> 50;
            case DIMENSIONAL_LOCK -> 60;
            case QUANTUM_DISRUPTION -> 55;
        };
    }
    
    /**
     * Add interdiction field
     */
    public boolean addInterdiction(TeleportInterdiction interdiction) {
        if (activeInterdictions.containsKey(interdiction.getInterdictionId())) {
            return false;
        }
        
        interdiction.setActive(true);
        interdiction.setCurrentDuration(interdiction.getDuration());
        interdiction.setStartTime(LocalDateTime.now());
        activeInterdictions.put(interdiction.getInterdictionId(), interdiction);
        
        return true;
    }
    
    /**
     * Update system
     */
    public boolean updateSystem() {
        // Update active teleports
        updateActiveTeleports();
        
        // Update visual effects
        updateVisualEffects();
        
        // Update sound effects
        updateSoundEffects();
        
        // Update interdictions
        updateInterdictions();
        
        return true;
    }
    
    /**
     * Update active teleports
     */
    private void updateActiveTeleports() {
        Iterator<Map.Entry<String, TeleportInstance>> iterator = activeTeleports.entrySet().iterator();
        
        while (iterator.hasNext()) {
            Map.Entry<String, TeleportInstance> entry = iterator.next();
            TeleportInstance teleport = entry.getValue();
            
            if (teleport.getCurrentDuration() > 0) {
                // Update duration
                teleport.setCurrentDuration(teleport.getCurrentDuration() - 1);
                
                // Check if teleport should complete
                if (teleport.getCurrentDuration() <= 0) {
                    teleport.setCompletionTime(LocalDateTime.now());
                }
            }
            
            // Update cooldown
            if (teleport.getCurrentCooldown() > 0) {
                teleport.setCurrentCooldown(teleport.getCurrentCooldown() - 1);
            }
        }
    }
    
    /**
     * Update visual effects
     */
    private void updateVisualEffects() {
        Iterator<TeleportVisualEffect> iterator = visualEffects.iterator();
        
        while (iterator.hasNext()) {
            TeleportVisualEffect effect = iterator.next();
            
            if (effect.isActive()) {
                // Update duration
                effect.setCurrentDuration(effect.getCurrentDuration() - 1);
                
                // Remove expired effects
                if (effect.getCurrentDuration() <= 0) {
                    iterator.remove();
                }
            }
        }
    }
    
    /**
     * Update sound effects
     */
    private void updateSoundEffects() {
        Iterator<TeleportSoundEffect> iterator = soundEffects.iterator();
        
        while (iterator.hasNext()) {
            TeleportSoundEffect effect = iterator.next();
            
            if (effect.isActive()) {
                // Update duration
                effect.setCurrentDuration(effect.getCurrentDuration() - 1);
                
                // Remove expired effects
                if (effect.getCurrentDuration() <= 0) {
                    iterator.remove();
                }
            }
        }
    }
    
    /**
     * Update interdictions
     */
    private void updateInterdictions() {
        Iterator<Map.Entry<String, TeleportInterdiction>> iterator = activeInterdictions.entrySet().iterator();
        
        while (iterator.hasNext()) {
            Map.Entry<String, TeleportInterdiction> entry = iterator.next();
            TeleportInterdiction interdiction = entry.getValue();
            
            if (interdiction.isActive()) {
                // Update duration
                interdiction.setCurrentDuration(interdiction.getCurrentDuration() - 1);
                
                // Remove expired interdictions
                if (interdiction.getCurrentDuration() <= 0) {
                    iterator.remove();
                }
            }
        }
    }
    
    /**
     * Get active teleports count
     */
    public int getActiveTeleportsCount() {
        return activeTeleports.size();
    }
    
    /**
     * Get active visual effects count
     */
    public int getActiveVisualEffectsCount() {
        return (int) visualEffects.stream().filter(TeleportVisualEffect::isActive).count();
    }
    
    /**
     * Get active sound effects count
     */
    public int getActiveSoundEffectsCount() {
        return (int) soundEffects.stream().filter(TeleportSoundEffect::isActive).count();
    }
    
    /**
     * Get active interdictions count
     */
    public int getActiveInterdictionsCount() {
        return (int) activeInterdictions.values().stream().filter(TeleportInterdiction::isActive).count();
    }
    
    /**
     * Get teleport success rate
     */
    public double getTeleportSuccessRate() {
        int total = teleportStatistics.get("total_teleports");
        int successful = teleportStatistics.get("successful_teleports");
        
        if (total == 0) {
            return 0.0;
        }
        
        return (double) successful / total * 100.0;
    }
}
