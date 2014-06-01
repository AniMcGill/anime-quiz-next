package org.msac.data;

/**
 * Created by Natsumi on 2014-05-21.
 */
public class Question extends QuestionItem {
    private String questionData;

    private Question(int questionId,
                     String questionData,
                     String questionAnswer,
                     int questionPoints,
                     boolean questionAnswered,
                     int questionTiming,
                     QuestionType questionCategory){
        super(questionId, questionAnswer, questionPoints, questionAnswered, questionTiming, questionCategory);
        this.questionData = questionData;
    }

    public String getQuestionData() {
        return questionData;
    }

    public void setQuestionData(String questionData) {
        this.questionData = questionData;
    }
}
