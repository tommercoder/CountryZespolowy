package com.example.country.Models;

import android.net.Uri;

public class User {
    private String email,passport,phone, password,city,votedCity,votedMayor,votedPresident,votedVer,imageUrl;

    public User(){}

    public User(String email, String passport, String phone, String password, String city,String votedCity,String votedMayor,String votedPresident,String votedVer) {
        this.email = email;
        this.passport = passport;
        this.phone = phone;
        this.password = password;
        this.city = city;
        this.votedCity = votedCity;
        this.votedMayor = votedMayor;
        this.votedPresident = votedPresident;
        this.votedVer = votedVer;
        this.imageUrl = imageUrl;

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCity()
    {
        return city;
    }
    public void setCity(String city)
    {
        this.city = city;
    }
    public String getEmail() {
        return email;
    }

    public String getVotedVer() {
        return votedVer;
    }

    public void setVotedVer(String votedVer) {
        this.votedVer = votedVer;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVotedCity() {
        return votedCity;
    }

    public void setVotedCity(String votedCity) {
        this.votedCity = votedCity;
    }

    public String getVotedMayor() {
        return votedMayor;
    }

    public void setVotedMayor(String votedMayor) {
        this.votedMayor = votedMayor;
    }

    public String getVotedPresident() {
        return votedPresident;
    }

    public void setVotedPresident(String votedPresident) {
        this.votedPresident = votedPresident;
    }
}
