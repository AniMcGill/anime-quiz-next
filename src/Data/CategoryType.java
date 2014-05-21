package Data;

/**
 * Created by Natsumi on 2014-05-20.
 */
public enum CategoryType {
    QUESTION (0),
    SCREENSHOT (1),
    MUSIC (2);

    private int value;

    CategoryType(int value){
        this.value = value;
    }
    public int value(){
        return value;
    }
}
