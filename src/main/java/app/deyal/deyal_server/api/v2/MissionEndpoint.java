package app.deyal.deyal_server.api.v2;

import app.deyal.deyal_server.manager.JwtManager;
import app.deyal.deyal_server.manager.MissionManager;
import app.deyal.deyal_server.model.ApiError;
import app.deyal.deyal_server.model.Mission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController("MissionEndpoint_v2")
@Api(tags = {"MissionEndpoint"}, value = "Handles Missions")
@RequestMapping("/v2/mission")
public class MissionEndpoint {
    private  static final Logger log = LoggerFactory.getLogger(AuthEndpoint.class);

    @Autowired
    private MissionManager missionManager;

    @Autowired
    private JwtManager jwtManager;

    @GetMapping(value = "/list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully showed mission lists", responseContainer = "List", response = Mission.class)
    })
    @ApiOperation("Shows the list of all missions")
    ResponseEntity<?> list() {
        return ResponseEntity.ok(ApiError.SUCCESS.toMap(missionManager.findAllMissions()));
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<?> delete(
            @RequestParam(value = "Token") String token,
            @RequestParam(value = "Mission Id") String missionId
    ) {
        try {
            String creatorId = jwtManager.verify(token);
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
