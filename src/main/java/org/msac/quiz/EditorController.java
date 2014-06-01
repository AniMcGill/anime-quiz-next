package org.msac.quiz;

import org.msac.controls.MediaControl;
import org.msac.data.QuestionType;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

/**
 * Created by Natsumi on 2014-05-28.
 */
public class EditorController {
    @FXML private GridPane mainGrid;
    @FXML private HBox questionDataContainer;
    @FXML private TextField questionDataInput;
    @FXML private TextField answerTextField;
    @FXML private TextField pointsTextField;
    @FXML private TextField timingTextField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private Button deleteButton;

    @FXML
    private void saveButton_Click() {
        //TODO
    }
    @FXML
    private void cancelButton_Click() {
        Action response = Dialogs.create()
                .owner(cancelButton.getScene().getWindow())
                .title("Possible unsaved changes")
                .message("Changes will not be saved. Are you sure you want to exit?")
                .showConfirm();
        if(response == Dialog.Actions.YES) {
            close();
        }
    }
    @FXML
    private void deleteButton_Click() {
        Action response = Dialogs.create()
                .owner(deleteButton.getScene().getWindow())
                .title("Confirm deletion")
                .message("Are you sure you want to delete this item?")
                .showConfirm();
        if(response == Dialog.Actions.YES) {
            //TODO delete item and update
            close();
        }
    }

    private QuestionType questionType;
    private Button filePickerButton;
    private Node previewNode;

    public void init(QuestionType questionType) {
        this.questionType = questionType;
        addIntegerRestrict();
        loadQuestionDataControl();
    }

    private void loadQuestionDataControl() {
        if(questionType != QuestionType.QUESTION){
            questionDataInput.setEditable(false);
            addFilePicker();
        }
    }

    private void addFilePicker() {
        filePickerButton = new Button();
        filePickerButton.setOnAction(event -> openFilePicker());
        filePickerButton.setText("...");
        questionDataContainer.getChildren().add(filePickerButton);
    }

    private void openFilePicker(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a file...");
        fileChooser.setInitialDirectory(new File("."));
        if(questionType == QuestionType.SCREENSHOT) {
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp")
            );
        }
        else{
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.mp4", "*.wav", "*.m4a")
            );
        }

        File file = fileChooser.showOpenDialog(filePickerButton.getScene().getWindow());
        if(file != null){
            try {
                if (questionType == QuestionType.SCREENSHOT) {
                    loadScreenshotPreview(file);
                }
                else{
                    loadMusicPreview(file);
                }
                questionDataInput.setText(file.getAbsolutePath());
                addPreviewNode();
            }
            catch (Exception e) {
                Dialogs.create()
                        .owner(saveButton.getScene().getWindow())
                        .title("Fail")
                        .masthead("An exception has occurred loading the file")
                        .message(e.getLocalizedMessage())
                        .showException(e);
            }
        }
    }

    private void addPreviewNode() {
        mainGrid.add(previewNode, 4, 0);
        GridPane.setColumnSpan(previewNode, 2);
        GridPane.setRowSpan(previewNode, 5);
        GridPane.setValignment(previewNode, VPos.TOP);
        GridPane.setHgrow(previewNode, Priority.SOMETIMES);
        GridPane.setVgrow(previewNode, Priority.SOMETIMES);
    }

    private void loadMusicPreview(File file) {
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(file.toURI().toString()));
        previewNode = new MediaControl(mediaPlayer);
    }

    private void loadScreenshotPreview(File file) throws IOException {
        previewNode = new ImageView();
        BufferedImage bufferedImage = ImageIO.read(file);
        WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
        ((ImageView)previewNode).setImage(image);
        mainGrid.boundsInLocalProperty().addListener((observable, oldValue, newValue) -> ((ImageView)previewNode).setFitWidth(newValue.getWidth()/3));
        ((ImageView)previewNode).setPreserveRatio(true);
    }

    private void addIntegerRestrict() {
        // filter pointsTextField for integers only
        pointsTextField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if(!event.getCharacter().matches("\\d")) {
                event.consume();
            }
        });

        // filter timingTextField for integers only
        timingTextField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if(!event.getCharacter().matches("\\d")) {
                event.consume();
            }
        });
    }

    private void close() {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }
}
