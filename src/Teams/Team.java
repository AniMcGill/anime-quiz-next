package Teams;

import java.util.ArrayList;

/**
 * Created by Natsumi on 2014-05-19.
 */
public class Team {
    private int teamId;
    private String teamName;
    private String teamBuzzer;  // might change to actual buzzer object
    private ArrayList<Member> teamMembers;

    public Team(int teamId, String teamName, String teamBuzzer) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamBuzzer = teamBuzzer;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamBuzzer() {
        return teamBuzzer;
    }

    public void setTeamBuzzer(String teamBuzzer) {
        this.teamBuzzer = teamBuzzer;
    }

    public ArrayList<Member> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(ArrayList<Member> teamMembers) {
        this.teamMembers = teamMembers;
    }
}
