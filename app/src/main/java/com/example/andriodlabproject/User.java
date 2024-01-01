package com.example.andriodlabproject;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Arrays;

public class User {

    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String password;
    private String country;
    private String city;
    private String phoneNumber;
    private String permission;
    private byte[] profilePicture;

    public User() {
    }

    public User(String firstName, String lastName, String gender, String email, String password,
                String country, String city, String phoneNumber, String permission, byte[] profilePicture) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.country = country;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.permission = permission;
        this.profilePicture = profilePicture;


    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPermission() {
        return permission;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setFirstName(String firstName) {
        firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", LastName='" + lastName + '\'' +
                ", Gender='" + gender + '\'' +
                ", Email='" + email + '\'' +
                ", Password='" + password + '\'' +
                ", Country='" + country + '\'' +
                ", City='" + city + '\'' +
                ", PhoneNumber='" + phoneNumber + '\'' +
                ", Permesion='" + permission + '\'' +
                ", ProfilePicture=" + Arrays.toString(profilePicture) +
                '}';
    }


    public static String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    public static boolean checkPassword(String plainTextPassword, String hashedPassword){
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}
