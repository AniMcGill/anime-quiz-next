package org.msac.quiz;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

/**
 * Created by Natsumi on 2014-05-28.
 */
public class EditorController {
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
                .owner((Stage) cancelButton.getScene().getWindow())
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
                .owner((Stage) deleteButton.getScene().getWindow())
                .title("Confirm deletion")
                .message("Are you sure you want to delete this item?")
                .showConfirm();
        if(response == Dialog.Actions.YES) {
            //TODO delete item and update
            close();
        }
    }

    private void close() {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }
}
