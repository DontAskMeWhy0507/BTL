package org.example.demo6.Classes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
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

    public void bindVolumeSlider(Slider volumeSlider) {
        if (mediaPlayer != null) {
            // Set initial slider value to current volume
            volumeSlider.setValue(mediaPlayer.getVolume() * 100);

            // Bind slider changes to volume property
            volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    mediaPlayer.setVolume(newValue.doubleValue() / 100); // Convert slider value to range 0.0 - 1.0
                }
            });
        }
    }
}
