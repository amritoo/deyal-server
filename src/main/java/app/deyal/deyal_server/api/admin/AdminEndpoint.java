package app.deyal.deyal_server.api.admin;


import app.deyal.deyal_server.model.User;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController("AdminEndpoint_v1")
@Api(tags = {"AdminEndpoint"}, value = "Handles Admin operations")
@RequestMapping("/admin")
public interface AdminEndpoint {

    @GetMapping(value = "/auth/user/all")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully showed user lists", responseContainer = "List", response = User.class)
    })
    @ApiOperation("Shows the list of all users")
    ResponseEntity<?> userList(
            @ApiParam(required = true, value = "Password")
            @RequestParam(value = "password") String password
    );

    @GetMapping(value = "/auth/user/exist")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully verified if email exists or not", response = User.class)
    })
    @ApiOperation("Verifies if a user exists by given email and if user exists then return the user data")
    ResponseEntity<?> userExist(
            @ApiParam(required = true, value = "Password")
            @RequestParam(value = "password") String password,
            @ApiParam(required = true, value = "Email address")
            @RequestParam(value = "email") String email
    );

    @PutMapping(value = "/auth/user/email/change")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully changed user email")
    })
    @ApiOperation("Changes an existing user email")
    ResponseEntity<?> userEmailChange(
            @ApiParam(required = true, value = "Password")
            @RequestParam(value = "password") String password,
            @ApiParam(required = true, value = "User id")
            @RequestParam(value = "userId") String id,
            @ApiParam(required = true, value = "Email address")
            @RequestParam(value = "email") String email
    );

    @DeleteMapping(value = "/auth/user/delete")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted user")
    })
    @ApiOperation("Deletes an existing user from database")
    ResponseEntity<?> userDelete(
            @ApiParam(required = true, value = "Password")
            @RequestParam(value = "password") String password,
            @ApiParam(required = true, value = "User id")
            @RequestParam(value = "userId") String id
    );

    @DeleteMapping(value = "/mission/delete")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted mission")
    })
    @ApiOperation("Deletes an existing mission from database")
    ResponseEntity<?> missionDelete(
            @ApiParam(required = true, value = "Password")
            @RequestParam(value = "password") String password,
            @ApiParam(required = true, value = "Mission id")
            @RequestParam(value = "missionId") String missionId
    );

    @DeleteMapping(value = "/missionEvent/delete")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted event from given mission")
    })
    @ApiOperation("Deletes an existing mission event from database")
    ResponseEntity<?> eventDelete(
            @ApiParam(required = true, value = "Password")
            @RequestParam(value = "password") String password,
            @ApiParam(required = true, value = "Mission event id")
            @RequestParam(value = "missionEventId") String eventId
    );
}
