package app.deyal.deyal_server.api.v1;

import app.deyal.deyal_server.manager.JwtManager;
import app.deyal.deyal_server.manager.MissionManager;
import app.deyal.deyal_server.model.ApiError;
import app.deyal.deyal_server.model.Mission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("MissionEndpoint_v1")
public class MissionEndpointImpl implements MissionEndpoint {

    public static final Logger log = LoggerFactory.getLogger(Mission.class);

    @Autowired
    private MissionManager missionManager;

    @Autowired
    private JwtManager jwtManager;

    @Override
    public ResponseEntity<?> create(String token, Mission mission) {
        try {
            String creatorId = jwtManager.verify(token);
            mission.setCreatorId(creatorId);
            mission.setId(null);
            missionManager.createMission(mission);
            return ResponseEntity.ok(ApiError.SUCCESS.toMap(mission));
        } catch (Exception ex) {
            log.error("Unknown exception", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiError.UNKNOWN.toMap());
        }
    }

    @Override
    public ResponseEntity<?> search(String token, String title) {
        try {
            String creatorId = jwtManager.verify(token);
            List<Mission> missions = missionManager.retrieveMission(creatorId, title);
            return ResponseEntity.ok(ApiError.SUCCESS.toMap(missions));
        } catch (ApiError er) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(er.toMap());
        } catch (Exception ex) {
            log.error("Unknown exception", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiError.UNKNOWN.toMap());
        }
    }

    @Override
    public ResponseEntity<?> update(String token, String missionId, Mission mission) {
        try {
            String creatorId = jwtManager.verify(token);
            Mission oldMission = missionManager.retrieveMissionById(missionId);
            if(!oldMission.getCreatorId().equals(creatorId)) {
                throw ApiError.INVALID;
            }

            oldMission.setTitle(mission.getTitle());
            oldMission.setDescription(mission.getDescription());
            oldMission.setLongDescription(mission.getLongDescription());
            oldMission.setDifficulty(mission.getDifficulty());

            missionManager.updateMission(oldMission);
            return ResponseEntity.ok(ApiError.SUCCESS.toMap("Mission data updated successfully"));
        } catch (ApiError er) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(er.toMap());
        } catch (Exception ex) {
            log.error("Unknown exception", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiError.UNKNOWN.toMap());
        }
    }
}
