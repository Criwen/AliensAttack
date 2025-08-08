package com.aliensattack;

import com.aliensattack.ui.GameWindow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

/**
 * Main application class for Aliens Attack - XCOM 2 Tactical Combat
 */
public class AliensAttackApplication {
    private static final Logger log = LogManager.getLogger(AliensAttackApplication.class);
    
    public static void main(String[] args) {
        // Launch the unified game window
        SwingUtilities.invokeLater(() -> {
            try {
                // Create and show the game window
                GameWindow gameWindow = new GameWindow();
                gameWindow.showWindow();
                log.info("Game window launched");
                
                System.out.println("=== ALIENS ATTACK - XCOM 2 TACTICAL COMBAT ===");
                System.out.println("All 85 XCOM 2 mechanics implemented:");
                System.out.println("=== ORIGINAL 20 MECHANICS ===");
                System.out.println("1. Concealment/Stealth System");
                System.out.println("2. Flanking Mechanics");
                System.out.println("3. Suppression Fire");
                System.out.println("4. Destructible Environment");
                System.out.println("5. Squad Cohesion");
                System.out.println("6. Psionic Combat System");
                System.out.println("7. Environmental Hazards");
                System.out.println("8. Squad Sight");
                System.out.println("9. Hacking/Technical Abilities");
                System.out.println("10. Concealment Breaks");
                System.out.println("11. Overwatch Ambush");
                System.out.println("12. Height Advantage");
                System.out.println("13. Grenade Launcher");
                System.out.println("14. Medikit System");
                System.out.println("15. Ammo Types");
                System.out.println("16. Bladestorm");
                System.out.println("17. Bluescreen Protocol");
                System.out.println("18. Volatile Mix");
                System.out.println("19. Rapid Fire");
                System.out.println("20. Deep Cover");
                System.out.println("=== ENHANCED 5 MECHANICS ===");
                System.out.println("21. Enhanced Destructible Environment");
                System.out.println("22. Advanced Squad Cohesion");
                System.out.println("23. Comprehensive Height Advantage");
                System.out.println("24. Reactive Abilities System");
                System.out.println("25. Advanced Medical System");
                System.out.println("=== NEW 10 MECHANICS ===");
                System.out.println("26. VIP and Civilian System");
                System.out.println("27. Extraction Point System");
                System.out.println("28. Objective Target System");
                System.out.println("29. Hacking Terminal System");
                System.out.println("30. Defense Position System");
                System.out.println("31. Enhanced Mission Objectives");
                System.out.println("32. Time-Sensitive Missions");
                System.out.println("33. Mission Value Calculation");
                System.out.println("34. Mission Status Tracking");
                System.out.println("35. Mission Completion Logic");
                System.out.println("=== LATEST 10 MECHANICS ===");
                System.out.println("36. Alien Ruler System");
                System.out.println("37. Chosen System");
                System.out.println("38. Alien Pod Activation");
                System.out.println("39. Concealment Ambush");
                System.out.println("40. Alien Reinforcement System");
                System.out.println("41. Alien AI Behavior Patterns");
                System.out.println("42. Alien Abilities System");
                System.out.println("43. Mission Timer System");
                System.out.println("44. Alien Research System");
                System.out.println("45. Alien Base System");
                System.out.println("=== NEWEST 10 MECHANICS ===");
                System.out.println("46. Alien AI Behavior Trees");
                System.out.println("47. Squad Size Management");
                System.out.println("48. Weapon Progression System");
                System.out.println("49. Alien Evolution System");
                System.out.println("50. Mission Type Variants");
                System.out.println("51. Environmental Weather Effects");
                System.out.println("52. Squad Bonding System");
                System.out.println("53. Alien Autopsy System");
                System.out.println("54. Facility Defense Mechanics");
                System.out.println("55. Alien Infestation System");
                System.out.println("=== ULTIMATE 5 MECHANICS ===");
                System.out.println("56. Advanced AI Behavior Trees");
                System.out.println("57. Enhanced Weather Effects System");
                System.out.println("58. Advanced Squad Bonding System");
                System.out.println("59. Alien Autopsy System");
                System.out.println("60. Facility Defense & Infestation Systems");
                System.out.println("=== FINAL 5 MECHANICS ===");
                System.out.println("61. Advanced Cover System");
                System.out.println("62. Advanced Terrain System");
                System.out.println("63. Advanced Status Effect System");
                System.out.println("64. Advanced Movement System");
                System.out.println("65. Advanced Weapon Specialization");
                System.out.println("=== LATEST 5 MECHANICS ===");
                System.out.println("66. Alien Ruler Reaction System");
                System.out.println("67. Chosen Adaptive Learning System");
                System.out.println("68. Alien Pod Coordination System");
                System.out.println("69. Advanced Cover Destruction System");
                System.out.println("70. Advanced Terrain Interaction System");
                System.out.println("=== NEWEST 5 MECHANICS ===");
                System.out.println("71. Advanced Squad Tactics System");
                System.out.println("72. Advanced Environmental Interaction System");
                System.out.println("73. Advanced Mission Timer System");
                System.out.println("74. Advanced Alien Evolution System");
                System.out.println("75. Advanced Intel and Research System");
                System.out.println("=== LATEST 5 MECHANICS ===");
                System.out.println("76. Advanced Soldier Class Specialization System");
                System.out.println("77. Advanced Psionic Warfare System");
                System.out.println("78. Advanced Stealth and Infiltration System");
                System.out.println("79. Advanced Mission Planning and Preparation System");
                System.out.println("80. Advanced Combat Environment and Weather Effects System");
                System.out.println("=== NEWEST 5 MECHANICS ===");
                System.out.println("81. Advanced Mission Failure and Success Conditions System");
                System.out.println("82. Advanced Soldier Injury and Recovery System");
                System.out.println("83. Advanced Equipment Degradation and Maintenance System");
                System.out.println("84. Advanced Alien Research and Technology System");
                System.out.println("85. Advanced Strategic Layer Integration System");
                System.out.println("================================================");
                
            } catch (Exception e) {
                log.error("Error launching game window", e);
            }
        });
    }
} 