package com.aliensattack.actions;

import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.Weapon;
import com.aliensattack.core.enums.StatusEffect;
import com.aliensattack.core.model.StatusEffectData;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Представляет действие юнита в тактическом бою
 */
@Getter
@Setter
public class UnitAction {
    private ActionType actionType;
    private Unit performer;
    private Unit target;
    private Position targetPosition;
    private String description;
    private boolean successful;
    private int damage;
    private String result;
    private int actualActionPointCost; // Фактическая стоимость очков действия
    
    public UnitAction(ActionType actionType, Unit performer) {
        this.actionType = actionType;
        this.performer = performer;
        this.successful = false;
        this.damage = 0;
        this.actualActionPointCost = actionType.getActionPointCost();
    }
    
    public UnitAction(ActionType actionType, Unit performer, Unit target) {
        this(actionType, performer);
        this.target = target;
    }
    
    public UnitAction(ActionType actionType, Unit performer, Position targetPosition) {
        this(actionType, performer);
        this.targetPosition = targetPosition;
        
        // Рассчитываем фактическую стоимость для движения
        if (actionType == ActionType.MOVE && targetPosition != null) {
            this.actualActionPointCost = calculateMoveCost(performer, targetPosition);
        } else if (actionType == ActionType.OVERWATCH) {
            // Overwatch тратит все оставшиеся AP
            this.actualActionPointCost = (int)performer.getActionPoints();
        }
    }
    
    private int calculateMoveCost(Unit unit, Position targetPosition) {
        if (unit == null || targetPosition == null) {
            return actionType.getActionPointCost();
        }
        
        Position currentPos = unit.getPosition();
        int distance = Math.abs(currentPos.getX() - targetPosition.getX()) + 
                      Math.abs(currentPos.getY() - targetPosition.getY());
        
        // Базовая стоимость движения
        int baseCost = actionType.getActionPointCost();
        
        // Дополнительная стоимость за расстояние
        if (distance > 1) {
            baseCost += (distance - 1); // +1 AP за каждую дополнительную клетку
        }
        
        return Math.min(baseCost, (int)unit.getActionPoints()); // Не превышаем доступные AP
    }
    
    public boolean canPerform() {
        if (performer == null || !performer.isAlive()) {
            return false;
        }
        
        if (performer.getActionPoints() < actualActionPointCost) {
            return false;
        }
        
        return switch (actionType) {
            case MOVE, DASH -> targetPosition != null;
            case ATTACK, HEAL -> target != null && target.isAlive();
            case OVERWATCH, RELOAD, DEFEND, SPECIAL_ABILITY -> true;
            case GRENADE -> targetPosition != null;
        };
    }
    
    public void execute() {
        if (!canPerform()) {
            successful = false;
            result = "Действие не может быть выполнено";
            return;
        }
        
        // Тратим очки действия
        for (int i = 0; i < actualActionPointCost; i++) {
            performer.spendActionPoint();
        }
        
        switch (actionType) {
            case MOVE -> executeMove();
            case ATTACK -> executeAttack();
            case OVERWATCH -> executeOverwatch();
            case RELOAD -> executeReload();
            case HEAL -> executeHeal();
            case GRENADE -> executeGrenade();
            case SPECIAL_ABILITY -> executeSpecialAbility();
            case DEFEND -> executeDefend();
            case DASH -> executeDash();
        }
    }
    
    private void executeMove() {
        if (targetPosition != null) {
            Position oldPosition = performer.getPosition();
            // performer.setPosition(targetPosition.getX(), targetPosition.getY()); // УБРАНО!
            successful = true;
            result = performer.getName() + " переместился из " + oldPosition + 
                    " в позицию " + targetPosition + 
                    " (стоимость: " + actualActionPointCost + " AP)";
        } else {
            successful = false;
            result = "Ошибка: целевая позиция не указана";
        }
    }
    
    private void executeAttack() {
        if (target != null) {
            // Простой расчет атаки
            int hitChance = 70; // Базовый шанс попадания
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
        
        // Реализация логики наблюдения
        performer.setOverwatching(true);
        performer.setOverwatchChance(70); // 70% шанс срабатывания
        
        // Находим врагов в зоне видимости
        List<Unit> enemiesInRange = findEnemiesInRange(performer, 8); // 8 клеток дальности
        
        if (!enemiesInRange.isEmpty()) {
            result = performer.getName() + " ведет наблюдение за " + enemiesInRange.size() + 
                    " врагами (AP spent: ALL, Overwatch: 70%)";
        } else {
            result = performer.getName() + " ведет наблюдение (AP spent: ALL, Overwatch: 70%)";
        }
    }
    
    private void executeReload() {
        successful = true;
        
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
            int healAmount = 25;
            target.heal(healAmount);
            successful = true;
            result = performer.getName() + " лечит " + target.getName() + " на " + healAmount + " HP";
        }
    }
    
    private void executeGrenade() {
        if (targetPosition != null) {
            damage = 30;
            successful = true;
            result = performer.getName() + " бросает гранату в позицию " + targetPosition + 
                    " и наносит " + damage + " урона по области";
        }
    }
    
    private void executeSpecialAbility() {
        successful = true;
        String abilityName = switch (performer.getUnitType()) {
            case SOLDIER -> "Тактический выстрел";
            case ALIEN -> "Псионическая атака";
            case ALIEN_RULER -> "Мощная псионическая атака";
            case CIVILIAN -> "Первая помощь";
            case VEHICLE -> "Усиленная броня";
        };
        result = performer.getName() + " использует " + abilityName;
    }
    
    private void executeDefend() {
        successful = true;
        
        // Реализация системы защиты
        int defenseBonus = 20; // +20% защиты
        performer.setDefense(performer.getDefense() + defenseBonus);
        
        // Добавляем статус эффект защиты
        StatusEffectData defenseEffect = new StatusEffectData(
            StatusEffect.PROTECTED, 
            2, // 2 хода
            defenseBonus
        );
        performer.addStatusEffect(defenseEffect);
        
        result = performer.getName() + " занимает оборонительную позицию (+" + defenseBonus + "% защиты на 2 хода)";
    }
    
    private void executeDash() {
        if (targetPosition != null) {
            performer.setPosition(targetPosition.getX(), targetPosition.getY());
            successful = true;
            result = performer.getName() + " совершает рывок в позицию " + targetPosition;
        }
    }
    
    /**
     * Найти врагов в зоне видимости для overwatch
     */
    private List<Unit> findEnemiesInRange(Unit unit, int range) {
        List<Unit> enemies = new ArrayList<>();
        
        // Здесь должна быть логика поиска врагов через CombatManager
        // Пока возвращаем пустой список
        return enemies;
    }
    
    @Override
    public String toString() {
        return String.format("%s выполняет %s: %s", 
                performer.getName(), 
                actionType.getDisplayName(), 
                result != null ? result : "в процессе");
    }
}