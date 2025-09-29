package org.example.demo6;

public class AppLaunch {
    /**
     * Enhanced app launcher with P-256 support
     * @param args command line arguments
     *             --real, --headless, --console : Launch in P-256 real mode (non-visual)
     *             (no args) : Launch in standard GUI mode with P-256 enhancement
     */
    public static void main(String[] args) {
        // Check for P-256 real mode arguments
        boolean realMode = false;
        for (String arg : args) {
            if ("--real".equals(arg) || "--headless".equals(arg) || "--console".equals(arg)) {
                realMode = true;
                break;
            }
        }
        
        if (realMode) {
            // Use P256Launcher for real mode
            P256Launcher.main(args);
        } else {
            // Standard GUI mode (now enhanced with P-256)
            LibraryApplication.main(args);
        }
    }
}