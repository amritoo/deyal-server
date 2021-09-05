package app.deyal.deyal_server.api.v1;


import app.deyal.deyal_server.model.Mission;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController("MissionEndpoint_v1")
@Api(tags = {"MissionEndpoint"}, value = "Handles mission related operations")
@RequestMapping("/v1/mission")
public interface MissionEndpoint {

    @GetMapping(value = "/list/all")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully showed mission list", responseContainer = "List", response = Mission.class)
    })
    @ApiOperation("Shows the list of all missions")
    ResponseEntity<?> list(
            @ApiParam(required = true, value = "Token")
            @RequestParam(value = "token") String token
    );

    @GetMapping(value = "/list/my")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully showed my mission list", responseContainer = "List", response = Mission.class)
    })
    @ApiOperation("Shows my missions list")
    ResponseEntity<?> myMissionsList(
            @ApiParam(required = true, value = "Token")
            @RequestParam(value = "token") String token
    );

    @GetMapping(value = "/search/title")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved mission list", responseContainer = "List", response = Mission.class)
    })
    @ApiOperation("Retrieves a list of existing missions with given title")
    ResponseEntity<?> search(
            @ApiParam(required = true, value = "Token")
            @RequestParam String token,
            @ApiParam(required = true, value = "Title")
            @RequestParam(value = "title") String title
    );

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

    @PutMapping(value = "/update")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated mission")
    })
    @ApiOperation("Updates an existing Mission")
    ResponseEntity<?> update(
            @ApiParam(required = true, value = "Token")
            @RequestParam(value = "token") String token,
            @ApiParam(required = true, value = "Mission")
            @RequestBody Mission mission
    );
}
