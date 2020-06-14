package app.deyal.deyal_server.api.admin;

import app.deyal.deyal_server.manager.MissionEventManager;
import app.deyal.deyal_server.manager.MissionManager;
import app.deyal.deyal_server.manager.SecurityManager;
import app.deyal.deyal_server.model.ApiError;
import app.deyal.deyal_server.model.Mission;
import app.deyal.deyal_server.model.MissionEvent;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController("MissionEventEndpoint_admin")
@Api(tags = {"MissionEventEndpoint"}, value = "Handles Mission events")
@RequestMapping("/admin/missionEvent")
public class MissionEventEndpoint {
    private  static final Logger log = LoggerFactory.getLogger(MissionEventEndpoint.class);

    @Autowired
    private MissionEventManager missionEventManager;

    @Autowired
    private SecurityManager securityManager;

    @PostMapping(value = "/delete")
    public ResponseEntity<?> delete(
            @RequestParam(value = "Mission Event Id") String eventId
    ) {
        try {
            MissionEvent missionEvent = missionEventManager.findById(eventId);
            missionEventManager.deleteEvent(missionEvent);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Deleted");
            return ResponseEntity.ok(response);
        } catch (ApiError ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex);
        }
    }
}
