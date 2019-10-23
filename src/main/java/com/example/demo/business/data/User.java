package com.example.demo.business.data;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "User_Data")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @NotEmpty
//    @ValidPassword
    @Column(name = "password")
    private String password;

    @NotEmpty
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty
    @Column(name = "last_name")
    private String lastName;

    @AssertTrue
    @Column(name = "enabled")
    private boolean enabled;

    @NotEmpty
    @Column(name = "username")
    private String username;



    @Column(name = "qualification")
    private String qualification;

    @NotEmpty
    @Column(name = "education")
    private String education;

    @NotNull
    @Column(name = "experience")
    private boolean experience;

    @Column(name = "checkq")
    private boolean checkq;



    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


    @OneToMany(mappedBy = "user")
    private Set<Applypost> applyposts;

    @OneToOne(mappedBy="user")
    private Questionnaire questionnaire;


    //For followers and following
    @ManyToMany
    private Set<User> followings;

    @ManyToMany(mappedBy = "followings")
    private Set<User> followers;


    
    


    public User() {
        roles = new HashSet<>();
        applyposts = new HashSet<>();
        followers = new HashSet<>();
        followings = new HashSet<>();
        qualification= "Pending";
        checkq=false;

    }

    public User(@NotEmpty @Email String email,
                @NotEmpty String password,
                @NotEmpty String firstName,
                @NotEmpty String lastName,
                @AssertTrue boolean enabled,
                @NotEmpty String username,
                @NotEmpty String education,
                @AssertFalse boolean experience) {
        this();
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.enabled = enabled;
        this.username = username;
        this.education=education;
        this.experience=experience;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }

    public Set<User> getFollowings() {
        return followings;
    }

    public void setFollowings(Set<User> followings) {
        this.followings = followings;
    }

    public Set<Applypost> getApplyposts() {
        return applyposts;
    }

    public void setApplyposts(Set<Applypost> applyposts) {
        this.applyposts = applyposts;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public boolean isFollowing(User user) {
        return followings.contains(user);
    }

    public boolean isExperience() {
        return experience;
    }

    public void setExperience(boolean experience) {
        this.experience = experience;
    }


    public void addFollower(User follower) {
        followers.add(follower);
        follower.followings.add(this);
    }

    public void removeFollower(User follower) {
        followers.remove(follower);
        follower.followings.remove(this);
    }

    public void addFollowing(User followed) {
        followings.add(followed);
        //followed.addFollower(this);
    }

    public void removeFollowing(User followed) {
        followings.remove(followed);
        //followed.removeFollower(this);
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    public boolean isCheckq() {
        return checkq;
    }

    public void setCheckq(boolean checkq) {
        this.checkq = checkq;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;
        if (this.id != user.id) return false;
        return this.username.equals(user.username);
    }
}
