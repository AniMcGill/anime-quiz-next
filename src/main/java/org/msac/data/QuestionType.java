package org.msac.data;

/**
 * Created by Natsumi on 2014-05-20.
 */
public enum QuestionType {
    QUESTION (0),
    SCREENSHOT (1),
    MUSIC (2);

    private int value;

    QuestionType(int value){
        this.value = value;
    }
    public int value(){
        return value;
    }

    @Override
    public String toString() {
        String s = super.toString();
        return s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
    }
}
