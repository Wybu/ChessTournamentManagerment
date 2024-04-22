package myModule.model;

public class Round {

    private int roundID;
    private String roundName;
    private String time;

    public Round(int roundID, String roundName, String time) {
        this.roundID = roundID;
        this.roundName = roundName;
        this.time = time;
    }

    public int getRoundID() {
        return roundID;
    }

    public void setRoundID(int roundID) {
        this.roundID = roundID;
    }

    public String getRoundName() {
        return roundName;
    }

    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}