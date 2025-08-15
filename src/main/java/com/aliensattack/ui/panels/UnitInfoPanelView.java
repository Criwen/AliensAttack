package com.aliensattack.ui.panels;

import com.aliensattack.core.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UnitInfoPanelView {
    private final JPanel panel;

    public UnitInfoPanelView(JPanel panel) {
        this.panel = panel;
    }

    public void render(Unit selectedUnit) {
        panel.removeAll();
        if (selectedUnit == null) {
            addLabel("No unit selected");
            panel.revalidate();
            panel.repaint();
            return;
        }

        addLabel("Name: " + selectedUnit.getName());
        addLabel("Health: " + selectedUnit.getCurrentHealth() + "/" + selectedUnit.getMaxHealth());

        Position position = selectedUnit.getPosition();
        if (position != null) {
            addLabel("Position: (" + position.getX() + ", " + position.getY() + ")");
        } else {
            addLabel("Position: Unknown");
        }

        addLabel("Unit Type: " + selectedUnit.getUnitType());
        addLabel("Action Points: " + selectedUnit.getActionPoints());

        Weapon weapon = selectedUnit.getWeapon();
        if (weapon != null) {
            addLabel("Weapon: " + weapon.getName());
            if (weapon.isAlienWeapon()) {
                addLabel("Type: ALIEN WEAPON");
                addLabel("Damage: " + weapon.getTotalAlienWeaponDamage() + " (Base: " + weapon.getBaseDamage() + " + " + weapon.getAlienWeaponDamageBonus() + ")");
                addLabel("Accuracy: " + weapon.getTotalAlienWeaponAccuracy() + "% (Base: " + weapon.getAccuracy() + "% + " + weapon.getAlienWeaponAccuracyBonus() + "%)");
                if (weapon.isPlasmaWeapon()) {
                    addLabel("Special: Plasma Technology");
                } else if (weapon.isLaserWeapon()) {
                    addLabel("Special: Laser Technology");
                }
            } else {
                addLabel("Damage: " + weapon.getBaseDamage());
                addLabel("Accuracy: " + weapon.getAccuracy() + "%");
            }
            addLabel("Ammo: " + weapon.getCurrentAmmo() + "/" + weapon.getAmmoCapacity());
        } else {
            addLabel("Weapon: Error displaying weapon info");
        }

        List<Explosive> explosives = selectedUnit.getExplosives();
        if (explosives != null && !explosives.isEmpty()) {
            addLabel("--- GRENADES ---");
            for (Explosive explosive : explosives) {
                addLabel(explosive.getName() + " (" + explosive.getDamage() + " dmg, " + explosive.getRadius() + " radius)");
            }
        }

        List<SoldierAbility> abilities = selectedUnit.getAbilities();
        if (abilities != null && !abilities.isEmpty()) {
            addLabel("--- ABILITIES ---");
            for (SoldierAbility ability : abilities) {
                addLabel(ability.getName() + " (AP: " + ability.getActionPointCost() + ")");
            }
        }

        if (selectedUnit.getSoldierClass() != null) {
            addLabel("Class: " + selectedUnit.getSoldierClass());
        }

        if (selectedUnit.isAlive() && selectedUnit.getActionPoints() > 1) {
            addLabel("Bladestorm: Active");
        }

        if (selectedUnit.isAlive() && selectedUnit.getActionPoints() == 2) {
            addLabel("Status: Concealed");
        }

        if (!selectedUnit.isAlive()) {
            addLabel("Status: Suppressed");
        }

        panel.revalidate();
        panel.repaint();
    }

    private void addLabel(String text) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
    }
}


