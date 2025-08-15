package com.aliensattack.core.model;

import com.aliensattack.core.enums.SoldierClass;
import com.aliensattack.core.enums.UnitType;
import com.aliensattack.core.enums.StatusEffect;
import com.aliensattack.core.model.Weapon;
import com.aliensattack.core.model.Armor;
import com.aliensattack.core.data.StatusEffectData;
import com.aliensattack.core.data.AmmoTypeData;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.ArrayList;

/**
 * Soldier unit - human military personnel
 * Extends BaseUnit with soldier-specific abilities and equipment
 */
@Getter
@Setter
public class Soldier extends BaseUnit {
    
    private SoldierClass soldierClass;
    private List<SoldierAbility> abilities;
    private Weapon weapon;
    private Armor armor;
    private List<AmmoTypeData> ammunition; // Added ammunition storage
    private int experience;
    private int rank;
    private boolean isConcealed;
    private int psiStrength;
    private int psiResistance;
    
    public Soldier(String name, int maxHealth, int movementRange, int attackRange, int attackDamage) {
        super(name, maxHealth, movementRange, attackRange, attackDamage, UnitType.SOLDIER);
        this.soldierClass = null;
        this.abilities = new ArrayList<>();
        this.weapon = null;
        this.armor = null;
        this.ammunition = new ArrayList<>(); // Initialize ammunition list
        this.experience = 0;
        this.rank = 1;
        this.isConcealed = false;
        this.psiStrength = 0;
        this.psiResistance = 0;
    }
    
    public Soldier(String name, int maxHealth, int movementRange, int attackRange, int attackDamage, SoldierClass soldierClass) {
        this(name, maxHealth, movementRange, attackRange, attackDamage);
        this.soldierClass = soldierClass;
        initializeClassAbilities();
    }
    
    /**
     * Initialize abilities based on soldier class
     */
    private void initializeClassAbilities() {
        if (soldierClass == null) return;
        
        switch (soldierClass) {
            case RANGER -> abilities.add(new SoldierAbility("Bladestorm", "Reactive melee attack", 0, 0));
            case SHARPSHOOTER -> abilities.add(new SoldierAbility("Squadsight", "Extended range vision", 0, 0));
            case HEAVY -> abilities.add(new SoldierAbility("Suppression", "Area suppression fire", 0, 0));
            case SPECIALIST -> abilities.add(new SoldierAbility("Medical Protocol", "Healing abilities", 0, 0));
            case GRENADIER -> abilities.add(new SoldierAbility("Grenade Launcher", "Enhanced explosive range", 0, 0));
            case MEDIC -> abilities.add(new SoldierAbility("Medical Protocol", "Advanced healing abilities", 0, 0));
            case TECHNICIAN -> abilities.add(new SoldierAbility("Hacking Protocol", "Enhanced hacking abilities", 0, 0));
            case ENGINEER -> abilities.add(new SoldierAbility("Engineering Protocol", "Robotic and technical abilities", 0, 0));
            case PSI_OPERATIVE -> abilities.add(new SoldierAbility("Psionic Protocol", "Psionic abilities", 0, 0));
            case TECHNICAL -> abilities.add(new SoldierAbility("Technical Protocol", "Technical support abilities", 0, 0));
            case SCOUT -> abilities.add(new SoldierAbility("Scout Protocol", "Enhanced reconnaissance abilities", 0, 0));
            case SNIPER -> abilities.add(new SoldierAbility("Sniper Protocol", "Enhanced long-range abilities", 0, 0));
            case ASSAULT -> abilities.add(new SoldierAbility("Assault Protocol", "Enhanced close combat abilities", 0, 0));
            case SUPPORT -> abilities.add(new SoldierAbility("Support Protocol", "Enhanced support abilities", 0, 0));
            case COMMANDO -> abilities.add(new SoldierAbility("Commando Protocol", "Special operations abilities", 0, 0));
            case ROOKIE, SOLDIER -> abilities.add(new SoldierAbility("Basic Training", "Standard soldier abilities", 0, 0));
        }
    }
    
    /**
     * Add experience and potentially rank up
     */
    public void addExperience(int amount) {
        experience += amount;
        checkRankUp();
    }
    
    /**
     * Check if soldier should rank up
     */
    private void checkRankUp() {
        int requiredExp = rank * 100; // Simple progression
        if (experience >= requiredExp) {
            rankUp();
        }
    }
    
    /**
     * Increase soldier rank
     */
    private void rankUp() {
        rank++;
        // Increase stats
        maxHealth += 5;
        currentHealth = Math.min(currentHealth + 5, maxHealth);
        attackDamage += 2;
        
        // Add new ability based on class
        addClassAbility();
    }
    
    /**
     * Add new ability based on soldier class and rank
     */
    private void addClassAbility() {
        if (soldierClass == null) return;
        
        String abilityName = switch (soldierClass) {
            case RANGER -> "Run and Gun";
            case SHARPSHOOTER -> "Deadeye";
            case HEAVY -> "Chain Shot";
            case SPECIALIST -> "Combat Protocol";
            case GRENADIER -> "Chain Shot";
            case MEDIC -> "Medical Protocol";
            case TECHNICIAN -> "Hacking Protocol";
            case ENGINEER -> "Engineering Protocol";
            case ROOKIE -> "Basic Training";
            case SOLDIER -> "Standard Training";
            case PSI_OPERATIVE -> "Psionic Training";
            case TECHNICAL -> "Technical Training";
            case SCOUT -> "Scouting Protocol";
            case SNIPER -> "Sniper Training";
            case ASSAULT -> "Assault Training";
            case SUPPORT -> "Support Protocol";
            case COMMANDO -> "Commando Training";
        };
        
        abilities.add(new SoldierAbility(abilityName, "Rank " + rank + " ability", 0, 0));
    }
    
    /**
     * Equip weapon
     */
    public void equipWeapon(Weapon weapon) {
        this.weapon = weapon;
        if (weapon != null) {
            // Update attack stats based on weapon
            this.attackDamage = weapon.getBaseDamage();
            this.attackRange = weapon.getRange();
        }
    }
    
    /**
     * Equip armor
     */
    public void equipArmor(Armor armor) {
        this.armor = armor;
        if (armor != null) {
            // Update health and defense based on armor
            this.maxHealth += armor.getMaxHealth();
            this.currentHealth += armor.getMaxHealth();
            this.defense += armor.getDamageReduction();
        }
    }
    
    /**
     * Conceal the soldier
     */
    public boolean conceal() {
        if (!canConceal()) return false;
        
        isConcealed = true;
        return true;
    }
    
    /**
     * Reveal the soldier
     */
    public void reveal() {
        isConcealed = false;
    }
    
    /**
     * Check if soldier can conceal
     */
    public boolean canConceal() {
        return !isConcealed && !hasStatusEffect(com.aliensattack.core.enums.StatusEffect.SUPPRESSED);
    }
    
    /**
     * Get stealth attack bonus
     */
    public int getStealthAttackBonus() {
        return isConcealed ? 25 : 0; // 25% bonus when concealed
    }
    
    /**
     * Get total attack damage including weapon and bonuses
     */
    @Override
    public int getAttackDamage() {
        int baseDamage = super.getAttackDamage();
        int weaponBonus = weapon != null ? weapon.getBaseDamage() - baseDamage : 0;
        int stealthBonus = getStealthAttackBonus();
        
        return baseDamage + weaponBonus + stealthBonus;
    }
    
    /**
     * Get total defense including armor
     */
    @Override
    public int getDefense() {
        int baseDefense = super.getDefense();
        int armorBonus = armor != null ? armor.getDamageReduction() : 0;
        
        return baseDefense + armorBonus;
    }
    
    /**
     * Check if soldier can perform special abilities
     */
    public boolean canPerformAbility(String abilityName) {
        return abilities.stream()
                .anyMatch(ability -> ability.getName().equals(abilityName) && ability.getCooldown() == 0);
    }
    
    /**
     * Use a special ability
     */
    public boolean useAbility(String abilityName) {
        SoldierAbility ability = abilities.stream()
                .filter(a -> a.getName().equals(abilityName))
                .findFirst()
                .orElse(null);
        
        if (ability == null || ability.getCooldown() > 0) {
            return false;
        }
        
        // Set cooldown
        ability.setCooldown(3); // 3 turn cooldown
        
        return true;
    }
    
    /**
     * Process ability cooldowns
     */
    public void processAbilityCooldowns() {
        abilities.forEach(ability -> {
            if (ability.getCooldown() > 0) {
                ability.setCooldown(ability.getCooldown() - 1);
            }
        });
    }
    
    /**
     * Add ammunition to soldier's inventory
     */
    public void addAmmunition(AmmoTypeData ammo) {
        if (ammo != null) {
            this.ammunition.add(ammo);
        }
    }
    
    /**
     * Add multiple ammunition types
     */
    public void addAmmunition(List<AmmoTypeData> ammoList) {
        if (ammoList != null) {
            this.ammunition.addAll(ammoList);
        }
    }
    
    /**
     * Remove ammunition from soldier's inventory
     */
    public void removeAmmunition(AmmoTypeData ammo) {
        this.ammunition.remove(ammo);
    }
    
    /**
     * Clear all ammunition
     */
    public void clearAmmunition() {
        this.ammunition.clear();
    }
    
    /**
     * Get ammunition by type
     */
    public List<AmmoTypeData> getAmmunitionByType(com.aliensattack.core.enums.AmmoType type) {
        return this.ammunition.stream()
                .filter(ammo -> ammo.getType() == type)
                .collect(java.util.stream.Collectors.toList());
    }
    
    /**
     * Get total ammunition count
     */
    public int getAmmunitionCount() {
        return this.ammunition.size();
    }
    
    /**
     * Check if soldier has ammunition
     */
    public boolean hasAmmunition() {
        return !this.ammunition.isEmpty();
    }
    
    /**
     * Get ammunition summary
     */
    public String getAmmunitionSummary() {
        if (ammunition.isEmpty()) {
            return "Амуниция не выбрана";
        }
        
        StringBuilder summary = new StringBuilder();
        summary.append("Амуниция (").append(ammunition.size()).append(" типов):\n");
        
        for (AmmoTypeData ammo : ammunition) {
            summary.append("- ").append(ammo.getName())
                   .append(" (").append(ammo.getDamageBonus()).append(" бонус урона, ")
                   .append(ammo.getCurrentUses()).append(" шт.)\n");
        }
        
        return summary.toString();
    }
    
    @Override
    public String toString() {
        return String.format("Soldier[name='%s', class=%s, rank=%d, health=%d/%d, concealed=%s]",
                getName(), soldierClass, rank, getCurrentHealth(), getMaxHealth(), isConcealed);
    }
    
    // Implementation of missing IUnit abstract methods
    @Override
    public int getCriticalDamageMultiplier() { return 2; }
    
    @Override
    public boolean isSuppressed() { return hasStatusEffect(com.aliensattack.core.enums.StatusEffect.SUPPRESSED); }
    
    @Override
    public int getHeight() { return 1; }
    
    @Override
    public void setSuppressed(boolean suppressed) {
        if (suppressed) {
            addStatusEffect(new StatusEffectData(StatusEffect.SUPPRESSED, 2, 1));
        } else {
            removeStatusEffect(new StatusEffectData(StatusEffect.SUPPRESSED, 0, 0));
        }
    }
    
    @Override
    public void applySuppression(int turns) {
        addStatusEffect(new StatusEffectData(StatusEffect.SUPPRESSED, turns, 1));
    }
    
    @Override
    public int getTotalAttackRange() { return getAttackRange(); }
    
    @Override
    public boolean canPerformSpecialAbility() { return canPerformAbility("Bladestorm"); }
    
    @Override
    public void setCriticalDamageMultiplier(int multiplier) { /* Not used in Soldier */ }
    
    @Override
    public int getCriticalChance() { return 10; }
    
    @Override
    public int getTotalCriticalChance() { return getCriticalChance(); }
    
    @Override
    public int getSuppressionTurns() { 
        return getStatusEffects().stream()
            .filter(e -> e.getEffect() == com.aliensattack.core.enums.StatusEffect.SUPPRESSED)
            .findFirst()
            .map(StatusEffectData::getDuration)
            .orElse(0);
    }
    
    @Override
    public void setSuppressionTurns(int turns) {
        getStatusEffects().removeIf(e -> e.getEffect() == com.aliensattack.core.enums.StatusEffect.SUPPRESSED);
        if (turns > 0) {
            addStatusEffect(new StatusEffectData(StatusEffect.SUPPRESSED, turns, 1));
        }
    }
    
    @Override
    public void setHeight(int height) { /* Height not used for Soldiers */ }
    
    @Override
    public void removeSuppression() {
        getStatusEffects().removeIf(e -> e.getEffect() == com.aliensattack.core.enums.StatusEffect.SUPPRESSED);
    }
    
    @Override
    public void setCriticalChance(int chance) { /* Critical chance not modifiable for Soldiers */ }
}
