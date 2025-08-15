package com.aliensattack.visualization.models;

import javafx.scene.Node;
import javafx.scene.paint.PhongMaterial;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

/**
 * Abstract base class for animated 3D models
 * Provides common animation and transformation functionality
 */
@Log4j2
public abstract class AbstractAnimatedModel implements AnimatedModel {
    
    @Getter
    protected Node modelNode;
    
    @Getter
    protected PhongMaterial material;
    
    @Getter
    @Setter
    protected double posX, posY, posZ;
    
    @Getter
    @Setter
    protected double rotX, rotY, rotZ;
    
    @Getter
    protected AnimationType currentAnimation = AnimationType.IDLE;
    
    @Getter
    protected boolean animating = false;
    
    protected Timeline currentTimeline;
    protected Translate translateTransform;
    protected Rotate rotateXTransform;
    protected Rotate rotateYTransform;
    protected Rotate rotateZTransform;
    
    protected AbstractAnimatedModel() {
        // Инициализация трансформов будет выполнена в подклассах
        // после создания modelNode
    }
    
        protected void initializeTransforms() {
        translateTransform = new Translate(0, 0, 0);
        rotateXTransform = new Rotate(0, Rotate.X_AXIS);
        rotateYTransform = new Rotate(0, Rotate.Y_AXIS);
        rotateZTransform = new Rotate(0, Rotate.Z_AXIS);

        // Добавляем трансформы только если модель уже создана
        if (modelNode != null) {
            modelNode.getTransforms().addAll(
                translateTransform, 
                rotateXTransform, 
                rotateYTransform, 
                rotateZTransform
            );
        }
    }
    
    private void setupDefaultAnimation() {
        // Start with idle animation
        playAnimation(AnimationType.IDLE);
    }
    
    @Override
    public Timeline playAnimation(AnimationType animationType) {
        if (currentTimeline != null) {
            currentTimeline.stop();
        }
        
        currentAnimation = animationType;
        animating = true;
        
        log.debug("Playing animation: {}", animationType);
        
        currentTimeline = createAnimationTimeline(animationType);
        currentTimeline.setOnFinished(event -> {
            animating = false;
            // Return to idle after animation completes
            if (animationType != AnimationType.IDLE) {
                playAnimation(AnimationType.IDLE);
            }
        });
        
        currentTimeline.play();
        return currentTimeline;
    }
    
    @Override
    public void stopAnimation() {
        if (currentTimeline != null) {
            currentTimeline.stop();
            animating = false;
        }
    }
    
    @Override
    public void setPosition(double x, double y, double z) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        
        if (translateTransform != null) {
            translateTransform.setX(x);
            translateTransform.setY(y);
            translateTransform.setZ(z);
        }
    }
    
    @Override
    public void setRotation(double x, double y, double z) {
        this.rotX = x;
        this.rotY = y;
        this.rotZ = z;
        
        if (rotateXTransform != null) {
            rotateXTransform.setAngle(x);
        }
        if (rotateYTransform != null) {
            rotateYTransform.setAngle(y);
        }
        if (rotateZTransform != null) {
            rotateZTransform.setAngle(z);
        }
    }
    
    @Override
    public void update(double deltaTime) {
        // Override in subclasses for specific update logic
    }
    
    /**
     * Create animation timeline for specific animation type
     * @param animationType Type of animation to create
     * @return Timeline for the animation
     */
    protected abstract Timeline createAnimationTimeline(AnimationType animationType);
    
    /**
     * Initialize the 3D model node
     * @return Initialized 3D node
     */
    protected abstract Node initializeModelNode();
    
    /**
     * Initialize the material for the model
     * @return Initialized material
     */
    protected abstract PhongMaterial initializeMaterial();
    
    /**
     * Apply material to all model parts
     */
    protected abstract void applyMaterial();
}
