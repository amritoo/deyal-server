package app.deyal.deyal_server.model;

import java.util.ArrayList;

public class MissionInfo {

    private int ratingAsClient;
    private int ratingAsContractor;
    private ArrayList<Integer> created;
    private ArrayList<Integer> completed;
    private ArrayList<Integer> failed;

    public MissionInfo() {
        this.ratingAsClient = 0;
        this.ratingAsContractor = 0;
    }

    public MissionInfo(int ratingAsClient, int ratingAsContractor, ArrayList<Integer> created, ArrayList<Integer> completed, ArrayList<Integer> failed) {
        this.ratingAsClient = ratingAsClient;
        this.ratingAsContractor = ratingAsContractor;
        this.created = created;
        this.completed = completed;
        this.failed = failed;
    }

    /* ------------------------------------------------------------------------- */

    public int getRatingAsClient() {
        return ratingAsClient;
    }

    public void setRatingAsClient(int ratingAsClient) {
        this.ratingAsClient = ratingAsClient;
    }

    public int getRatingAsContractor() {
        return ratingAsContractor;
    }

    public void setRatingAsContractor(int ratingAsContractor) {
        this.ratingAsContractor = ratingAsContractor;
    }

    public ArrayList<Integer> getCreated() {
        return created;
    }

    public void setCreated(ArrayList<Integer> created) {
        this.created = created;
    }

    public ArrayList<Integer> getCompleted() {
        return completed;
    }

    public void setCompleted(ArrayList<Integer> completed) {
        this.completed = completed;
    }

    public ArrayList<Integer> getFailed() {
        return failed;
    }

    public void setFailed(ArrayList<Integer> failed) {
        this.failed = failed;
    }

}
