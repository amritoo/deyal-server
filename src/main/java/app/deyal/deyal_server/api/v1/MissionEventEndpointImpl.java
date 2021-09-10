package app.deyal.deyal_server.api.v1;

import app.deyal.deyal_server.manager.AuthManager;
import app.deyal.deyal_server.manager.MissionEventManager;
import app.deyal.deyal_server.manager.MissionManager;
import app.deyal.deyal_server.manager.SecurityManager;
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(er.toMap());
        } catch (Exception ex) {
            log.error("Unknown exception", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiError.UNKNOWN.toMap());
        }
    }

    @Override
    public ResponseEntity<?> addEventToMission(String token, MissionEvent missionEvent) {
        try {
            //verify that user exists
            String userId = securityManager.verify(token);
            User user = authManager.retrieveUserById(userId);

            missionEvent.setId(null);
            missionEvent.setEventTime(new Date());
            missionEventManager.addEvent(missionEvent);

            Mission mission = missionManager.retrieveMissionById(missionEvent.getMissionId());
            switch (missionEvent.getEventType()) {
                case REQUEST:   //if someone requests then notify creator
                    authManager.addNotificationToUser(mission.getCreatorId(), Message.missionRequestedNotification, mission.getId());
                    break;
                case ASSIGN:    //if creator assigns someone, notify them
                    String contractorId = missionEvent.getAssign().getAssignTo();
                    mission.setContractorId(contractorId);
                    mission.setContractorName(missionEvent.getUsername());
                    //change mission name to [Assigned] name or [A] name
                    mission.setTitle("[A] " + mission.getTitle());
                    missionManager.updateMission(mission);

                    authManager.addMissionToUser(contractorId, mission.getId(), RequestType.ONGOING);
                    authManager.addNotificationToUser(contractorId, Message.missionAssignedNotification, mission.getId());
                    break;
                case SUBMIT:    //if contractor submits results, then notify creator
                    authManager.addNotificationToUser(mission.getCreatorId(), Message.missionSubmittedNotification, mission.getId());
                    break;
                case APPROVE:   //if creator approves results, then notify contractor
                    authManager.addNotificationToUser(mission.getContractorId(), Message.missionApprovedNotification, mission.getId());
                    break;
                case REJECT:    //if creator rejects results, then notify contractor
                    authManager.addMissionToUser(mission.getContractorId(), mission.getId(), RequestType.FAILED);
                    authManager.addNotificationToUser(mission.getContractorId(), Message.missionRejectedNotification, mission.getId());

                    //change mission name to [Failed] name or [F] name
                    mission.setTitle("[F]" + mission.getTitle());
                    missionManager.updateMission(mission);

                    //decrease rating for both creator and contractor
                    authManager.changeRating(mission.getCreatorId(), RequestType.CLIENT_DECREASE, mission.getDifficulty());
                    authManager.changeRating(mission.getContractorId(), RequestType.CONTRACTOR_DECREASE, mission.getDifficulty());
                    break;
                case REVIEW:    //if mission is completes notify both creator and contractor
                    authManager.addMissionToUser(user.getId(), mission.getId(), RequestType.COMPLETED);
                    authManager.addNotificationToUser(mission.getCreatorId(), Message.missionCompletedNotificationClient, mission.getId());
                    authManager.addNotificationToUser(mission.getContractorId(), Message.missionCompletedNotificationContractor, mission.getId());

                    //change mission name to [Successful] name or [S] name
                    mission.setTitle("[S]" + mission.getTitle());
                    missionManager.updateMission(mission);

                    //increase contractor rating
                    authManager.changeRating(mission.getContractorId(), RequestType.CONTRACTOR_INCREASE, mission.getDifficulty());
                    //increase or decrease creator rating based on reward given or not
                    authManager.changeRating(
                            mission.getCreatorId(),
                            missionEvent.getReview().isGotReward() ? RequestType.CLIENT_INCREASE : RequestType.CLIENT_DECREASE_MORE,
                            mission.getDifficulty()
                    );
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
