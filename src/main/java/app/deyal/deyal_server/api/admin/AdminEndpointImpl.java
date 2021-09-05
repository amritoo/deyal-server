package app.deyal.deyal_server.api.admin;

import app.deyal.deyal_server.manager.AuthManager;
import app.deyal.deyal_server.manager.MailManager;
import app.deyal.deyal_server.manager.MissionEventManager;
import app.deyal.deyal_server.manager.MissionManager;
import app.deyal.deyal_server.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("AdminEndpoint_v1")
public class AdminEndpointImpl implements AdminEndpoint {

    private static final Logger log = LoggerFactory.getLogger(AdminEndpoint.class);

    @Autowired
    private AuthManager authManager;

    @Autowired
    private MissionManager missionManager;

    @Autowired
    private MissionEventManager missionEventManager;

    @Autowired
    private MailManager mailManager;

    @Value("${admin.password}")
    private String adminPass;

    @Override
    public ResponseEntity<?> userList(String password) {
        if (!password.equals(adminPass)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiError.WRONG_PASSWORD.toMap());
        }

        try {
            return ResponseEntity.ok(ApiError.SUCCESS.toMap(authManager.findAllUsers()));
        } catch (Exception ex) {
            log.error("Unknown exception", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiError.UNKNOWN.toMap());
        }
    }

    @Override
    public ResponseEntity<?> userExist(String password, String email) {
        if (!password.equals(adminPass)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiError.WRONG_PASSWORD.toMap());
        }

        try {
            User user = authManager.retrieveUserByEmail(email);
            return ResponseEntity.ok(ApiError.SUCCESS.toMap(user));
        } catch (ApiError er) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(er.toMap());
        } catch (Exception ex) {
            log.error("Unknown exception", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiError.UNKNOWN.toMap());
        }
    }

    @Override
    public ResponseEntity<?> userEmailChange(String password, String id, String email) {
        if (!password.equals(adminPass)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiError.WRONG_PASSWORD.toMap());
        }

        try {
            User oldUser = authManager.retrieveUserById(id);
            String oldEmail = oldUser.getEmail();

            authManager.validateEmail(email);
            oldUser.setEmail(email);

            authManager.updateUser(oldUser);
            authManager.addNotificationToUser(id, Message.profileUpdateNotification, null);
            mailManager.sendEmail(oldEmail,
                    "Email changed",
                    "Hello,\nYour account email has been changed to " + email + Message.emailChangeEmailOld);
            mailManager.sendEmail(email,
                    "Email changed",
                    Message.emailChangeEmailNew);

            return ResponseEntity.ok(ApiError.SUCCESS.toMap("User email changed successfully"));
        } catch (ApiError er) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(er.toMap());
        } catch (Exception ex) {
            log.error("Unknown exception", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiError.UNKNOWN.toMap());
        }
    }

    @Override
    public ResponseEntity<?> userDelete(String password, String id) {
        if (!password.equals(adminPass)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiError.WRONG_PASSWORD.toMap());
        }

        try {
            User user = authManager.retrieveUserById(id);
            authManager.deleteUser(user);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Deleted");
            return ResponseEntity.ok(response);
        } catch (ApiError er) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(er.toMap());
        } catch (Exception ex) {
            log.error("Unknown exception", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiError.UNKNOWN.toMap());
        }
    }

    @Override
    public ResponseEntity<?> missionDelete(String password, String missionId) {
        if (!password.equals(adminPass)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiError.WRONG_PASSWORD.toMap());
        }

        try {
            Mission mission = missionManager.retrieveMissionById(missionId);
            missionManager.deleteMission(mission);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Deleted");
            return ResponseEntity.ok(response);
        } catch (ApiError er) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(er.toMap());
        } catch (Exception ex) {
            log.error("Unknown exception", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiError.UNKNOWN.toMap());
        }
    }

    @Override
    public ResponseEntity<?> eventDelete(String password, String eventId) {
        if (!password.equals(adminPass)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiError.WRONG_PASSWORD.toMap());
        }

        try {
            MissionEvent missionEvent = missionEventManager.findById(eventId);
            missionEventManager.deleteEvent(missionEvent);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Deleted");
            return ResponseEntity.ok(response);
        } catch (ApiError er) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(er.toMap());
        } catch (Exception ex) {
            log.error("Unknown exception", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiError.UNKNOWN.toMap());
        }
    }
}
