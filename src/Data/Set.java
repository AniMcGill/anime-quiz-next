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
    private ArrayList<Question> questionList = new ArrayList<Question>();
    private ArrayList<Screenshot> screenshotList = new ArrayList<Screenshot>();
    private ArrayList<Music> musicList = new ArrayList<Music>();

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

    public ArrayList<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(ArrayList<Question> questionList) {
        this.questionList = questionList;
    }

    public ArrayList<Screenshot> getScreenshotList() {
        return screenshotList;
    }

    public void setScreenshotList(ArrayList<Screenshot> screenshotList) {
        this.screenshotList = screenshotList;
    }

    public ArrayList<Music> getMusicList() {
        return musicList;
    }

    public void setMusicList(ArrayList<Music> musicList) {
        this.musicList = musicList;
    }

    @Override
    public String toString() {
        return setName;
    }
}
