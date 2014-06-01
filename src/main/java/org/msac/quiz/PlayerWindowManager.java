package org.msac.quiz;

import org.msac.data.QuestionType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.controlsfx.dialog.Dialogs;

import java.io.IOException;

/**
 * Created by Natsumi on 2014-05-27.
 */
public class PlayerWindowManager {
    private Scene scene;
    private PlayerController playerController;
    private QuestionController questionController;

    public PlayerWindowManager(Scene scene){
        this.scene = scene;
    }

    public void showPlayerWindow(int setIndex) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PlayerWindow.fxml"));
            scene.setRoot(loader.load());
            playerController = loader.<PlayerController>getController();
            playerController.init(setIndex);
        } catch (IOException e) {
            Dialogs.create()
                    .owner(Main.getStage())
                    .title("Fail")
                    .masthead("An exception has occurred opening the player window")
                    .message(e.getLocalizedMessage())
                    .showException(e);
        }
    }

    public void showQuestionWindow(QuestionType questionType, int questionIndex) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/QuestionWindow.fxml"));
            scene.setRoot(loader.load());
            questionController = loader.<QuestionController>getController();
            // TODO: init method
        } catch (IOException e) {
            Dialogs.create()
                    .owner(Main.getStage())
                    .title("Fail")
                    .masthead(e.getLocalizedMessage())
                    .showException(e);
        }
    }

    /**
     * Ask the player controller to update the visibility of the edit mode UI in the player window
     */
    public void toggleEditMode() {
        playerController.updateAddButtonVisibility();
    }
}
