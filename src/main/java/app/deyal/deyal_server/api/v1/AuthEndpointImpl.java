package app.deyal.deyal_server.api.v1;

import app.deyal.deyal_server.manager.AuthManager;
import app.deyal.deyal_server.manager.MailManager;
import app.deyal.deyal_server.manager.OTPManager;
import app.deyal.deyal_server.manager.SecurityManager;
import app.deyal.deyal_server.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController("AuthEndpoint_v1")
public class AuthEndpointImpl implements AuthEndpoint {

    private static final Logger log = LoggerFactory.getLogger(AuthEndpoint.class);

    @Autowired
    private AuthManager authManager;

    @Autowired
    private SecurityManager securityManager;

    @Autowired
    private OTPManager otpManager;

    @Autowired
    private MailManager mailManager;

    @Override
    public ResponseEntity<?> register(RegisterUser registerUser) {
        try {
            authManager.validateEmail(registerUser.getEmail());
            User user = registerUser.toUser();

            String passwordHash = securityManager.getHash(user.getPassword());
            user.setPassword(passwordHash);

            authManager.insertUser(user);
            authManager.addNotificationToUser(user.getId(), Message.registerNotification, null);
            mailManager.sendEmail(user.getEmail(),
                    "Account created",
                    Message.registerEmail);
            return ResponseEntity.ok(ApiError.SUCCESS.toMap(user));
        } catch (ApiError er) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(er.toMap());
        } catch (Exception ex) {
            log.error("Unknown exception", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiError.UNKNOWN.toMap());
        }
    }

    @Override
    public ResponseEntity<?> login(String email, String password) {
        try {
            User user = authManager.retrieveUserByEmail(email);
            if (!securityManager.matchWithHash(password, user.getPassword())) {
                throw ApiError.WRONG_PASSWORD;
            }
            String token = securityManager.sign(user, 8760); // set token validity to 365 days
            return ResponseEntity.ok(ApiError.SUCCESS.toMap(token));
        } catch (ApiError ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.toMap());
        } catch (Exception ex) {
            log.error("Unknown exception", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiError.UNKNOWN.toMap());
        }
    }

    @Override
    public ResponseEntity<?> user(String token) {
        try {
            String id = securityManager.verify(token);
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
            String id = securityManager.verify(token);
            User oldUser = authManager.retrieveUserById(id);

            oldUser.setUserName(user.getUserName());
            oldUser.setFullName(user.getFullName());
            oldUser.setPhoneNumber(user.getPhoneNumber());
            oldUser.setDateOfBirth(user.getDateOfBirth());
            oldUser.setAddress(user.getAddress());

            authManager.updateUser(oldUser);
            authManager.addNotificationToUser(id, Message.profileUpdateNotification, null);
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
            String id = securityManager.verify(token);
            User user = authManager.retrieveUserById(id);
            if (!securityManager.matchWithHash(oldPassword, user.getPassword())) {
                throw ApiError.WRONG_PASSWORD;
            }

            String newPasswordHash = securityManager.getHash(newPassword);
            user.setPassword(newPasswordHash);
            authManager.updateUser(user);
            authManager.addNotificationToUser(id, Message.passwordChangeNotification, null);
            mailManager.sendEmail(user.getEmail(),
                    "Password changed",
                    Message.passwordChangeEmail);
            return ResponseEntity.ok(ApiError.SUCCESS.toMap("Password changed successfully"));
        } catch (ApiError er) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(er.toMap());
        } catch (Exception ex) {
            log.error("Unknown exception", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiError.UNKNOWN.toMap());
        }
    }

    @Override
    public ResponseEntity<?> search(String token, String id) {
        try {
            String userId = securityManager.verify(token);
            User user = authManager.retrieveUserById(userId);

            User requestedUser = authManager.retrieveUserById(id);
            requestedUser.setPassword(null);
            return ResponseEntity.ok(ApiError.SUCCESS.toMap(requestedUser));
        } catch (ApiError ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.toMap());
        } catch (Exception ex) {
            log.error("Unknown exception", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiError.UNKNOWN.toMap());
        }
    }

    @Override
    public ResponseEntity<?> forgot(String email) {
        try {
            String otp = securityManager.generateOTP();
            if(otpManager.isEmailExist(email)){
                otpManager.deleteOTPData(otpManager.retrieveOTPDataByEmail(email));
            }
            OTPData otpData = new OTPData(email, otp);

            otpManager.saveOTPData(otpData);
            mailManager.sendEmail(email,
                    "Password change code",
                    Message.otpEmail + otp + "\n\nWith best regards,\nDeyal Team.");
            return ResponseEntity.ok(ApiError.SUCCESS.toMap());
        } catch (Exception ex) {
            log.error("Unknown exception", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiError.UNKNOWN.toMap());
        }
    }

    @Override
    public ResponseEntity<?> verify(String email, String otp, String password) {
        try {
            OTPData otpData = otpManager.retrieveOTPDataByEmail(email);
            if (!otpData.getEmail().equals(email) || !otpData.getOtp().equals(otp) || !otpData.getExpiryTime().after(new Date())) {
                throw ApiError.INVALID_OTP;
            }
            otpManager.deleteOTPData(otpData);
            User user = authManager.retrieveUserByEmail(email);

            String passwordHash = securityManager.getHash(password);
            user.setPassword(passwordHash);
            authManager.updateUser(user);
            authManager.addNotificationToUser(user.getId(), Message.passwordChangeNotification, null);
            mailManager.sendEmail(user.getEmail(),
                    "Password changed",
                    Message.passwordChangeEmail);
            return ResponseEntity.ok(ApiError.SUCCESS.toMap("Password changed successfully"));
        } catch (ApiError er) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(er.toMap());
        } catch (Exception ex) {
            log.error("Unknown exception", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiError.UNKNOWN.toMap());
        }
    }

    @Override
    public ResponseEntity<?> listUserName(String token) {
        try {
            String userId = securityManager.verify(token);
            User user = authManager.retrieveUserById(userId);

            Map<String, String> userNameMap = authManager.userNameMap();
            return ResponseEntity.ok(ApiError.SUCCESS.toMap(userNameMap));
        } catch (ApiError ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.toMap());
        } catch (Exception ex) {
            log.error("Unknown exception", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiError.UNKNOWN.toMap());
        }
    }
}
