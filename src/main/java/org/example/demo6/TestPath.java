package org.example.demo6;

import java.io.File;

public class TestPath {
    // Test the validity of the path \Uploaded\BookCovers\Screenshot_20221227_050315.png
    public static void main(String[] args) {
        String path = "Uploaded/BookCovers/Screenshot_20221227_050315.png"; // Use forward slashes for cross-platform compatibility
        boolean isValid = isValidPath(path);

        if (isValid) {
            System.out.println("The path is valid: " + path);
        } else {
            System.out.println("The path is not valid: " + path);
        }
    }

    // Method to check if the path is valid
    public static boolean isValidPath(String path) {
        File file = new File(path); // Create a File object with the relative path
        return file.exists(); // Check if the file exists
    }
}
