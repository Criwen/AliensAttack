package com.aliensattack.combat;

import com.aliensattack.core.model.Explosive;
import com.aliensattack.core.enums.ExplosiveType;
import com.aliensattack.core.config.GameConfig;

/**
 * Factory for creating explosive devices
 */
public class ExplosiveFactory {
    
    /**
     * Creates fragmentation grenade
     */
    public static Explosive createGrenade() {
        return new Explosive(
            GameConfig.getExplosiveName("grenade"),
            ExplosiveType.GRENADE,
            GameConfig.getExplosiveDamage("grenade"),
            GameConfig.getExplosiveRange("grenade"),
            GameConfig.getExplosiveDuration("grenade")
        );
    }
    
    /**
     * Creates flashbang
     */
    public static Explosive createFlashbang() {
        return new Explosive(
            GameConfig.getExplosiveName("flashbang"),
            ExplosiveType.FLASHBANG,
            GameConfig.getExplosiveDamage("flashbang"),
            GameConfig.getExplosiveRange("flashbang"),
            GameConfig.getExplosiveDuration("flashbang")
        );
    }
    
    /**
     * Creates smoke grenade
     */
    public static Explosive createSmokeGrenade() {
        return new Explosive(
            GameConfig.getExplosiveName("smoke"),
            ExplosiveType.SMOKE_GRENADE,
            GameConfig.getExplosiveDamage("smoke"),
            GameConfig.getExplosiveRange("smoke"),
            GameConfig.getExplosiveDuration("smoke")
        );
    }
    
    /**
     * Creates proximity mine
     */
    public static Explosive createProximityMine() {
        return new Explosive(
            GameConfig.getExplosiveName("proximity"),
            ExplosiveType.PROXIMITY_MINE,
            GameConfig.getExplosiveDamage("proximity"),
            GameConfig.getExplosiveRange("proximity"),
            GameConfig.getExplosiveDuration("proximity")
        );
    }
    
    /**
     * Creates remote charge
     */
    public static Explosive createRemoteCharge() {
        return new Explosive(
            GameConfig.getExplosiveName("remote"),
            ExplosiveType.REMOTE_CHARGE,
            GameConfig.getExplosiveDamage("remote"),
            GameConfig.getExplosiveRange("remote"),
            GameConfig.getExplosiveDuration("remote")
        );
    }
    
    /**
     * Creates timed charge
     */
    public static Explosive createTimedCharge() {
        return new Explosive(
            GameConfig.getExplosiveName("timed"),
            ExplosiveType.TIMED_CHARGE,
            GameConfig.getExplosiveDamage("timed"),
            GameConfig.getExplosiveRange("timed"),
            GameConfig.getExplosiveDuration("timed")
        );
    }
    
    /**
     * Creates acid grenade
     */
    public static Explosive createAcidGrenade() {
        return new Explosive(
            GameConfig.getExplosiveName("acid"),
            ExplosiveType.ACID_GRENADE,
            GameConfig.getExplosiveDamage("acid"),
            GameConfig.getExplosiveRange("acid"),
            GameConfig.getExplosiveDuration("acid")
        );
    }
    
    /**
     * Creates fire grenade
     */
    public static Explosive createFireGrenade() {
        return new Explosive(
            GameConfig.getExplosiveName("fire"),
            ExplosiveType.FIRE_GRENADE,
            GameConfig.getExplosiveDamage("fire"),
            GameConfig.getExplosiveRange("fire"),
            GameConfig.getExplosiveDuration("fire")
        );
    }
    
    /**
     * Creates ice grenade
     */
    public static Explosive createIceGrenade() {
        return new Explosive(
            GameConfig.getExplosiveName("ice"),
            ExplosiveType.ICE_GRENADE,
            GameConfig.getExplosiveDamage("ice"),
            GameConfig.getExplosiveRange("ice"),
            GameConfig.getExplosiveDuration("ice")
        );
    }
    
    /**
     * Creates poison grenade
     */
    public static Explosive createPoisonGrenade() {
        return new Explosive(
            GameConfig.getExplosiveName("poison"),
            ExplosiveType.POISON_GRENADE,
            GameConfig.getExplosiveDamage("poison"),
            GameConfig.getExplosiveRange("poison"),
            GameConfig.getExplosiveDuration("poison")
        );
    }
    
    /**
     * Creates explosive based on type
     */
    public static Explosive createExplosive(ExplosiveType type) {
        return switch (type) {
            case GRENADE -> createGrenade();
            case FLASHBANG -> createFlashbang();
            case SMOKE_GRENADE -> createSmokeGrenade();
            case PROXIMITY_MINE -> createProximityMine();
            case REMOTE_CHARGE -> createRemoteCharge();
            case TIMED_CHARGE -> createTimedCharge();
            case ACID_GRENADE -> createAcidGrenade();
            case FIRE_GRENADE -> createFireGrenade();
            case ICE_GRENADE -> createIceGrenade();
            case POISON_GRENADE -> createPoisonGrenade();
            case VOLATILE_MIX -> createGrenade(); // Use grenade as base for volatile mix
        };
    }
} 