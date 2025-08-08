package com.aliensattack.combat;

import com.aliensattack.core.model.Armor;
import com.aliensattack.core.enums.ArmorType;
import com.aliensattack.core.config.GameConfig;

/**
 * Factory for creating different armor types
 */
public class ArmorFactory {
    
    /**
     * Creates light armor
     */
    public static Armor createLightArmor() {
        return new Armor(
            GameConfig.getArmorName("light"),
            ArmorType.LIGHT_ARMOR,
            GameConfig.getArmorDamageReduction("light"),
            GameConfig.getArmorHealthBonus("light")
        );
    }
    
    /**
     * Creates medium armor
     */
    public static Armor createMediumArmor() {
        return new Armor(
            GameConfig.getArmorName("medium"),
            ArmorType.MEDIUM_ARMOR,
            GameConfig.getArmorDamageReduction("medium"),
            GameConfig.getArmorHealthBonus("medium")
        );
    }
    
    /**
     * Creates heavy armor
     */
    public static Armor createHeavyArmor() {
        return new Armor(
            GameConfig.getArmorName("heavy"),
            ArmorType.HEAVY_ARMOR,
            GameConfig.getArmorDamageReduction("heavy"),
            GameConfig.getArmorHealthBonus("heavy")
        );
    }
    
    /**
     * Creates plated armor
     */
    public static Armor createPlatedArmor() {
        return new Armor(
            GameConfig.getArmorName("plated"),
            ArmorType.PLATED_ARMOR,
            GameConfig.getArmorDamageReduction("plated"),
            GameConfig.getArmorHealthBonus("plated")
        );
    }
    
    /**
     * Creates powered armor
     */
    public static Armor createPoweredArmor() {
        return new Armor(
            GameConfig.getArmorName("powered"),
            ArmorType.POWERED_ARMOR,
            GameConfig.getArmorDamageReduction("powered"),
            GameConfig.getArmorHealthBonus("powered")
        );
    }
    
    /**
     * Creates armor based on type
     */
    public static Armor createArmor(ArmorType type) {
        return switch (type) {
            case NONE -> null;
            case LIGHT_ARMOR -> createLightArmor();
            case MEDIUM_ARMOR -> createMediumArmor();
            case HEAVY_ARMOR -> createHeavyArmor();
            case PLATED_ARMOR -> createPlatedArmor();
            case POWERED_ARMOR -> createPoweredArmor();
        };
    }
} 