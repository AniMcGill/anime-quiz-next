package org.msac.quiz;

import org.msac.data.Set;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.util.ArrayList;

public class Main extends Application {
    private static Stage stage;
    private static Stage playerStage;
    private static PlayerWindowManager playerWindowManager;
    private static File file;

    private static String dbConnectionString;

    protected static ObservableList<Set> setObservableList = FXCollections.observableArrayList(new ArrayList<>());

    private static boolean editMode = true;
    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainWindow.fxml"));
        stage.setTitle("Anime Quiz Next");
        stage.setScene(new Scene(root, 1024, 768));

        // Set up Player Window
        playerStage = new Stage(StageStyle.UTILITY);
        Scene playerScene = new Scene(new StackPane());
        playerWindowManager = new PlayerWindowManager(playerScene);
        playerStage.setTitle("Anime Quiz Next - Player Window");
        playerStage.setScene(playerScene);

        stage.setOnCloseRequest(event -> playerStage.close());

        stage.show();
        playerStage.show();
        stage.toFront();
    }
    public static Stage getStage(){
        return stage;
    }

    public static void showPlayerWindow(int setIndex){
        playerWindowManager.showPlayerWindow(setIndex);
    }

    public static File getFile(){
        return file;
    }
    public static void setFile(File file) {
        Main.file = file;
        setDbConnectionString();
    }

    public static String getDbConnectionString(){
        return dbConnectionString;
    }
    public static void setDbConnectionString(){
        if (file != null){
            dbConnectionString = "jdbc:sqlite:" + file.getAbsolutePath();
        }
    }

    public static boolean isEditMode() {
        return editMode;
    }
    public static void setEditMode(boolean editMode) {
        Main.editMode = editMode;
        playerWindowManager.toggleEditMode();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
