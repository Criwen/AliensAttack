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
 * Animated 3D model for XCOM aliens
 * Implements alien-specific animations and body structure
 */
@Log4j2
public class AlienAnimatedModel extends AbstractAnimatedModel {
    
    private Group alienGroup;
    private Box mainBody;
    private Sphere head;
    private Box leftTentacle;
    private Box rightTentacle;
    private Box leftLeg;
    private Box rightLeg;
    private Box weapon;
    private Sphere psionicCore;
    
    // Animation parameters
    private double tentacleWave = 0.0;
    private double psionicPulse = 0.0;
    private double alienMovement = 0.0;
    
    public AlienAnimatedModel() {
        super();
        this.modelNode = initializeModelNode();
        this.material = initializeMaterial();
        // Применяем материал к частям модели
        applyMaterial();
        initializeTransforms();
    }
    
    @Override
    protected Node initializeModelNode() {
        alienGroup = new Group();
        
        // Create alien body parts
        mainBody = new Box(1.0, 1.5, 0.6);
        head = new Sphere(0.3);
        leftTentacle = new Box(0.15, 1.2, 0.15);
        rightTentacle = new Box(0.15, 1.2, 0.15);
        leftLeg = new Box(0.3, 1.2, 0.3);
        rightLeg = new Box(0.3, 1.2, 0.3);
        weapon = new Box(0.2, 0.2, 1.0);
        psionicCore = new Sphere(0.2);
        
        // Position body parts
        head.setTranslateY(-1.0);
        
        leftTentacle.setTranslateX(-0.6);
        leftTentacle.setTranslateY(-0.3);
        leftTentacle.setTranslateZ(0.0);
        
        rightTentacle.setTranslateX(0.6);
        rightTentacle.setTranslateY(-0.3);
        rightTentacle.setTranslateZ(0.0);
        
        leftLeg.setTranslateX(-0.35);
        leftLeg.setTranslateY(1.35);
        leftLeg.setTranslateZ(0.0);
        
        rightLeg.setTranslateX(0.35);
        rightLeg.setTranslateY(1.35);
        rightLeg.setTranslateZ(0.0);
        
        weapon.setTranslateX(0.8);
        weapon.setTranslateY(-0.2);
        weapon.setTranslateZ(0.0);
        
        psionicCore.setTranslateX(0.0);
        psionicCore.setTranslateY(-0.5);
        psionicCore.setTranslateZ(0.3);
        
        // Add all parts to group
        alienGroup.getChildren().addAll(
            mainBody, head, leftTentacle, rightTentacle, 
            leftLeg, rightLeg, weapon, psionicCore
        );
        
        return alienGroup;
    }
    
    @Override
    protected PhongMaterial initializeMaterial() {
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.DARKRED);
        material.setSpecularColor(Color.RED);
        material.setSpecularPower(64.0);
        return material;
    }
    
    @Override
    protected void applyMaterial() {
        if (material != null) {
            mainBody.setMaterial(material);
            head.setMaterial(material);
            leftTentacle.setMaterial(material);
            rightTentacle.setMaterial(material);
            leftLeg.setMaterial(material);
            rightLeg.setMaterial(material);
            weapon.setMaterial(material);
            psionicCore.setMaterial(material);
        }
    }
    
    @Override
    protected Timeline createAnimationTimeline(AnimationType animationType) {
        switch (animationType) {
            case IDLE:
                return createAlienIdleAnimation();
            case WALK:
                return createAlienWalkAnimation();
            case RUN:
                return createAlienRunAnimation();
            case ATTACK_MELEE:
                return createAlienMeleeAttackAnimation();
            case ATTACK_RANGED:
                return createAlienRangedAttackAnimation();
            case PSIONIC_CAST:
                return createPsionicCastAnimation();
            case MIND_CONTROLLED:
                return createMindControlAnimation();
            case DEATH:
                return createAlienDeathAnimation();
            default:
                return createAlienIdleAnimation();
        }
    }
    
    private Timeline createAlienIdleAnimation() {
        Timeline timeline = new Timeline();
        
        // Psionic core pulse
        KeyFrame pulse1 = new KeyFrame(Duration.ZERO, 
            new KeyValue(psionicCore.scaleXProperty(), 1.0),
            new KeyValue(psionicCore.scaleYProperty(), 1.0),
            new KeyValue(psionicCore.scaleZProperty(), 1.0)
        );
        
        KeyFrame pulse2 = new KeyFrame(Duration.millis(1500), 
            new KeyValue(psionicCore.scaleXProperty(), 1.3),
            new KeyValue(psionicCore.scaleYProperty(), 1.3),
            new KeyValue(psionicCore.scaleZProperty(), 1.3)
        );
        
        KeyFrame pulse3 = new KeyFrame(Duration.millis(3000), 
            new KeyValue(psionicCore.scaleXProperty(), 1.0),
            new KeyValue(psionicCore.scaleYProperty(), 1.0),
            new KeyValue(psionicCore.scaleZProperty(), 1.0)
        );
        
        // Tentacle movement
        KeyFrame tentacle1 = new KeyFrame(Duration.ZERO,
            new KeyValue(leftTentacle.rotateProperty(), 0),
            new KeyValue(rightTentacle.rotateProperty(), 0)
        );
        
        KeyFrame tentacle2 = new KeyFrame(Duration.millis(2000),
            new KeyValue(leftTentacle.rotateProperty(), 5),
            new KeyValue(rightTentacle.rotateProperty(), -5)
        );
        
        KeyFrame tentacle3 = new KeyFrame(Duration.millis(4000),
            new KeyValue(leftTentacle.rotateProperty(), 0),
            new KeyValue(rightTentacle.rotateProperty(), 0)
        );
        
        timeline.getKeyFrames().addAll(pulse1, pulse2, pulse3, tentacle1, tentacle2, tentacle3);
        timeline.setCycleCount(Timeline.INDEFINITE);
        
        return timeline;
    }
    
    private Timeline createAlienWalkAnimation() {
        Timeline timeline = new Timeline();
        
        // Leg movement
        KeyFrame leg1 = new KeyFrame(Duration.ZERO,
            new KeyValue(leftLeg.rotateProperty(), 0),
            new KeyValue(rightLeg.rotateProperty(), 0)
        );
        
        KeyFrame leg2 = new KeyFrame(Duration.millis(600),
            new KeyValue(leftLeg.rotateProperty(), 20),
            new KeyValue(rightLeg.rotateProperty(), -20)
        );
        
        KeyFrame leg3 = new KeyFrame(Duration.millis(1200),
            new KeyValue(leftLeg.rotateProperty(), 0),
            new KeyValue(rightLeg.rotateProperty(), 0)
        );
        
        // Body sway
        KeyFrame sway1 = new KeyFrame(Duration.ZERO,
            new KeyValue(mainBody.rotateProperty(), 0)
        );
        
        KeyFrame sway2 = new KeyFrame(Duration.millis(600),
            new KeyValue(mainBody.rotateProperty(), 3)
        );
        
        KeyFrame sway3 = new KeyFrame(Duration.millis(1200),
            new KeyValue(mainBody.rotateProperty(), 0)
        );
        
        timeline.getKeyFrames().addAll(leg1, leg2, leg3, sway1, sway2, sway3);
        timeline.setCycleCount(Timeline.INDEFINITE);
        
        return timeline;
    }
    
    private Timeline createAlienRunAnimation() {
        Timeline timeline = new Timeline();
        
        // Faster leg movement
        KeyFrame leg1 = new KeyFrame(Duration.ZERO,
            new KeyValue(leftLeg.rotateProperty(), 0),
            new KeyValue(rightLeg.rotateProperty(), 0)
        );
        
        KeyFrame leg2 = new KeyFrame(Duration.millis(300),
            new KeyValue(leftLeg.rotateProperty(), 30),
            new KeyValue(rightLeg.rotateProperty(), -30)
        );
        
        KeyFrame leg3 = new KeyFrame(Duration.millis(600),
            new KeyValue(leftLeg.rotateProperty(), 0),
            new KeyValue(rightLeg.rotateProperty(), 0)
        );
        
        // Faster body sway
        KeyFrame sway1 = new KeyFrame(Duration.ZERO,
            new KeyValue(mainBody.rotateProperty(), 0)
        );
        
        KeyFrame sway2 = new KeyFrame(Duration.millis(300),
            new KeyValue(mainBody.rotateProperty(), 5)
        );
        
        KeyFrame sway3 = new KeyFrame(Duration.millis(600),
            new KeyValue(mainBody.rotateProperty(), 0)
        );
        
        timeline.getKeyFrames().addAll(leg1, leg2, leg3, sway1, sway2, sway3);
        timeline.setCycleCount(Timeline.INDEFINITE);
        
        return timeline;
    }
    
    private Timeline createAlienMeleeAttackAnimation() {
        Timeline timeline = new Timeline();
        
        // Tentacle attack
        KeyFrame attack1 = new KeyFrame(Duration.ZERO,
            new KeyValue(leftTentacle.rotateProperty(), 0),
            new KeyValue(rightTentacle.rotateProperty(), 0)
        );
        
        KeyFrame attack2 = new KeyFrame(Duration.millis(200),
            new KeyValue(leftTentacle.rotateProperty(), 45),
            new KeyValue(rightTentacle.rotateProperty(), -45)
        );
        
        KeyFrame attack3 = new KeyFrame(Duration.millis(500),
            new KeyValue(leftTentacle.rotateProperty(), 0),
            new KeyValue(rightTentacle.rotateProperty(), 0)
        );
        
        timeline.getKeyFrames().addAll(attack1, attack2, attack3);
        timeline.setCycleCount(1);
        
        return timeline;
    }
    
    private Timeline createAlienRangedAttackAnimation() {
        Timeline timeline = new Timeline();
        
        // Weapon recoil
        KeyFrame recoil1 = new KeyFrame(Duration.ZERO,
            new KeyValue(weapon.translateZProperty(), 0)
        );
        
        KeyFrame recoil2 = new KeyFrame(Duration.millis(150),
            new KeyValue(weapon.translateZProperty(), -0.15)
        );
        
        KeyFrame recoil3 = new KeyFrame(Duration.millis(400),
            new KeyValue(weapon.translateZProperty(), 0)
        );
        
        timeline.getKeyFrames().addAll(recoil1, recoil2, recoil3);
        timeline.setCycleCount(1);
        
        return timeline;
    }
    
    private Timeline createPsionicCastAnimation() {
        Timeline timeline = new Timeline();
        
        // Psionic core expansion
        KeyFrame cast1 = new KeyFrame(Duration.ZERO,
            new KeyValue(psionicCore.scaleXProperty(), 1.0),
            new KeyValue(psionicCore.scaleYProperty(), 1.0),
            new KeyValue(psionicCore.scaleZProperty(), 1.0)
        );
        
        KeyFrame cast2 = new KeyFrame(Duration.millis(750),
            new KeyValue(psionicCore.scaleXProperty(), 2.0),
            new KeyValue(psionicCore.scaleYProperty(), 2.0),
            new KeyValue(psionicCore.scaleZProperty(), 2.0)
        );
        
        KeyFrame cast3 = new KeyFrame(Duration.millis(1500),
            new KeyValue(psionicCore.scaleXProperty(), 1.0),
            new KeyValue(psionicCore.scaleYProperty(), 1.0),
            new KeyValue(psionicCore.scaleZProperty(), 1.0)
        );
        
        // Tentacle positioning
        KeyFrame tentacle1 = new KeyFrame(Duration.ZERO,
            new KeyValue(leftTentacle.rotateProperty(), 0),
            new KeyValue(rightTentacle.rotateProperty(), 0)
        );
        
        KeyFrame tentacle2 = new KeyFrame(Duration.millis(750),
            new KeyValue(leftTentacle.rotateProperty(), 30),
            new KeyValue(rightTentacle.rotateProperty(), -30)
        );
        
        KeyFrame tentacle3 = new KeyFrame(Duration.millis(1500),
            new KeyValue(leftTentacle.rotateProperty(), 0),
            new KeyValue(rightTentacle.rotateProperty(), 0)
        );
        
        timeline.getKeyFrames().addAll(cast1, cast2, cast3, tentacle1, tentacle2, tentacle3);
        timeline.setCycleCount(1);
        
        return timeline;
    }
    
    private Timeline createMindControlAnimation() {
        Timeline timeline = new Timeline();
        
        // Psionic core pulsing
        KeyFrame pulse1 = new KeyFrame(Duration.ZERO,
            new KeyValue(psionicCore.scaleXProperty(), 1.0)
        );
        
        KeyFrame pulse2 = new KeyFrame(Duration.millis(500),
            new KeyValue(psionicCore.scaleXProperty(), 1.5)
        );
        
        KeyFrame pulse3 = new KeyFrame(Duration.millis(1000),
            new KeyValue(psionicCore.scaleXProperty(), 1.0)
        );
        
        // Head movement
        KeyFrame head1 = new KeyFrame(Duration.ZERO,
            new KeyValue(head.rotateProperty(), 0)
        );
        
        KeyFrame head2 = new KeyFrame(Duration.millis(1000),
            new KeyValue(head.rotateProperty(), 360)
        );
        
        timeline.getKeyFrames().addAll(pulse1, pulse2, pulse3, head1, head2);
        timeline.setCycleCount(Timeline.INDEFINITE);
        
        return timeline;
    }
    
    private Timeline createAlienDeathAnimation() {
        Timeline timeline = new Timeline();
        
        // Body collapse
        KeyFrame death1 = new KeyFrame(Duration.ZERO,
            new KeyValue(mainBody.scaleYProperty(), 1.0),
            new KeyValue(mainBody.translateYProperty(), 0)
        );
        
        KeyFrame death2 = new KeyFrame(Duration.millis(1000),
            new KeyValue(mainBody.scaleYProperty(), 0.3),
            new KeyValue(mainBody.translateYProperty(), 0.6)
        );
        
        // Psionic core fade
        KeyFrame core1 = new KeyFrame(Duration.ZERO,
            new KeyValue(psionicCore.opacityProperty(), 1.0)
        );
        
        KeyFrame core2 = new KeyFrame(Duration.millis(1500),
            new KeyValue(psionicCore.opacityProperty(), 0.0)
        );
        
        timeline.getKeyFrames().addAll(death1, death2, core1, core2);
        timeline.setCycleCount(1);
        
        return timeline;
    }
    
    @Override
    public void update(double deltaTime) {
        // Update animation parameters
        tentacleWave += deltaTime * 1.5;
        psionicPulse += deltaTime * 2.0;
        alienMovement += deltaTime * 1.0;
        
        // Apply subtle movements for realism
        if (currentAnimation == AnimationType.IDLE) {
            // Subtle tentacle movement
            leftTentacle.setRotate(Math.sin(tentacleWave) * 3.0);
            rightTentacle.setRotate(Math.sin(tentacleWave + Math.PI) * 3.0);
        }
    }
}
