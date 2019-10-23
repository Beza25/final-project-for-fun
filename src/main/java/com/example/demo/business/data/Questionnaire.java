package com.example.demo.business.data;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Questionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String educationLevel;
    private String workStatus;
    private String yourMajor;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date graduationDate;
    private String school;
    private String schoolLocation;
    private String veteran;
    private String technicalBackground;
    private String selfintroduction;
    @OneToOne
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public String getYourMajor() {
        return yourMajor;
    }

    public void setYourMajor(String yourMajor) {
        this.yourMajor = yourMajor;
    }

    public Date getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(Date graduationDate) {
        this.graduationDate = graduationDate;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSchoolLocation() {
        return schoolLocation;
    }

    public void setSchoolLocation(String schoolLocation) {
        this.schoolLocation = schoolLocation;
    }

    public String getVeteran() {
        return veteran;
    }

    public void setVeteran(String veteran) {
        this.veteran = veteran;
    }

    public String getTechnicalBackground() {
        return technicalBackground;
    }

    public void setTechnicalBackground(String technicalBackground) {
        this.technicalBackground = technicalBackground;
    }

    public String getSelfintroduction() {
        return selfintroduction;
    }

    public void setSelfintroduction(String selfintroduction) {
        this.selfintroduction = selfintroduction;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Questionnaire(String educationLevel, String workStatus, String yourMajor, Date graduationDate, String school, String schoolLocation, String veteran, String technicalBackground,String selfintroduction) {
        this.educationLevel = educationLevel;
        this.workStatus = workStatus;
        this.yourMajor = yourMajor;
        this.graduationDate = graduationDate;
        this.school = school;
        this.schoolLocation = schoolLocation;
        this.veteran = veteran;
        this.technicalBackground = technicalBackground;
        this.selfintroduction=selfintroduction;
    }

    public Questionnaire() {}

}
