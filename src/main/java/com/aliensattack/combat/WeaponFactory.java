package com.aliensattack.combat;

import com.aliensattack.core.model.Weapon;
import com.aliensattack.core.enums.WeaponType;
import com.aliensattack.core.config.GameConfig;

/**
 * Factory for creating different weapon types with predefined characteristics
 */
public class WeaponFactory {
    
    /**
     * Creates a standard rifle
     */
    public static Weapon createRifle() {
        return new Weapon(
            GameConfig.getWeaponName("rifle"),
            WeaponType.RIFLE,
            GameConfig.getWeaponRange("rifle"),
            GameConfig.getWeaponAmmo("rifle"),
            GameConfig.getWeaponDamage("rifle"),
            GameConfig.getWeaponAccuracy("rifle"),
            GameConfig.getWeaponExplosionRadius("rifle")
        );
    }
    
    /**
     * Creates a shotgun
     */
    public static Weapon createShotgun() {
        return new Weapon(
            GameConfig.getWeaponName("shotgun"),
            WeaponType.SHOTGUN,
            GameConfig.getWeaponRange("shotgun"),
            GameConfig.getWeaponAmmo("shotgun"),
            GameConfig.getWeaponDamage("shotgun"),
            GameConfig.getWeaponAccuracy("shotgun"),
            GameConfig.getWeaponExplosionRadius("shotgun")
        );
    }
    
    /**
     * Creates a sniper rifle
     */
    public static Weapon createSniperRifle() {
        return new Weapon(
            GameConfig.getWeaponName("sniper"),
            WeaponType.SNIPER_RIFLE,
            GameConfig.getWeaponRange("sniper"),
            GameConfig.getWeaponAmmo("sniper"),
            GameConfig.getWeaponDamage("sniper"),
            GameConfig.getWeaponAccuracy("sniper"),
            GameConfig.getWeaponExplosionRadius("sniper")
        );
    }
    
    /**
     * Creates a pistol
     */
    public static Weapon createPistol() {
        return new Weapon(
            GameConfig.getWeaponName("pistol"),
            WeaponType.PISTOL,
            GameConfig.getWeaponRange("pistol"),
            GameConfig.getWeaponAmmo("pistol"),
            GameConfig.getWeaponDamage("pistol"),
            GameConfig.getWeaponAccuracy("pistol"),
            GameConfig.getWeaponExplosionRadius("pistol")
        );
    }
    
    /**
     * Creates a grenade
     */
    public static Weapon createGrenade() {
        return new Weapon(
            GameConfig.getWeaponName("grenade"),
            WeaponType.GRENADE,
            GameConfig.getWeaponRange("grenade"),
            GameConfig.getWeaponAmmo("grenade"),
            GameConfig.getWeaponDamage("grenade"),
            GameConfig.getWeaponAccuracy("grenade"),
            GameConfig.getWeaponExplosionRadius("grenade"),
            GameConfig.isWeaponExplosive("grenade"),
            GameConfig.getWeaponExplosionRadius("grenade")
        );
    }
    
    /**
     * Creates a rocket launcher
     */
    public static Weapon createRocketLauncher() {
        return new Weapon(
            GameConfig.getWeaponName("rocket"),
            WeaponType.ROCKET_LAUNCHER,
            GameConfig.getWeaponRange("rocket"),
            GameConfig.getWeaponAmmo("rocket"),
            GameConfig.getWeaponDamage("rocket"),
            GameConfig.getWeaponAccuracy("rocket"),
            GameConfig.getWeaponExplosionRadius("rocket"),
            GameConfig.isWeaponExplosive("rocket"),
            GameConfig.getWeaponExplosionRadius("rocket")
        );
    }
    
    /**
     * Creates a plasma rifle (alien weapon)
     */
    public static Weapon createPlasmaRifle() {
        return new Weapon(
            GameConfig.getWeaponName("plasma"),
            WeaponType.PLASMA_RIFLE,
            GameConfig.getWeaponRange("plasma"),
            GameConfig.getWeaponAmmo("plasma"),
            GameConfig.getWeaponDamage("plasma"),
            GameConfig.getWeaponAccuracy("plasma"),
            GameConfig.getWeaponExplosionRadius("plasma")
        );
    }
    
    /**
     * Creates a laser rifle
     */
    public static Weapon createLaserRifle() {
        return new Weapon(
            GameConfig.getWeaponName("laser"),
            WeaponType.LASER_RIFLE,
            GameConfig.getWeaponRange("laser"),
            GameConfig.getWeaponAmmo("laser"),
            GameConfig.getWeaponDamage("laser"),
            GameConfig.getWeaponAccuracy("laser"),
            GameConfig.getWeaponExplosionRadius("laser")
        );
    }
    
    /**
     * Creates weapon based on type
     */
    public static Weapon createWeapon(WeaponType type) {
        return switch (type) {
            case RIFLE -> createRifle();
            case SHOTGUN -> createShotgun();
            case SNIPER_RIFLE -> createSniperRifle();
            case PISTOL -> createPistol();
            case GRENADE -> createGrenade();
            case ROCKET_LAUNCHER -> createRocketLauncher();
            case PLASMA_RIFLE -> createPlasmaRifle();
            case LASER_RIFLE -> createLaserRifle();
            case HEAVY_WEAPON -> createRifle(); // Use rifle as base
            case GRENADE_LAUNCHER -> createGrenade(); // Use grenade as base
            case LASER_WEAPON -> createLaserRifle(); // Use laser rifle as base
            case PLASMA_WEAPON -> createPlasmaRifle(); // Use plasma rifle as base
            case MELEE_WEAPON -> createPistol(); // Use pistol as base
            case BLUESCREEN -> createRifle(); // Use rifle as base
        };
    }
} 