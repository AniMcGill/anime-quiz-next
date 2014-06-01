package org.msac.data;

/**
 * Created by Natsumi on 2014-05-20.
 */
public abstract class QuestionItem {
    private int questionId;

    private String questionAnswer;
    private int questionPoints;
    private boolean questionAnswered;
    private int questionTiming;
    private QuestionType questionCategory;  // might be needed?

    protected QuestionItem(int questionId,
                           String questionAnswer,
                           int questionPoints,
                           boolean questionAnswered,
                           int questionTiming,
                           QuestionType questionCategory) {
        this.questionId = questionId;
        this.questionAnswer = questionAnswer;
        this.questionPoints = questionPoints;
        this.questionAnswered = questionAnswered;
        this.questionTiming = questionTiming;
        this.questionCategory = questionCategory;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public int getQuestionPoints() {
        return questionPoints;
    }

    public void setQuestionPoints(int questionPoints) {
        this.questionPoints = questionPoints;
    }

    public boolean isQuestionAnswered() {
        return questionAnswered;
    }

    public void setQuestionAnswered(boolean questionAnswered) {
        this.questionAnswered = questionAnswered;
    }

    public int getQuestionTiming() {
        return questionTiming;
    }

    public void setQuestionTiming(int questionTiming) {
        this.questionTiming = questionTiming;
    }

    public QuestionType getQuestionCategory() {
        return questionCategory;
    }

    public void setQuestionCategory(QuestionType questionCategory) {
        this.questionCategory = questionCategory;
    }

    @Override
    public String toString() {
        return Integer.toString(questionPoints);
    }
}
