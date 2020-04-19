package app.deyal.deyal_server.model.events;

public enum EventType {
    CREATE,
    UPDATE,
    PUBLISH,
    REQUEST,
    ASSIGN,
    SUBMIT,
    APPROVE,
    REJECT,
    REVIEW;

    EventType() {
    }
}
