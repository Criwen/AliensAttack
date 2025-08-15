# Animated Models System for XCOM 2 Style Tactical Combat

## Overview

The Animated Models System provides sophisticated 3D animated models for soldiers and aliens in the XCOM 2 style tactical combat game. The system uses JavaFX 3D graphics and animation capabilities to create immersive, interactive 3D models with realistic animations.

## Architecture

### Core Components

#### 1. AnimatedModel Interface
- **Purpose**: Base interface for all animated 3D models
- **Key Methods**:
  - `getModelNode()`: Returns the JavaFX 3D node
  - `playAnimation(AnimationType)`: Plays specific animations
  - `setPosition(x, y, z)`: Sets 3D position
  - `setRotation(x, y, z)`: Sets 3D rotation
  - `update(deltaTime)`: Updates model state

#### 2. AbstractAnimatedModel
- **Purpose**: Abstract base class implementing common functionality
- **Features**:
  - Transform management (position, rotation, scale)
  - Animation timeline management
  - Common update loop
  - Material and node handling

#### 3. SoldierAnimatedModel
- **Purpose**: Humanoid soldier model with combat animations
- **Features**:
  - Humanoid body structure (torso, head, arms, legs, weapon)
  - Combat-specific animations (attack, reload, defend, overwatch)
  - Movement animations (walk, run, crouch)
  - Breathing and idle animations

#### 4. AlienAnimatedModel
- **Purpose**: Alien model with alien-specific animations
- **Features**:
  - Alien body structure (main body, head, tentacles, psionic core)
  - Psionic ability animations
  - Tentacle movement and attack animations
  - Death and status effect animations

#### 5. AnimationType Enum
- **Purpose**: Defines all available animation types
- **Categories**:
  - **Idle**: IDLE, IDLE_COMBAT, IDLE_RELAXED
  - **Movement**: WALK, RUN, CROUCH_WALK, CRAWL
  - **Combat**: ATTACK_MELEE, ATTACK_RANGED, RELOAD, THROW_GRENADE
  - **Defensive**: DEFEND, DODGE, TAKE_COVER
  - **Special**: PSIONIC_CAST, HEAL, OVERWATCH
  - **Status**: INJURED, STUNNED, MIND_CONTROLLED
  - **Death**: DEATH, DEATH_EXPLOSIVE
  - **Victory/Defeat**: VICTORY, DEFEAT

#### 6. AnimatedModelFactory
- **Purpose**: Factory class for creating appropriate models
- **Features**:
  - Unit type-based model creation
  - Soldier class-specific customizations
  - Alien type-specific customizations
  - Custom material support

## Animation System

### Timeline-Based Animations
- Uses JavaFX Timeline for smooth, keyframe-based animations
- Configurable duration and easing
- Automatic animation cycling and completion handling

### Animation Categories

#### Soldier Animations
1. **Idle Animations**
   - Breathing cycle with subtle torso scaling
   - Head movement for realism

2. **Movement Animations**
   - Walk cycle with alternating leg and arm movement
   - Run cycle with faster, more pronounced movements
   - Crouch and crawl for stealth

3. **Combat Animations**
   - Weapon recoil for ranged attacks
   - Reload sequence with weapon movement
   - Defensive crouching
   - Overwatch aiming stance

#### Alien Animations
1. **Idle Animations**
   - Psionic core pulsing
   - Tentacle wave movement
   - Alien breathing patterns

2. **Movement Animations**
   - Leg movement with body sway
   - Tentacle positioning during movement

3. **Combat Animations**
   - Tentacle attack sequences
   - Weapon recoil for ranged attacks
   - Psionic casting with core expansion

4. **Special Animations**
   - Mind control effects
   - Death sequences with body collapse

## Usage Examples

### Basic Model Creation
```java
// Create soldier model
AnimatedModel soldier = AnimatedModelFactory.createModel(UnitType.SOLDIER);

// Create alien model
AnimatedModel alien = AnimatedModelFactory.createModel(UnitType.ALIEN);

// Create soldier with specific class
SoldierAnimatedModel ranger = AnimatedModelFactory.createSoldierModel(SoldierClass.RANGER);
```

### Animation Control
```java
// Play specific animation
model.playAnimation(AnimationType.WALK);

// Stop current animation
model.stopAnimation();

// Check animation status
if (model.isAnimating()) {
    System.out.println("Model is currently animating: " + model.getCurrentAnimation());
}
```

### Position and Rotation
```java
// Set 3D position
model.setPosition(10.0, 0.0, 5.0);

// Set rotation (degrees)
model.setRotation(0.0, 45.0, 0.0);

// Get current position
double x = model.getPosX();
double y = model.getPosY();
double z = model.getPosZ();
```

### Integration with Game Systems
```java
// Update model based on game state
public void updateGameLoop(double deltaTime) {
    for (AnimatedModel model : unitModels.values()) {
        model.update(deltaTime);
    }
}

// Trigger animations based on game events
public void onUnitAttack(Unit unit) {
    AnimatedModel model = unitModels.get(unit);
    if (model != null) {
        model.playAnimation(AnimationType.ATTACK_RANGED);
    }
}
```


### Enhanced3DVisualizer
- **Purpose**: Full tactical combat visualization
- **Features**:
  - Integrated with game field
  - Unit management and positioning
  - Camera controls and lighting
  - Real-time animation updates

## Performance Considerations

### Optimization Strategies
1. **Object Pooling**: Reuse animation timelines and objects
2. **LOD System**: Level of detail based on camera distance
3. **Animation Caching**: Cache frequently used animations
4. **Efficient Updates**: Only update visible models

### Memory Management
- Proper disposal of animation timelines
- Efficient 3D node management
- Material and texture optimization

## Customization and Extension

### Adding New Animation Types
1. Extend `AnimationType` enum
2. Implement animation logic in model classes
3. Add animation parameters and keyframes
4. Update factory methods if needed

### Creating New Model Types
1. Extend `AbstractAnimatedModel`
2. Implement `initializeModelNode()` and `initializeMaterial()`
3. Override `createAnimationTimeline()` for custom animations
4. Add to `AnimatedModelFactory`

### Material Customization
```java
// Custom soldier material
PhongMaterial customMaterial = new PhongMaterial();
customMaterial.setDiffuseColor(Color.CUSTOM);
customMaterial.setSpecularColor(Color.CUSTOM_SPECULAR);

// Apply to model
soldierModel.setMaterial(customMaterial);
```

## Technical Requirements

### Dependencies
- Java 21
- JavaFX 21.0.2
- Lombok 1.18.30
- Log4j2 2.23.1

### System Requirements
- OpenGL 3.2+ compatible graphics card
- Minimum 4GB RAM
- JavaFX runtime environment

## Future Enhancements

### Planned Features
1. **Skeletal Animation**: Support for external 3D model formats
2. **Particle Effects**: Explosions, smoke, and environmental effects
3. **Advanced Materials**: Normal mapping, specular mapping
4. **Animation Blending**: Smooth transitions between animations
5. **Performance Profiling**: Built-in performance monitoring

### Integration Plans
1. **Combat System**: Direct integration with combat mechanics
2. **AI Behavior**: Animation-driven AI behavior system
3. **Multiplayer**: Synchronized animations across network
4. **Modding Support**: External animation and model loading

## Troubleshooting

### Common Issues
1. **Animation Not Playing**: Check if model is properly initialized
2. **Performance Issues**: Reduce number of active animations
3. **Material Problems**: Verify material properties and colors
4. **Position Issues**: Check coordinate system and transforms

### Debug Tools
- Log4j2 logging for animation events
- Console output for model selection
- Visual highlighting for selected models
- Performance metrics in console

## Conclusion

The Animated Models System provides a robust foundation for 3D visualization in the XCOM 2 style tactical combat game. With its modular architecture, comprehensive animation support, and easy extensibility, it enables developers to create immersive, interactive 3D experiences that enhance gameplay and visual appeal.

The system is designed to be performant, maintainable, and extensible, making it suitable for both current development needs and future enhancements.


