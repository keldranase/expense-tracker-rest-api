package com.keldranase.expencetrackingapi.entities;

/**
 * Class, representing single user, with his(hers) fields
 */
public class User {

    public static enum PrivilegeLevel {
        USER_FREE,
        USER_PREMIUM,
        STAFF,
        ADMIN
    }

    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private PrivilegeLevel privilegeLevel;

    public User(Integer userId, String firstName, String lastName,
                String email, String password) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.privilegeLevel = PrivilegeLevel.USER_FREE;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public PrivilegeLevel getPrivilegeLevel() {
        return privilegeLevel;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void getPrivilegeLevel(PrivilegeLevel privilegeLevel) {
        this.privilegeLevel = privilegeLevel;
    }
}
