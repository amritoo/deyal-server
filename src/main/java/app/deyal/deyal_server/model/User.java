package app.deyal.deyal_server.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;

import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;

//@ApiModel("UserData")
@CompoundIndex(def = "{'userName':1, 'email':1}", unique = true, name = "unique_fields")
public class User {

    @Id
    private String id;

    //@ApiModelProperty("Name of User")
    private String userName;
    private String email;
    private String password;

    private String fullName;
    private long dateOfBirth;
    private String phoneNumber;
    private Address address;

    private MissionInfo missionInfo;
    private long reputation;
    private Stack<Notification> notifications;
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
        long totalRating = missionInfo.getRatingAsClient() + missionInfo.getRatingAsContractor();
        // 2 ^ reputation * 100. So, total rating will double for each reputation level
        if (totalRating >= (1L << reputation) * 100) {
            reputation++;
        }
    }

    /*............................................................................................................*/

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public long getReputation() {
        return reputation;
    }

    public void setReputation(long reputation) {
        this.reputation = reputation;
    }

    public Stack<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Stack<Notification> notifications) {
        this.notifications = notifications;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

}
