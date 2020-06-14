package app.deyal.deyal_server.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.ArrayList;
import java.util.Date;

@ApiModel("UserData")
public class User {

    @Id
    private String id;

    @ApiModelProperty("Name of User")
    private String userName;
    private String fullName;

    @Indexed(unique = true)
    private String email;

    private String password;
    private long dateOfBirth;
    private String phoneNumber;

    private Address address;

    private MissionInfo missionInfo;
    private double reputation;
    private ArrayList<Notification> notifications;

    private Date registrationDate;

    /*............................................................................................................*/

    public User() {
    }

    public ArrayList<String> getAllMissionInfoExceptOngoing() {
        ArrayList<String> missions = new ArrayList<>();
        missions.addAll(missionInfo.getCreated());
        missions.addAll(missionInfo.getCompleted());
        missions.addAll(missionInfo.getFailed());
        return missions;
    }

    public ArrayList<String> getAllMissionInfo() {
        ArrayList<String> missions = new ArrayList<>();
        missions.addAll(missionInfo.getCreated());
        missions.addAll(missionInfo.getCompleted());
        missions.addAll(missionInfo.getFailed());
        missions.addAll(missionInfo.getOngoing());
        return missions;
    }

    public void calculateReputation() {
        reputation = (missionInfo.getRatingAsClient() + missionInfo.getRatingAsContractor()) / 10.0;
    }

    public String getId() {
        return id;
    }

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

    public long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public MissionInfo getMissionInfo() {
        return missionInfo;
    }

    public void setMissionInfo(MissionInfo missionInfo) {
        this.missionInfo = missionInfo;
    }

    public double getReputation() {
        return reputation;
    }

    public void setReputation(double reputation) {
        this.reputation = reputation;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
}
