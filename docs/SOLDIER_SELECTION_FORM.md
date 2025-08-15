# Soldier Selection and Equipment Form

## Overview

The Soldier Selection and Equipment Form is a comprehensive UI component for the AliensAttack project that allows players to select soldiers and equip them with weapons, armor, and explosives before missions.

## Features

### Soldier Selection
- **Available Soldiers List**: Displays all available soldiers with their class, rank, and experience
- **Soldier Information Panel**: Shows detailed stats including health, movement, attack range, and abilities
- **Class-based Abilities**: Each soldier class has unique abilities that are displayed

### Equipment Selection
- **Weapon Selection**: Choose from various weapon types (rifles, sniper rifles, heavy weapons, etc.)
- **Armor Selection**: Select armor based on protection level and weight
- **Explosive Selection**: Multiple selection of grenades and explosives
- **Equipment Information**: Detailed stats for all selected equipment

### User Interface
- **Three-Panel Layout**: 
  - Left: Soldier selection and info
  - Center: Equipment selection (weapons, armor, explosives)
  - Right: Equipment information and confirmation buttons
- **Real-time Updates**: Information panels update as selections are made
- **Validation**: Confirm button only enabled when all required selections are made

## Technical Implementation

### Architecture
- **Swing-based UI**: Built using Java Swing for cross-platform compatibility
- **MVC Pattern**: Separates UI logic from data models
- **Custom Cell Renderers**: Enhanced list displays with formatted information

### Key Classes
- `SoldierSelectionForm`: Main form component
- `Soldier`: Soldier model with class-specific abilities
- `Weapon`: Weapon model with damage, accuracy, and range stats
- `Armor`: Armor model with protection and health values
- `Explosive`: Explosive model with damage and radius

### Data Flow
1. **Sample Data Loading**: Form loads with sample soldiers and equipment
2. **Selection Events**: User selections trigger information updates
3. **Equipment Assignment**: Selected equipment is assigned to the soldier
4. **Confirmation**: Final confirmation equips the soldier for the mission

## Usage

### Running the Application
```bash
# Build the project
mvn clean compile

# Run the main application
mvn exec:java -Dexec.mainClass="com.aliensattack.AliensAttackApplication"
```

### Using the Form
1. **Select a Soldier**: Click on a soldier from the left panel
2. **Choose Equipment**: Select weapon, armor, and explosives from center panels
3. **Review Information**: Check equipment details in the right panel
4. **Confirm Selection**: Click "Подтвердить выбор" when ready
5. **Reset if Needed**: Use "Отмена" to clear all selections

## Sample Data

### Available Soldiers
- **Сержант Иванов** (Ranger, Level 3) - Stealth specialist
- **Капрал Петров** (Specialist, Level 2) - Technical support
- **Рядовой Сидоров** (Heavy, Level 4) - Heavy weapons expert
- **Снайпер Козлов** (Sharpshooter, Level 3) - Long-range specialist

### Available Weapons
- **Автомат АК-12** (Rifle) - Balanced assault rifle
- **Снайперская винтовка СВД** (Sniper Rifle) - Long-range precision
- **Пулемет ПКМ** (Heavy Weapon) - Sustained fire support
- **Дробовик Сайга** (Shotgun) - Close combat specialist
- **Гранатомет РПГ-7** (Grenade Launcher) - Area effect weapon

### Available Armor
- **Легкая броня** (Light Armor) - Minimal protection, high mobility
- **Средняя броня** (Medium Armor) - Balanced protection and mobility
- **Тяжелая броня** (Heavy Armor) - Maximum protection, reduced mobility
- **Пластинчатая броня** (Plated Armor) - High protection with moderate mobility

### Available Explosives
- **Ф-1** (Grenade) - High damage fragmentation grenade
- **РГД-5** (Grenade) - Standard fragmentation grenade
- **Дымовая граната** (Smoke Grenade) - Tactical smoke screen
- **Световая граната** (Flashbang) - Stun and disorient

## Integration

### With Main Game
The form is designed to integrate with the main AliensAttack game system:
- **Mission Preparation**: Used before starting tactical missions
- **Squad Management**: Part of the larger squad management system
- **Equipment Tracking**: Integrates with equipment degradation and maintenance systems

### Future Enhancements
- **Equipment Loadouts**: Save and load equipment configurations
- **Soldier Experience**: Track mission performance and progression
- **Equipment Maintenance**: Integrate with maintenance and repair systems
- **Mission Requirements**: Validate equipment against mission objectives

## Technical Requirements

### Dependencies
- Java 21+
- Swing UI framework
- Lombok for boilerplate reduction
- Maven for build management

### Build Configuration
```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.30</version>
    <scope>provided</scope>
</dependency>
```

## Troubleshooting

### Common Issues
1. **Build Errors**: Ensure Java 21 is in PATH and Maven is properly configured
2. **UI Display Issues**: Check system look and feel compatibility
3. **Missing Dependencies**: Verify all required libraries are in the classpath

### Debug Information
The form includes console output for debugging:
- Initialization messages
- Selection change notifications
- Equipment assignment confirmations

## Contributing

### Code Standards
- Follow Java 21 conventions
- Use Lombok annotations where appropriate
- Maintain clean separation of concerns
- Add comprehensive JavaDoc comments

### Testing
- Unit tests for all business logic
- Integration tests for UI components
- Manual testing for user experience validation

---

This form represents a key component in the AliensAttack tactical combat system, providing players with intuitive control over their squad's equipment and preparation for missions.
