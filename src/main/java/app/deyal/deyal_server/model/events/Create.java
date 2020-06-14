package app.deyal.deyal_server.model.events;

public class Create {
    private String createdBy;

    public Create() {
    }

    public Create(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
