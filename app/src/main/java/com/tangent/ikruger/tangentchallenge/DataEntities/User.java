package com.tangent.ikruger.tangentchallenge.DataEntities;

/**
 * Created by Ivan Kruger on 2016-10-28.
 */

public class User {

    private String id;
    private String first_name;
    private String last_name;
    private String username;//Required. 30 characters or fewer. Letters, digits and @/./+/-/_ only.
    private String email;
    private boolean is_staff; //Designates whether the user can log into this admin site.
    private boolean is_superuser; //Designates that this user has all permissions without explicitly assigning them.
    private UserProfile profile;
    private String[] authentications;
    private String[] roles;

    public User () {};


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean is_staff() {
        return is_staff;
    }

    public void setIs_staff(boolean is_staff) {
        this.is_staff = is_staff;
    }

    public boolean is_superuser() {
        return is_superuser;
    }

    public void setIs_superuser(boolean is_superuser) {
        this.is_superuser = is_superuser;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public void setProfile(String contactnumber, String statusMessage, String bio) {
        this.profile = new UserProfile(contactnumber, statusMessage, bio);
    }

    public String[] getAuthentications() {
        return authentications;
    }

    public void setAuthentications(String[] authentications) {
        this.authentications = authentications;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }
}
