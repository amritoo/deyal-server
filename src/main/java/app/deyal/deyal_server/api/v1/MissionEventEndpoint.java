package app.deyal.deyal_server.api.v1;

import app.deyal.deyal_server.model.MissionEvent;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController("MissionEventEndpoint_v1")
@Api(tags = {"MissionEventEndpoint"}, value = "Handles mission event operations")
@RequestMapping("/v1/event")
public interface MissionEventEndpoint {

    @GetMapping(value = "/list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully showed mission event lists", responseContainer = "List", response = MissionEvent.class)
    })
    @ApiOperation("Shows the list of all events in a mission")
    ResponseEntity<?> list(
            @ApiParam(required = true, value = "Token")
            @RequestParam(value = "token") String token,
            @ApiParam(required = true, value = "Mission id")
            @RequestParam(value = "missionId") String missionId
    );

    @PostMapping(value = "/add")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created mission event")
    })
    @ApiOperation("Creates and adds a mission event to a mission")
    ResponseEntity<?> addEventToMission(
            @ApiParam(required = true, value = "Token")
            @RequestParam(value = "token") String token,
            @ApiParam(required = true, value = "Mission event")
            @RequestBody MissionEvent missionEvent
    );

}
