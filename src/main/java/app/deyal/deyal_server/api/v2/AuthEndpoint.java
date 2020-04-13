package app.deyal.deyal_server.api.v2;

import app.deyal.deyal_server.model.RegisterUser;
import app.deyal.deyal_server.model.User;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController("AuthEndpoint_v2")
@Api(tags = {"AuthEndpoint"}, value = "Handles Authentications")
@RequestMapping("/v2/auth")
public interface AuthEndpoint {

    @PostMapping(value = "/register")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created new account")
    })
    @ApiOperation("Registers a new user")
    ResponseEntity<?> register(
            @ApiParam(required = true, value = "User")
            @RequestBody RegisterUser registerUser
    );

    @PostMapping(value = "/login")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully logged in", response = User.class),
            @ApiResponse(code = 0, message = "Successfully logged in", response = User.class)
    })
    @ApiOperation("Login an existing user")
    ResponseEntity<?> login(
            @ApiParam(required = true, value = "Email Address")
            @RequestParam(value = "email") String email,
            @ApiParam(required = true, value = "Password")
            @RequestParam(value = "password") String password
    );

    @GetMapping(value = "/user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved User", response = User.class)
    })
    @ApiOperation("Retrieves an existing user")
    ResponseEntity<?> user(
            @ApiParam(required = true, value = "Token")
            @RequestParam(value = "token") String token
    );

    @PutMapping(value = "/update")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated User", response = User.class)
    })
    @ApiOperation("Updates an existing user")
    ResponseEntity<?> update(
            @ApiParam(required = true, value = "Token")
            @RequestParam(value = "token") String token,
            @ApiParam(required = true, value = "User")
            @RequestBody User user
    );

    @PostMapping(value = "/changepassword")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved User", response = User.class)
    })
    @ApiOperation("Changes user password")
    ResponseEntity<?> changePassword(
            @ApiParam(required = true, value = "Token")
            @RequestParam(value = "token") String token,
            @ApiParam(required = true, value = "Old Password")
            @RequestParam(value = "old password") String oldPassword,
            @ApiParam(required = true, value = "New Password")
            @RequestParam(value = "new password") String newPassword
    );

}
