package org.example.demo6.Classes;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class Music {

    private MediaPlayer mediaPlayer;

    // Constructor to initialize the music file
    public Music(String filePath) {
        try {
            Media media = new Media(new File(filePath).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
        } catch (Exception e) {
            System.out.println("Error loading music file: " + e.getMessage());
        }
    }

    // Method to play music
    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    // Method to pause music
    public void pause() {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
        }
    }

    // Method to stop music
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    // Method to loop music
    public void loop() {
        if (mediaPlayer != null) {
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        }
    }
}
