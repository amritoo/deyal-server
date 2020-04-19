package app.deyal.deyal_server.model.events;

import app.deyal.deyal_server.model.Mission;

public class Update {
    Mission oldMission;

    public Mission getOldMission() {
        return oldMission;
    }

    public void setOldMission(Mission oldMission) {
        this.oldMission = oldMission;
    }
}
