package com.aliensattack.core.model.components;

import com.aliensattack.core.model.Weapon;
import com.aliensattack.core.model.Armor;
import com.aliensattack.core.model.Explosive;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Component for managing unit equipment
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentManager {
    private Weapon weapon;
    private Armor armor;
    private List<Explosive> explosives;
    
    {
        this.explosives = new ArrayList<>();
    }
    
    public boolean hasWeapon() {
        return weapon != null;
    }
    
    public boolean hasArmor() {
        return armor != null;
    }
    
    public boolean hasExplosives() {
        return !explosives.isEmpty();
    }
    
    public void addExplosive(Explosive explosive) {
        if (explosive != null) {
            explosives.add(explosive);
        }
    }
    
    public void removeExplosive(Explosive explosive) {
        explosives.remove(explosive);
    }
    
    public int getArmorDamageReduction() {
        return hasArmor() ? armor.getDamageReduction() : 0;
    }
}
