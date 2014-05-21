package Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natsumi on 2014-05-20.
 */
public class Set {
    private int setId;
    private String setName;
    private boolean setCompleted;
    private ArrayList<QuestionItem> questionList = new ArrayList<QuestionItem>();
    private ArrayList<QuestionItem> screenshotList = new ArrayList<QuestionItem>();
    private ArrayList<QuestionItem> musicList = new ArrayList<QuestionItem>();

    public Set(int setId, String setName, boolean setCompleted) {
        this.setId = setId;
        this.setName = setName;
        this.setCompleted = setCompleted;
    }

    public int getSetId() {
        return setId;
    }

    public void setSetId(int setId) {
        this.setId = setId;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public boolean isSetCompleted() {
        return setCompleted;
    }

    public void setSetCompleted(boolean setCompleted) {
        this.setCompleted = setCompleted;
    }

    public ArrayList<QuestionItem> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(ArrayList<QuestionItem> questionList) {
        this.questionList = questionList;
    }

    public ArrayList<QuestionItem> getScreenshotList() {
        return screenshotList;
    }

    public void setScreenshotList(ArrayList<QuestionItem> screenshotList) {
        this.screenshotList = screenshotList;
    }

    public ArrayList<QuestionItem> getMusicList() {
        return musicList;
    }

    public void setMusicList(ArrayList<QuestionItem> musicList) {
        this.musicList = musicList;
    }
}
