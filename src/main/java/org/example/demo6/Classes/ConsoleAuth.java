package org.example.demo6.Classes;

import java.io.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Console-based authentication for P-256 real mode (non-visual)
 * Provides headless authentication without JavaFX dependencies
 */
public class ConsoleAuth {
    
    private P256Crypto crypto;
    private DBUltis dbUtils;
    private Scanner scanner;
    
    public ConsoleAuth() {
        this.crypto = new P256Crypto(true); // Initialize in real mode
        this.dbUtils = new DBUltis();
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Console-based login with P-256 authentication
     */
    public User consoleLogin() {
        System.out.println("=== P-256 Real Mode Authentication ===");
        System.out.println(crypto.getCurveInfo());
        System.out.println();
        
        System.out.print("Username: ");
        String username = scanner.nextLine();
        
        String password = readPassword();
        
        return authenticateUser(username, password);
    }
    
    /**
     * Console-based signup with P-256 hashing
     */
    public boolean consoleSignup() {
        System.out.println("=== P-256 Real Mode Registration ===");
        System.out.println(crypto.getCurveInfo());
        System.out.println();
        
        System.out.print("User ID: ");
        String id = scanner.nextLine();
        
        System.out.print("Username: ");
        String username = scanner.nextLine();
        
        String password = readPassword();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        return signupUser(id, username, password, email);
    }
    
    /**
     * Authenticate user with P-256 enhanced security
     */
    private User authenticateUser(String username, String password) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database/LibraryMain")) {
            
            // First check if user exists
            PreparedStatement psCheck = connection.prepareStatement(
                "SELECT * FROM USERS WHERE username = ?");
            psCheck.setString(1, username);
            ResultSet rs = psCheck.executeQuery();
            
            if (!rs.isBeforeFirst()) {
                System.err.println("Authentication failed: User does not exist");
                return null;
            }
            
            while (rs.next()) {
                String storedPassword = rs.getString("password");
                String role = rs.getString("role");
                
                // Check if password is already hashed (contains P256 marker)
                boolean isHashed = storedPassword.contains("P256:");
                boolean passwordValid;
                
                if (isHashed) {
                    // Extract hash part after P256: marker
                    String storedHash = storedPassword.substring(5);
                    String inputHash = crypto.hashPassword(password);
                    passwordValid = inputHash != null && inputHash.equals(storedHash);
                } else {
                    // Legacy plain text password comparison
                    passwordValid = storedPassword.equals(password);
                }
                
                if (passwordValid) {
                    System.out.println("Authentication successful!");
                    System.out.println("User: " + username + " (" + role + ")");
                    
                    // Create and return user object
                    User loggedInUser;
                    if ("Admin".equals(role)) {
                        loggedInUser = new Admin(
                            rs.getInt("ID"),
                            rs.getString("USERNAME"),
                            rs.getString("PASSWORD"),
                            rs.getString("EMAIL"),
                            LocalDate.parse(rs.getString("DATE_OF_BIRTH")),
                            rs.getString("AVATAR"),
                            rs.getString("ROLE"),
                            new Streak(LocalDate.parse(rs.getString("LAST_ACCESS")), 
                                     rs.getInt("STREAK"), rs.getInt("LONGEST_STREAK"))
                        );
                    } else {
                        loggedInUser = new User(
                            rs.getInt("ID"),
                            rs.getString("USERNAME"),
                            rs.getString("PASSWORD"),
                            rs.getString("EMAIL"),
                            LocalDate.parse(rs.getString("DATE_OF_BIRTH")),
                            rs.getString("AVATAR"),
                            rs.getString("ROLE"),
                            new Streak(LocalDate.parse(rs.getString("LAST_ACCESS")), 
                                     rs.getInt("STREAK"), rs.getInt("LONGEST_STREAK"))
                        );
                    }
                    return loggedInUser;
                } else {
                    System.err.println("Authentication failed: Invalid password");
                    return null;
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error during authentication: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Signup user with P-256 enhanced password hashing
     */
    private boolean signupUser(String id, String username, String password, String email) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database/LibraryMain")) {
            
            // Check if username already exists
            if (checkUserExists(connection, "username", username)) {
                System.err.println("Registration failed: Username already exists");
                return false;
            }
            
            // Check if email already exists
            if (checkUserExists(connection, "email", email)) {
                System.err.println("Registration failed: Email already exists");
                return false;
            }
            
            // Check if ID already exists
            if (checkUserExists(connection, "id", id)) {
                System.err.println("Registration failed: ID already exists");
                return false;
            }
            
            // Hash password with P-256
            String hashedPassword = "P256:" + crypto.hashPassword(password);
            
            String sql = "INSERT INTO Users(id, username, password, email, date_of_birth, avatar, role, last_access, streak, longest_streak) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, username);
            pstmt.setString(3, hashedPassword);
            pstmt.setString(4, email);
            pstmt.setString(5, LocalDate.now().toString());
            pstmt.setString(6, "/Image/Avatar/Gekko.png");
            pstmt.setString(7, "User");
            pstmt.setString(8, LocalDate.now().toString());
            pstmt.setInt(9, 0);
            pstmt.setInt(10, 0);
            
            pstmt.executeUpdate();
            System.out.println("User registered successfully with P-256 encryption!");
            return true;
            
        } catch (SQLException e) {
            System.err.println("Database error during registration: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Check if user exists in database
     */
    private boolean checkUserExists(Connection connection, String field, String value) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM Users WHERE " + field + " = ?");
        ps.setString(1, value);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1) > 0;
    }
    
    /**
     * Read password from console (hiding input when possible)
     */
    private String readPassword() {
        Console console = System.console();
        if (console != null) {
            char[] passwordChars = console.readPassword("Password: ");
            return new String(passwordChars);
        } else {
            // Fallback for environments without console (like IDEs)
            System.out.print("Password: ");
            return scanner.nextLine();
        }
    }
    
    /**
     * Main menu for console authentication
     */
    public void runConsoleInterface() {
        while (true) {
            System.out.println("\n=== Library Management System - P-256 Real Mode ===");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    User user = consoleLogin();
                    if (user != null) {
                        System.out.println("Login successful! Welcome, " + user.getUsername());
                        // Here you could launch additional console functionality
                        return;
                    }
                    break;
                case "2":
                    if (consoleSignup()) {
                        System.out.println("Registration successful! Please login.");
                    }
                    break;
                case "3":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    /**
     * Entry point for console mode
     */
    public static void main(String[] args) {
        ConsoleAuth auth = new ConsoleAuth();
        auth.runConsoleInterface();
    }
}