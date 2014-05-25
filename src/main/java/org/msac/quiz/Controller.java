package org.msac.quiz;

import Data.Set;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import org.controlsfx.dialog.Dialogs;

import java.io.File;
import java.sql.*;

public class Controller {
    @FXML private HBox setButtonBar;
    @FXML private ComboBox setComboBox;
    @FXML private Button setRenameButton;
    @FXML private ToggleButton editModeToggleButton;

    @FXML
    private void createGameDialog(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Create a new game file...");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Anime Quiz", "*.s3db")
        );
        File file = fileChooser.showSaveDialog(Main.getStage());
        if(file != null) {
            // delete existing file
            file.delete();
            Main.setFile(file);

            createDatabase(Main.getFile());
            loadFile(Main.getFile());
        }
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

    /**
     * Load a Set when selected from the setComboBox
     * and display additional UI elements.
     */
    @FXML
    private void setComboBox_SelectionChanged(){
        setRenameButton.setVisible(setComboBox.getValue() != null);
    }

    @FXML
    private void setAddButton_Click(){
        String setName = Dialogs.create()
                .owner(Main.getStage())
                .title("Add Set")
                .message("Enter Set name")
                .showTextInput();
        Set addedSet = addSet(setName);
        if(addedSet != null){
            Main.setObservableList.add(addedSet);
            // bind the set combobox items
            if(Main.setObservableList.size() == 1) {
                setComboBox.setItems(Main.setObservableList);
            }
            // Select the current set
            setComboBox.setValue(Main.setObservableList.get(Main.setObservableList.indexOf(addedSet)));
        }
    }


    @FXML void setRenameButton_Click(){
        String newSetName = Dialogs.create()
                .owner(Main.getStage())
                .title("Rename Set")
                .message("Enter new Set name")
                .showTextInput();
        if(renameSet(((Set) setComboBox.getValue()).getSetId(), newSetName)) {
            int setIndex = Main.setObservableList.indexOf(setComboBox.getValue());
            Main.setObservableList.get(setIndex).setSetName(newSetName);
            setComboBox.getSelectionModel().clearSelection();
            setComboBox.getSelectionModel().select(setIndex);
        }
    }

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
            Connection connection = DriverManager.getConnection(Main.getDbConnectionString());
            connection.setAutoCommit(true);
            Statement statement = connection.createStatement();
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
            // Questions
            sql = "CREATE TABLE Questions (" +
                    "question_id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "question_data BLOB  NOT NULL," +
                    "question_answer NVARCHAR(255)  NOT NULL," +
                    "question_timing INTEGER  NOT NULL," +
                    "question_points INTEGER  NOT NULL," +
                    "question_answered BOOLEAN DEFAULT 'false' NOT NULL," +
                    "question_cat INTEGER  NOT NULL," +
                    "set_id INTEGER  NOT NULL," +
                    "FOREIGN KEY (set_id) REFERENCES Sets(set_id))";
            statement.execute(sql);

            // Close
            statement.close();
            connection.close();
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
        if(!Main.setObservableList.isEmpty()){
            Main.setObservableList.clear();
        }
        if(getSets()) {
            // if the set list isn't empty, we can bind it
            if(!Main.setObservableList.isEmpty()){
                setComboBox.setItems(Main.setObservableList);
            }
            // show UI
            setButtonBar.setVisible(true);
            editModeToggleButton.setSelected(Main.isEditMode());
        }
        else{   // just in case
            setButtonBar.setVisible(false);
        }
    }

    /**
     * Get the Sets.
     */
    private boolean getSets(){
        try {
            Connection connection = DriverManager.getConnection(Main.getDbConnectionString());
            Statement statement = connection.createStatement();
            String setQuery = "SELECT * FROM Sets";
            ResultSet setResults = statement.executeQuery(setQuery);

            while(setResults.next()){
                Set newSet = new Set(setResults.getInt("set_id"), setResults.getString("set_name"), setResults.getBoolean("set_completed"));
                Main.setObservableList.add(newSet);
            }
            statement.close();
            connection.close();
            return true;
        } catch (Exception e) {
            Dialogs.create()
                    .owner(Main.getStage())
                    .title("Fail")
                    .masthead("An exception has occurred loading the file.")
                    .message(e.getLocalizedMessage())
                    .showException(e);
            return false;
        }
    }

    /**
     * Add a Set to the database
     * @param setName
     * @return
     */
    private Set addSet(String setName){
        try {
            Connection connection = DriverManager.getConnection(Main.getDbConnectionString());
            Statement statement = connection.createStatement();
            String addQuery = "INSERT INTO Sets (set_name) VALUES ('" + setName + "')";
            statement.execute(addQuery);
            ResultSet resultSet = statement.getGeneratedKeys();
            int setId = resultSet.getInt("last_insert_rowid()");
            statement.close();
            connection.close();
            Set addedSet = new Set(setId, setName, false);

            return addedSet;
        } catch (Exception e){
            Dialogs.create()
                    .owner(Main.getStage())
                    .title("Fail")
                    .masthead("An exception has occurred adding the Set")
                    .message(e.getLocalizedMessage())
                    .showException(e);
            return null;
        }
    }

    /**
     * Rename a Set
     * @param setId the Set ID
     * @param newSetName the new Set name
     * @return true if the operation was successful; false otherwise
     */
    private boolean renameSet(int setId, String newSetName){
        try{
            Connection connection = DriverManager.getConnection(Main.getDbConnectionString());
            Statement statement = connection.createStatement();
            String updateQuery = "UPDATE Sets SET set_name = '" + newSetName + "' WHERE set_id = " + setId;
            statement.execute(updateQuery);
            ResultSet resultSet = statement.getGeneratedKeys();
            return resultSet.next();
        } catch (Exception e){
            Dialogs.create()
                    .owner(Main.getStage())
                    .title("Fail")
                    .masthead("An exception has occurred renaming the Set")
                    .message(e.getLocalizedMessage())
                    .showException(e);
            return false;
        }
    }
}
