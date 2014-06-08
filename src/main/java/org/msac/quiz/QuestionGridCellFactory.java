package org.msac.quiz;

import javafx.scene.layout.Border;
import javafx.util.Callback;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;
import org.msac.data.Question;

/**
 * Created by Natsumi on 2014-06-08.
 */
public class QuestionGridCellFactory<T> implements Callback<GridView<T>, GridCell<T>> {
    public QuestionGridCellFactory() {
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
        //TODO: event handlers for cell
        return cell;
    }
}
