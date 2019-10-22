package com.example.demo.business.data;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
@Entity
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String name;

    @Column
    private String description;

    @Column
    private double price;
    @Column
    private ArrayList<String> educations;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startdate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate enddate;

    @ManyToOne
    private Org org;

    @ManyToOne
    private Applypost applypost;




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }

    public Applypost getApplypost() {
        return applypost;
    }

    public void setApplypost(Applypost applypost) {
        this.applypost = applypost;
    }

    public void setEducations(ArrayList<String> educations) {
        this.educations = educations;
    }

    public LocalDate getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDate startdate) {
        this.startdate = startdate;
    }

    public LocalDate getEnddate() {
        return enddate;
    }

    public void setEnddate(LocalDate enddate) {
        this.enddate = enddate;
    }

    public void setEducations(String education) {
        this.educations.add(education);
    }

    public ArrayList<String> getEducations() {
        return educations;
    }


    public Program() {
        this.educations=new ArrayList<>();
    }

    public Program(String name, String description, double price, LocalDate startdate, LocalDate enddate,String education) {
        this();
        this.name = name;
        this.description = description;
        this.price = price;
        this.startdate=startdate;
        this.enddate=enddate;
        this.educations.add(education);
    }

    public boolean checkQualification(User u) {
        if (this.getEducations().contains(u.getEducation())){
            return true; }
        else{return false;}
    }
}
