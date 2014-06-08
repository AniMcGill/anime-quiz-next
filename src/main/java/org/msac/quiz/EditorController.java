package org.msac.quiz;

import javafx.scene.control.CheckBox;
import org.msac.controls.MediaControl;
import org.msac.data.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.io.*;
import java.sql.*;

/**
 * Created by Natsumi on 2014-05-28.
 */
public class EditorController {
    @FXML private GridPane mainGrid;
    @FXML private HBox questionDataContainer;
    @FXML private TextField questionDataInput;
    @FXML private TextField answerTextField;
    @FXML private TextField pointsTextField;
    @FXML private TextField timingTextField;
    @FXML private CheckBox answeredCheckBox;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private Button deleteButton;

    private int setIndex;
    private int setId;
    private Question currentQuestion = null;
    private QuestionType questionType;
    private Button filePickerButton;
    private Node previewNode;

    @FXML
    private void saveButton_Click() {
        try {
            Question question = setQuestionItem();
            if (question != null && currentQuestion != null && question.equals(currentQuestion)) {
                switch(questionType) {
                    case QUESTION:
                        Main.setObservableList.get(setIndex).questionList.remove(currentQuestion);
                        break;
                    case SCREENSHOT:
                        Main.setObservableList.get(setIndex).screenshotList.remove(currentQuestion);
                        break;
                    case MUSIC:
                        Main.setObservableList.get(setIndex).musicList.remove(currentQuestion);
                        break;
                }
            }
            if(question != null) {
                switch(questionType) {
                    case QUESTION:
                        Main.setObservableList.get(setIndex).questionList.add(question);
                        break;
                    case SCREENSHOT:
                        Main.setObservableList.get(setIndex).screenshotList.add(question);
                        break;
                    case MUSIC:
                        Main.setObservableList.get(setIndex).musicList.add(question);
                        break;
                }
            }
            this.close();
        } catch (IOException e) {
            Dialogs.create()
                    .owner(saveButton.getScene().getWindow())
                    .title("Fail")
                    .masthead("An exception has occurred saving the question")
                    .message(e.getLocalizedMessage())
                    .showException(e);
        }
    }

    private Question setQuestionItem() throws IOException {
        String questionDataString = questionDataInput.getText();
        String questionAnswer = answerTextField.getText();
        int questionPoints = Integer.parseInt(pointsTextField.getText());
        int questionTiming = Integer.parseInt(timingTextField.getText());
        boolean questionAnswered = answeredCheckBox.isSelected();

        byte[] questionData;
        if(questionType == QuestionType.QUESTION) {
            questionData = questionDataString.getBytes("UTF-8");
        }
        else {
            //http://www.programcreek.com/2009/02/java-convert-a-file-to-byte-array-then-convert-byte-array-to-a-file/
            FileInputStream fileInputStream = new FileInputStream(questionDataString);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            for (int read = 0; (read = fileInputStream.read(buffer)) != -1 ; ) {
                byteArrayOutputStream.write(buffer, 0, read);
            }

            questionData = byteArrayOutputStream.toByteArray();
        }
        Question question = saveQuestionItem(questionData, questionAnswer, questionPoints, questionTiming, questionAnswered);
        return question;
    }

    private Question saveQuestionItem(byte[] questionData, String questionAnswer, int questionPoints, int questionTiming, boolean questionAnswered) {
        try {
            Connection connection = DriverManager.getConnection(Main.getDbConnectionString());
            String insertQuery = "INSERT OR REPLACE INTO Questions (question_data, question_answer, question_timing, " +
                    "question_points, question_answered, question_cat, set_id) VALUES (?,?,?,?,?,?,?);";
            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.setBytes(1, questionData);
            statement.setString(2, questionAnswer);
            statement.setInt(3, questionTiming);
            statement.setInt(4, questionPoints);
            statement.setBoolean(5, questionAnswered);
            statement.setInt(6, questionType.value());
            statement.setInt(7, setId);

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            int questionId = resultSet.getInt("last_insert_rowid()");
            statement.close();
            connection.close();

            return new Question(questionId, questionData, questionAnswer, questionPoints, questionAnswered, questionTiming, questionType);
        } catch (SQLException e) {
            Dialogs.create()
                    .owner(saveButton.getScene().getWindow())
                    .title("Fail")
                    .masthead("An exception has occurred saving the question to the database")
                    .message(e.getLocalizedMessage())
                    .showException(e);
        }
        return null;
    }

    @FXML
    private void cancelButton_Click() {
        Action response = Dialogs.create()
                .owner(cancelButton.getScene().getWindow())
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
                .owner(deleteButton.getScene().getWindow())
                .title("Confirm deletion")
                .message("Are you sure you want to delete this item?")
                .showConfirm();
        if(response == Dialog.Actions.YES) {
            try{
                Connection connection = DriverManager.getConnection(Main.getDbConnectionString());
                String query = "DELETE FROM Questions WHERE question_id = " + currentQuestion.getQuestionId() + ";";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.executeUpdate();

                statement.close();
                connection.close();

                switch (questionType) {
                    case QUESTION:
                        Main.setObservableList.get(setIndex).questionList.remove(currentQuestion);
                        break;
                    case SCREENSHOT:
                        Main.setObservableList.get(setIndex).screenshotList.remove(currentQuestion);
                        break;
                    case MUSIC:
                        Main.setObservableList.get(setIndex).musicList.remove(currentQuestion);
                        break;
                }
            } catch (SQLException e) {
                Dialogs.create()
                        .owner(saveButton.getScene().getWindow())
                        .title("Fail")
                        .masthead("An exception has occurred deleting the question")
                        .message(e.getLocalizedMessage())
                        .showException(e);
            }

            close();
        }
    }

    public void init(QuestionType questionType, int setIndex) {
        this.setIndex = setIndex;
        this.setId = Main.setObservableList.get(setIndex).getSetId();
        this.questionType = questionType;
        addIntegerRestrict();
        loadQuestionDataControl();
    }

    public void init(Question currentQuestion, int setIndex) {
        this.setIndex = setIndex;
        this.setId = Main.setObservableList.get(setIndex).getSetId();
        this.questionType = currentQuestion.getQuestionCategory();
        this.currentQuestion = currentQuestion;

        loadQuestion(currentQuestion);

        addIntegerRestrict();
        loadQuestionDataControl();
        deleteButton.setVisible(true);
    }

    private void loadQuestion(Question currentQuestion) {
        if(questionType == QuestionType.QUESTION) {
            try {
                questionDataInput.setText(new String(currentQuestion.getQuestionData(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                Dialogs.create()
                        .owner(saveButton.getScene().getWindow())
                        .title("Fail")
                        .masthead("An exception has occurred loading the question")
                        .message(e.getLocalizedMessage())
                        .showException(e);
            }
        }
        // TODO: music and screenshot previews from stream
        answerTextField.setText(currentQuestion.getQuestionAnswer());
        pointsTextField.setText(Integer.toString(currentQuestion.getQuestionPoints()));
        timingTextField.setText(Integer.toString(currentQuestion.getQuestionTiming()));
        answeredCheckBox.setSelected(currentQuestion.isQuestionAnswered());
    }

    private void loadQuestionDataControl() {
        if(questionType != QuestionType.QUESTION){
            questionDataInput.setEditable(false);
            addFilePicker();
        }
    }

    private void addFilePicker() {
        filePickerButton = new Button();
        filePickerButton.setOnAction(event -> openFilePicker());
        filePickerButton.setText("...");
        questionDataContainer.getChildren().add(filePickerButton);
    }

    private void openFilePicker(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a file...");
        fileChooser.setInitialDirectory(new File("."));
        if(questionType == QuestionType.SCREENSHOT) {
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp")
            );
        }
        else{
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.mp4", "*.wav", "*.m4a")
            );
        }

        File file = fileChooser.showOpenDialog(filePickerButton.getScene().getWindow());
        if(file != null){
            try {
                if (questionType == QuestionType.SCREENSHOT) {
                    loadScreenshotPreview(file);
                }
                else{
                    loadMusicPreview(file);
                }
                questionDataInput.setText(file.getAbsolutePath());
                addPreviewNode();
            }
            catch (Exception e) {
                Dialogs.create()
                        .owner(saveButton.getScene().getWindow())
                        .title("Fail")
                        .masthead("An exception has occurred loading the file")
                        .message(e.getLocalizedMessage())
                        .showException(e);
            }
        }
    }

    private void addPreviewNode() {
        mainGrid.add(previewNode, 4, 0);
        GridPane.setColumnSpan(previewNode, 2);
        GridPane.setRowSpan(previewNode, 5);
        GridPane.setValignment(previewNode, VPos.TOP);
        GridPane.setHgrow(previewNode, Priority.SOMETIMES);
        GridPane.setVgrow(previewNode, Priority.SOMETIMES);
    }

    private void loadMusicPreview(File file) {
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(file.toURI().toString()));
        previewNode = new MediaControl(mediaPlayer);
    }

    private void loadScreenshotPreview(File file) throws IOException {
        previewNode = new ImageView();
        BufferedImage bufferedImage = ImageIO.read(file);
        WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
        ((ImageView)previewNode).setImage(image);
        mainGrid.boundsInLocalProperty().addListener((observable, oldValue, newValue) -> ((ImageView)previewNode).setFitWidth(newValue.getWidth()/3));
        ((ImageView)previewNode).setPreserveRatio(true);
    }

    private void addIntegerRestrict() {
        // filter pointsTextField for integers only
        pointsTextField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if(!event.getCharacter().matches("\\d")) {
                event.consume();
            }
        });

        // filter timingTextField for integers only
        timingTextField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if(!event.getCharacter().matches("\\d")) {
                event.consume();
            }
        });
    }

    /**
     * Stop the music preview if necessary and close the stage
     */
    protected void close() {
        if (questionType == QuestionType.MUSIC) {
            ((MediaControl)previewNode).stopMusic();
        }
        ((Stage) cancelButton.getScene().getWindow()).close();
    }
}
