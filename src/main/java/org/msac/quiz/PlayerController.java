package org.msac.quiz;

import Data.Music;
import Data.Question;
import Data.Screenshot;
import Data.Set;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
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

    protected void notifySetSelected(Set selectedSet) {
        int setIndex = Main.setObservableList.indexOf(selectedSet);
        if(!Main.setObservableList.get(setIndex).questionList.isEmpty()){
            questionGridView.setItems(Main.setObservableList.get(setIndex).questionList);
        }
        if(!Main.setObservableList.get(setIndex).screenshotList.isEmpty()) {
            screenshotGridView.setItems(Main.setObservableList.get(setIndex).screenshotList);
        }
        if(!Main.setObservableList.get(setIndex).musicList.isEmpty()){
            musicGridView.setItems(Main.setObservableList.get(setIndex).musicList);
        }

    }
}
