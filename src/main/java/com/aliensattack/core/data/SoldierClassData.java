package com.aliensattack.core.data;

import com.aliensattack.core.enums.SoldierClass;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Represents a soldier class with unique abilities and specializations
 * Implements XCOM 2 soldier class mechanics
 */
@Getter
@Setter
public class SoldierClassData {
    private String name;
    private SoldierClass soldierClass;
    private String description;
    private int baseHealth;
    private int baseMobility;
    private int baseAim;
    private int baseWill;
    private int baseHacking;
    private int basePsi;
    
    // Class-specific abilities
    private List<String> primaryAbilities;
    private List<String> secondaryAbilities;
    private List<String> passiveAbilities;
    private List<String> uniqueAbilities;
    
    // Class bonuses
    private int healthBonus;
    private int mobilityBonus;
    private int aimBonus;
    private int willBonus;
    private int hackingBonus;
    private int psiBonus;
    
    // Specializations
    private boolean canUseHeavyWeapons;
    private boolean canUsePsionics;
    private boolean canHack;
    private boolean canUseStealth;
    private boolean canUseMelee;
    private boolean canUseGrenades;
    private boolean canUseMedkits;
    private boolean canUseDrones;
    
    // Equipment restrictions
    private List<String> allowedWeapons;
    private List<String> allowedArmor;
    private List<String> allowedItems;
    
    public SoldierClassData(SoldierClass soldierClass) {
        this.soldierClass = soldierClass;
        this.primaryAbilities = new ArrayList<>();
        this.secondaryAbilities = new ArrayList<>();
        this.passiveAbilities = new ArrayList<>();
        this.uniqueAbilities = new ArrayList<>();
        this.allowedWeapons = new ArrayList<>();
        this.allowedArmor = new ArrayList<>();
        this.allowedItems = new ArrayList<>();
        
        setPropertiesByClass(soldierClass);
    }
    
    private void setPropertiesByClass(SoldierClass soldierClass) {
        switch (soldierClass) {
            case ROOKIE:
                setRookieProperties();
                break;
            case SOLDIER:
                setSoldierProperties();
                break;
            case RANGER:
                setRangerProperties();
                break;
            case GRENADIER:
                setGrenadierProperties();
                break;
            case SPECIALIST:
                setSpecialistProperties();
                break;
            case SHARPSHOOTER:
                setSharpshooterProperties();
                break;
            case PSI_OPERATIVE:
                setPsiOperativeProperties();
                break;
            case TECHNICAL:
                setTechnicalProperties();
                break;
            case TECHNICIAN:
                setTechnicianProperties();
                break;
            case MEDIC:
                setMedicProperties();
                break;
            case SCOUT:
                setScoutProperties();
                break;
            case HEAVY:
                setHeavyProperties();
                break;
            case SNIPER:
                setSniperProperties();
                break;
            case ASSAULT:
                setAssaultProperties();
                break;
            case SUPPORT:
                setSupportProperties();
                break;
            case COMMANDO:
                setCommandoProperties();
                break;
            case ENGINEER:
                setEngineerProperties();
                break;
        }
    }
    
    private void setRookieProperties() {
        name = "Rookie";
        description = "Basic soldier with no specialization";
        baseHealth = 4;
        baseMobility = 12;
        baseAim = 65;
        baseWill = 30;
        baseHacking = 0;
        basePsi = 0;
        
        primaryAbilities.add("Standard Shot");
        primaryAbilities.add("Overwatch");
        
        allowedWeapons.add("Assault Rifle");
        allowedArmor.add("Light Armor");
        allowedItems.add("Frag Grenade");
    }
    
    private void setSoldierProperties() {
        name = "Soldier";
        description = "Standard combat soldier";
        baseHealth = 5;
        baseMobility = 12;
        baseAim = 70;
        baseWill = 35;
        baseHacking = 0;
        basePsi = 0;
        
        primaryAbilities.add("Standard Shot");
        primaryAbilities.add("Overwatch");
        primaryAbilities.add("Suppression");
        
        allowedWeapons.add("Assault Rifle");
        allowedWeapons.add("Shotgun");
        allowedArmor.add("Light Armor");
        allowedArmor.add("Medium Armor");
        allowedItems.add("Frag Grenade");
        allowedItems.add("Smoke Grenade");
    }
    
    private void setRangerProperties() {
        name = "Ranger";
        description = "Melee and stealth specialist";
        baseHealth = 5;
        baseMobility = 14;
        baseAim = 70;
        baseWill = 35;
        baseHacking = 0;
        basePsi = 0;
        
        primaryAbilities.add("Bladestorm");
        primaryAbilities.add("Phantom");
        primaryAbilities.add("Shadowstep");
        primaryAbilities.add("Conceal");
        primaryAbilities.add("Rapid Fire");
        
        passiveAbilities.add("Stealth Movement");
        passiveAbilities.add("Melee Bonus");
        
        canUseStealth = true;
        canUseMelee = true;
        
        allowedWeapons.add("Shotgun");
        allowedWeapons.add("Sword");
        allowedArmor.add("Light Armor");
        allowedItems.add("Frag Grenade");
        allowedItems.add("Smoke Grenade");
    }
    
    private void setGrenadierProperties() {
        name = "Grenadier";
        description = "Explosive and heavy weapon specialist";
        baseHealth = 6;
        baseMobility = 10;
        baseAim = 65;
        baseWill = 30;
        baseHacking = 0;
        basePsi = 0;
        
        primaryAbilities.add("Grenade Launcher");
        primaryAbilities.add("Heavy Weapons");
        primaryAbilities.add("Suppression");
        primaryAbilities.add("Volatile Mix");
        
        passiveAbilities.add("Extra Grenades");
        passiveAbilities.add("Heavy Weapon Bonus");
        
        canUseHeavyWeapons = true;
        canUseGrenades = true;
        
        allowedWeapons.add("Assault Rifle");
        allowedWeapons.add("Heavy Weapon");
        allowedArmor.add("Medium Armor");
        allowedArmor.add("Heavy Armor");
        allowedItems.add("Frag Grenade");
        allowedItems.add("Acid Grenade");
        allowedItems.add("Flashbang");
    }
    
    private void setSpecialistProperties() {
        name = "Specialist";
        description = "Hacking and drone support specialist";
        baseHealth = 4;
        baseMobility = 12;
        baseAim = 70;
        baseWill = 40;
        baseHacking = 50;
        basePsi = 0;
        
        primaryAbilities.add("Hack");
        primaryAbilities.add("Medical Protocol");
        primaryAbilities.add("Combat Protocol");
        primaryAbilities.add("Drone Support");
        
        passiveAbilities.add("Hacking Bonus");
        passiveAbilities.add("Drone Control");
        
        canHack = true;
        canUseDrones = true;
        canUseMedkits = true;
        
        allowedWeapons.add("Assault Rifle");
        allowedWeapons.add("Pistol");
        allowedArmor.add("Light Armor");
        allowedItems.add("Medikit");
        allowedItems.add("Smoke Grenade");
    }
    
    private void setSharpshooterProperties() {
        name = "Sharpshooter";
        description = "Long-range precision specialist";
        baseHealth = 4;
        baseMobility = 10;
        baseAim = 85;
        baseWill = 35;
        baseHacking = 0;
        basePsi = 0;
        
        primaryAbilities.add("Squadsight");
        primaryAbilities.add("Deadeye");
        primaryAbilities.add("Kill Zone");
        primaryAbilities.add("Serial");
        
        passiveAbilities.add("Long Range Bonus");
        passiveAbilities.add("Critical Hit Bonus");
        
        allowedWeapons.add("Sniper Rifle");
        allowedWeapons.add("Pistol");
        allowedArmor.add("Light Armor");
        allowedItems.add("Frag Grenade");
        allowedItems.add("Smoke Grenade");
    }
    
    private void setPsiOperativeProperties() {
        name = "Psi Operative";
        description = "Advanced psionic abilities specialist";
        baseHealth = 5;
        baseMobility = 12;
        baseAim = 65;
        baseWill = 60;
        baseHacking = 0;
        basePsi = 50;
        
        primaryAbilities.add("Mind Control");
        primaryAbilities.add("Psionic Storm");
        primaryAbilities.add("Insanity");
        primaryAbilities.add("Domination");
        primaryAbilities.add("Void Rift");
        
        passiveAbilities.add("Psionic Resistance");
        passiveAbilities.add("Psi Energy Regeneration");
        
        canUsePsionics = true;
        
        allowedWeapons.add("Pistol");
        allowedWeapons.add("Psionic Amplifier");
        allowedArmor.add("Psi Armor");
        allowedItems.add("Psi Grenade");
        allowedItems.add("Mind Shield");
    }
    
    private void setTechnicalProperties() {
        name = "Technical";
        description = "Technical support and hacking";
        baseHealth = 5;
        baseMobility = 11;
        baseAim = 65;
        baseWill = 35;
        baseHacking = 40;
        basePsi = 0;
        
        primaryAbilities.add("Hack");
        primaryAbilities.add("Technical Support");
        primaryAbilities.add("Repair");
        primaryAbilities.add("Sabotage");
        
        passiveAbilities.add("Technical Bonus");
        passiveAbilities.add("Repair Speed");
        
        canHack = true;
        
        allowedWeapons.add("Assault Rifle");
        allowedWeapons.add("Pistol");
        allowedArmor.add("Light Armor");
        allowedItems.add("Tool Kit");
        allowedItems.add("Smoke Grenade");
    }
    
    private void setTechnicianProperties() {
        name = "Technician";
        description = "Advanced technical and hacking specialist";
        baseHealth = 5;
        baseMobility = 12;
        baseAim = 65;
        baseWill = 35;
        baseHacking = 60;
        basePsi = 0;
        
        primaryAbilities.add("Advanced Hack");
        primaryAbilities.add("System Override");
        primaryAbilities.add("Network Infiltration");
        primaryAbilities.add("Security Bypass");
        
        passiveAbilities.add("Advanced Hacking Bonus");
        passiveAbilities.add("System Access");
        
        canHack = true;
        
        allowedWeapons.add("Assault Rifle");
        allowedWeapons.add("Pistol");
        allowedArmor.add("Light Armor");
        allowedItems.add("Advanced Tool Kit");
        allowedItems.add("Hacking Device");
    }
    
    private void setMedicProperties() {
        name = "Medic";
        description = "Medical support and healing";
        baseHealth = 5;
        baseMobility = 12;
        baseAim = 65;
        baseWill = 45;
        baseHacking = 0;
        basePsi = 0;
        
        primaryAbilities.add("Heal");
        primaryAbilities.add("Stabilize");
        primaryAbilities.add("Medical Protocol");
        primaryAbilities.add("Revival Protocol");
        
        passiveAbilities.add("Medical Bonus");
        passiveAbilities.add("Healing Speed");
        
        canUseMedkits = true;
        
        allowedWeapons.add("Assault Rifle");
        allowedWeapons.add("Pistol");
        allowedArmor.add("Light Armor");
        allowedItems.add("Medikit");
        allowedItems.add("Stabilizer");
    }
    
    private void setScoutProperties() {
        name = "Scout";
        description = "Reconnaissance and stealth";
        baseHealth = 4;
        baseMobility = 15;
        baseAim = 70;
        baseWill = 35;
        baseHacking = 0;
        basePsi = 0;
        
        primaryAbilities.add("Conceal");
        primaryAbilities.add("Scout Movement");
        primaryAbilities.add("Reconnaissance");
        primaryAbilities.add("Stealth Attack");
        
        passiveAbilities.add("Stealth Bonus");
        passiveAbilities.add("Movement Bonus");
        
        canUseStealth = true;
        
        allowedWeapons.add("Assault Rifle");
        allowedWeapons.add("Pistol");
        allowedArmor.add("Light Armor");
        allowedItems.add("Smoke Grenade");
        allowedItems.add("Flashbang");
    }
    
    private void setHeavyProperties() {
        name = "Heavy";
        description = "Heavy weapons and suppression";
        baseHealth = 6;
        baseMobility = 8;
        baseAim = 60;
        baseWill = 30;
        baseHacking = 0;
        basePsi = 0;
        
        primaryAbilities.add("Heavy Weapon");
        primaryAbilities.add("Suppression");
        primaryAbilities.add("Area Suppression");
        primaryAbilities.add("Demolition");
        
        passiveAbilities.add("Heavy Weapon Bonus");
        passiveAbilities.add("Suppression Bonus");
        
        canUseHeavyWeapons = true;
        
        allowedWeapons.add("Heavy Weapon");
        allowedWeapons.add("Assault Rifle");
        allowedArmor.add("Heavy Armor");
        allowedItems.add("Frag Grenade");
        allowedItems.add("Acid Grenade");
    }
    
    private void setSniperProperties() {
        name = "Sniper";
        description = "Long-range precision shooting";
        baseHealth = 4;
        baseMobility = 10;
        baseAim = 90;
        baseWill = 35;
        baseHacking = 0;
        basePsi = 0;
        
        primaryAbilities.add("Squadsight");
        primaryAbilities.add("Deadeye");
        primaryAbilities.add("Kill Zone");
        primaryAbilities.add("Serial");
        
        passiveAbilities.add("Long Range Bonus");
        passiveAbilities.add("Critical Hit Bonus");
        
        allowedWeapons.add("Sniper Rifle");
        allowedWeapons.add("Pistol");
        allowedArmor.add("Light Armor");
        allowedItems.add("Frag Grenade");
        allowedItems.add("Smoke Grenade");
    }
    
    private void setAssaultProperties() {
        name = "Assault";
        description = "Close combat and mobility";
        baseHealth = 5;
        baseMobility = 14;
        baseAim = 70;
        baseWill = 35;
        baseHacking = 0;
        basePsi = 0;
        
        primaryAbilities.add("Run and Gun");
        primaryAbilities.add("Close Combat");
        primaryAbilities.add("Rapid Fire");
        primaryAbilities.add("Lightning Reflexes");
        
        passiveAbilities.add("Close Combat Bonus");
        passiveAbilities.add("Mobility Bonus");
        
        canUseMelee = true;
        
        allowedWeapons.add("Shotgun");
        allowedWeapons.add("Assault Rifle");
        allowedArmor.add("Light Armor");
        allowedItems.add("Frag Grenade");
        allowedItems.add("Flashbang");
    }
    
    private void setSupportProperties() {
        name = "Support";
        description = "Support and utility abilities";
        baseHealth = 5;
        baseMobility = 12;
        baseAim = 65;
        baseWill = 40;
        baseHacking = 20;
        basePsi = 0;
        
        primaryAbilities.add("Support Protocol");
        primaryAbilities.add("Medical Protocol");
        primaryAbilities.add("Hack");
        primaryAbilities.add("Suppression");
        
        passiveAbilities.add("Support Bonus");
        passiveAbilities.add("Utility Bonus");
        
        canHack = true;
        canUseMedkits = true;
        
        allowedWeapons.add("Assault Rifle");
        allowedWeapons.add("Pistol");
        allowedArmor.add("Light Armor");
        allowedItems.add("Medikit");
        allowedItems.add("Smoke Grenade");
    }
    
    private void setCommandoProperties() {
        name = "Commando";
        description = "Special operations specialist";
        baseHealth = 6;
        baseMobility = 13;
        baseAim = 75;
        baseWill = 40;
        baseHacking = 10;
        basePsi = 0;
        
        primaryAbilities.add("Special Operations");
        primaryAbilities.add("Stealth Attack");
        primaryAbilities.add("Tactical Movement");
        primaryAbilities.add("Combat Expertise");
        
        passiveAbilities.add("Special Operations Bonus");
        passiveAbilities.add("Tactical Bonus");
        
        canUseStealth = true;
        canUseMelee = true;
        canHack = true;
        
        allowedWeapons.add("Assault Rifle");
        allowedWeapons.add("Shotgun");
        allowedWeapons.add("Pistol");
        allowedArmor.add("Light Armor");
        allowedArmor.add("Medium Armor");
        allowedItems.add("Frag Grenade");
        allowedItems.add("Smoke Grenade");
        allowedItems.add("Flashbang");
    }
    
    private void setEngineerProperties() {
        name = "Engineer";
        description = "Engineering and robotic specialist";
        baseHealth = 5;
        baseMobility = 11;
        baseAim = 65;
        baseWill = 35;
        baseHacking = 30;
        basePsi = 0;
        
        primaryAbilities.add("Repair");
        primaryAbilities.add("Sabotage");
        primaryAbilities.add("Technical Support");
        primaryAbilities.add("Robotic Control");
        
        passiveAbilities.add("Engineering Bonus");
        passiveAbilities.add("Repair Speed");
        
        canHack = true;
        
        allowedWeapons.add("Assault Rifle");
        allowedWeapons.add("Pistol");
        allowedArmor.add("Light Armor");
        allowedItems.add("Tool Kit");
        allowedItems.add("Repair Kit");
    }
    
    /**
     * Get total health including bonuses
     */
    public int getTotalHealth() {
        return baseHealth + healthBonus;
    }
    
    /**
     * Get total mobility including bonuses
     */
    public int getTotalMobility() {
        return baseMobility + mobilityBonus;
    }
    
    /**
     * Get total aim including bonuses
     */
    public int getTotalAim() {
        return baseAim + aimBonus;
    }
    
    /**
     * Get total will including bonuses
     */
    public int getTotalWill() {
        return baseWill + willBonus;
    }
    
    /**
     * Get total hacking including bonuses
     */
    public int getTotalHacking() {
        return baseHacking + hackingBonus;
    }
    
    /**
     * Get total psi including bonuses
     */
    public int getTotalPsi() {
        return basePsi + psiBonus;
    }
    
    /**
     * Check if soldier can use a specific weapon
     */
    public boolean canUseWeapon(String weaponType) {
        return allowedWeapons.contains(weaponType);
    }
    
    /**
     * Check if soldier can use a specific armor
     */
    public boolean canUseArmor(String armorType) {
        return allowedArmor.contains(armorType);
    }
    
    /**
     * Check if soldier can use a specific item
     */
    public boolean canUseItem(String itemType) {
        return allowedItems.contains(itemType);
    }
    
    /**
     * Get all abilities for this soldier class
     */
    public List<String> getAllAbilities() {
        List<String> allAbilities = new ArrayList<>();
        allAbilities.addAll(primaryAbilities);
        allAbilities.addAll(secondaryAbilities);
        allAbilities.addAll(passiveAbilities);
        allAbilities.addAll(uniqueAbilities);
        return allAbilities;
    }
} 
