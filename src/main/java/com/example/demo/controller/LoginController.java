package com.example.demo.controller;

import com.example.demo.business.config.UserService;
import com.example.demo.business.data.User;
import com.example.demo.business.data.repository.UserRepository;
import com.example.demo.business.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    HttpServletRequest httpServletRequest;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/forgot-password")
    public String forgetPassword() {
        return "/";
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("mD5Util", new MD5Util());
        model.addAttribute("page_title", "New User Registration");
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistrationPage(@Valid @ModelAttribute("user") User user,
                                          BindingResult result,
                                          Model model/*,
                                          @RequestParam("password") String password*/
    ) {
        model.addAttribute("page_title", "Update Profile");
        model.addAttribute("mD5Util", new MD5Util());
        if (result.hasErrors()) {
            return "register";
        } else {
            //Update User and Admin
            boolean isUser = userRepository.findById(user.getId()).isPresent();
            if (isUser) {
                //updating with existed username
                if (userRepository.findByUsername(user.getUsername()) != null &&
                        //current user
                        !userRepository.findByUsername(user.getUsername()).equals(user)) {
                    model.addAttribute("message",
                            "We already have a username called " + user.getUsername() + "!" + " Try something else.");
                    return "register";
                }

                User userInDB = userRepository.findById(user.getId()).get();
                userInDB.setFirstName(user.getFirstName());
                userInDB.setLastName(user.getLastName());
                userInDB.setEmail(user.getEmail());
                userInDB.setUsername(user.getUsername());
                userInDB.setPassword(userService.encode(user.getPassword()));
                userInDB.setEnabled(user.isEnabled());
                userRepository.save(userInDB);
                model.addAttribute("message", "User Account Successfully Updated");
            }
            //New User
            else {
                //Registering with existed username
                if (userRepository.findByUsername(user.getUsername()) != null) {
                    model.addAttribute("message",
                            "We already have a username called " + user.getUsername() + "!" + " Try something else.");
                    return "register";
                } else {
                    user.setPassword(userService.encode(user.getPassword()));
                    userService.saveUser(user);
                    model.addAttribute("message", "User Account Successfully Created");
                }
            }
        }
        return "login";
    }


    @GetMapping("/updateUser")
    public String updateUser(Model model) {
        model.addAttribute("mD5Util", new MD5Util());
        model.addAttribute("myuser", userService.getUser());
        model.addAttribute("page_title", "Update Profile");
        model.addAttribute("user", userService.getUser());

        return "updateUserForm";
    }

    @PostMapping("/updateUser")
    public String processUpdate(@Valid @ModelAttribute("user") User myuser,
                                BindingResult result,HttpServletRequest request, HttpServletResponse response,
                                Model model) {
        model.addAttribute("page_title", "Update Profile");
        model.addAttribute("mD5Util", new MD5Util());
//        myuser.setPassword(userService.encode(myuser.getPassword()));
//        myuser.setEnabled(myuser.isEnabled());
//        userRepository.save(myuser);
        userService.saveUser(myuser);

        model.addAttribute("myuser", myuser);
        model.addAttribute("user", myuser);
        try {
            httpServletRequest.logout();
        } catch(ServletException e) {

        }
                return "redirect:/logout";}
//        return "redirect:/relogin";
//            }
//    @RequestMapping("/relogin")
//    public String relogin(@Valid @ModelAttribute("user") User myuser,
//                                BindingResult result,HttpServletRequest request, HttpServletResponse response,
//                                Model model) {
//        try {
//            httpServletRequest.login(myuser.getUsername(),myuser.getPassword());
//        } catch(ServletException e) {
//        }
//        model.addAttribute("myuser", userService.getUser());
//        return "redirect:/";}
    }


