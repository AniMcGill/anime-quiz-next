package MainWindow;

import Data.Set;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private static Stage stage;
    private static File file;

    private static String dbConnectionString;

    private static ArrayList<Set> setList = new ArrayList<Set>();

    private static boolean editMode = true;
    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
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

    public static ArrayList<Set> getSetList() {
        return setList;
    }
    public static void setSetList(ArrayList<Set> setList) {
        Main.setList = setList;
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
