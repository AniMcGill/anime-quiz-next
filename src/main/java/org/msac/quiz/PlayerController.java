package org.msac.quiz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.GridView;
import org.controlsfx.dialog.Dialogs;
import org.msac.data.Music;
import org.msac.data.Question;
import org.msac.data.QuestionType;
import org.msac.data.Screenshot;

import java.io.IOException;

/**
 * Created by Natsumi on 2014-05-24.
 */
public class PlayerController {
    @FXML protected FlowPane questionFlowPane;
    @FXML protected FlowPane screenshotFlowPane;
    @FXML protected FlowPane musicFlowPane;

    @FXML protected GridView<Question> questionGridView;
    @FXML protected GridView<Screenshot> screenshotGridView;
    @FXML protected GridView<Music> musicGridView;

    @FXML protected Button addQuestionButton;
    @FXML protected Button addScreenshotButton;
    @FXML protected Button addMusicButton;

    @FXML
    private void addQuestionButton_Click(){
        openEditorWindow(QuestionType.QUESTION);
        loadQuestions();
    }

    @FXML
    private void addScreenshotButton_Click(){
        openEditorWindow(QuestionType.SCREENSHOT);
        loadScreenshots();
    }

    @FXML
    private void addMusicButton_Click(){
        openEditorWindow(QuestionType.MUSIC);
        loadMusics();
    }

    private int setIndex;

    public void init(int setIndex) {
        this.setIndex = setIndex;
        loadSet();
    }
    public void loadSet() {
        loadQuestions();
        loadScreenshots();
        loadMusics();

        updateAddButtonVisibility();
    }

    private void loadQuestions() {
        if(!Main.setObservableList.get(setIndex).questionList.isEmpty()){
            questionGridView.setItems(Main.setObservableList.get(setIndex).questionList);
        }
    }

    private void loadScreenshots() {
        if(!Main.setObservableList.get(setIndex).screenshotList.isEmpty()) {
            screenshotGridView.setItems(Main.setObservableList.get(setIndex).screenshotList);
        }
    }

    private void loadMusics() {
        if(!Main.setObservableList.get(setIndex).musicList.isEmpty()){
            musicGridView.setItems(Main.setObservableList.get(setIndex).musicList);
        }
    }

    private void openEditorWindow(QuestionType questionType) {
        try {
            Stage editorStage = new Stage(StageStyle.UTILITY);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EditorWindow.fxml"));
            editorStage.setTitle(questionType.toString() + " Editor");
            editorStage.setScene(new Scene(loader.load(), 480, 240));
            loader.<EditorController>getController().init(questionType);

            editorStage.showAndWait();
        } catch (IOException e) {
            Dialogs.create()
                    .owner(Main.getStage())
                    .title("Fail")
                    .masthead("An exception has occurred opening the editor")
                    .message(e.getLocalizedMessage())
                    .showException(e);
        }
    }

    /**
     * Update the visibility of the edit mode UI
     */
    public void updateAddButtonVisibility() {
        addQuestionButton.setVisible(Main.isEditMode());
        addScreenshotButton.setVisible(Main.isEditMode());
        addMusicButton.setVisible(Main.isEditMode());
    }
}
