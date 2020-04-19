package app.deyal.deyal_server.api.v1;

import app.deyal.deyal_server.manager.AuthManager;
import app.deyal.deyal_server.manager.JwtManager;
import app.deyal.deyal_server.manager.PasswordManager;
import app.deyal.deyal_server.model.ApiError;
import app.deyal.deyal_server.model.RegisterUser;
import app.deyal.deyal_server.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController("AuthEndpoint_v1")
public class AuthEndpointImpl implements AuthEndpoint {

    private static final Logger log = LoggerFactory.getLogger(AuthEndpoint.class);

    @Autowired
    private AuthManager authManager;

    @Autowired
    private JwtManager jwtManager;

    @Autowired
    private PasswordManager passwordManager;

    @Override
    public ResponseEntity<?> login(String email, String password) {
        try {
            User user = authManager.retrieveUserByEmail(email);
            if(!passwordManager.matchWithHash(password, user.getPassword())) {
                throw ApiError.WRONG_PASSWORD;
            }

            String token = jwtManager.sign(user, 100);
            return ResponseEntity.ok(ApiError.SUCCESS.toMap(token));
        } catch (ApiError ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.toMap());
        } catch (Exception ex) {
            log.error("Unknown exception", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiError.UNKNOWN.toMap());
        }
    }

    @Override
    public ResponseEntity<?> register(RegisterUser registerUser) {
        try {
            authManager.validateEmail(registerUser.getEmail());
            User user = registerUser.toUser();

            String passwordHash = passwordManager.getHash(user.getPassword());
            user.setPassword(passwordHash);

            authManager.insertUser(user);
            return ResponseEntity.ok(ApiError.SUCCESS.toMap(user));
        } catch (ApiError ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.toMap());
        } catch (Exception ex) {
            log.error("Unknown exception", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiError.UNKNOWN.toMap());
        }
    }

    @Override
    public ResponseEntity<?> user(String token) {
        try {
            String id = jwtManager.verify(token);
            User user = authManager.retrieveUserById(id);
            user.setPassword(null);
            return ResponseEntity.ok(ApiError.SUCCESS.toMap(user));
        } catch (ApiError ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.toMap());
        } catch (Exception ex) {
            log.error("Unknown exception", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiError.UNKNOWN.toMap());
        }
    }

    @Override
    public ResponseEntity<?> update(String token, User user) {
        try {
            String id = jwtManager.verify(token);
            User oldUser = authManager.retrieveUserById(id);

            oldUser.setUserName(user.getUserName());
            oldUser.setFullName(user.getFullName());
            oldUser.setPhoneNumber(user.getPhoneNumber());
            oldUser.setDateOfBirth(user.getDateOfBirth());
            oldUser.setAddress(user.getAddress());

            authManager.updateUser(oldUser);
            return ResponseEntity.ok(ApiError.SUCCESS.toMap("User data updated successfully"));
        } catch (ApiError ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.toMap());
        } catch (Exception ex) {
            log.error("Unknown exception", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiError.UNKNOWN.toMap());
        }
    }

    @Override
    public ResponseEntity<?> changePassword(String token, String oldPassword, String newPassword) {
        try {
            String id = jwtManager.verify(token);
            User user = authManager.retrieveUserById(id);
            if(!passwordManager.matchWithHash(oldPassword, user.getPassword())) {
                throw ApiError.WRONG_PASSWORD;
            }

            String newPasswordHash = passwordManager.getHash(newPassword);
            user.setPassword(newPasswordHash);
            authManager.updateUser(user);

            return ResponseEntity.ok(ApiError.SUCCESS.toMap("Password changed successfully"));
        } catch (ApiError ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.toMap());
        } catch (Exception ex) {
            log.error("Unknown exception", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiError.UNKNOWN.toMap());
        }
    }
}
