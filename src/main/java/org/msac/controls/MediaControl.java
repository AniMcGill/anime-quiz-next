package org.msac.controls;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;
import org.controlsfx.glyphfont.GlyphFontRegistry;


/**
 * http://docs.oracle.com/javafx/2/media/playercontrol.htm with some modifications
 */
public class MediaControl extends VBox {
    private MediaPlayer mediaPlayer;
    private boolean stopRequested = false;
    private boolean atEndOfMedia = false;
    private Duration duration;
    private Slider timeSlider;

    public MediaControl(final MediaPlayer mediaPlayer){
        this.mediaPlayer = mediaPlayer;
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(5,10,5,10));

        addTimeSlider();
        addPlayButton();

    }

    private void addTimeSlider(){
        timeSlider = new Slider();
        timeSlider.setMaxWidth(Double.MAX_VALUE);

        timeSlider.valueProperty().addListener(observable -> {
            if (timeSlider.isValueChanging()) {
                // multiply duration by percentage calculated by slider position
                mediaPlayer.seek(duration.multiply(timeSlider.getValue() / 100.0));
            }
        });

        this.getChildren().add(timeSlider);
    }
    private void addPlayButton(){
        final Button playButton  = new Button("", GlyphFontRegistry.glyph("FontAwesome|PLAY"));
        playButton.setOnAction(event -> {
            Status status = mediaPlayer.getStatus();
            if(status == Status.UNKNOWN || status == Status.HALTED){
                return;
            }
            if(status == Status.PAUSED || status == Status.READY || status == Status.STOPPED) {
                if(atEndOfMedia){
                    mediaPlayer.seek(mediaPlayer.getStartTime());
                    atEndOfMedia = false;
                }
                mediaPlayer.play();
            }
            else {
                mediaPlayer.pause();
            }
        });

        mediaPlayer.currentTimeProperty().addListener(observable -> {
            updateValues();
        });

        mediaPlayer.setOnPlaying(() -> {
            if(stopRequested){
                mediaPlayer.pause();
                stopRequested = false;
            }
            else{
                playButton.setGraphic(GlyphFontRegistry.glyph("FontAwesome|PAUSE"));
            }
        });

        mediaPlayer.setOnPaused(() -> playButton.setGraphic(GlyphFontRegistry.glyph("FontAwesome|PLAY")));

        mediaPlayer.setOnReady(() -> {
            duration = mediaPlayer.getMedia().getDuration();
            updateValues();
        });

        mediaPlayer.setCycleCount(1);

        mediaPlayer.setOnEndOfMedia(() -> {
            playButton.setGraphic(GlyphFontRegistry.glyph("FontAwesome|PLAY"));
            stopRequested = true;
            atEndOfMedia = true;
        });
        this.getChildren().add(playButton);
    }

    private void updateValues() {
        if (timeSlider != null){
            Platform.runLater(() -> {
                Duration currentTime = mediaPlayer.getCurrentTime();
                timeSlider.setDisable(duration.isUnknown());
                if (!timeSlider.isDisabled() && duration.greaterThan(Duration.ZERO) && !timeSlider.isValueChanging()) {
                    timeSlider.setValue(currentTime.toMillis() / duration.toMillis() * 100.0);
                }
            });
        }
    }

    public void stopMusic(){
        mediaPlayer.stop();
    }
}
