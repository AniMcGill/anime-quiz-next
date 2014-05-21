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
    private ArrayList<Category> categoryList = new ArrayList<Category>();

    public Set(int setId, String setName, boolean setCompleted) {
        this.setId = setId;
        this.setName = setName;
        this.setCompleted = setCompleted;
    }

    /*
    public Set(String setName) {
        this.setName = setName;
        this.setCompleted = false;
    } */

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

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(ArrayList<Category> categoryList) {
        this.categoryList = categoryList;
    }
}
