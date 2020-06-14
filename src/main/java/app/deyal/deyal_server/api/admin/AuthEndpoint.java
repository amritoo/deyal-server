package app.deyal.deyal_server.api.admin;

import app.deyal.deyal_server.manager.AuthManager;
import app.deyal.deyal_server.manager.MailManager;
import app.deyal.deyal_server.model.ApiError;
import app.deyal.deyal_server.model.User;
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
@RestController("AuthEndpoint_admin")
@Api(tags = {"AuthEndpoint"}, value = "Handles Authentications")
@RequestMapping("/admin/auth")
public class AuthEndpoint {
    private  static final Logger log = LoggerFactory.getLogger(AuthEndpoint.class);

    @Autowired
    private AuthManager authManager;

    @Autowired
    private MailManager mailManager;

    @GetMapping(value = "/list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully showed user lists", responseContainer = "List", response = User.class)
    })
    @ApiOperation("Shows the list of all users")
    ResponseEntity<?> list() {
        return ResponseEntity.ok(ApiError.SUCCESS.toMap(authManager.findAllUsers()));
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<?> delete(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password
    ) {
        try {
            User user = authManager.retrieveUserByEmail(email);

            authManager.deleteUser(user);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Deleted");
            return ResponseEntity.ok(response);
        } catch (ApiError ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex);
        }
    }

    /*
    @GetMapping(value = "/list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "UserList", response = User.class, responseContainer = "List")
    })
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(manager.findAllUsers());
    }

    @PostMapping(value = "/register")
    @ApiOperation("description")
    public ResponseEntity<?> register(
            @RequestBody User user
//            @RequestParam(value = "userName") String userName,
//            @RequestParam(value = "fullName") String fullName,
//            @RequestParam(value = "email") String email,
//            @RequestParam(value = "password") String password,
//            @RequestParam(value = "dateOfBirth", defaultValue = "") String dateOfBirth,
//            @RequestParam(value = "phoneNumber", defaultValue = "") String phoneNumber,
//            @RequestParam(value = "house", defaultValue = "") String house,
//            @RequestParam(value = "block", defaultValue = "") String block,
//            @RequestParam(value = "district", defaultValue = "Dhaka") String district,
//            @RequestParam(value = "policeStation", defaultValue = "") String policeStation,
//            @RequestParam(value = "postOffice", defaultValue = "") String postOffice
    ) {
        try {
//            User user = new User(userName, fullName, email, password, dateOfBirth, phoneNumber,
//                    new Address(house, block, district, policeStation, postOffice));

            manager.validateEmail(user.getEmail());
            manager.insertUser(user);

            Map<String, String> response = new HashMap<>();
            response.put("message", "OK");
            return ResponseEntity.ok(response);
        } catch (DuplicateKeyException ex) {
            Map<String, String> response = new HashMap<>();
            response.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(
            @ApiParam(required = true, value = "Email Address")
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password
    ) {
        try {
            User user = manager.retrieveUser(email, password);
            Map<String, User> response = new HashMap<>();
            response.put("user", user);
            return ResponseEntity.ok(response);
        } catch (DataAccessException ex) {
            Map<String, String> response = new HashMap<>();
            response.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
    */

    /*
    @PostMapping(value = "/update")
    public ResponseEntity<?> update(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "dateOfBirth", defaultValue = "") String dateOfBirth,
            @RequestParam(value = "phoneNumber", defaultValue = "") String phoneNumber
    ) {
        try {
            UserInfo user = retrieveUser(email, password);

            user.setEmail(email);
            user.setUserName(username);
            user.setUserName(username);
            user.setPassword(password);
            user.setDateOfBirth(dateOfBirth);
            user.setPhoneNumber(phoneNumber);

            userRepo.save(user);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Updated");
            return ResponseEntity.ok(response);
        } catch (DataAccessException ex) {
            Map<String, String> response = new HashMap<>();
            response.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }*/
}
