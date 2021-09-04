package app.deyal.deyal_server.model;

import java.util.Date;
import java.util.Stack;

public class RegisterUser {

    private String userName;
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;

    /* ------------------------------------------------------------------------- */

    public RegisterUser(String userName, String fullName, String email, String password, String phoneNumber) {
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public User toUser() {
        User user = new User();
        user.setUserName(userName);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);
        user.setDateOfBirth(10957);
        user.setMissionInfo(new MissionInfo());
        user.setNotifications(new Stack<>());
        user.setRegistrationDate(new Date());
        return user;
    }

    /* ------------------------------------------------------------------------- */

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
