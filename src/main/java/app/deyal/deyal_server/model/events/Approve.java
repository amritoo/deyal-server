package app.deyal.deyal_server.model.events;

public class Approve {
    private String approveMessage;

    public Approve() {
    }

    public Approve(String approveMessage) {
        this.approveMessage = approveMessage;
    }

    public String getApproveMessage() {
        return approveMessage;
    }

    public void setApproveMessage(String approveMessage) {
        this.approveMessage = approveMessage;
    }
}
