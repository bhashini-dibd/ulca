package org.ulca.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class User {

    @JsonProperty("userID")
    public String userID;

    @JsonProperty("email")
    public String email;

    @JsonProperty("firstName")
    public String firstName;

    @JsonProperty("lastName")
    public String lastName;

    @JsonProperty("phoneNo")
    public String phoneNo;

    @JsonProperty("isVerified")
    public Boolean isVerified;

    @JsonProperty("isActive")
    public Boolean isActive;

    @JsonProperty("registeredTime")
    public String registeredTime;

    @JsonProperty("activatedTime")
    public String activatedTime;

    @JsonProperty("roles")
    public List<String> roles;

    @JsonProperty("privateKey")
    public String privateKey;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getRegisteredTime() {
        return registeredTime;
    }

    public void setRegisteredTime(String registeredTime) {
        this.registeredTime = registeredTime;
    }

    public String getActivatedTime() {
        return activatedTime;
    }

    public void setActivatedTime(String activatedTime) {
        this.activatedTime = activatedTime;
    }


    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public User(String userID, String email, String firstName, String lastName, String phoneNo, Boolean isVerified, Boolean isActive, String registeredTime, String activatedTime, List<String> roles, String privateKey) {
        this.userID = userID;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
        this.isVerified = isVerified;
        this.isActive = isActive;
        this.registeredTime = registeredTime;
        this.activatedTime = activatedTime;
        this.roles = roles;
        this.privateKey = privateKey;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", isVerified=" + isVerified +
                ", isActive=" + isActive +
                ", registeredTime='" + registeredTime + '\'' +
                ", activatedTime='" + activatedTime + '\'' +
                ", roles=" + roles +
                ", privateKey='" + privateKey + '\'' +
                '}';
    }
}

