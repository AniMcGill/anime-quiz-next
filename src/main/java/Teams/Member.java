package Teams;

/**
 * Created by Natsumi on 2014-05-19.
 */
public class Member {
    private int memberId;
    private String memberName;
    private int memberScore;

    public Member(int memberId) {
        this.memberId = memberId;
        this.memberScore = 0;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getMemberScore() {
        return memberScore;
    }
}
