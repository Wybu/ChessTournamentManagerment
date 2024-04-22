package myModule.model;

public class Matches {
    private int matchID;
    private String category;
    private String round;
    private int refereeID;
    private String date;
    private String time;

    public Matches(int matchID, String category, String round, int refereeID, String date, String time) {
        this.matchID = matchID;
        this.category = category;
        this.round = round;
        this.refereeID = refereeID;
        this.date = date;
        this.time = time;
    }

    public int getMatchID() {
        return matchID;
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public int getRefereeID() {
        return refereeID;
    }

    public void setRefereeID(int refereeID) {
        this.refereeID = refereeID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void searchMatch() {
        // Logic to search for a match
        // This method can be implemented based on your specific requirements
        System.out.println("Match searched!");
    }
}