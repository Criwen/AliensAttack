package com.aliensattack.visualization.models;

import javafx.scene.Node;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.Group;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;
import javafx.scene.transform.Rotate;
import lombok.extern.log4j.Log4j2;

/**
 * Animated 3D model for XCOM soldiers
 * Implements humanoid animations and combat poses
 */
@Log4j2
public class SoldierAnimatedModel extends AbstractAnimatedModel {
    
    private Group soldierGroup;
    private Box torso;
    private Sphere head;
    private Box leftArm;
    private Box rightArm;
    private Box leftLeg;
    private Box rightLeg;
    private Box weapon;
    
    // Animation parameters
    private double walkCycle = 0.0;
    private double idleBreathing = 0.0;
    private double combatStance = 0.0;
    
    public SoldierAnimatedModel() {
        super();
        this.modelNode = initializeModelNode();
        this.material = initializeMaterial();
        // Применяем материал к частям модели
        applyMaterial();
        // Инициализируем трансформы после создания модели
        initializeTransforms();
        // Запускаем анимацию по умолчанию
        playAnimation(AnimationType.IDLE);
    }
    
    @Override
    protected Node initializeModelNode() {
        soldierGroup = new Group();
        
        // Create humanoid body parts
        torso = new Box(0.8, 1.2, 0.4);
        head = new Sphere(0.25);
        leftArm = new Box(0.2, 0.8, 0.2);
        rightArm = new Box(0.2, 0.8, 0.2);
        leftLeg = new Box(0.25, 1.0, 0.25);
        rightLeg = new Box(0.25, 1.0, 0.25);
        weapon = new Box(0.1, 0.1, 0.8);
        
        // Position body parts
        head.setTranslateY(-0.85);
        
        leftArm.setTranslateX(-0.5);
        leftArm.setTranslateY(-0.2);
        leftArm.setTranslateZ(0.0);
        
        rightArm.setTranslateX(0.5);
        rightArm.setTranslateY(-0.2);
        rightArm.setTranslateZ(0.0);
        
        leftLeg.setTranslateX(-0.25);
        leftLeg.setTranslateY(1.1);
        leftLeg.setTranslateZ(0.0);
        
        rightLeg.setTranslateX(0.25);
        rightLeg.setTranslateY(1.1);
        rightLeg.setTranslateZ(0.0);
        
        weapon.setTranslateX(0.6);
        weapon.setTranslateY(-0.1);
        weapon.setTranslateZ(0.0);
        
        // Add all parts to group
        soldierGroup.getChildren().addAll(
            torso, head, leftArm, rightArm, leftLeg, rightLeg, weapon
        );
        
        return soldierGroup;
    }
    
    @Override
    protected PhongMaterial initializeMaterial() {
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.BLUE);
        material.setSpecularColor(Color.LIGHTBLUE);
        material.setSpecularPower(32.0);
        return material;
    }
    
    @Override
    protected void applyMaterial() {
        if (material != null) {
            torso.setMaterial(material);
            head.setMaterial(material);
            leftArm.setMaterial(material);
            rightArm.setMaterial(material);
            leftLeg.setMaterial(material);
            rightLeg.setMaterial(material);
            weapon.setMaterial(material);
        }
    }
    
    @Override
    protected Timeline createAnimationTimeline(AnimationType animationType) {
        switch (animationType) {
            case IDLE:
                return createIdleAnimation();
            case WALK:
                return createWalkAnimation();
            case RUN:
                return createRunAnimation();
            case ATTACK_RANGED:
                return createAttackRangedAnimation();
            case RELOAD:
                return createReloadAnimation();
            case DEFEND:
                return createDefendAnimation();
            case OVERWATCH:
                return createOverwatchAnimation();
            default:
                return createIdleAnimation();
        }
    }
    
    private Timeline createIdleAnimation() {
        Timeline timeline = new Timeline();
        
        // Breathing animation
        KeyFrame breathing1 = new KeyFrame(Duration.ZERO, 
            new KeyValue(torso.scaleXProperty(), 1.0),
            new KeyValue(torso.scaleYProperty(), 1.0)
        );
        
        KeyFrame breathing2 = new KeyFrame(Duration.millis(2000), 
            new KeyValue(torso.scaleXProperty(), 1.02),
            new KeyValue(torso.scaleYProperty(), 1.02)
        );
        
        KeyFrame breathing3 = new KeyFrame(Duration.millis(4000), 
            new KeyValue(torso.scaleXProperty(), 1.0),
            new KeyValue(torso.scaleYProperty(), 1.0)
        );
        
        timeline.getKeyFrames().addAll(breathing1, breathing2, breathing3);
        timeline.setCycleCount(Timeline.INDEFINITE);
        
        return timeline;
    }
    
    private Timeline createWalkAnimation() {
        Timeline timeline = new Timeline();
        
        // Leg movement
        KeyFrame leg1 = new KeyFrame(Duration.ZERO,
            new KeyValue(leftLeg.rotateProperty(), 0),
            new KeyValue(rightLeg.rotateProperty(), 0)
        );
        
        KeyFrame leg2 = new KeyFrame(Duration.millis(500),
            new KeyValue(leftLeg.rotateProperty(), 15),
            new KeyValue(rightLeg.rotateProperty(), -15)
        );
        
        KeyFrame leg3 = new KeyFrame(Duration.millis(1000),
            new KeyValue(leftLeg.rotateProperty(), 0),
            new KeyValue(rightLeg.rotateProperty(), 0)
        );
        
        // Arm swing
        KeyFrame arm1 = new KeyFrame(Duration.ZERO,
            new KeyValue(leftArm.rotateProperty(), 0),
            new KeyValue(rightArm.rotateProperty(), 0)
        );
        
        KeyFrame arm2 = new KeyFrame(Duration.millis(500),
            new KeyValue(leftArm.rotateProperty(), -20),
            new KeyValue(rightArm.rotateProperty(), 20)
        );
        
        KeyFrame arm3 = new KeyFrame(Duration.millis(1000),
            new KeyValue(leftArm.rotateProperty(), 0),
            new KeyValue(rightArm.rotateProperty(), 0)
        );
        
        timeline.getKeyFrames().addAll(leg1, leg2, leg3, arm1, arm2, arm3);
        timeline.setCycleCount(Timeline.INDEFINITE);
        
        return timeline;
    }
    
    private Timeline createRunAnimation() {
        Timeline timeline = new Timeline();
        
        // Faster leg movement
        KeyFrame leg1 = new KeyFrame(Duration.ZERO,
            new KeyValue(leftLeg.rotateProperty(), 0),
            new KeyValue(rightLeg.rotateProperty(), 0)
        );
        
        KeyFrame leg2 = new KeyFrame(Duration.millis(250),
            new KeyValue(leftLeg.rotateProperty(), 25),
            new KeyValue(rightLeg.rotateProperty(), -25)
        );
        
        KeyFrame leg3 = new KeyFrame(Duration.millis(500),
            new KeyValue(leftLeg.rotateProperty(), 0),
            new KeyValue(rightLeg.rotateProperty(), 0)
        );
        
        // Faster arm swing
        KeyFrame arm1 = new KeyFrame(Duration.ZERO,
            new KeyValue(leftArm.rotateProperty(), 0),
            new KeyValue(rightArm.rotateProperty(), 0)
        );
        
        KeyFrame arm2 = new KeyFrame(Duration.millis(250),
            new KeyValue(leftArm.rotateProperty(), -30),
            new KeyValue(rightArm.rotateProperty(), 30)
        );
        
        KeyFrame arm3 = new KeyFrame(Duration.millis(500),
            new KeyValue(leftArm.rotateProperty(), 0),
            new KeyValue(rightArm.rotateProperty(), 0)
        );
        
        timeline.getKeyFrames().addAll(leg1, leg2, leg3, arm1, arm2, arm3);
        timeline.setCycleCount(Timeline.INDEFINITE);
        
        return timeline;
    }
    
    private Timeline createAttackRangedAnimation() {
        Timeline timeline = new Timeline();
        
        // Weapon recoil
        KeyFrame recoil1 = new KeyFrame(Duration.ZERO,
            new KeyValue(weapon.translateZProperty(), 0)
        );
        
        KeyFrame recoil2 = new KeyFrame(Duration.millis(100),
            new KeyValue(weapon.translateZProperty(), -0.1)
        );
        
        KeyFrame recoil3 = new KeyFrame(Duration.millis(300),
            new KeyValue(weapon.translateZProperty(), 0)
        );
        
        timeline.getKeyFrames().addAll(recoil1, recoil2, recoil3);
        timeline.setCycleCount(1);
        
        return timeline;
    }
    
    private Timeline createReloadAnimation() {
        Timeline timeline = new Timeline();
        
        // Weapon movement
        KeyFrame reload1 = new KeyFrame(Duration.ZERO,
            new KeyValue(weapon.rotateProperty(), 0)
        );
        
        KeyFrame reload2 = new KeyFrame(Duration.millis(600),
            new KeyValue(weapon.rotateProperty(), 45)
        );
        
        KeyFrame reload3 = new KeyFrame(Duration.millis(1200),
            new KeyValue(weapon.rotateProperty(), 0)
        );
        
        timeline.getKeyFrames().addAll(reload1, reload2, reload3);
        timeline.setCycleCount(1);
        
        return timeline;
    }
    
    private Timeline createDefendAnimation() {
        Timeline timeline = new Timeline();
        
        // Crouching pose
        KeyFrame defend1 = new KeyFrame(Duration.ZERO,
            new KeyValue(torso.scaleYProperty(), 1.0),
            new KeyValue(leftLeg.scaleYProperty(), 1.0),
            new KeyValue(rightLeg.scaleYProperty(), 1.0)
        );
        
        KeyFrame defend2 = new KeyFrame(Duration.millis(500),
            new KeyValue(torso.scaleYProperty(), 0.8),
            new KeyValue(leftLeg.scaleYProperty(), 0.7),
            new KeyValue(rightLeg.scaleYProperty(), 0.7)
        );
        
        timeline.getKeyFrames().addAll(defend1, defend2);
        timeline.setCycleCount(1);
        
        return timeline;
    }
    
    private Timeline createOverwatchAnimation() {
        Timeline timeline = new Timeline();
        
        // Weapon aiming
        KeyFrame aim1 = new KeyFrame(Duration.ZERO,
            new KeyValue(weapon.rotateProperty(), 0)
        );
        
        KeyFrame aim2 = new KeyFrame(Duration.millis(500),
            new KeyValue(weapon.rotateProperty(), -10)
        );
        
        timeline.getKeyFrames().addAll(aim1, aim2);
        timeline.setCycleCount(1);
        
        return timeline;
    }
    
    @Override
    public void update(double deltaTime) {
        // Update animation parameters
        walkCycle += deltaTime * 2.0;
        idleBreathing += deltaTime * 0.5;
        combatStance += deltaTime * 1.0;
        
        // Apply subtle movements for realism
        if (currentAnimation == AnimationType.IDLE) {
            // Subtle head movement
            head.setRotate(Math.sin(idleBreathing) * 2.0);
        }
    }
}
