package com.tangent.ikruger.tangentchallenge.DataEntities;

/**
 * Created by Ivan Kruger on 2016-10-28.
 */

public class UserProfile {

    private String contact_number;
    private String status_message; // Twitter style status message,
    private String bio;

    public UserProfile(){

    }

    public UserProfile(String contact_number, String status_message, String bio){
        this.contact_number = contact_number;
        this.status_message = status_message;
        this.bio = bio;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
