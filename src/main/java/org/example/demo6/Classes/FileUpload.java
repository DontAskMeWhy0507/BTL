package org.example.demo6.Classes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUpload {
    public static void uploadFile(File file, String directoryPath) {
        if (file == null || !file.exists()) {
            System.out.println("File does not exist: " + (file != null ? file.getName() : "null"));
            return; // Exit if file is null or doesn't exist
        }

        // Create destination directory if it doesn't exist
        File dir = new File(directoryPath);
        if (!dir.exists()) {
            dir.mkdirs(); // Create the directory if it doesn't exist
        }

        // Destination path to save the file
        Path destinationPath = new File(dir, file.getName()).toPath();

        try {
            // Copy file to the destination
            Files.copy(file.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File saved to: " + destinationPath.toString());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to save the file: " + e.getMessage());
        }
    }
    public static File downloadFileImage(String imageUrl, String destinationPath) {
        try (InputStream in = new URL(imageUrl).openStream()) {
            // Print the URL and destination path for debugging
            System.out.println("Downloading from URL: " + imageUrl);
            System.out.println("Saving to path: " + destinationPath);

            Files.copy(in, Paths.get(destinationPath));
            System.out.println("Image downloaded successfully to: " + destinationPath);
            return new File(destinationPath);
        } catch (IOException e) {
            System.err.println("Failed to download the image: " + e.getMessage());
            return null;
        }
    }

}
