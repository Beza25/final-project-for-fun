package com.example.demo.controller;

import com.example.demo.business.config.UserService;
import com.example.demo.business.data.User;
import com.example.demo.business.data.repository.UserRepository;
import com.example.demo.business.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FollowController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @RequestMapping("/followers")
    public String getFollowers(Model model) {
        model.addAttribute("myuser", userService.getUser());
        model.addAttribute("message", "My Followers");
        model.addAttribute("mD5Util", new MD5Util());
        model.addAttribute("users", userRepository.findAllByFollowings(userService.getUser()));

        return "fellowlist";
    }

    @RequestMapping("/following")
    public String getFollowing(Model model) {
        model.addAttribute("myuser", userService.getUser());
        model.addAttribute("message", "People I`m Following");
        model.addAttribute("mD5Util", new MD5Util());
        model.addAttribute("users", userRepository.findAllByFollowers(userService.getUser()));
        return "fellowlist";
    }

    @RequestMapping("/follow/{id}")
    public String follow(@PathVariable("id") long id, Model model) {
        model.addAttribute("myuser", userService.getUser());
        model.addAttribute("mD5Util", new MD5Util());
        User follower = userRepository.findById(id).get();
        User user = userService.getUser();
        user.addFollowing(follower);
        userRepository.save(user);
        return "redirect:/";
    }

    @RequestMapping("/unfollow/{id}")
    public String unfollow(@PathVariable("id") long id, Model model) {
        model.addAttribute("myuser", userService.getUser());
        model.addAttribute("mD5Util", new MD5Util());
        User unfollower = userRepository.findById(id).get();
        User user = userService.getUser();
        user.removeFollowing(unfollower);
        userRepository.save(user);
        return "redirect:/";
    }
}
