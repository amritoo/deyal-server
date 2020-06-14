package app.deyal.deyal_server.model;

import java.util.ArrayList;

public class MissionInfo {

    private double ratingAsClient;
    private double ratingAsContractor;
    private ArrayList<String> created;
    private ArrayList<String> completed;
    private ArrayList<String> failed;
    private ArrayList<String> ongoing;

    public MissionInfo() {
        this.ratingAsClient = 0.0;
        this.ratingAsContractor = 0.0;
        created = new ArrayList<>();
        completed = new ArrayList<>();
        failed = new ArrayList<>();
        ongoing = new ArrayList<>();
    }

    /* ------------------------------------------------------------------------- */

    public void changeRatingAsClient(boolean increase) {
        if(increase) {
            ratingAsClient += ratingAsClient >= 5.0 ? 0.25 : 0.5;
        } else {
            ratingAsClient -= ratingAsClient >= 5.0 ? 0.25 : 0.5;
        }
    }

    public void changeRatingAsContractor(boolean increase) {
        if(increase) {
            ratingAsContractor += ratingAsContractor >= 5.0 ? 0.5 : 1.0;
        } else {
            ratingAsContractor -= ratingAsContractor >= 5.0 ? 0.25 : 0.5;
        }
    }

    public double getRatingAsClient() {
        return ratingAsClient;
    }

    public void setRatingAsClient(double ratingAsClient) {
        this.ratingAsClient = ratingAsClient;
    }

    public double getRatingAsContractor() {
        return ratingAsContractor;
    }

    public void setRatingAsContractor(double ratingAsContractor) {
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
