package com.aliensattack.visualization.models;

/**
 * Enumeration of available animation types for 3D models
 * Covers all common tactical combat animations
 */
public enum AnimationType {
    
    // Idle animations
    IDLE("idle", 2000),
    IDLE_COMBAT("idle_combat", 3000),
    IDLE_RELAXED("idle_relaxed", 4000),
    
    // Movement animations
    WALK("walk", 1000),
    RUN("run", 800),
    CROUCH_WALK("crouch_walk", 1500),
    CRAWL("crawl", 2000),
    
    // Combat animations
    ATTACK_MELEE("attack_melee", 500),
    ATTACK_RANGED("attack_ranged", 300),
    RELOAD("reload", 1200),
    THROW_GRENADE("throw_grenade", 800),
    
    // Defensive animations
    DEFEND("defend", 1000),
    DODGE("dodge", 400),
    TAKE_COVER("take_cover", 600),
    
    // Special animations
    PSIONIC_CAST("psionic_cast", 1500),
    HEAL("heal", 2000),
    OVERWATCH("overwatch", 1000),
    
    // Status animations
    INJURED("injured", 3000),
    STUNNED("stunned", 2000),
    MIND_CONTROLLED("mind_controlled", 2500),
    
    // Death animations
    DEATH("death", 1500),
    DEATH_EXPLOSIVE("death_explosive", 800),
    
    // Victory/defeat animations
    VICTORY("victory", 3000),
    DEFEAT("defeat", 3000);
    
    private final String animationName;
    private final int defaultDuration;
    
    AnimationType(String animationName, int defaultDuration) {
        this.animationName = animationName;
        this.defaultDuration = defaultDuration;
    }
    
    public String getAnimationName() {
        return animationName;
    }
    
    public int getDefaultDuration() {
        return defaultDuration;
    }
    
    @Override
    public String toString() {
        return animationName;
    }
}


