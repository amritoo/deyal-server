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

    public void changeRatingAsClient(boolean increase) {
        if(increase) {
            ratingAsClient += ratingAsClient >= 50 ?  2 : 5;
        } else {
            ratingAsClient -= ratingAsClient >= 50 ? 2 : 5;
        }
    }

    public void changeRatingAsContractor(boolean increase) {
        if(increase) {
            ratingAsContractor += ratingAsContractor >= 50 ? 2 : 5;
        } else {
            ratingAsContractor -= ratingAsContractor >= 50 ? 2 : 5;
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
