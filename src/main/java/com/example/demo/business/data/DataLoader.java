package com.example.demo.business.data;

import com.example.demo.business.config.UserService;
import com.example.demo.business.data.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.validation.constraints.AssertFalse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ApplypostRepository applypostRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrgRepository orgRepository;

    @Autowired
    ProgramRepository programRepository;


    @Override
    public void run(String... args) throws Exception {
        roleRepository.save(new Role("USER"));
        roleRepository.save(new Role("ADMIN"));


        Org mc=new Org("MC","USA","123-456-7890","1@1");
        orgRepository.save(mc);
        Org um=new Org("University of Maryland","USA","123-456-7890","2@2");
        orgRepository.save(um);

        Program java=new Program("java","basic java",200, LocalDate.of( 2019 , 11 , 23 ),LocalDate.of( 2020 , 1 , 23 ),"high school");
        java.setOrg(mc);
        programRepository.save(java);
        Program python=new Program("python","basic python",200, LocalDate.of( 2020 , 1 , 23 ),LocalDate.of( 2020 , 3 , 23 ),"university");
        python.setOrg(um);
        programRepository.save(python);

        Program css=new Program("css","basic css",800, LocalDate.of( 2020 , 8 , 23 ),LocalDate.of( 2020 , 12 , 23 ),"high school");
        css.setOrg(um);
        programRepository.save(css);




        User admin = new User("nora@gmail.com",
                userService.encode("1"),
                "Admin",
                "User",
                true,
                "someone",
                "high school",true);
        userService.saveAdmin(admin);

        User user = new User("1@1",
                userService.encode("1"),
                "1",
                "1",
                true,
                "user2",
                "university",
                true);
        userService.saveUser(user);

        User me = new User("seeyounora@gmail.com",
                userService.encode("1"),
                "Xiyu",
                "Yu",
                true,
                "seeyounora",
                "university",true);
        userService.saveUser(me);


//
//
//        Applypost applypost = new Applypost("hi",
//                LocalDateTime.of(2019, 11, 16, 14, 15),
//                 me);
//        applypostRepository.save(applypost);
//


//
//        applypost = new Applypost("Pet Care : feed rabbit",
//                LocalDateTime.of(2019, 11, 16, 14, 15),
//                "https://media.npr.org/assets/img/2017/09/14/gettyimages-10141026_slide-67be9fc1bca330b26debade87690b5e84286614d-s800-c85.jpg",
//                me);
//        applypostRepository.save(applypost);
//        applypost = new Applypost("Teacher : Teaching",
//                LocalDateTime.of(2019, 11, 16, 14, 15),
//                "https://media.edutopia.org/styles/responsive_2880px_16x9/s3/masters/d7_images/cover_media/alber-169hero-thelook-shutterstock_0.jpg",
//                admin);
//        applypostRepository.save(applypost);

    }}