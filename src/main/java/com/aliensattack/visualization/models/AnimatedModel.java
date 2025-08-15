package com.aliensattack.visualization.models;

import javafx.scene.Node;
import javafx.scene.paint.PhongMaterial;
import javafx.animation.Animation;
import javafx.animation.Timeline;

/**
 * Base interface for animated 3D models in the tactical combat system
 * Provides common functionality for both soldier and alien models
 */
public interface AnimatedModel {
    
    /**
     * Get the 3D node representation of the model
     * @return JavaFX 3D node
     */
    Node getModelNode();
    
    /**
     * Get the material used for the model
     * @return PhongMaterial for rendering
     */
    PhongMaterial getMaterial();
    
    /**
     * Play a specific animation
     * @param animationType Type of animation to play
     * @return Timeline for the animation
     */
    Timeline playAnimation(AnimationType animationType);
    
    /**
     * Stop current animation
     */
    void stopAnimation();
    
    /**
     * Update model state based on game state
     * @param deltaTime Time since last update in seconds
     */
    void update(double deltaTime);
    
    /**
     * Set model position in 3D space
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     */
    void setPosition(double x, double y, double z);
    
    /**
     * Set model rotation
     * @param x X rotation in degrees
     * @param y Y rotation in degrees
     * @param z Z rotation in degrees
     */
    void setRotation(double x, double y, double z);
    
    /**
     * Get current animation state
     * @return Current animation type
     */
    AnimationType getCurrentAnimation();
    
    /**
     * Check if model is currently animating
     * @return true if animating
     */
    boolean isAnimating();
}


