    package org.example.demo6.Classes;

    import javafx.beans.value.ChangeListener;
    import javafx.beans.value.ObservableValue;
    import javafx.scene.control.Slider;
    import javafx.scene.media.Media;
    import javafx.scene.media.MediaPlayer;
    import java.io.File;

    public class Music {
        // Singleton
        public static Music instance = null;
        public static Music getInstance() {
            if (instance == null) {
                instance = new Music("src/main/resources/Sound/jingle-bells.mp3");
            }
            return instance;
        }


        private MediaPlayer mediaPlayer;

        // file nhạc
        public Music(String filePath) {
            try {
                Media media = new Media(new File(filePath).toURI().toString());
                mediaPlayer = new MediaPlayer(media);
            } catch (Exception e) {
                System.out.println("Error loading music file: " + e.getMessage());
            }
        }


        public void play() {
            if (mediaPlayer != null) {
                mediaPlayer.play();
            }
        }

        public void pause() {
            if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
            }
        }

        public void stop() {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
        }

        public void loop() {
            if (mediaPlayer != null) {
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.play();
            }
        }

        public void setVolume(double volume) {
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(volume);
            }
        }

    }
