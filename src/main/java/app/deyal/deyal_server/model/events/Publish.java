package app.deyal.deyal_server.model.events;

public class Publish {
    private String note;

    public Publish() {
    }

    public Publish(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
