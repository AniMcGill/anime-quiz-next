package MainWindow;

import Data.Category;
import Data.CategoryType;
import Data.Set;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import org.controlsfx.dialog.Dialogs;

import java.io.File;
import java.sql.*;

public class Controller {
    @FXML private MenuBar menuBar;
    @FXML private HBox setButtonBar;

    @FXML
    private void createGameDialog(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Create a new game file...");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Anime Quiz", "*.s3db")
        );
        File file = fileChooser.showSaveDialog(Main.getStage());
        // delete existing file
        file.delete();
        Main.setFile(file);

        createDatabase(Main.getFile());
        loadFile(Main.getFile());
    }

    @FXML
    private void loadGameDialog(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load a game file...");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Anime Quiz", "*.s3db")
        );
        File file = fileChooser.showOpenDialog(Main.getStage());
        if(file != null){
            Main.setFile(file);
            loadFile(Main.getFile());
        }
    }

    @FXML
    private void exitGame(){
        //TODO: saving state before exiting?
        Platform.exit();
    }

    @FXML
    private void setComboBox_SelectionChanged(){
        //TODO event handler
    }

    @FXML
    private void setAddButton_Click(){
        String setName = Dialogs.create()
                .owner(Main.getStage())
                .title("Add Set")
                .message("Enter Set name")
                .showTextInput();
        // TODO: add set and set as current set
    }
    @FXML private ToggleButton editModeToggleButton;
    @FXML
    private void editModeToggle_Toggled(){
        Main.setEditMode(editModeToggleButton.isSelected());
    }

    /**
     * Create a new database file
     * @param file the database filename
     */
    private void createDatabase(File file){
        try {
            Statement statement = Main.getConnection().createStatement();
            // Teams
            String sql = "CREATE TABLE Teams (" +
                    "team_id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "team_name NVARCHAR(255)  NULL," +
                    "team_buzzer VARCHAR(9)  NOT NULL," +
                    "team_score INTEGER DEFAULT '0' NOT NULL)";
            statement.execute(sql);
            // Members
            sql = "CREATE TABLE Members (" +
                    "member_id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "member_name NVARCHAR(255)  NOT NULL," +
                    "member_score INTEGER DEFAULT '0' NOT NULL," +
                    "team_id INTEGER  NOT NULL," +
                    "FOREIGN KEY (team_id) REFERENCES Teams(team_id))";
            statement.execute(sql);

            // Sets
            sql = "CREATE TABLE Sets (" +
                    "set_id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "set_name NVARCHAR(20)  NOT NULL," +
                    "set_completed BOOLEAN DEFAULT 'false' NOT NULL)";
            statement.execute(sql);
            // Categories
            sql = "CREATE TABLE Categories (" +
                    "cat_id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "cat_type INTEGER  NOT NULL," +
                    "set_id INTEGER  NOT NULL," +
                    "FOREIGN KEY (set_id) REFERENCES Sets(set_id))";
            statement.execute(sql);
            // Questions
            sql = "CREATE TABLE Questions (" +
                    "question_id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "question_data BLOB  NOT NULL," +
                    "question_answer NVARCHAR(255)  NOT NULL," +
                    "question_timing INTEGER  NOT NULL," +
                    "question_points INTEGER  NOT NULL," +
                    "question_answered BOOLEAN DEFAULT 'false' NOT NULL," +
                    "cat_id INTEGER  NOT NULL," +
                    "FOREIGN KEY (cat_id) REFERENCES Categories(cat_id))";
            statement.execute(sql);

            // Close
            statement.close();
            //connection.close(); //never closing the connection, what could go wrong?
        } catch (Exception e) {
            Dialogs.create()
                    .owner( Main.getStage())
                    .title("Fail")
                    .masthead("An exception has occurred creating the file.")
                    .message(e.getLocalizedMessage())
                    .showException(e);
        }
    }

    /**
     * Load the database file.
     * @param file the database filename
     */
    private void loadFile(File file){
        //TODO
        try {
            Statement statement = Main.getConnection().createStatement();
            String setQuery = "SELECT * FROM Sets";
            ResultSet setResults = statement.executeQuery(setQuery);

            while(setResults.next()){
                Set newSet = new Set(setResults.getInt("set_id"), setResults.getNString("set_name"), setResults.getBoolean("set_completed"));
                String categoryQuery = "SELECT * FROM Categories WHERE set_id = " + setResults.getInt("set_id");
                ResultSet categoryResults = statement.executeQuery(categoryQuery);
                while(categoryResults.next()) {
                    Category newCategory = new Category(categoryResults.getInt("cat_id"),CategoryType.values()[categoryResults.getInt("cat_type")]);
                    newSet.getCategoryList().add(newCategory);  //TODO: check if this is safe
                    // TODO: get questions........
                }
                Main.getSetList().add(newSet);  //TODO: check if this is safe
            }
        } catch (Exception e) {
            Dialogs.create()
                    .owner(Main.getStage())
                    .title("Fail")
                    .masthead("An exception has occurred loading the file.")
                    .message(e.getLocalizedMessage())
                    .showException(e);
        }
        setButtonBar.setVisible(true);
        editModeToggleButton.setSelected(Main.isEditMode());
    }
}
