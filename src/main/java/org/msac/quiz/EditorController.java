package org.msac.quiz;

import Data.QuestionType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import java.io.File;

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
        fileChooser.setInitialDirectory(new File("."));
        File file = fileChooser.showOpenDialog(filePickerButton.getScene().getWindow());
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
