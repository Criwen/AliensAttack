package com.aliensattack.actions;

import com.aliensattack.core.config.GameConfig;
import com.aliensattack.core.data.StatusEffectData;
import com.aliensattack.core.enums.StatusEffect;
import com.aliensattack.core.enums.ActionType;
import com.aliensattack.core.interfaces.IUnit;
import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.Weapon;
import com.aliensattack.core.model.Position;

import java.util.List;

/**
 * Unit action implementation for XCOM 2 tactical combat
 */
public class UnitAction implements com.aliensattack.actions.interfaces.IAction {
    private final Unit performer;
    private final ActionType actionType;
    private final Unit target;
    private final Position targetPosition;
    private final int actionPointCost;
    private int damage;
    private boolean successful;
    private String result;
    
    public UnitAction(Unit performer, ActionType actionType, Unit target, Position targetPosition, int actionPointCost) {
        this.performer = performer;
        this.actionType = actionType;
        this.target = target;
        this.targetPosition = targetPosition;
        this.actionPointCost = actionPointCost;
        this.damage = 0;
        this.successful = false;
        this.result = "";
    }
    
    // Convenience constructors
    public UnitAction(Unit performer, ActionType actionType, Unit target, int actionPointCost) {
        this(performer, actionType, target, null, actionPointCost);
    }
    
    public UnitAction(Unit performer, ActionType actionType, Position targetPosition, int actionPointCost) {
        this(performer, actionType, null, targetPosition, actionPointCost);
    }
    
    public UnitAction(Unit performer, ActionType actionType, int actionPointCost) {
        this(performer, actionType, null, null, actionPointCost);
    }
    
    @Override
    public boolean canPerform() {
        if (performer == null || !performer.isAlive()) {
            return false;
        }
        
        if (performer.getActionPoints() < actionPointCost) {
            return false;
        }
        
        return switch (actionType) {
            case MOVE, DASH -> targetPosition != null;
            case ATTACK, HEAL -> target != null && target.isAlive();
            case OVERWATCH, RELOAD, DEFEND, SPECIAL_ABILITY -> true;
            case GRENADE -> targetPosition != null;
            default -> false;
        };
    }
    
    @Override
    public void execute() {
        if (!canPerform()) {
            successful = false;
            result = "Действие не может быть выполнено";
            return;
        }
        
        performer.spendActionPoints(actionPointCost);
        
        switch (actionType) {
            case MOVE -> executeMove();
            case ATTACK -> executeAttack();
            case DEFEND -> executeDefend();
            case HEAL -> executeHeal();
            case GRENADE -> executeGrenade();
            case OVERWATCH -> executeOverwatch();
            case RELOAD -> executeReload();
            case SPECIAL_ABILITY -> executeSpecialAbility();
            default -> {
                result = "Неизвестное действие: " + actionType;
                successful = false;
            }
        }
    }
    
    private void executeMove() {
        if (targetPosition != null) {
            // TODO: Implement proper movement validation and pathfinding
            // - Check if path is clear
            // - Validate movement range
            // - Handle different terrain types
            // - Apply movement penalties
            // - Update unit position on field
            successful = true;
            result = performer.getName() + " перемещается в позицию " + targetPosition;
        } else {
            successful = false;
            result = "Целевая позиция не указана";
        }
    }
    
    private void executeAttack() {
        if (target != null) {
            // TODO: Implement comprehensive attack calculation system
            // - Calculate hit chance based on weapon, distance, cover, status effects
            // - Apply critical hit calculations
            // - Handle different damage types (kinetic, energy, explosive)
            // - Apply armor and damage reduction
            // - Handle weapon degradation
            // - Apply status effects from attacks
            
            int hitChance = GameConfig.getBaseHitChance(); // Use configuration instead of hardcoded 70
            boolean hit = Math.random() * 100 < hitChance;
            
            if (hit) {
                damage = performer.getAttackDamage();
                boolean killed = target.takeDamage(damage);
                successful = true;
                result = performer.getName() + " атакует " + target.getName() + 
                        " и наносит " + damage + " урона" + (killed ? " (убит!)" : "");
            } else {
                successful = false;
                result = performer.getName() + " промахивается по " + target.getName();
            }
        }
    }
    
    private void executeOverwatch() {
        successful = true;
        // Тратим все оставшиеся очки действия
        double allAP = performer.getActionPoints();
        performer.spendActionPoints(allAP);
        
        // TODO: Implement comprehensive overwatch system
        // - Overwatch trigger conditions
        // - Reaction shot mechanics
        // - Overwatch accuracy penalties
        // - Multiple target handling
        // - Overwatch duration and cancellation
        
        // Реализация логики наблюдения
        performer.setOverwatching(true);
        performer.setOverwatchChance(GameConfig.getOverwatchChance()); // Use configuration instead of hardcoded 70
        
        // Находим врагов в зоне видимости
        List<Unit> enemiesInRange = findEnemiesInRange(performer, GameConfig.getOverwatchRange()); // Use configuration instead of hardcoded 8
        
        if (!enemiesInRange.isEmpty()) {
            result = performer.getName() + " ведет наблюдение за " + enemiesInRange.size() + 
                    " врагами (AP spent: ALL, Overwatch: " + GameConfig.getOverwatchChance() + "%)";
        } else {
            result = performer.getName() + " ведет наблюдение (AP spent: ALL, Overwatch: " + GameConfig.getOverwatchChance() + "%)";
        }
    }
    
    private void executeReload() {
        successful = true;
        
        // TODO: Implement comprehensive ammunition system
        // - Different ammo types and effects
        // - Ammo capacity management
        // - Reload time and action cost
        // - Ammo scarcity mechanics
        // - Special ammo effects
        
        // Реализация системы боеприпасов
        Weapon weapon = performer.getWeapon();
        if (weapon != null) {
            int ammoToReload = weapon.getAmmoCapacity() - weapon.getCurrentAmmo();
            weapon.setCurrentAmmo(weapon.getAmmoCapacity());
            
            result = performer.getName() + " перезаряжается (+" + ammoToReload + " патронов)";
        } else {
            result = performer.getName() + " не имеет оружия для перезарядки";
            successful = false;
        }
    }
    
    private void executeHeal() {
        if (target != null) {
            int healAmount = GameConfig.getHealAmount(); // Use configuration instead of hardcoded 25
            target.heal(healAmount);
            successful = true;
            result = performer.getName() + " лечит " + target.getName() + " на " + healAmount + " HP";
        }
        
        // TODO: Implement comprehensive medical system
        // - Medikit system with limited uses
        // - Medical stabilization for critically wounded soldiers
        // - Healing rate based on medic skill
        // - Medical priority system
        // - Injury recovery system
        // - Medical equipment degradation
        // - Different healing types (basic, advanced, psionic)
        // - Medical cooldowns and limitations
    }
    
    private void executeGrenade() {
        if (targetPosition != null) {
            damage = GameConfig.getGrenadeDamage(); // Use configuration instead of hardcoded 30
            successful = true;
            result = performer.getName() + " бросает гранату в позицию " + targetPosition + 
                    " и наносит " + damage + " урона по области";
        }
        
        // TODO: Implement comprehensive grenade and explosive system
        // - Different grenade types (Frag, Acid, Fire, Poison, Smoke, Flashbang)
        // - Area of effect damage calculation
        // - Environmental destruction
        // - Chain reactions
        // - Grenade launcher mechanics
        // - Throw distance and accuracy
        // - Grenade effects on different terrain
        // - Smoke and gas mechanics
    }
    
    private void executeSpecialAbility() {
        successful = true;
        String abilityName = switch (performer.getUnitType()) {
            case SOLDIER -> "Тактический выстрел";
            case ALIEN -> "Псионическая атака";
            case ALIEN_RULER -> "Мощная псионическая атака";
            case CHOSEN -> "Мощная псионическая атака";
            case CIVILIAN -> "Первая помощь";
            case VEHICLE -> "Усиленная броня";
            case ROBOTIC -> "Техническая атака";
        };
        result = performer.getName() + " использует " + abilityName;
        
        // TODO: Implement comprehensive special abilities system
        // - Psionic abilities (Mind Control, Teleport, Psychic Blast)
        // - Technical abilities (Hacking, Robot Control)
        // - Soldier class abilities (Ranger, Sharpshooter, Heavy, Specialist)
        // - Alien abilities (Evolution, Ruler abilities, Chosen abilities)
        // - Ability cooldowns and energy costs
        // - Ability progression and upgrades
        // - Ability combinations and synergies
        // - Ability failure and backlash effects
    }
    
    private void executeDefend() {
        successful = true;
        
        // TODO: Implement comprehensive defense system
        // - Different defense types (cover, armor, abilities)
        // - Defense stacking and diminishing returns
        // - Temporary defense bonuses
        // - Defense against different damage types
        // - Counter-attack mechanics
        
        // Реализация системы защиты
        int defenseBonus = GameConfig.getDefendBonus(); // Use configuration instead of hardcoded 20
        performer.setDefense(performer.getDefense() + defenseBonus);
        
        // Добавляем статус эффект защиты
        StatusEffectData defenseEffect = new StatusEffectData(
            StatusEffect.PROTECTED,
            GameConfig.getDefendDuration(), // Use configuration instead of hardcoded 2
            defenseBonus
        );
        
        // TODO: Implement proper status effect system
        // - Status effect application and removal
        // - Effect duration management
        // - Effect stacking and interactions
        // - Effect resistance and immunity
        // - Effect visualization and UI
        
        result = performer.getName() + " принимает оборонительную стойку (+" + defenseBonus + "% защиты на " + GameConfig.getDefendDuration() + " ход)";
    }
    
    private List<Unit> findEnemiesInRange(Unit unit, int range) {
        // TODO: Implement proper enemy detection system
        // - Line of sight calculations
        // - Cover and concealment effects
        // - Detection range modifiers
        // - Enemy identification and classification
        // - Stealth mechanics
        
        // Placeholder implementation
        return List.of();
    }
    
    @Override
    public boolean isSuccessful() {
        return successful;
    }
    
    @Override
    public String getResult() {
        return result;
    }
    
    @Override
    public int getDamage() {
        return damage;
    }
    
    @Override
    public String getActionType() {
        return actionType.name();
    }
    
    @Override
    public IUnit getPerformer() {
        return performer;
    }
    
    @Override
    public IUnit getTarget() {
        return target;
    }
    
    @Override
    public Position getTargetPosition() {
        return targetPosition;
    }
    
    @Override
    public int getActionPointCost() {
        return actionPointCost;
    }
    
    @Override
    public int getActualActionPointCost() {
        return actionPointCost;
    }
}