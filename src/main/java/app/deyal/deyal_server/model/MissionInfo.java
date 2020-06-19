package app.deyal.deyal_server.model;

import java.util.ArrayList;

public class MissionInfo {

    private long ratingAsClient;
    private long ratingAsContractor;
    private ArrayList<String> created;
    private ArrayList<String> completed;
    private ArrayList<String> failed;
    private ArrayList<String> ongoing;

    /*............................................................................................................*/

    public MissionInfo() {
        this.ratingAsClient = 0;
        this.ratingAsContractor = 0;
        created = new ArrayList<>();
        completed = new ArrayList<>();
        failed = new ArrayList<>();
        ongoing = new ArrayList<>();
    }

    public void changeRatingAsClient(boolean increase, MissionDifficulty missionDifficulty) {
        switch (missionDifficulty) {
            case VERY_EASY:
                ratingAsClient += increase ? 10 : -10;
                break;
            case EASY:
                ratingAsClient += increase ? 20 : -20;
                break;
            case MEDIUM:
                ratingAsClient += increase ? 30 : -30;
                break;
            case HARD:
                ratingAsClient += increase ? 40 : -40;
                break;
            case VERY_HARD:
                ratingAsClient += increase ? 50 : -50;
                break;
        }
    }

    public void changeRatingAsContractor(boolean increase, MissionDifficulty missionDifficulty) {
        switch (missionDifficulty) {
            case VERY_EASY:
                ratingAsContractor += increase ? 10 : -10;
                break;
            case EASY:
                ratingAsContractor += increase ? 20 : -20;
                break;
            case MEDIUM:
                ratingAsContractor += increase ? 30 : -30;
                break;
            case HARD:
                ratingAsContractor += increase ? 40 : -40;
                break;
            case VERY_HARD:
                ratingAsContractor += increase ? 50 : -50;
                break;
        }
    }

    /*............................................................................................................*/

    public long getRatingAsClient() {
        return ratingAsClient;
    }

    public void setRatingAsClient(long ratingAsClient) {
        this.ratingAsClient = ratingAsClient;
    }

    public long getRatingAsContractor() {
        return ratingAsContractor;
    }

    public void setRatingAsContractor(long ratingAsContractor) {
        this.ratingAsContractor = ratingAsContractor;
    }

    public ArrayList<String> getCreated() {
        return created;
    }

    public void setCreated(ArrayList<String> created) {
        this.created = created;
    }

    public ArrayList<String> getCompleted() {
        return completed;
    }

    public void setCompleted(ArrayList<String> completed) {
        this.completed = completed;
    }

    public ArrayList<String> getFailed() {
        return failed;
    }

    public void setFailed(ArrayList<String> failed) {
        this.failed = failed;
    }

    public ArrayList<String> getOngoing() {
        return ongoing;
    }

    public void setOngoing(ArrayList<String> ongoing) {
        this.ongoing = ongoing;
    }

}
