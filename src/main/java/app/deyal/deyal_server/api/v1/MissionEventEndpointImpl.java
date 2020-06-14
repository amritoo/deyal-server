package app.deyal.deyal_server.api.v1;

import app.deyal.deyal_server.manager.AuthManager;
import app.deyal.deyal_server.manager.SecurityManager;
import app.deyal.deyal_server.manager.MissionEventManager;
import app.deyal.deyal_server.manager.MissionManager;
import app.deyal.deyal_server.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController("MissionEventEndpoint_v1")
public class MissionEventEndpointImpl implements MissionEventEndpoint {

    public static final Logger log = LoggerFactory.getLogger(MissionEvent.class);

    @Autowired
    private SecurityManager securityManager;

    @Autowired
    private AuthManager authManager;

    @Autowired
    private MissionManager missionManager;

    @Autowired
    private MissionEventManager missionEventManager;

    @Override
    public ResponseEntity<?> list(String token, String missionId) {
        try {
            String userId = securityManager.verify(token);
            User user = authManager.retrieveUserById(userId);
            List<MissionEvent> missionEvents = missionEventManager.findAllMissionEvents(missionId);
            return ResponseEntity.ok(ApiError.SUCCESS.toMap(missionEvents));
        } catch (ApiError er) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(er.toMap());
        } catch (Exception ex) {
            log.error("Unknown exception", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiError.UNKNOWN.toMap());
        }
    }

    @Override
    public ResponseEntity<?> addEventToMission(String token, MissionEvent missionEvent) {
        try {
            String userId = securityManager.verify(token);
            User user = authManager.retrieveUserById(userId); //to verify that user exists
            missionEvent.setId(null);
            missionEvent.setEventTime(new Date());

            missionEventManager.addEvent(missionEvent);
            Mission mission = missionManager.retrieveMissionById(missionEvent.getMissionId());;
            switch (missionEvent.getEventType()) {
                case REQUEST:
                    authManager.addNotificationToUser(mission.getCreatorId(), Message.missionRequestedNotification, mission.getId());
                    break;
                case ASSIGN:
                    mission.setContractorId(missionEvent.getAssign().getAssignTo());
                    missionManager.updateMission(mission);
                    authManager.addMissionToUser(mission.getContractorId(), missionEvent.getMissionId(), RequestType.ONGOING);
                    authManager.addNotificationToUser(mission.getContractorId(), Message.missionAssignedNotification, mission.getId());
                    break;
                case SUBMIT:
                    authManager.addNotificationToUser(mission.getCreatorId(), Message.missionSubmittedNotification, mission.getId());
                    break;
                case APPROVE:
                    authManager.addNotificationToUser(mission.getContractorId(), Message.missionApprovedNotification, mission.getId());
                    break;
                case REJECT:
                    authManager.addMissionToUser(mission.getContractorId(), missionEvent.getMissionId(), RequestType.FAILED);
                    authManager.addNotificationToUser(mission.getContractorId(), Message.missionRejectedNotification, mission.getId());
                    authManager.changeRating(mission.getCreatorId(), RequestType.CLIENT_DECREASE);          //decrease creator rating
                    authManager.changeRating(mission.getContractorId(), RequestType.CONTRACTOR_DECREASE);   //decrease contractor rating
                    break;
                case REVIEW:
                    authManager.addMissionToUser(user.getId(), missionEvent.getMissionId(), RequestType.COMPLETED);
                    authManager.addNotificationToUser(mission.getCreatorId(), Message.missionCompletedNotification, mission.getId());
                    authManager.changeRating(mission.getContractorId(), RequestType.CONTRACTOR_INCREASE);   //increase contractor rating
                    authManager.changeRating(mission.getCreatorId(),    //increase or decrease creator rating based on reward given or not
                            missionEvent.getReview().isGotReward() ? RequestType.CLIENT_INCREASE : RequestType.CLIENT_DECREASE_MORE);
                    break;
            }
            return ResponseEntity.ok(ApiError.SUCCESS.toMap());
        } catch (ApiError er) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(er.toMap());
        } catch (Exception ex) {
            log.error("Unknown exception", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiError.UNKNOWN.toMap());
        }
    }

}
