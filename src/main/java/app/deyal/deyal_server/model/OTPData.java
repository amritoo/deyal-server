package app.deyal.deyal_server.model;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class OTPData {
    @Id
    private String id;

    private String email;
    private String otp;
    private Date expiryTime;

    public OTPData() {
    }

    public OTPData(String email, String otp) {
        this.email = email;
        this.otp = otp;
        this.expiryTime = new Date();
        this.expiryTime.setTime(this.expiryTime.getTime() + 900_000);   //expires after 15 minutes
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }
}
