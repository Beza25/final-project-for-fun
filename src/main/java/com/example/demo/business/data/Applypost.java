package com.example.demo.business.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.method.P;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Applypost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime postedDateTime;

//    @NotNull
//    private String pic;

    @OneToMany(mappedBy = "applypost")
    private Set<Program> programs;
    @ManyToOne
    private User user;

    public Applypost() {
//        pic = "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/colorful-of-dahlia-pink-flower-in-beautiful-garden-royalty-free-image-825886130-1554743243.jpg?crop=0.669xw:1.00xh;0.331xw,0&resize=640:*";
        postedDateTime = LocalDateTime.now();
        programs=new HashSet();
    }

    public Applypost(User user) {
        this.postedDateTime = LocalDateTime.now();
//        this.pic = pic;
        this.user = user;
    }

    public Applypost(Program program) {
        this.postedDateTime = LocalDateTime.now();
        this.programs=new HashSet();
        this.programs.add(program);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public LocalDateTime getPostedDateTime() {
        return postedDateTime;
    }

    public void setPostedDateTime(LocalDateTime postedDateTime) {
        this.postedDateTime = postedDateTime;
    }

//    public String getPic() {
//        return pic;
//    }
//
//    public void setPic(String pic) {
//        this.pic = pic;
//    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(Set<Program> programs) {
        this.programs = programs;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return (int) id + 7 * user.hashCode();
    }

    public double total_price(){
        double sum=0;
        try{
            for(Program p:this.getPrograms()){
                sum+=p.getPrice();
            }}
        catch (NullPointerException e){e.printStackTrace();sum=0;}
        return sum;
    }

   }
