package app.deyal.deyal_server.model;

public enum RequestType {
    CREATE,
    COMPLETED,
    FAILED,
    ONGOING,
    CLIENT_INCREASE,
    CLIENT_DECREASE,
    CLIENT_DECREASE_MORE,
    CONTRACTOR_INCREASE,
    CONTRACTOR_DECREASE;

    RequestType() {
    }
}
