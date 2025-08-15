package com.aliensattack.visualization;

import com.aliensattack.visualization.models.*;
import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.CoverObject;
import com.aliensattack.field.TacticalField;
import com.aliensattack.core.enums.UnitType;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Enhanced 3D Visualization with Animated Models
 * Provides immersive 3D battlefield visualization with animated soldiers and aliens
 */
public class Enhanced3DVisualizer extends Application {
    
    private TacticalField field;
    private Group root;
    private Camera camera;
    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private final Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
    
    // Animated model mappings
    private Map<Unit, AnimatedModel> unitModels = new HashMap<>();
    private Map<CoverObject, Box> coverBoxes = new HashMap<>();
    private Map<Position, Box> terrainBoxes = new HashMap<>();
    private Map<Position, Box> moveIndicators = new HashMap<>();
    
    // Interaction state
    private Unit selectedUnit = null;
    private AnimatedModel selectedModel = null;
    
    // Materials for environment
    private final PhongMaterial coverMaterial = new PhongMaterial();
    private final PhongMaterial terrainMaterial = new PhongMaterial();
    private final PhongMaterial moveIndicatorMaterial = new PhongMaterial();
    private final PhongMaterial selectedUnitMaterial = new PhongMaterial();
    
    // Animation timer
    private AnimationTimer animationTimer;
    private long lastUpdateTime;
    
    public Enhanced3DVisualizer(TacticalField field) {
        this.field = field;
        initializeMaterials();
        setupAnimationTimer();
    }
    
    private void initializeMaterials() {
        coverMaterial.setDiffuseColor(Color.BROWN);
        coverMaterial.setSpecularColor(Color.SANDYBROWN);
        
        terrainMaterial.setDiffuseColor(Color.DARKGREEN);
        terrainMaterial.setSpecularColor(Color.FORESTGREEN);
        
        moveIndicatorMaterial.setDiffuseColor(Color.YELLOW);
        moveIndicatorMaterial.setSpecularColor(Color.GOLD);
        
        selectedUnitMaterial.setDiffuseColor(Color.WHITE);
        selectedUnitMaterial.setSpecularColor(Color.LIGHTGRAY);
    }
    
    private void setupAnimationTimer() {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdateTime == 0) {
                    lastUpdateTime = now;
                    return;
                }
                
                double deltaTime = (now - lastUpdateTime) / 1_000_000_000.0; // Convert to seconds
                lastUpdateTime = now;
                
                // Update all animated models
                for (AnimatedModel model : unitModels.values()) {
                    model.update(deltaTime);
                }
            }
        };
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("XCOM 2 Style Tactical Combat - Enhanced 3D Visualization");
        
        // Create 3D scene
        root = new Group();
        Scene scene = new Scene(root, 1400, 900, true);
        
        // Setup camera
        setupCamera();
        
        // Setup lighting
        setupLighting();
        
        // Setup terrain
        setupTerrain();
        
        // Setup units with animated models
        setupUnits();
        
        // Setup controls
        setupControls(scene);
        
        // Start animation timer
        animationTimer.start();
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void setupCamera() {
        camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-15);
        camera.setTranslateY(-5);
        camera.setTranslateX(0);
        
        root.getChildren().add(camera);
    }
    
    private void setupLighting() {
        // Ambient light
        AmbientLight ambientLight = new AmbientLight(Color.WHITE);
        
        // Directional light
        PointLight directionalLight = new PointLight(Color.WHITE);
        directionalLight.setTranslateY(-10);
        directionalLight.setTranslateZ(-5);
        
        root.getChildren().addAll(ambientLight, directionalLight);
    }
    
    private void setupTerrain() {
        // Create basic terrain grid
        for (int x = -5; x <= 5; x++) {
            for (int z = -5; z <= 5; z++) {
                Box terrainBox = new Box(1, 0.1, 1);
                terrainBox.setTranslateX(x);
                terrainBox.setTranslateY(2);
                terrainBox.setTranslateZ(z);
                terrainBox.setMaterial(terrainMaterial);
                
                Position pos = new Position(x, z, 0);
                terrainBoxes.put(pos, terrainBox);
                root.getChildren().add(terrainBox);
            }
        }
    }
    
    private void setupUnits() {
        // Create sample units for demonstration
        createSampleUnits();
        
        // Add all unit models to scene
        for (AnimatedModel model : unitModels.values()) {
            root.getChildren().add(model.getModelNode());
        }
    }
    
    private void createSampleUnits() {
        // Create sample soldier
        Unit soldier = createSampleSoldier();
        AnimatedModel soldierModel = AnimatedModelFactory.createModel(UnitType.SOLDIER);
        soldierModel.setPosition(0, 1, 0);
        unitModels.put(soldier, soldierModel);
        
        // Create sample alien
        Unit alien = createSampleAlien();
        AnimatedModel alienModel = AnimatedModelFactory.createModel(UnitType.ALIEN);
        alienModel.setPosition(3, 1, 2);
        unitModels.put(alien, alienModel);
        
        // Create additional units for demonstration
        createAdditionalUnits();
    }
    
    private Unit createSampleSoldier() {
        // This would normally come from the game state
        // For demo purposes, creating a mock unit
        return new Unit("Sample Soldier", 100, 3, 2, 25, UnitType.SOLDIER);
    }
    
    private Unit createSampleAlien() {
        return new Unit("Sample Alien", 80, 4, 3, 30, UnitType.ALIEN);
    }
    
    private void createAdditionalUnits() {
        // Create more units for a richer scene
        for (int i = 0; i < 3; i++) {
            Unit soldier = createSampleSoldier();
            AnimatedModel model = AnimatedModelFactory.createModel(UnitType.SOLDIER);
            model.setPosition(-2 + i, 1, -2 + i);
            unitModels.put(soldier, model);
        }
        
        for (int i = 0; i < 2; i++) {
            Unit alien = createSampleAlien();
            AnimatedModel model = AnimatedModelFactory.createModel(UnitType.ALIEN);
            model.setPosition(4 + i, 1, 1 + i);
            unitModels.put(alien, model);
        }
    }
    
    private void setupControls(Scene scene) {
        // Mouse controls for camera
        scene.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = rotateX.getAngle();
            anchorAngleY = rotateY.getAngle();
        });
        
        scene.setOnMouseDragged(event -> {
            rotateX.setAngle(anchorAngleX - (anchorY - event.getSceneY()));
            rotateY.setAngle(anchorAngleY + anchorX - event.getSceneX());
        });
        
        // Keyboard controls
        scene.setOnKeyPressed(event -> handleKeyPress(event));
        
        // Mouse click for unit selection
        scene.setOnMouseClicked(event -> handleMouseClick(event));
    }
    
    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case W:
                camera.setTranslateZ(camera.getTranslateZ() + 1);
                break;
            case S:
                camera.setTranslateZ(camera.getTranslateZ() - 1);
                break;
            case A:
                camera.setTranslateX(camera.getTranslateX() - 1);
                break;
            case D:
                camera.setTranslateX(camera.getTranslateX() + 1);
                break;
            case Q:
                camera.setTranslateY(camera.getTranslateY() + 1);
                break;
            case E:
                camera.setTranslateY(camera.getTranslateY() - 1);
                break;
            case SPACE:
                // Trigger random animation on selected unit
                if (selectedModel != null) {
                    triggerRandomAnimation(selectedModel);
                }
                break;
            case DIGIT1:
                // Play idle animation
                if (selectedModel != null) {
                    selectedModel.playAnimation(AnimationType.IDLE);
                }
                break;
            case DIGIT2:
                // Play walk animation
                if (selectedModel != null) {
                    selectedModel.playAnimation(AnimationType.WALK);
                }
                break;
            case DIGIT3:
                // Play attack animation
                if (selectedModel != null) {
                    selectedModel.playAnimation(AnimationType.ATTACK_RANGED);
                }
                break;
            default:
                break;
        }
    }
    
    private void handleMouseClick(MouseEvent event) {
        // Simple unit selection - could be enhanced with ray casting
        if (event.getClickCount() == 1) {
            // For demo purposes, cycle through units
            cycleUnitSelection();
        }
    }
    
    private void cycleUnitSelection() {
        List<AnimatedModel> models = new ArrayList<>(unitModels.values());
        if (models.isEmpty()) return;
        
        if (selectedModel == null) {
            selectedModel = models.get(0);
        } else {
            int currentIndex = models.indexOf(selectedModel);
            int nextIndex = (currentIndex + 1) % models.size();
            selectedModel = models.get(nextIndex);
        }
        
        // Highlight selected unit
        highlightSelectedUnit();
        
        System.out.println("Selected unit model: " + selectedModel.getClass().getSimpleName());
    }
    
    private void highlightSelectedUnit() {
        // Remove previous highlighting
        for (AnimatedModel model : unitModels.values()) {
            model.getModelNode().setEffect(null);
        }
        
        // Highlight selected unit
        if (selectedModel != null) {
            // Apply glow effect or other highlighting
            selectedModel.getModelNode().setEffect(new javafx.scene.effect.Glow(0.8));
        }
    }
    
    private void triggerRandomAnimation(AnimatedModel model) {
        AnimationType[] animations = AnimationType.values();
        AnimationType randomAnimation = animations[(int) (Math.random() * animations.length)];
        model.playAnimation(randomAnimation);
        System.out.println("Playing random animation: " + randomAnimation);
    }
    
    public void addUnit(Unit unit, AnimatedModel model) {
        unitModels.put(unit, model);
        if (root != null) {
            root.getChildren().add(model.getModelNode());
        }
    }
    
    public void removeUnit(Unit unit) {
        AnimatedModel model = unitModels.remove(unit);
        if (model != null && root != null) {
            root.getChildren().remove(model.getModelNode());
        }
    }
    
    public void updateUnitPosition(Unit unit, Position newPosition) {
        AnimatedModel model = unitModels.get(unit);
        if (model != null) {
            model.setPosition(newPosition.getX(), newPosition.getY(), newPosition.getHeight());
        }
    }
    
    public void playUnitAnimation(Unit unit, AnimationType animationType) {
        AnimatedModel model = unitModels.get(unit);
        if (model != null) {
            model.playAnimation(animationType);
        }
    }
    
    @Override
    public void stop() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
        
        // Stop all animations
        for (AnimatedModel model : unitModels.values()) {
            model.stopAnimation();
        }
    }
}
