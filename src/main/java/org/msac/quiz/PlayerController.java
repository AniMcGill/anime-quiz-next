package org.msac.quiz;

import Data.Music;
import Data.Question;
import Data.Screenshot;
import Data.Set;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.util.Callback;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;

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

    }

    @FXML
    private void addScreenshotButton_Click(){

    }

    @FXML
    private void addMusicButton_Click(){

    }

    private int setIndex;

    public void init(int setIndex) {
        this.setIndex = setIndex;
        loadSet();
    }
    public void loadSet() {
        //TODO: get QuestionItems
        if(!Main.setObservableList.get(setIndex).questionList.isEmpty()){
            questionGridView.setItems(Main.setObservableList.get(setIndex).questionList);
        }
        if(!Main.setObservableList.get(setIndex).screenshotList.isEmpty()) {
            screenshotGridView.setItems(Main.setObservableList.get(setIndex).screenshotList);
        }
        if(!Main.setObservableList.get(setIndex).musicList.isEmpty()){
            musicGridView.setItems(Main.setObservableList.get(setIndex).musicList);
        }
        updateAddButtonVisibility();
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
