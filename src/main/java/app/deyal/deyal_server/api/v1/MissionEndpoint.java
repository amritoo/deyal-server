package app.deyal.deyal_server.api.v1;


import app.deyal.deyal_server.model.Mission;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController("MissionEndpoint_v1")
@Api(tags = {"MissionEndpoint"}, value = "Handles Missions")
@RequestMapping("/v1/mission")
public interface MissionEndpoint {

    @PostMapping(value = "/create")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created new mission")
    })
    @ApiOperation("Creates a new mission")
    ResponseEntity<?> create(
            @ApiParam(required = true, value = "Token")
            @RequestParam String token,
            @ApiParam(required = true, value = "Mission")
            @RequestBody Mission mission
    );

    @GetMapping(value = "/search")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved mission", response = Mission.class)
    })
    @ApiOperation("Retrieves an existing mission")
    ResponseEntity<?> search(
            @ApiParam(required = true, value = "Token")
            @RequestParam String token,
            @ApiParam(required = true, value = "Title")
            @RequestParam(value = "Title") String title
    );

    @PutMapping(value = "/update")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated Mission", response = Mission.class)
    })
    @ApiOperation("Updates an existing Mission")
    ResponseEntity<?> update(
            @ApiParam(required = true, value = "Token")
            @RequestParam String token,
            @ApiParam(required = true, value = "Mission Id")
            @RequestParam String missionId,
            @ApiParam(required = true, value = "Mission")
            @RequestBody Mission mission
    );
}
