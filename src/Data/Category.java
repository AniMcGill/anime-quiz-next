package Data;

import java.util.ArrayList;

/**
 * Created by Natsumi on 2014-05-20.
 */
public class Category {
    private int categoryId;
    private CategoryType categoryType;
    private ArrayList<QuestionItem> questionItemList;

    public Category(int categoryId, CategoryType categoryType) {
        this.categoryId = categoryId;
        this.categoryType = categoryType;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }

    public ArrayList<QuestionItem> getQuestionItemList() {
        return questionItemList;
    }

    public void setQuestionItemList(ArrayList<QuestionItem> questionItemList) {
        this.questionItemList = questionItemList;
    }
}
