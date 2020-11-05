package com.example.country.Models;

public class User {
    private String email,passport,phone, password,city;

    public User(){}

    public User(String email, String passport, String phone, String password, String city) {
        this.email = email;
        this.passport = passport;
        this.phone = phone;
        this.password = password;
        this.city = city;
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
}
