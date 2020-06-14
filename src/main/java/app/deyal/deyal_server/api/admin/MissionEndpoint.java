package app.deyal.deyal_server.api.admin;

import app.deyal.deyal_server.manager.SecurityManager;
import app.deyal.deyal_server.manager.MissionManager;
import app.deyal.deyal_server.model.ApiError;
import app.deyal.deyal_server.model.Mission;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController("MissionEndpoint_admin")
@Api(tags = {"MissionEndpoint"}, value = "Handles Missions")
@RequestMapping("/admin/mission")
public class MissionEndpoint {
    private  static final Logger log = LoggerFactory.getLogger(MissionEndpoint.class);

    @Autowired
    private MissionManager missionManager;

    @Autowired
    private SecurityManager securityManager;

    @PostMapping(value = "/delete")
    public ResponseEntity<?> delete(
            @RequestParam(value = "Token") String token,
            @RequestParam(value = "Mission Id") String missionId
    ) {
        try {
            String creatorId = securityManager.verify(token);
            Mission mission = missionManager.retrieveMissionById(missionId);

            missionManager.deleteMission(mission);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Deleted");
            return ResponseEntity.ok(response);
        } catch (ApiError ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex);
        }
    }
}
