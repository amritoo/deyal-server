package app.deyal.deyal_server.api.v1;

import app.deyal.deyal_server.model.RegisterUser;
import app.deyal.deyal_server.model.User;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController("AuthEndpoint_v1")
@Api(tags = {"AuthEndpoint"}, value = "Handles authentication and user profile operations")
@RequestMapping("/v1/auth")
public interface AuthEndpoint {

    @PostMapping(value = "/register")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created new account", response = User.class)
    })
    @ApiOperation("Registers a new user")
    ResponseEntity<?> register(
            @ApiParam(required = true, value = "User")
            @RequestBody RegisterUser registerUser
    );

    @PostMapping(value = "/login")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully logged in")
    })
    @ApiOperation("Login an existing user")
    ResponseEntity<?> login(
            @ApiParam(required = true, value = "Email")
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

    @GetMapping(value = "/search")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved User", response = User.class)
    })
    @ApiOperation("Retrieves an existing user by userId")
    ResponseEntity<?> search(
            @ApiParam(required = true, value = "Token")
            @RequestParam(value = "token") String token,
            @ApiParam(required = true, value = "User id")
            @RequestParam(value = "userId") String id
    );

    @GetMapping(value = "/search/name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved Username", response = String.class)
    })
    @ApiOperation("Retrieves an existing username by userId")
    ResponseEntity<?> username(
            @ApiParam(required = true, value = "Token")
            @RequestParam(value = "token") String token,
            @ApiParam(required = true, value = "User id")
            @RequestParam(value = "userId") String userId
    );

    @PutMapping(value = "/update/user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated User")
    })
    @ApiOperation("Updates an existing user")
    ResponseEntity<?> update(
            @ApiParam(required = true, value = "Token")
            @RequestParam(value = "token") String token,
            @ApiParam(required = true, value = "User")
            @RequestBody User user
    );

    @PutMapping(value = "/update/password")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully changed to new password")
    })
    @ApiOperation("Changes user password")
    ResponseEntity<?> changePassword(
            @ApiParam(required = true, value = "Token")
            @RequestParam(value = "token") String token,
            @ApiParam(required = true, value = "Old password")
            @RequestParam(value = "oldPassword") String oldPassword,
            @ApiParam(required = true, value = "New password")
            @RequestParam(value = "newPassword") String newPassword
    );

    @PostMapping(value = "/forgot/send")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully send email")
    })
    @ApiOperation("Sends an email to user's email with an OTP")
    ResponseEntity<?> forgot(
            @ApiParam(required = true, value = "Email")
            @RequestParam(value = "email") String email
    );

    @PostMapping(value = "/forgot/verify")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully verified")
    })
    @ApiOperation("Verifies the OTP and changes password")
    ResponseEntity<?> verify(
            @ApiParam(required = true, value = "Email")
            @RequestParam(value = "email") String email,
            @ApiParam(required = true, value = "OTP")
            @RequestParam(value = "otp") String otp,
            @ApiParam(required = true, value = "Password")
            @RequestParam(value = "password") String password
    );
}
