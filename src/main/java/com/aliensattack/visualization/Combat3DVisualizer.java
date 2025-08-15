package com.aliensattack.visualization;

import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.CoverObject;
import com.aliensattack.field.TacticalField;
import com.aliensattack.combat.DefaultCombatManager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;

import java.util.Map;
import java.util.HashMap;

/**
 * 3D Visualization for XCOM 2 Style Tactical Combat System
 * Provides immersive 3D battlefield visualization with interactive controls
 */
public class Combat3DVisualizer extends Application {
    
    private TacticalField field;
    private Group root;
    private Camera camera;
    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private final Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
    
    // 3D object mappings
    private Map<Unit, Sphere> unitSpheres = new HashMap<>();
    private Map<CoverObject, Box> coverBoxes = new HashMap<>();
    private Map<Position, Box> terrainBoxes = new HashMap<>();
    private Map<Position, Sphere> moveIndicators = new HashMap<>();
    
    // Interaction state
    private Unit selectedUnit = null;
    
    // Materials for different unit types
    private final PhongMaterial soldierMaterial = new PhongMaterial();
    private final PhongMaterial alienMaterial = new PhongMaterial();
    private final PhongMaterial civilianMaterial = new PhongMaterial();
    private final PhongMaterial vehicleMaterial = new PhongMaterial();
    private final PhongMaterial coverMaterial = new PhongMaterial();
    private final PhongMaterial terrainMaterial = new PhongMaterial();
    private final PhongMaterial moveIndicatorMaterial = new PhongMaterial();
    private final PhongMaterial selectedUnitMaterial = new PhongMaterial();
    
    public Combat3DVisualizer(TacticalField field) {
        this.field = field;
        initializeMaterials();
    }
    
    private void initializeMaterials() {
        soldierMaterial.setDiffuseColor(Color.BLUE);
        soldierMaterial.setSpecularColor(Color.LIGHTBLUE);
        
        alienMaterial.setDiffuseColor(Color.RED);
        alienMaterial.setSpecularColor(Color.PINK);
        
        civilianMaterial.setDiffuseColor(Color.GREEN);
        civilianMaterial.setSpecularColor(Color.LIGHTGREEN);
        
        vehicleMaterial.setDiffuseColor(Color.GRAY);
        vehicleMaterial.setSpecularColor(Color.LIGHTGRAY);
        
        coverMaterial.setDiffuseColor(Color.BROWN);
        coverMaterial.setSpecularColor(Color.SANDYBROWN);
        
        terrainMaterial.setDiffuseColor(Color.DARKGREEN);
        terrainMaterial.setSpecularColor(Color.FORESTGREEN);
        
        // Материал для индикаторов перемещения
        moveIndicatorMaterial.setDiffuseColor(Color.YELLOW);
        moveIndicatorMaterial.setSpecularColor(Color.GOLD);
        
        // Материал для выбранного юнита
        selectedUnitMaterial.setDiffuseColor(Color.WHITE);
        selectedUnitMaterial.setSpecularColor(Color.LIGHTGRAY);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("XCOM 2 Style Tactical Combat - 3D Visualization");
        
        // Create 3D scene
        root = new Group();
        Scene scene = new Scene(root, 1200, 800, true);
        scene.setFill(Color.SKYBLUE);
        
        // Setup camera (стартовая позиция для большого поля 128x128)
        camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-200); // Отодвигаем камеру намного дальше для большого поля
        camera.setTranslateY(-150); // Поднимаем камеру выше
        camera.setTranslateX(0);     // Центрируем по X
        camera.setNearClip(0.1);
        camera.setFarClip(2000.0);   // Увеличиваем дальность видимости
        scene.setCamera(camera);
        

        
        // Add lighting
        setupLighting();
        
        // Create 3D battlefield
        create3DBattlefield();
        
        // Add controls
        setupControls(scene);
        
        // Add animation
        setupAnimation();
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void setupLighting() {
        // Ambient light for overall visibility
        AmbientLight ambientLight = new AmbientLight(Color.WHITE);
        root.getChildren().add(ambientLight);
        
        // Main directional light
        PointLight mainLight = new PointLight(Color.WHITE);
        mainLight.setTranslateX(20);
        mainLight.setTranslateY(-30);
        mainLight.setTranslateZ(-20);
        root.getChildren().add(mainLight);
        
        // Fill light from opposite side
        PointLight fillLight = new PointLight(Color.LIGHTBLUE);
        fillLight.setTranslateX(-20);
        fillLight.setTranslateY(-20);
        fillLight.setTranslateZ(20);
        root.getChildren().add(fillLight);
        
        System.out.println("Lighting setup complete");
    }
    
    private void create3DBattlefield() {
        System.out.println("Creating 3D battlefield...");
        System.out.println("Field size: " + field.getWidth() + "x" + field.getHeight());
        
        // Calculate center offset to center the field
        double centerOffsetX = -(field.getWidth() - 1) * 2.5 / 2.0;
        double centerOffsetZ = -(field.getHeight() - 1) * 2.5 / 2.0;
        
        System.out.println("Center offsets: X=" + centerOffsetX + ", Z=" + centerOffsetZ);
        
        // Create terrain grid with visible borders
        int terrainCount = 0;
        for (int x = 0; x < field.getWidth(); x++) {
            for (int y = 0; y < field.getHeight(); y++) {
                // Main terrain tile
                Box terrainTile = new Box(2.0, 0.2, 2.0);
                terrainTile.setMaterial(terrainMaterial);
                terrainTile.setTranslateX(x * 2.5 + centerOffsetX);
                terrainTile.setTranslateZ(y * 2.5 + centerOffsetZ);
                terrainTile.setTranslateY(0);
                
                terrainBoxes.put(new Position(x, y), terrainTile);
                root.getChildren().add(terrainTile);
                terrainCount++;
                
                // Add grid lines for better visibility
                createGridLines(x, y, centerOffsetX, centerOffsetZ);
            }
        }
        System.out.println("Created " + terrainCount + " terrain tiles");
        
        // Create cover objects
        System.out.println("Creating " + field.getAllCoverObjects().size() + " cover objects...");
        for (CoverObject cover : field.getAllCoverObjects()) {
            Position pos = cover.getPosition();
            Box coverBox = createCoverBox(cover);
            coverBox.setTranslateX(pos.getX() * 2.5 + centerOffsetX);
            coverBox.setTranslateZ(pos.getY() * 2.5 + centerOffsetZ);
            coverBox.setTranslateY(0.8); // Укрытия на уровне поля
            
            coverBoxes.put(cover, coverBox);
            root.getChildren().add(coverBox);
            System.out.println("Added cover: " + cover.getName() + " at " + pos);
        }
        
        // Create units
        System.out.println("Creating " + field.getAllUnits().size() + " units...");
        for (Unit unit : field.getAllUnits()) {
            Position pos = unit.getPosition();
            Sphere unitSphere = createUnitSphere(unit);
            unitSphere.setTranslateX(pos.getX() * 2.5 + centerOffsetX);
            unitSphere.setTranslateZ(pos.getY() * 2.5 + centerOffsetZ);
            unitSphere.setTranslateY(0.9); // Юниты на уровне поля
            
            unitSpheres.put(unit, unitSphere);
            root.getChildren().add(unitSphere);
            System.out.println("Added unit: " + unit.getName() + " (" + unit.getUnitType() + ") at " + pos);
        }
        
        // Add battlefield border for better visibility
        createBattlefieldBorder(centerOffsetX, centerOffsetZ);
    }
    
    private void createBattlefieldBorder(double centerOffsetX, double centerOffsetZ) {
        PhongMaterial borderMaterial = new PhongMaterial();
        borderMaterial.setDiffuseColor(Color.YELLOW);
        borderMaterial.setSpecularColor(Color.GOLD);
        
        double fieldWidth = field.getWidth() * 2.5; // Используем правильный размер
        double fieldHeight = field.getHeight() * 2.5; // Используем правильный размер
        
        // Top border (верхняя граница)
        Box topBorder = new Box(fieldWidth + 0.2, 0.3, 0.1);
        topBorder.setMaterial(borderMaterial);
        topBorder.setTranslateX(centerOffsetX + (field.getWidth() - 1) * 2.5 / 2.0);
        topBorder.setTranslateZ(centerOffsetZ - 1.25);
        topBorder.setTranslateY(0.15);
        root.getChildren().add(topBorder);
        
        // Bottom border (нижняя граница)
        Box bottomBorder = new Box(fieldWidth + 0.2, 0.3, 0.1);
        bottomBorder.setMaterial(borderMaterial);
        bottomBorder.setTranslateX(centerOffsetX + (field.getWidth() - 1) * 2.5 / 2.0);
        bottomBorder.setTranslateZ(centerOffsetZ + (field.getHeight() - 1) * 2.5 + 1.25);
        bottomBorder.setTranslateY(0.15);
        root.getChildren().add(bottomBorder);
        
        // Left border (левая граница)
        Box leftBorder = new Box(0.1, 0.3, fieldHeight + 0.2);
        leftBorder.setMaterial(borderMaterial);
        leftBorder.setTranslateX(centerOffsetX - 1.25);
        leftBorder.setTranslateZ(centerOffsetZ + (field.getHeight() - 1) * 2.5 / 2.0);
        leftBorder.setTranslateY(0.15);
        root.getChildren().add(leftBorder);
        
        // Right border (правая граница)
        Box rightBorder = new Box(0.1, 0.3, fieldHeight + 0.2);
        rightBorder.setMaterial(borderMaterial);
        rightBorder.setTranslateX(centerOffsetX + (field.getWidth() - 1) * 2.5 + 1.25);
        rightBorder.setTranslateZ(centerOffsetZ + (field.getHeight() - 1) * 2.5 / 2.0);
        rightBorder.setTranslateY(0.15);
        root.getChildren().add(rightBorder);
    }
    
    private void createGridLines(int x, int y, double centerOffsetX, double centerOffsetZ) {
        PhongMaterial gridMaterial = new PhongMaterial();
        gridMaterial.setDiffuseColor(Color.DARKGRAY);
        gridMaterial.setSpecularColor(Color.LIGHTGRAY);
        
        double posX = x * 1.2 + centerOffsetX;
        double posZ = y * 1.2 + centerOffsetZ;
        
        // Vertical grid lines (along Z-axis)
        if (x < field.getWidth() - 1) {
            Box verticalLine = new Box(0.02, 0.1, 1.0);
            verticalLine.setMaterial(gridMaterial);
            verticalLine.setTranslateX(posX + 0.6);
            verticalLine.setTranslateZ(posZ);
            verticalLine.setTranslateY(0.05);
            root.getChildren().add(verticalLine);
        }
        
        // Horizontal grid lines (along X-axis)
        if (y < field.getHeight() - 1) {
            Box horizontalLine = new Box(1.0, 0.1, 0.02);
            horizontalLine.setMaterial(gridMaterial);
            horizontalLine.setTranslateX(posX);
            horizontalLine.setTranslateZ(posZ + 0.6);
            horizontalLine.setTranslateY(0.05);
            root.getChildren().add(horizontalLine);
        }
        
        // Corner markers for better visibility
        Sphere cornerMarker = new Sphere(0.05);
        cornerMarker.setMaterial(gridMaterial);
        cornerMarker.setTranslateX(posX);
        cornerMarker.setTranslateZ(posZ);
        cornerMarker.setTranslateY(0.1);
        root.getChildren().add(cornerMarker);
    }
    
    private Box createCoverBox(CoverObject cover) {
        double height = switch (cover.getCoverType()) {
            case FULL_COVER -> 1.5;
            case HALF_COVER -> 1.0;
            case LOW_COVER -> 0.5;
            default -> 0.3;
        };
        
        Box coverBox = new Box(0.8, height, 0.8);
        coverBox.setMaterial(coverMaterial);
        
        // Add cover type label
        Label label = new Label(cover.getCoverType().toString());
        label.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        label.setTranslateX(cover.getPosition().getX() * 1.2);
        label.setTranslateZ(cover.getPosition().getY() * 1.2);
        label.setTranslateY(height + 0.2);
        
        return coverBox;
    }
    
    private Sphere createUnitSphere(Unit unit) {
        Sphere sphere = new Sphere(0.8);
        
        switch (unit.getUnitType()) {
            case SOLDIER -> sphere.setMaterial(soldierMaterial);
            case ALIEN -> sphere.setMaterial(alienMaterial);
            case ALIEN_RULER -> sphere.setMaterial(alienMaterial); // Alien rulers use same material as regular aliens
            case CIVILIAN -> sphere.setMaterial(civilianMaterial);
            case VEHICLE -> sphere.setMaterial(vehicleMaterial);
            case ROBOTIC -> sphere.setMaterial(vehicleMaterial); // Robotic units use vehicle material
        }
        
        // Add health indicator
        if (unit.getHealthPercentage() < 0.5) {
            sphere.setMaterial(new PhongMaterial(Color.ORANGE));
        }
        if (unit.getHealthPercentage() < 0.25) {
            sphere.setMaterial(new PhongMaterial(Color.RED));
        }
        
        return sphere;
    }
    
    private void setupControls(Scene scene) {
        // Mouse controls for camera rotation and unit selection
        scene.setOnMousePressed((MouseEvent event) -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = rotateX.getAngle();
            anchorAngleY = rotateY.getAngle();
        });
        
        scene.setOnMouseDragged((MouseEvent event) -> {
            rotateX.setAngle(anchorAngleX - (anchorY - event.getSceneY()));
            rotateY.setAngle(anchorAngleY + anchorX - event.getSceneX());
        });
        
        // Mouse click for unit selection and movement
        scene.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 1) {
                handleMouseClick(event);
            }
        });
        
        // Keyboard controls (стандартное игровое управление)
        scene.setOnKeyPressed((KeyEvent event) -> {
            double moveDistance = 10.0; // Увеличиваем скорость для большого поля
            
            switch (event.getCode()) {
                case W -> camera.setTranslateZ(camera.getTranslateZ() + moveDistance); // Вперед
                case S -> camera.setTranslateZ(camera.getTranslateZ() - moveDistance); // Назад
                case A -> camera.setTranslateX(camera.getTranslateX() - moveDistance); // Влево
                case D -> camera.setTranslateX(camera.getTranslateX() + moveDistance); // Вправо
                case Q -> camera.setTranslateY(camera.getTranslateY() + moveDistance); // Вверх
                case E -> camera.setTranslateY(camera.getTranslateY() - moveDistance); // Вниз
                case R -> resetCamera(); // Сброс камеры
                case SPACE -> toggleAnimation(); // Переключение анимации
                case ESCAPE -> clearSelection(); // Сброс выбора юнита
                default -> {
                    // Ignore all other key codes
                }
            }
        });
        
        // Mouse scroll for zoom (колесико мыши для изменения масштаба)
        scene.setOnScroll(event -> {
            PerspectiveCamera perspectiveCamera = (PerspectiveCamera) camera;
            double zoomFactor = 0.1;
            double deltaY = event.getDeltaY();
            double currentFOV = perspectiveCamera.getFieldOfView();
            
            if (deltaY > 0) {
                // Приближение (scroll up) - уменьшаем поле зрения
                double newFOV = Math.max(currentFOV - (currentFOV * zoomFactor), 10); // Минимум 10 градусов
                perspectiveCamera.setFieldOfView(newFOV);
            } else {
                // Отдаление (scroll down) - увеличиваем поле зрения
                double newFOV = Math.min(currentFOV + (currentFOV * zoomFactor), 120); // Максимум 120 градусов
                perspectiveCamera.setFieldOfView(newFOV);
            }
        });
        
        // Apply rotations to camera with initial angle
        rotateX.setAngle(-20); // Начальный наклон камеры вниз для лучшего обзора поля
        rotateY.setAngle(180); // Поворачиваем поле на 180 градусов
        camera.getTransforms().addAll(rotateX, rotateY);
    }
    
    private void resetCamera() {
        camera.setTranslateX(0);
        camera.setTranslateY(-150); // Стартовая позиция для большого поля
        camera.setTranslateZ(-200); // Стартовая позиция для большого поля
        rotateX.setAngle(-20);      // Начальный наклон
        rotateY.setAngle(180);      // Поле повернуто на 180 градусов
        
        // Сброс масштаба (поле зрения)
        PerspectiveCamera perspectiveCamera = (PerspectiveCamera) camera;
        perspectiveCamera.setFieldOfView(60); // Стандартное поле зрения
    }
    
    /**
     * Центрирует камеру на указанном юните
     */
    public void centerCameraOnUnit(Unit unit) {
        if (unit == null) {
            return;
        }
        
        Position unitPos = unit.getPosition();
        if (unitPos == null) {
            return;
        }
        
        // Вычисляем мировые координаты юнита (как в create3DBattlefield)
        double centerOffsetX = -(field.getWidth() - 1) * 2.5 / 2.0;
        double centerOffsetZ = -(field.getHeight() - 1) * 2.5 / 2.0;
        
        double unitWorldX = unitPos.getX() * 2.5 + centerOffsetX;
        double unitWorldZ = unitPos.getY() * 2.5 + centerOffsetZ;
        
        // Позиционируем камеру над юнитом
        camera.setTranslateX(-unitWorldX); // Инвертируем X из-за поворота на 180 градусов
        camera.setTranslateZ(-unitWorldZ - 50); // Отодвигаем камеру назад от юнита
        camera.setTranslateY(-80); // Высота камеры для хорошего обзора
        
        System.out.println("Камера центрирована на " + unit.getName() + 
                          " в позиции (" + unitPos.getX() + "," + unitPos.getY() + ")" +
                          " мировые координаты: (" + unitWorldX + "," + unitWorldZ + ")");
    }
    
    private boolean animationEnabled = true;
    
    private void toggleAnimation() {
        animationEnabled = !animationEnabled;
    }
    
    private void setupAnimation() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (animationEnabled) {
                    // Animate units (subtle floating effect)
                    for (Unit unit : unitSpheres.keySet()) {
                        Sphere sphere = unitSpheres.get(unit);
                        double time = now / 1_000_000_000.0;
                        sphere.setTranslateY(0.6 + Math.sin(time * 2 + unit.getName().hashCode()) * 0.1);
                    }
                    
                    // Animate cover objects (subtle rotation)
                    for (CoverObject cover : coverBoxes.keySet()) {
                        Box box = coverBoxes.get(cover);
                        double time = now / 1_000_000_000.0;
                        box.setRotate(time * 10);
                    }
                }
            }
        };
        timer.start();
    }
    
    public void updateUnitPosition(Unit unit, Position newPosition) {
        Platform.runLater(() -> {
            Sphere sphere = unitSpheres.get(unit);
            if (sphere != null) {
                double centerOffsetX = -(field.getWidth() - 1) * 2.5 / 2.0;
                double centerOffsetZ = -(field.getHeight() - 1) * 2.5 / 2.0;
                sphere.setTranslateX(newPosition.getX() * 2.5 + centerOffsetX);
                sphere.setTranslateZ(newPosition.getY() * 2.5 + centerOffsetZ);
            }
        });
    }
    
    public void updateUnitHealth(Unit unit) {
        Platform.runLater(() -> {
            Sphere sphere = unitSpheres.get(unit);
            if (sphere != null) {
                if (!unit.isAlive()) {
                    sphere.setMaterial(new PhongMaterial(Color.BLACK));
                    sphere.setTranslateY(0.1);
                } else {
                    createUnitSphere(unit).setMaterial(sphere.getMaterial());
                }
            }
        });
    }
    
    public void showAttackAnimation(Unit attacker, Unit target) {
        Platform.runLater(() -> {
            Sphere attackerSphere = unitSpheres.get(attacker);
            Sphere targetSphere = unitSpheres.get(target);
            
            if (attackerSphere != null && targetSphere != null) {
                // Create laser beam effect
                Cylinder laser = new Cylinder(0.02, 
                    Math.sqrt(Math.pow(attackerSphere.getTranslateX() - targetSphere.getTranslateX(), 2) +
                             Math.pow(attackerSphere.getTranslateZ() - targetSphere.getTranslateZ(), 2)));
                
                laser.setMaterial(new PhongMaterial(Color.YELLOW));
                laser.setTranslateX((attackerSphere.getTranslateX() + targetSphere.getTranslateX()) / 2);
                laser.setTranslateZ((attackerSphere.getTranslateZ() + targetSphere.getTranslateZ()) / 2);
                laser.setTranslateY(0.6);
                
                root.getChildren().add(laser);
                
                // Remove laser after animation
                new Thread(() -> {
                    try {
                        Thread.sleep(500);
                        Platform.runLater(() -> root.getChildren().remove(laser));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }).start();
            }
        });
    }
    
    private void handleMouseClick(MouseEvent event) {
        // Получаем объект под курсором
        Node picked = event.getPickResult().getIntersectedNode();
        
        if (picked instanceof Sphere) {
            // Клик по юниту - выбираем его
            Unit clickedUnit = findUnitBySphere((Sphere) picked);
            if (clickedUnit != null) {
                selectUnit(clickedUnit);
                return;
            }
            
            // Клик по индикатору перемещения - перемещаем юнита
            Position movePosition = findPositionByMoveIndicator((Sphere) picked);
            if (movePosition != null && selectedUnit != null) {
                moveUnitToPosition(selectedUnit, movePosition);
                return;
            }
        } else if (picked instanceof Box) {
            // Клик по полю - если юнит выбран, показываем возможные ходы
            Position clickedPosition = findPositionByTerrain((Box) picked);
            if (clickedPosition != null && selectedUnit != null) {
                // Проверяем, можно ли переместиться в эту позицию
                if (isValidMovePosition(selectedUnit, clickedPosition)) {
                    moveUnitToPosition(selectedUnit, clickedPosition);
                }
            }
        }
    }
    
    private Unit findUnitBySphere(Sphere sphere) {
        for (Map.Entry<Unit, Sphere> entry : unitSpheres.entrySet()) {
            if (entry.getValue() == sphere) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    private Position findPositionByMoveIndicator(Sphere sphere) {
        for (Map.Entry<Position, Sphere> entry : moveIndicators.entrySet()) {
            if (entry.getValue() == sphere) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    private Position findPositionByTerrain(Box box) {
        for (Map.Entry<Position, Box> entry : terrainBoxes.entrySet()) {
            if (entry.getValue() == box) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    public void selectUnit(Unit unit) {
        if (unit == null || !unit.isAlive() || unit.getActionPoints() <= 0) {
            return;
        }
        
        // Сбрасываем предыдущий выбор
        clearSelection();
        
        selectedUnit = unit;
        
        // Подсвечиваем выбранного юнита
        Sphere unitSphere = unitSpheres.get(unit);
        if (unitSphere != null) {
            unitSphere.setMaterial(selectedUnitMaterial);
        }
        
        // Показываем возможные позиции для перемещения
        showMoveIndicators(unit);
        
        System.out.println("Выбран юнит: " + unit.getName() + " (AP: " + unit.getActionPoints() + ")");
    }
    
    public void clearSelection() {
        if (selectedUnit != null) {
            // Восстанавливаем оригинальный материал юнита
            Sphere unitSphere = unitSpheres.get(selectedUnit);
            if (unitSphere != null) {
                unitSphere.setMaterial(getOriginalMaterial(selectedUnit));
            }
        }
        
        selectedUnit = null;
        
        // Убираем индикаторы перемещения
        hideMoveIndicators();
    }
    
    private PhongMaterial getOriginalMaterial(Unit unit) {
        return switch (unit.getUnitType()) {
            case SOLDIER -> soldierMaterial;
            case ALIEN -> alienMaterial;
            case ALIEN_RULER -> alienMaterial; // Alien rulers use same material as regular aliens
            case CIVILIAN -> civilianMaterial;
            case VEHICLE -> vehicleMaterial;
            case ROBOTIC -> vehicleMaterial; // Robotic units use vehicle material
        };
    }
    
    private void showMoveIndicators(Unit unit) {
        hideMoveIndicators(); // Сначала убираем старые индикаторы
        
        double centerOffsetX = -(field.getWidth() - 1) * 2.5 / 2.0;
        double centerOffsetZ = -(field.getHeight() - 1) * 2.5 / 2.0;
        
        for (int x = 0; x < field.getWidth(); x++) {
            for (int y = 0; y < field.getHeight(); y++) {
                Position pos = new Position(x, y);
                
                if (isValidMovePosition(unit, pos)) {
                    // Создаем индикатор перемещения
                    Sphere indicator = new Sphere(0.3);
                    indicator.setMaterial(moveIndicatorMaterial);
                    indicator.setTranslateX(x * 2.5 + centerOffsetX);
                    indicator.setTranslateZ(y * 2.5 + centerOffsetZ);
                    indicator.setTranslateY(1.5); // Выше юнитов
                    
                    moveIndicators.put(pos, indicator);
                    root.getChildren().add(indicator);
                }
            }
        }
    }
    
    private void hideMoveIndicators() {
        for (Sphere indicator : moveIndicators.values()) {
            root.getChildren().remove(indicator);
        }
        moveIndicators.clear();
    }
    
    private boolean isValidMovePosition(Unit unit, Position targetPos) {
        Position currentPos = unit.getPosition();
        
        // Нельзя остаться на месте
        if (currentPos.equals(targetPos)) {
            return false;
        }
        
        // Проверяем дистанцию
        int distance = Math.abs(currentPos.getX() - targetPos.getX()) + 
                      Math.abs(currentPos.getY() - targetPos.getY());
        
        if (distance > unit.getMovementRange()) {
            return false;
        }
        
        // Проверяем, что позиция в пределах поля
        if (targetPos.getX() < 0 || targetPos.getX() >= field.getWidth() ||
            targetPos.getY() < 0 || targetPos.getY() >= field.getHeight()) {
            return false;
        }
        
        // Проверяем, что позиция свободна
        return field.getUnitAt(targetPos.getX(), targetPos.getY()) == null;
    }
    
    private void moveUnitToPosition(Unit unit, Position targetPos) {
        if (!isValidMovePosition(unit, targetPos)) {
            System.out.println("Невозможно переместиться в позицию " + targetPos);
            return;
        }
        
        // Перемещаем юнита
        Position oldPos = unit.getPosition();
        unit.setPosition(targetPos.getX(), targetPos.getY());
        unit.spendActionPoint(); // Тратим очко действия
        
        // Обновляем поле
        field.moveUnit(unit, targetPos.getX(), targetPos.getY());
        
        // Обновляем 3D визуализацию
        updateUnitPosition(unit, targetPos);
        
        System.out.println(unit.getName() + " перемещен с " + oldPos + " в " + targetPos + 
                          " (осталось AP: " + unit.getActionPoints() + ")");
        
        // Если у юнита закончились очки действия, снимаем выбор
        if (unit.getActionPoints() <= 0) {
            clearSelection();
        } else {
            // Обновляем индикаторы перемещения
            showMoveIndicators(unit);
        }
    }
    
    public static void launch3DVisualization(TacticalField field, DefaultCombatManager combatManager) {
        Combat3DVisualizer visualizer = new Combat3DVisualizer(field);
        new Thread(() -> {
            Platform.startup(() -> {});
            Platform.runLater(() -> {
                try {
                    visualizer.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }).start();
    }
} 