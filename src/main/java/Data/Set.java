package Data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natsumi on 2014-05-20.
 */
public class Set {
    private int setId;
    private String setName;
    private boolean setCompleted;
    protected ObservableList<Question> questionList = FXCollections.observableArrayList(new ArrayList<Question>());
    protected ObservableList<Screenshot> screenshotList = FXCollections.observableArrayList(new ArrayList<Screenshot>());
    protected ObservableList<Music> musicList = FXCollections.observableArrayList(new ArrayList<Music>());
    //private ArrayList<Question> questionList = new ArrayList<Question>();
    //private ArrayList<Screenshot> screenshotList = new ArrayList<Screenshot>();
    //private ArrayList<Music> musicList = new ArrayList<Music>();

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

    @Override
    public String toString() {
        return setName;
    }
}
