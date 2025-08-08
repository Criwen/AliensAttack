package com.aliensattack.core.model;

import com.aliensattack.core.enums.TerrainType;
import com.aliensattack.core.enums.CoverType;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a single tile on the tactical field
 * Contains unit, cover, and terrain information
 */
@Getter
@Setter
public class Tile {
    private int x, y;
    private Unit unit;
    private CoverObject coverObject;
    private TerrainType terrain;
    private boolean isHighGround;
    
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.terrain = TerrainType.GROUND;
        this.isHighGround = false;
    }
    
    public boolean isOccupied() {
        return unit != null;
    }
    
    public boolean hasCover() {
        return coverObject != null;
    }
    
    public CoverType getCoverType() {
        if (coverObject == null) return CoverType.NONE;
        return coverObject.getCoverType();
    }
    
    // Additional setter methods for compatibility
    public void setUnit(Unit unit) {
        this.unit = unit;
    }
    
    public void setCoverObject(CoverObject coverObject) {
        this.coverObject = coverObject;
    }
    
    public CoverObject getCoverObject() {
        return coverObject;
    }
} 