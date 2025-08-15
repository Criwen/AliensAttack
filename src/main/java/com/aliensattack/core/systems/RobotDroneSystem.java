package com.aliensattack.core.systems;

import com.aliensattack.core.interfaces.IRobotDroneSystem;
import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.CombatAction;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Robot and Drone System - XCOM2 Tactical Combat
 * Manages autonomous robots, drones, and their tactical behaviors
 */
@Log4j2
public class RobotDroneSystem implements IRobotDroneSystem {
    
    // Action type constants
    public static final String ACTION_MOVE = "MOVE";
    public static final String ACTION_ATTACK = "ATTACK";
    public static final String ACTION_OVERWATCH = "OVERWATCH";
    public static final String ACTION_SPECIAL_ABILITY = "SPECIAL_ABILITY";
    
    private final Map<String, Robot> activeRobots;
    private final Map<String, Drone> activeDrones;
    private final RobotAI robotAI;
    private final DroneAI droneAI;
    private final RobotFactory robotFactory;
    private final DroneFactory droneFactory;
    
    public RobotDroneSystem() {
        this.activeRobots = new ConcurrentHashMap<>();
        this.activeDrones = new ConcurrentHashMap<>();
        this.robotAI = new RobotAI();
        this.droneAI = new DroneAI();
        this.robotFactory = new RobotFactory();
        this.droneFactory = new DroneFactory();
    }
    
    @Override
    public Robot createRobot(Robot.Type type, Position position) {
        Robot robot = robotFactory.createRobot(type, position);
        activeRobots.put(robot.getId(), robot);
        log.info("Robot created: {} at position {}", robot.getName(), position);
        return robot;
    }
    
    @Override
    public Drone createDrone(Drone.Type type, Position position) {
        Drone drone = droneFactory.createDrone(type, position);
        activeDrones.put(drone.getId(), drone);
        log.info("Drone created: {} at position {}", drone.getName(), position);
        return drone;
    }
    
    @Override
    public void updateRobotAI() {
        for (Robot robot : activeRobots.values()) {
            if (robot.isOperational()) {
                robotAI.updateRobot(robot);
            }
        }
    }
    
    @Override
    public void updateDroneAI() {
        for (Drone drone : activeDrones.values()) {
            if (drone.isOperational()) {
                droneAI.updateDrone(drone);
            }
        }
    }
    
    @Override
    public List<Robot> getActiveRobots() {
        return new ArrayList<>(activeRobots.values());
    }
    
    @Override
    public List<Drone> getActiveDrones() {
        return new ArrayList<>(activeDrones.values());
    }
    
    @Override
    public boolean assignRobotToSquad(Robot robot, String squadId) {
        if (robot.isOperational() && !robot.isAssigned()) {
            robot.setSquadId(squadId);
            robot.setAssigned(true);
            log.info("Robot {} assigned to squad {}", robot.getName(), squadId);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean assignDroneToSquad(Drone drone, String squadId) {
        if (drone.isOperational() && !drone.isAssigned()) {
            drone.setSquadId(squadId);
            drone.setAssigned(true);
            log.info("Drone {} assigned to squad {}", drone.getName(), squadId);
            return true;
        }
        return false;
    }
    
    @Override
    public void executeRobotAction(Robot robot, CombatAction action) {
        if (robot.canExecuteAction(action)) {
            robot.executeAction(action);
            log.info("Robot {} executed action: {}", robot.getName(), action.getActionType());
        }
    }
    
    @Override
    public void executeDroneAction(Drone drone, CombatAction action) {
        if (drone.canExecuteAction(action)) {
            drone.executeAction(action);
            log.info("Drone {} executed action: {}", drone.getName(), action.getActionType());
        }
    }
    
    @Override
    public boolean deployDrone(Position position) {
        try {
            Drone drone = createDrone(Drone.Type.RECON, position);
            log.info("Drone deployed at position: {}", position);
            return true;
        } catch (Exception e) {
            log.error("Failed to deploy drone at position: {}", position, e);
            return false;
        }
    }
    
    @Override
    public boolean controlDrone(String droneId) {
        Drone drone = activeDrones.get(droneId);
        if (drone != null && drone.isOperational()) {
            log.info("Taking control of drone: {}", drone.getName());
            return true;
        }
        log.warn("Cannot control drone with ID: {}", droneId);
        return false;
    }
    
    @Override
    public int getActiveDroneCount() {
        return activeDrones.size();
    }
    
    public void updateAllUnits() {
        updateRobotAI();
        updateDroneAI();
        
        // Update unit states
        updateRobotStates();
        updateDroneStates();
    }
    
    private void updateRobotStates() {
        Iterator<Map.Entry<String, Robot>> iterator = activeRobots.entrySet().iterator();
        
        while (iterator.hasNext()) {
            Map.Entry<String, Robot> entry = iterator.next();
            Robot robot = entry.getValue();
            
            robot.update();
            
            if (!robot.isOperational()) {
                iterator.remove();
                log.info("Robot {} removed due to destruction", robot.getName());
            }
        }
    }
    
    private void updateDroneStates() {
        Iterator<Map.Entry<String, Drone>> iterator = activeDrones.entrySet().iterator();
        
        while (iterator.hasNext()) {
            Map.Entry<String, Drone> entry = iterator.next();
            Drone drone = entry.getValue();
            
            drone.update();
            
            if (!drone.isOperational()) {
                iterator.remove();
                log.info("Drone {} removed due to destruction", drone.getName());
            }
        }
    }
    
    // Inner classes for robot and drone system components
    @Data
    public static class Robot extends Unit {
        
        private final String id;
        private final String name;
        private final Type type;
        private final Position position;
        private boolean isOperational;
        private boolean isAssigned;
        private String squadId;
        private int health;
        private int maxHealth;
        private int maxActionPoints;
        private List<String> robotAbilities; // Renamed to avoid conflict
        private RobotBehavior behavior;
        
        public Robot(String id, String name, Type type, Position position) {
            super(name, 100, 3, 2, 15, com.aliensattack.core.enums.UnitType.ROBOTIC);
            this.id = id;
            this.name = name;
            this.type = type;
            this.position = position;
            this.isOperational = true;
            this.isAssigned = false;
            this.health = calculateMaxHealth(type);
            this.maxHealth = health;
            this.maxActionPoints = calculateMaxActionPoints(type);
            this.robotAbilities = new ArrayList<>();
            this.behavior = new RobotBehavior(this);
            
            // Set initial action points
            setActionPoints((int) maxActionPoints);
            
            initializeRobot();
        }
        
        private void initializeRobot() {
            switch (type) {
                case HEAVY -> {
                    robotAbilities.add("HEAVY_WEAPONS");
                    robotAbilities.add("ARMOR_PLATING");
                    robotAbilities.add("OVERWATCH");
                }
                case SCOUT -> {
                    robotAbilities.add("STEALTH");
                    robotAbilities.add("SENSORS");
                    robotAbilities.add("FAST_MOVEMENT");
                }
                case SUPPORT -> {
                    robotAbilities.add("HEALING");
                    robotAbilities.add("BUFFS");
                    robotAbilities.add("REPAIR");
                }
                case ASSAULT -> {
                    robotAbilities.add("CLOSE_COMBAT");
                    robotAbilities.add("RAPID_FIRE");
                    robotAbilities.add("CHARGE");
                }
            }
        }
        
        private int calculateMaxHealth(Type type) {
            return switch (type) {
                case HEAVY -> 150;
                case SCOUT -> 80;
                case SUPPORT -> 100;
                case ASSAULT -> 120;
                default -> 100;
            };
        }
        
        private int calculateMaxActionPoints(Type type) {
            return switch (type) {
                case HEAVY -> 2;
                case SCOUT -> 3;
                case SUPPORT -> 2;
                case ASSAULT -> 3;
                default -> 2;
            };
        }
        
        public boolean canExecuteAction(CombatAction action) {
            return isOperational && 
                   getActionPoints() >= action.getActionCost() &&
                   hasAbilityForAction(action);
        }
        
        public void executeAction(CombatAction action) {
            if (canExecuteAction(action)) {
                setActionPoints((int) (getActionPoints() - action.getActionCost()));
                // Execute the action
                log.debug("Robot {} executing action: {}", name, action.getActionType());
            }
        }
        
        private boolean hasAbilityForAction(CombatAction action) {
            // Check if robot has required ability for action
            return true; // Simplified for now
        }
        
        public void update() {
            // Regenerate action points
            if (getActionPoints() < maxActionPoints) {
                setActionPoints((int) Math.min(maxActionPoints, getActionPoints() + 1));
            }
            
            // Update behavior
            behavior.update();
        }
        
        public boolean takeDamage(int damage) {
            health = Math.max(0, health - damage);
            if (health <= 0) {
                isOperational = false;
            }
            return health <= 0;
        }
        
        public void repair(int amount) {
            health = Math.min(maxHealth, health + amount);
            if (health > 0 && !isOperational) {
                isOperational = true;
            }
        }
        
        /**
         * Robot types with different specializations
         */
        public enum Type {
            HEAVY("Heavy", "Heavy weapons and armor"),
            SCOUT("Scout", "Stealth and reconnaissance"),
            SUPPORT("Support", "Healing and buffs"),
            ASSAULT("Assault", "Close combat specialist");
            
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
    
    @Data
    public static class Drone extends Unit {
        
        private final String id;
        private final String name;
        private final Type type;
        private final Position position;
        private boolean isOperational;
        private boolean isAssigned;
        private String squadId;
        private int health;
        private int maxHealth;
        private int maxActionPoints;
        private List<String> droneAbilities; // Renamed to avoid conflict
        private DroneBehavior behavior;
        private double fuel;
        private double maxFuel;
        
        public Drone(String id, String name, Type type, Position position) {
            super(name, 80, 4, 3, 12, com.aliensattack.core.enums.UnitType.ROBOTIC);
            this.id = id;
            this.name = name;
            this.type = type;
            this.position = position;
            this.isOperational = true;
            this.isAssigned = false;
            this.health = calculateMaxHealth(type);
            this.maxHealth = health;
            this.maxActionPoints = calculateMaxActionPoints(type);
            this.droneAbilities = new ArrayList<>();
            this.behavior = new DroneBehavior(this);
            this.fuel = calculateMaxFuel(type);
            this.maxFuel = fuel;
            
            // Set initial action points
            setActionPoints((int) maxActionPoints);
            
            initializeDrone();
        }
        
        private void initializeDrone() {
            switch (type) {
                case RECON -> {
                    droneAbilities.add("STEALTH");
                    droneAbilities.add("SENSORS");
                    droneAbilities.add("FAST_MOVEMENT");
                }
                case COMBAT -> {
                    droneAbilities.add("LIGHT_WEAPONS");
                    droneAbilities.add("OVERWATCH");
                    droneAbilities.add("EVASION");
                }
                case SUPPORT -> {
                    droneAbilities.add("HEALING");
                    droneAbilities.add("BUFFS");
                    droneAbilities.add("REPAIR");
                }
                case TRANSPORT -> {
                    droneAbilities.add("CARGO_CAPACITY");
                    droneAbilities.add("FAST_TRANSPORT");
                    droneAbilities.add("PROTECTION");
                }
            }
        }
        
        private int calculateMaxHealth(Type type) {
            return switch (type) {
                case RECON -> 60;
                case COMBAT -> 80;
                case SUPPORT -> 70;
                case TRANSPORT -> 100;
                default -> 70;
            };
        }
        
        private int calculateMaxActionPoints(Type type) {
            return switch (type) {
                case RECON -> 4;
                case COMBAT -> 3;
                case SUPPORT -> 2;
                case TRANSPORT -> 2;
                default -> 2;
            };
        }
        
        private double calculateMaxFuel(Type type) {
            return switch (type) {
                case RECON -> 100.0;
                case COMBAT -> 80.0;
                case SUPPORT -> 90.0;
                case TRANSPORT -> 120.0;
                default -> 90.0;
            };
        }
        
        public boolean canExecuteAction(CombatAction action) {
            return isOperational && 
                   getActionPoints() >= action.getActionCost() &&
                   fuel > 0 &&
                   hasAbilityForAction(action);
        }
        
        public void executeAction(CombatAction action) {
            if (canExecuteAction(action)) {
                setActionPoints((int) (getActionPoints() - action.getActionCost()));
                fuel -= calculateFuelCost(action);
                
                if (fuel <= 0) {
                    isOperational = false;
                }
                
                log.debug("Drone {} executing action: {}", name, action.getActionType());
            }
        }
        
        private double calculateFuelCost(CombatAction action) {
            return switch (action.getActionType()) {
                case ACTION_MOVE -> 5.0;
                case ACTION_ATTACK -> 3.0;
                case ACTION_OVERWATCH -> 2.0;
                case ACTION_SPECIAL_ABILITY -> 8.0;
                default -> 1.0;
            };
        }
        
        private boolean hasAbilityForAction(CombatAction action) {
            // Check if drone has required ability for action
            return true; // Simplified for now
        }
        
        public void update() {
            // Regenerate action points
            if (getActionPoints() < maxActionPoints) {
                setActionPoints((int) Math.min(maxActionPoints, getActionPoints() + 1));
            }
            
            // Consume fuel over time
            fuel = Math.max(0, fuel - 0.5);
            if (fuel <= 0) {
                isOperational = false;
            }
            
            // Update behavior
            behavior.update();
        }
        
        public void refuel(double amount) {
            fuel = Math.min(maxFuel, fuel + amount);
            if (fuel > 0 && !isOperational) {
                isOperational = true;
            }
        }
        
        public boolean takeDamage(int damage) {
            health = Math.max(0, health - damage);
            if (health <= 0) {
                isOperational = false;
            }
            return health <= 0;
        }
        
        /**
         * Drone types with different specializations
         */
        public enum Type {
            RECON("Recon", "Stealth and reconnaissance"),
            COMBAT("Combat", "Light combat operations"),
            SUPPORT("Support", "Healing and support"),
            TRANSPORT("Transport", "Cargo and personnel transport");
            
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
    
    public static class RobotAI {
        
        public void updateRobot(Robot robot) {
            // Implement robot AI behavior
            if (robot.getBehavior() != null) {
                robot.getBehavior().update();
            }
        }
    }
    
    public static class DroneAI {
        
        public void updateDrone(Drone drone) {
            // Implement drone AI behavior
            if (drone.getBehavior() != null) {
                drone.getBehavior().update();
            }
        }
    }
    
    public static class RobotBehavior {
        
        private final Robot robot;
        private BehaviorState currentState;
        
        public RobotBehavior(Robot robot) {
            this.robot = robot;
            this.currentState = BehaviorState.IDLE;
        }
        
        public void update() {
            // Update behavior based on current state
            switch (currentState) {
                case IDLE -> handleIdleState();
                case COMBAT -> handleCombatState();
                case SUPPORT -> handleSupportState();
                case RETREAT -> handleRetreatState();
            }
        }
        
        private void handleIdleState() {
            // Look for enemies or objectives
            if (detectEnemies()) {
                currentState = BehaviorState.COMBAT;
            }
        }
        
        private void handleCombatState() {
            // Engage enemies
            if (robot.getHealth() < robot.getMaxHealth() * 0.3) {
                currentState = BehaviorState.RETREAT;
            }
        }
        
        private void handleSupportState() {
            // Provide support to allies
            if (detectEnemies()) {
                currentState = BehaviorState.COMBAT;
            }
        }
        
        private void handleRetreatState() {
            // Move to safety
            if (robot.getHealth() > robot.getMaxHealth() * 0.5) {
                currentState = BehaviorState.IDLE;
            }
        }
        
        private boolean detectEnemies() {
            // Simplified enemy detection
            return Math.random() > 0.7;
        }
        
        public enum BehaviorState {
            IDLE, COMBAT, SUPPORT, RETREAT
        }
    }
    
    public static class DroneBehavior {
        
        private final Drone drone;
        private BehaviorState currentState;
        
        public DroneBehavior(Drone drone) {
            this.drone = drone;
            this.currentState = BehaviorState.IDLE;
        }
        
        public void update() {
            // Update behavior based on current state
            switch (currentState) {
                case IDLE -> handleIdleState();
                case COMBAT -> handleCombatState();
                case SUPPORT -> handleSupportState();
                case RETREAT -> handleRetreatState();
            }
        }
        
        private void handleIdleState() {
            // Look for enemies or objectives
            if (detectEnemies()) {
                currentState = BehaviorState.COMBAT;
            }
        }
        
        private void handleCombatState() {
            // Engage enemies
            if (drone.getHealth() < drone.getMaxHealth() * 0.3) {
                currentState = BehaviorState.RETREAT;
            }
        }
        
        private void handleSupportState() {
            // Provide support to allies
            if (detectEnemies()) {
                currentState = BehaviorState.COMBAT;
            }
        }
        
        private void handleRetreatState() {
            // Move to safety
            if (drone.getHealth() > drone.getMaxHealth() * 0.5) {
                currentState = BehaviorState.IDLE;
            }
        }
        
        private boolean detectEnemies() {
            // Simplified enemy detection
            return Math.random() > 0.7;
        }
        
        public enum BehaviorState {
            IDLE, COMBAT, SUPPORT, RETREAT
        }
    }
    
    public static class RobotFactory {
        
        public Robot createRobot(Robot.Type type, Position position) {
            String id = "ROBOT_" + System.currentTimeMillis();
            String name = generateRobotName(type);
            return new Robot(id, name, type, position);
        }
        
        private String generateRobotName(Robot.Type type) {
            return type.getDisplayName() + " Robot " + (System.currentTimeMillis() % 1000);
        }
    }
    
    public static class DroneFactory {
        
        public Drone createDrone(Drone.Type type, Position position) {
            String id = "DRONE_" + System.currentTimeMillis();
            String name = generateDroneName(type);
            return new Drone(id, name, type, position);
        }
        
        private String generateDroneName(Drone.Type type) {
            return type.getDisplayName() + " Drone " + (System.currentTimeMillis() % 1000);
        }
    }
}
