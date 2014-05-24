package org.msac.quiz;

import Data.Set;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class Main extends Application {
    private static Stage stage;
    private static File file;

    private static String dbConnectionString;

    protected static ObservableList<Set> setObservableList = FXCollections.observableArrayList(new ArrayList<Set>());

    private static boolean editMode = true;
    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainWindow.fxml"));
        stage.setTitle("Anime Quiz Next");
        stage.setScene(new Scene(root, 1024, 768));

        stage.show();
    }
    public static Stage getStage(){
        return stage;
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
    }

    public static void main(String[] args) {
        launch(args);
    }
}
