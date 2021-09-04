package app.deyal.deyal_server.api.v1;

import app.deyal.deyal_server.manager.AuthManager;
import app.deyal.deyal_server.manager.MissionEventManager;
import app.deyal.deyal_server.manager.MissionManager;
import app.deyal.deyal_server.manager.SecurityManager;
import app.deyal.deyal_server.model.*;
import app.deyal.deyal_server.model.events.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController("MissionEndpoint_v1")
public class MissionEndpointImpl implements MissionEndpoint {

    public static final Logger log = LoggerFactory.getLogger(Mission.class);

    @Autowired
    private SecurityManager securityManager;

    @Autowired
    private AuthManager authManager;

    @Autowired
    private MissionManager missionManager;

    @Autowired
    private MissionEventManager missionEventManager;

    @Override
    public ResponseEntity<?> list(String token) {
        try {
            String userId = securityManager.verify(token);
            User user = authManager.retrieveUserById(userId);

            List<Mission> missions = missionManager.findAllMissionsSorted();
            return ResponseEntity.ok(ApiError.SUCCESS.toMap(missions));
        } catch (ApiError er) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(er.toMap());
        } catch (Exception ex) {
            log.error("Unknown exception", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiError.UNKNOWN.toMap());
        }
    }

    @Override
    public ResponseEntity<?> myMissionsList(String token) {
        try {
            String userId = securityManager.verify(token);
            User user = authManager.retrieveUserById(userId);

            ArrayList<String> missionIds = user.getAllMissionInfo();
            List<Mission> myMissions = missionManager.findMyMissions(missionIds);

            return ResponseEntity.ok(ApiError.SUCCESS.toMap(myMissions));
        } catch (ApiError er) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(er.toMap());
        } catch (Exception ex) {
            log.error("Unknown exception", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiError.UNKNOWN.toMap());
        }
    }

    @Override
    public ResponseEntity<?> search(String token, String title) {
        try {
            String creatorId = securityManager.verify(token);
            User user = authManager.retrieveUserById(creatorId);

            List<Mission> missions = missionManager.retrieveMissionByTitle(title);
            return ResponseEntity.ok(ApiError.SUCCESS.toMap(missions));
        } catch (ApiError er) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(er.toMap());
        } catch (Exception ex) {
            log.error("Unknown exception", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiError.UNKNOWN.toMap());
        }
    }

    @Override
    public ResponseEntity<?> create(String token, Mission mission) {
        try {
            String creatorId = securityManager.verify(token);
            User user = authManager.retrieveUserById(creatorId);

            mission.setCreatorId(creatorId);
            mission.setCreatorName(user.getUserName());
            mission.setContractorId(null);
            mission.setId(null);
            missionManager.createMission(mission);

            MissionEvent missionEvent = new MissionEvent(mission.getId(), EventType.CREATE, user.getUserName());
            missionEvent.create(creatorId);
            missionEventManager.addEvent(missionEvent);

            authManager.addMissionToUser(creatorId, mission.getId(), RequestType.CREATE);
            authManager.addNotificationToUser(creatorId, Message.missionCreatedNotification, mission.getId());

            return ResponseEntity.ok(ApiError.SUCCESS.toMap(mission));
        } catch (ApiError er) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(er.toMap());
        } catch (Exception ex) {
            log.error("Unknown exception", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiError.UNKNOWN.toMap());
        }
    }

    @Override
    public ResponseEntity<?> update(String token, Mission mission) {
        try {
            String creatorId = securityManager.verify(token);
            Mission oldMission = missionManager.retrieveMissionById(mission.getId());
            if (!oldMission.getCreatorId().equals(creatorId)) {
                throw ApiError.INVALID;
            }

            oldMission.setTitle(mission.getTitle());
            oldMission.setDescription(mission.getDescription());
            oldMission.setLongDescription(mission.getLongDescription());
            oldMission.setDifficulty(mission.getDifficulty());

            missionManager.updateMission(oldMission);
            authManager.addNotificationToUser(creatorId, Message.missionUpdatedNotification, oldMission.getId());

            return ResponseEntity.ok(ApiError.SUCCESS.toMap("Mission data updated successfully"));
        } catch (ApiError er) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(er.toMap());
        } catch (Exception ex) {
            log.error("Unknown exception", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiError.UNKNOWN.toMap());
        }
    }
}
