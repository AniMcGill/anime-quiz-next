package org.msac.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by Natsumi on 2014-05-20.
 */
public class Set {
    private int setId;
    private String setName;
    private boolean setCompleted;
    public ObservableList<Question> questionList = FXCollections.observableArrayList(new ArrayList<>());
    public ObservableList<Question> screenshotList = FXCollections.observableArrayList(new ArrayList<>());
    public ObservableList<Question> musicList = FXCollections.observableArrayList(new ArrayList<>());

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
