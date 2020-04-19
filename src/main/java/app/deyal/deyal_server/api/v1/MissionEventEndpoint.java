package app.deyal.deyal_server.api.v1;

import app.deyal.deyal_server.model.MissionEvent;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController("MissionEventEndpoint_v1")
@Api(tags = {"MissionEventEndpoint"}, value = "Handles Mission Events")
@RequestMapping("/v1/event")
public interface MissionEventEndpoint {

    @GetMapping(value = "/list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully showed mission event lists", responseContainer = "List", response = MissionEvent.class)
    })
    @ApiOperation("Shows the list of all events of a mission")
    ResponseEntity<?> list(
            @ApiParam(required = true, value = "Mission Id")
            @RequestParam(value = "Mission Id") String missionId
    );

}
