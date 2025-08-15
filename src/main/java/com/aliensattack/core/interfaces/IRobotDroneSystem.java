package com.aliensattack.core.interfaces;

import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.CombatAction;
import com.aliensattack.core.systems.RobotDroneSystem.Robot;
import com.aliensattack.core.systems.RobotDroneSystem.Drone;

import java.util.List;

/**
 * Interface for Robot Drone System - XCOM2 Tactical Combat
 * Defines contract for robot and drone management
 */
public interface IRobotDroneSystem {
    
    /**
     * Create a robot of specified type at position
     * @param type Robot type to create
     * @param position Position to create robot
     * @return Created robot instance
     */
    Robot createRobot(Robot.Type type, Position position);
    
    /**
     * Create a drone of specified type at position
     * @param type Drone type to create
     * @param position Position to create drone
     * @return Created drone instance
     */
    Drone createDrone(Drone.Type type, Position position);
    
    /**
     * Update robot AI for all active robots
     */
    void updateRobotAI();
    
    /**
     * Update drone AI for all active drones
     */
    void updateDroneAI();
    
    /**
     * Get all active robots
     * @return List of active robots
     */
    List<Robot> getActiveRobots();
    
    /**
     * Get all active drones
     * @return List of active drones
     */
    List<Drone> getActiveDrones();
    
    /**
     * Assign robot to squad
     * @param robot Robot to assign
     * @param squadId Squad ID to assign to
     * @return true if assignment successful
     */
    boolean assignRobotToSquad(Robot robot, String squadId);
    
    /**
     * Assign drone to squad
     * @param drone Drone to assign
     * @param squadId Squad ID to assign to
     * @return true if assignment successful
     */
    boolean assignDroneToSquad(Drone drone, String squadId);
    
    /**
     * Execute action for robot
     * @param robot Robot to execute action
     * @param action Action to execute
     */
    void executeRobotAction(Robot robot, CombatAction action);
    
    /**
     * Execute action for drone
     * @param drone Drone to execute action
     * @param action Action to execute
     */
    void executeDroneAction(Drone drone, CombatAction action);
    
    /**
     * Deploy a robot drone at specified position
     * @param position Position to deploy drone
     * @return true if deployment successful
     */
    boolean deployDrone(Position position);
    
    /**
     * Control a robot drone
     * @param droneId ID of the drone to control
     * @return true if control successful
     */
    boolean controlDrone(String droneId);
    
    /**
     * Get active drone count
     * @return Number of active drones
     */
    int getActiveDroneCount();
}
