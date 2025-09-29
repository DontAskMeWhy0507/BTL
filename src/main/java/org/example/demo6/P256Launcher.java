package org.example.demo6;

import org.example.demo6.Classes.ConsoleAuth;

/**
 * P-256 Enhanced Launcher that supports both visual and real (non-visual) modes
 * Usage:
 * - java P256Launcher              -> Launch in GUI mode (visual)
 * - java P256Launcher --real       -> Launch in console mode (non-visual)  
 * - java P256Launcher --headless   -> Launch in console mode (non-visual)
 * - java P256Launcher --console    -> Launch in console mode (non-visual)
 */
public class P256Launcher {
    
    public static void main(String[] args) {
        boolean realMode = false;
        
        // Parse command line arguments
        for (String arg : args) {
            if ("--real".equals(arg) || "--headless".equals(arg) || "--console".equals(arg)) {
                realMode = true;
                break;
            }
        }
        
        if (realMode) {
            // Launch in real mode (non-visual/console)
            System.out.println("=== P-256 Real Mode (Non-Visual) ===");
            System.out.println("Starting Library Management System in console mode...");
            System.out.println("Enhanced with P-256 elliptic curve cryptography");
            System.out.println();
            
            try {
                ConsoleAuth consoleAuth = new ConsoleAuth();
                consoleAuth.runConsoleInterface();
            } catch (Exception e) {
                System.err.println("Error launching console mode: " + e.getMessage());
                e.printStackTrace();
                System.exit(1);
            }
        } else {
            // Launch in visual mode (GUI) - delegate to existing application
            System.out.println("=== P-256 Visual Mode (GUI) ===");
            System.out.println("Starting Library Management System in GUI mode...");
            System.out.println("Enhanced with P-256 elliptic curve cryptography");
            
            try {
                // Check if JavaFX is available for GUI mode
                Class.forName("javafx.application.Application");
                LibraryApplication.main(args);
            } catch (ClassNotFoundException e) {
                System.err.println("JavaFX not available. Falling back to console mode...");
                System.out.println();
                ConsoleAuth consoleAuth = new ConsoleAuth();
                consoleAuth.runConsoleInterface();
            } catch (Exception e) {
                System.err.println("Error launching GUI mode: " + e.getMessage());
                System.err.println("Falling back to console mode...");
                System.out.println();
                ConsoleAuth consoleAuth = new ConsoleAuth();
                consoleAuth.runConsoleInterface();
            }
        }
    }
}