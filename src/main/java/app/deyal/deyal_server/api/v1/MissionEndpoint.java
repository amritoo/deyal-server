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
            @ApiResponse(code = 200, message = "Successfully created new mission", response = Mission.class)
    })
    @ApiOperation("Creates a new mission")
    ResponseEntity<?> create(
            @ApiParam(required = true, value = "Token")
            @RequestParam(value = "token") String token,
            @ApiParam(required = true, value = "Mission")
            @RequestBody Mission mission
    );

    @GetMapping(value = "/search")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved mission", responseContainer = "List", response = Mission.class)
    })
    @ApiOperation("Retrieves an existing mission")
    ResponseEntity<?> search(
            @ApiParam(required = true, value = "Token")
            @RequestParam String token,
            @ApiParam(required = true, value = "Title")
            @RequestParam(value = "title") String title
    );

    @PutMapping(value = "/update")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated Mission")
    })
    @ApiOperation("Updates an existing Mission")
    ResponseEntity<?> update(
            @ApiParam(required = true, value = "Token")
            @RequestParam(value = "token") String token,
            @ApiParam(required = true, value = "Mission Id")
            @RequestParam(value = "missionId") String missionId,
            @ApiParam(required = true, value = "Mission")
            @RequestBody Mission mission
    );

    @GetMapping(value = "/list/mine")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully showed my mission list", responseContainer = "List", response = Mission.class)
    })
    @ApiOperation("Shows my missions list")
    ResponseEntity<?> myMissionsList(
            @ApiParam(required = true, value = "Token")
            @RequestParam(value = "token") String token
    );

    @GetMapping(value = "/list/all")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully showed mission lists", responseContainer = "List", response = Mission.class)
    })
    @ApiOperation("Shows the list of all missions")
    ResponseEntity<?> list(
            @ApiParam(required = true, value = "Token")
            @RequestParam(value = "token") String token
    );
}
