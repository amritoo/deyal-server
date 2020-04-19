package app.deyal.deyal_server.api.v1;

import app.deyal.deyal_server.manager.MissionEventManager;
import app.deyal.deyal_server.model.ApiError;
import app.deyal.deyal_server.model.MissionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("MissionEventEndpoint_v1")
public class MissionEventEndpointImpl implements MissionEventEndpoint {

    public static final Logger log = LoggerFactory.getLogger(MissionEvent.class);

    @Autowired
    private MissionEventManager missionEventManager;

    @Override
    public ResponseEntity<?> list(String missionId) {
        List<MissionEvent> missionEvents = missionEventManager.findAllMissionEvents(missionId);
        return ResponseEntity.ok(ApiError.SUCCESS.toMap(missionEvents));
    }
}
