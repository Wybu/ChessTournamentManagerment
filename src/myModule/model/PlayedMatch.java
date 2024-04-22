package myModule.model;

public class PlayedMatch {
    private String startTime;
    private String endTime;
    private int matchScore;

    public PlayedMatch(String startTime, String endTime, int matchScore) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.matchScore = matchScore;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(int matchScore) {
        this.matchScore = matchScore;
    }

    public void saveResult() {
        // Logic to save the match result
        // This method can be implemented based on your specific requirements
        System.out.println("Match result saved!");
    }
}