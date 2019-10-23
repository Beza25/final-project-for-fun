package com.example.demo.controller;

import com.example.demo.business.config.CloudinaryConfig;
//import com.example.demo.business.config.CustomerUserDetails;
import com.example.demo.business.config.PaypalService;
import com.example.demo.business.config.UserService;
import com.example.demo.business.data.*;
import com.example.demo.business.data.repository.*;
import com.example.demo.business.util.MD5Util;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.constraints.Null;
import java.security.Principal;
import java.util.*;


@Controller
public class HomeController {
    @Autowired
    ApplypostRepository applypostRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    OrgRepository orgRepository;

    @Autowired
    ProgramRepository programRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @Autowired
    QuestionnaireRepository questionnaireRepository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PaypalService service;

    @Autowired
    private JavaMailSender mailSender;

    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";

    @RequestMapping("/")
    public String homePage(Model model) {

        if(userService.getUser()!=null){
            model.addAttribute("myuser", userService.getUser());
        }
        ArrayList<Program> programs = programRepository.findAll();
        model.addAttribute("programs", programs);
        model.addAttribute("applyposts", applypostRepository.findAllByOrderByPostedDateTimeDesc());
        model.addAttribute("mD5Util", new MD5Util());
        return "program";}




    @RequestMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("myuser", userService.getUser());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("mD5Util", new MD5Util());
        return "admin";
    }

    @GetMapping("/add")
    public String applypostForm(Principal principal, Model model) {
        model.addAttribute("myuser", userService.getUser());
        model.addAttribute("user", userService.getUser());
        model.addAttribute("mD5Util", new MD5Util());
        model.addAttribute("questionnaire", new Questionnaire());
        return "questionnaireform";
    }


    @PostMapping("/process")
    public String processForm(@ModelAttribute Questionnaire questionnaire,Model model) {
        model.addAttribute("myuser", userService.getUser());
        model.addAttribute("user", userService.getUser());
        model.addAttribute("mD5Util", new MD5Util());
        questionnaire.setUser(userService.getUser());
        questionnaireRepository.save(questionnaire);
        User myuser=userService.getUser();
        myuser.setCheckq(true);
        userRepository.save(myuser);
//send email to admin
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("wamysy1990@gmail.com");
        message.setTo("wamysy1990@gmail.com");
        //message.setTo("playrey2010@gmail.com");
        message.setSubject(userService.getUser().getUsername()+" applied");
        message.setText("Please approve or decline");

        mailSender.send(message);

        return  "redirect:/";
        }

    @RequestMapping("/questionnairedetail/{id}")
    public String showQuestionnaire(@PathVariable("id") long id, Model model){
        model.addAttribute("myuser", userService.getUser());
        model.addAttribute("questionnaire", questionnaireRepository.findById(id).get());
        model.addAttribute("mD5Util", new MD5Util());
        if (userService.getUser() != null) {
            model.addAttribute("user_id", userService.getUser().getId());
        }
        return "showquestionnaire";
    }

    @RequestMapping("/detail/{id}")
    public String showProgram(@PathVariable("id") long id, Model model){
        model.addAttribute("myuser", userService.getUser());
        model.addAttribute("program", programRepository.findById(id).get());
        model.addAttribute("mD5Util", new MD5Util());
        if (userService.getUser() != null) {
            model.addAttribute("user_id", userService.getUser().getId());
        }
        return "show";
    }



    @RequestMapping("/myprofile")
    public String getProfile(Principal principal, Model model) {
        User user = userService.getUser();
        model.addAttribute("user", user);
        model.addAttribute("myuser", user);
        if(user.isCheckq()){
            model.addAttribute("questionnaire", questionnaireRepository.findByUser(user));}
        model.addAttribute("HASH", MD5Util.md5Hex(user.getEmail()));
        model.addAttribute("mD5Util", new MD5Util());
        return "profile";
    }



    @RequestMapping("/user/{id}")
    public String getUser(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        model.addAttribute("myuser", userService.getUser());
        model.addAttribute("mD5Util", new MD5Util());
        model.addAttribute("HASH", MD5Util.md5Hex(user.getEmail())); //save every person email as hash
        return "profile";
    }

    @RequestMapping("/secure")
    public String secure(Principal principal, Model model) {
        User myuser = userService.getUser();
        model.addAttribute("myuser", myuser);
        model.addAttribute("mD5Util", new MD5Util());
        return "secure";
    }

    @RequestMapping("/setAdmin/user/{id}")
    public String setAdmin(@PathVariable("id") long id,Principal principal, Model model) {
        User myuser = userService.getUser();
        User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        model.addAttribute("myuser", myuser);
        model.addAttribute("mD5Util", new MD5Util());
        model.addAttribute("HASH", MD5Util.md5Hex(user.getEmail()));
        userService.saveAdmin(user);
        return "profile";
    }

    @RequestMapping("/setUser/user/{id}")
    public String setUser(@PathVariable("id") long id,Principal principal, Model model) {
        User myuser = userService.getUser();
        User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        model.addAttribute("myuser", myuser);
        model.addAttribute("mD5Util", new MD5Util());
        model.addAttribute("HASH", MD5Util.md5Hex(user.getEmail()));
        userService.saveUser(user);
        return "profile";
    }


    @RequestMapping("/approve/user/{id}")
    public String approveUser(@PathVariable("id") long id,Principal principal, Model model) {
        User myuser = userService.getUser();
        User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        model.addAttribute("myuser", myuser);
        model.addAttribute("mD5Util", new MD5Util());
        model.addAttribute("HASH", MD5Util.md5Hex(user.getEmail()));
        user.setQualification("Active");
        userRepository.save(user);
        return "profile";
    }


    @RequestMapping("/decline/user/{id}")
    public String declineUser(@PathVariable("id") long id,Principal principal, Model model) {
        User myuser = userService.getUser();
        User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        model.addAttribute("myuser", myuser);
        model.addAttribute("mD5Util", new MD5Util());
        model.addAttribute("HASH", MD5Util.md5Hex(user.getEmail()));
        user.setQualification("Declined");
        userRepository.save(user);
        return "profile";
    }

    @PostMapping("/processsearch")
    public String searchResult(Model model, @RequestParam(name = "search") String search) {
        String[] list = search.split(" ");
        ArrayList<Org> orglist = new ArrayList<>();
        ArrayList<Program> programlist = new ArrayList<>();
        Set<Program> plist;
        Set<Program> alllist = new HashSet();

        for (String s : list) {
            orglist = orgRepository.findByNameContainingIgnoreCase(s);
            programlist = programRepository.findByNameContainingIgnoreCase(s);
            for (Program p :programlist) {
                alllist.add(p);
            }
            for (Org o : orglist) {
                plist = o.getPrograms();
                for (Program p : plist) {
                    alllist.add(p);
                }
            }
            model.addAttribute("programs", alllist);
            model.addAttribute("myuser", userService.getUser());
            model.addAttribute("mD5Util", new MD5Util());
        }
        return "program";
    }
//need to change this method. how to check if a person have made a payment? maybe add a boolean default false, when pay successfully. made it true.
    //if it's true, create a new cart, false use the old cart.
    @RequestMapping("/enrollprogram/{id}")
    public String enrollprogram(@PathVariable("id") long id,Principal principal, Model model) {
        User myuser = userService.getUser();
        Program program = programRepository.findById(id).get();
        model.addAttribute("program", program);
        model.addAttribute("myuser", myuser);
        model.addAttribute("mD5Util", new MD5Util());
        model.addAttribute("HASH", MD5Util.md5Hex(myuser.getEmail()));
        if(applypostRepository.count()==0){
            Applypost applypost=new Applypost(myuser);
            applypostRepository.save(applypost);
            Iterable<Applypost>newapplyposts=applypostRepository.findAll();
            Applypost newapplypost=newapplyposts.iterator().next();
            program.setApplypost(newapplypost);
            programRepository.save(program);
        }
        else{
            Program program2 = programRepository.findById(id).get();
            Iterable<Applypost>newapplyposts=applypostRepository.findAll();
            Applypost newapplypost=newapplyposts.iterator().next();
            program2.setApplypost(newapplypost);
            programRepository.save(program2);
        }
        return "check";}

    @RequestMapping("/cart/{id}")
    public String show_cart(@PathVariable("id") long id, Model model){
        User myuser = userService.getUser();
        Program program = programRepository.findById(id).get();
        model.addAttribute("program", program);
        model.addAttribute("myuser", myuser);
        model.addAttribute("mD5Util", new MD5Util());
        model.addAttribute("HASH", MD5Util.md5Hex(myuser.getEmail()));
        model.addAttribute("program",program);
        model.addAttribute("cart", applypostRepository.findAll().iterator().next());
        return "paymentform";
    }

    @RequestMapping("/pocesscart/{id}")
    public String processcartForm(@PathVariable("id") long id, Model model, @ModelAttribute Applypost cart) {
        Program program = programRepository.findById(id).get();
        model.addAttribute("program", programRepository.findById(id).get());

        try {

            Payment payment = service.createPayment(cart.total_price(), cart.getCurrency(), cart.getMethod(),
                    cart.getIntent(), cart.getDescription(), "http://localhost:8080/" + CANCEL_URL,
                    "http://localhost:8080/" + SUCCESS_URL);


            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return "redirect:" + link.getHref();
                }
            }

        } catch (PayPalRESTException e) {

            e.printStackTrace();
        }

        return "redirect:/cart/{id}";
    }

    @RequestMapping("/qualifysearch/{id}")
    public String searchqaulify(@PathVariable("id") long id,Model model) {
        User u =userRepository.findById(id).get();
        ArrayList<Org> orglist = orgRepository.findAll();
        ArrayList<Program> programlist =programRepository.findAll();
        Set<Program> alllist = new HashSet();

        for(Program p:programlist){
            if(p.checkQualification(u)) {
                alllist.add(p);
            }
        }
        model.addAttribute("programs", alllist);
        model.addAttribute("myuser", userService.getUser());
        model.addAttribute("mD5Util", new MD5Util());
        return "program";
    }

    @RequestMapping("/enrollment/{id}")
    public String enrollment(@PathVariable("id") long id,Model model) {
        User u =userRepository.findById(id).get();
        ArrayList<Applypost> applyposts = applypostRepository.findByUser(u);
//
        Set<Program> alllist = new HashSet();

        for(Applypost a:applyposts){
            for(Program p: a.getPrograms()) {
                alllist.add(p);
            }
        }
        model.addAttribute("programs", alllist);
        model.addAttribute("myuser", userService.getUser());
        model.addAttribute("mD5Util", new MD5Util());
        return "program";
    }



    //I don't use it right now. when users submit it, they cannot make change of it
    @RequestMapping("/update/{id}")
    public String updateQuestionnaire(@PathVariable("id") long id, Model model){
        model.addAttribute("myuser", userService.getUser());
        model.addAttribute("questionnaire", questionnaireRepository.findById(id).get());
        model.addAttribute("mD5Util", new MD5Util());
        if (userService.getUser() != null) {
            model.addAttribute("user", userService.getUser());
        }
        Questionnaire questionnaire = questionnaireRepository.findById(id).get();
        return "questionnaireform";}

    //don't use this right now, applypost html is also have errors
    @RequestMapping("/applylist")
    public String applypostList(Model model) {

        if(userService.getUser()!=null){
            model.addAttribute("myuser", userService.getUser());
        }
        model.addAttribute("applyposts", applypostRepository.findAllByOrderByPostedDateTimeDesc());
        model.addAttribute("mD5Util", new MD5Util());
        return "applypost";
    }

    //don't use it now, no one can delete anything.
    @RequestMapping("/delete/{id}")
    public String deleteProgram(@PathVariable("id") long id){
        programRepository.deleteById(id);
        return "redirect:/";
    }

    //don't use it now, no one can delete anything.
    @PostMapping("/check")
    public String check(@RequestParam("check") long[] ids,
                        Model model) {
        for (long id : ids) {
            applypostRepository.deleteById(id);
        }

        return "redirect:/";
    }



}
