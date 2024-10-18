package com.App.Yogesh.Models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class ForgetPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer forgetPasswordId;

    @Column(nullable = false)
    private Integer otp;

    @Column(nullable = false)
    private Date expirationTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id") // or whatever foreign key column you are using
    private User user;


    // Default constructor
    public ForgetPassword() {}

    // Constructor with fields
    public ForgetPassword(Integer otp, Date expirationTime, User user) {
        this.otp = otp;
        this.expirationTime = expirationTime;
        this.user = user;
    }

    // Getters
    public Integer getForgetPasswordId() {
        return forgetPasswordId;
    }

    public Integer getOtp() {
        return otp;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public User getUser() {
        return user;
    }

    // Setters
    public void setForgetPasswordId(Integer forgetPasswordId) {
        this.forgetPasswordId = forgetPasswordId;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
