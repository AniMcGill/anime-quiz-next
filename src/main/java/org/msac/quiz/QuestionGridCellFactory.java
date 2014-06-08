package org.msac.quiz;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;
import org.controlsfx.dialog.Dialogs;
import org.msac.data.Question;

import java.io.IOException;

/**
 * Created by Natsumi on 2014-06-08.
 */
public class QuestionGridCellFactory<T> implements Callback<GridView<T>, GridCell<T>> {
    private int setIndex;

    public QuestionGridCellFactory(int setIndex) {
        this.setIndex = setIndex;
    }

    @Override
    public GridCell call(GridView param) {
        GridCell<Question> cell = new GridCell<Question>(){
            @Override
            protected void updateItem(Question item, boolean empty) {
                super.updateItem(item, empty);
                if(item != null) {
                    setText(item.toString());
                    setStyle("-fx-border-color: black; -fx-background-color: lightcoral; -fx-font-weight: bolder; -fx-alignment: center; -fx-font-size: 18px;");
                    setVisible(Main.isEditMode() || !item.isQuestionAnswered());
                }
                else {
                    setText("");
                }
            }
        };
        cell.setOnMouseClicked(event -> openEditorWindow(cell.getItem()));
        return cell;
    }

    private void openEditorWindow(Question question){
        try {
            Stage editorStage = new Stage(StageStyle.UTILITY);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EditorWindow.fxml"));
            editorStage.setTitle(question.getQuestionCategory().toString() + " Editor");
            editorStage.setScene(new Scene(loader.load(), 480, 240));
            loader.<EditorController>getController().init(question, setIndex);

            editorStage.setOnHiding(event -> loader.<EditorController>getController().close());

            editorStage.show();
        } catch (IOException e) {
            Dialogs.create()
                    .owner(Main.getStage())
                    .title("Fail")
                    .masthead("An exception has occurred opening the editor")
                    .message(e.getLocalizedMessage())
                    .showException(e);
        }
    }
}
