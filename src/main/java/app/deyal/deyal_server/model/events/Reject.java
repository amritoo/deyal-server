package app.deyal.deyal_server.model.events;

public class Reject {
    private String rejectMessage;

    public Reject() {
    }

    public Reject(String rejectMessage) {
        this.rejectMessage = rejectMessage;
    }

    public String getRejectMessage() {
        return rejectMessage;
    }

    public void setRejectMessage(String rejectMessage) {
        this.rejectMessage = rejectMessage;
    }
}
