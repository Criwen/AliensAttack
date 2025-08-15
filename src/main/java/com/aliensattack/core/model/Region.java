package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.ArrayList;

/**
 * Region Model - XCOM2 Strategic Layer
 * Represents a geographical region with facilities and bonuses
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Region {
    
    private String id;
    private String name;
    private Type type;
    private int contactCost;
    private boolean contacted;
    private List<Facility> facilities;
    private double incomeBonus;
    private double researchBonus;
    private double constructionBonus;
    private int maxFacilities;
    private boolean isAccessible;
    
    public Region(String id, String name, Type type, int contactCost) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.contactCost = contactCost;
        this.contacted = false;
        this.facilities = new ArrayList<>();
        this.maxFacilities = calculateMaxFacilities(type);
        this.isAccessible = true;
        initializeBonuses();
    }
    
    public void addFacility(Facility facility) {
        if (canSupportFacility(facility)) {
            facilities.add(facility);
        }
    }
    
    public void removeFacility(Facility facility) {
        facilities.remove(facility);
    }
    
    public boolean canSupportFacility(Facility facility) {
        return facilities.size() < maxFacilities;
    }
    
    public int getAvailableFacilitySlots() {
        return maxFacilities - facilities.size();
    }
    
    public boolean hasFacility(String facilityType) {
        return facilities.stream()
                .anyMatch(f -> f.getType().equals(facilityType));
    }
    
    public List<Facility> getFacilitiesByType(String facilityType) {
        return facilities.stream()
                .filter(f -> f.getType().equals(facilityType))
                .toList();
    }
    
    private int calculateMaxFacilities(Type type) {
        return switch (type) {
            case URBAN -> 4;
            case RURAL -> 3;
            case INDUSTRIAL -> 5;
            case MILITARY -> 3;
            case RESEARCH -> 4;
            default -> 3;
        };
    }
    
    private void initializeBonuses() {
        switch (type) {
            case URBAN -> {
                incomeBonus = 25.0;
                researchBonus = 15.0;
            }
            case RURAL -> {
                incomeBonus = 15.0;
                constructionBonus = 20.0;
            }
            case INDUSTRIAL -> {
                incomeBonus = 35.0;
                constructionBonus = 30.0;
            }
            case MILITARY -> {
                incomeBonus = 20.0;
            }
            case RESEARCH -> {
                incomeBonus = 10.0;
                researchBonus = 25.0;
            }
        }
    }
    
    public double getTotalIncome() {
        return incomeBonus + facilities.stream()
                .mapToDouble(Facility::getIncomeBonus)
                .sum();
    }
    
    public double getTotalResearchBonus() {
        return researchBonus + facilities.stream()
                .mapToDouble(Facility::getResearchBonus)
                .sum();
    }
    
    public double getTotalConstructionBonus() {
        return constructionBonus + facilities.stream()
                .mapToDouble(Facility::getConstructionBonus)
                .sum();
    }
    
    /**
     * Region types with different bonuses
     */
    public enum Type {
        URBAN("Urban", "High population centers"),
        RURAL("Rural", "Agricultural and open areas"),
        INDUSTRIAL("Industrial", "Manufacturing and production"),
        MILITARY("Military", "Defense and security"),
        RESEARCH("Research", "Scientific and academic");
        
        private final String displayName;
        private final String description;
        
        Type(String displayName, String description) {
            this.displayName = displayName;
            this.description = description;
        }
        
        public String getDisplayName() {
            return displayName;
        }
        
        public String getDescription() {
            return description;
        }
    }
}
