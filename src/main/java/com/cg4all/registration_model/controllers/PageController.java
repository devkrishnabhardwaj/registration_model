package com.cg4all.registration_model.controllers;

import com.cg4all.registration_model.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cg4all.registration_model.entities.User;
import com.cg4all.registration_model.forms.UserForm;
import com.cg4all.registration_model.helper.Message;
import com.cg4all.registration_model.helper.MessageType;
import com.cg4all.registration_model.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class PageController {
    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }

    @RequestMapping("/services")
    public String services() {
        return "services";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/verify-otp")
    public String verifyOtp() {
        return "verify-otp";
    }


    @GetMapping("/register")
    public String register(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "register";
    }

    //processing register
    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult, HttpSession session) {

        //fetch from data
        //validate from data
//        System.out.println(userForm);

        // validate form data
        if (rBindingResult.hasErrors()) {
            return "register";
        }

        //save to database
        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setEnabled(true);
        user.setProfilePic(
                "https://static.tnn.in/thumb/msid-112774102,thumbsize-67998,width-1280,height-720,resizemode-75/112774102.jpg?quality=100");


        if(userService.isUserExistByEmail(user.getEmail())){
            Message message = Message.builder().content("Already Registered").type(MessageType.red).build();
            session.setAttribute("message",message);
            return "redirect:/register";
        }

        session.setAttribute("user",user);

         int otp = emailService.sendOtpEmail(user.getEmail());
         session.setAttribute("otp", String.valueOf(otp));
        System.out.println("otp is : "+otp);
        System.out.println(session.getAttribute("otp"));

//        User savedUser = userService.saveUser(user);

//        System.out.println("user saved :");
        //message="Registration Successfull"
//        Message message = Message.builder().content("Registration Suceessful").type(MessageType.green).build();
//        session.setAttribute("message", message);
//        //redirect login page
        return "redirect:/verify-otp";
    }

    @PostMapping("/verify-email")
    public String verifyMail(@RequestParam String otp, HttpSession httpSession){

        String sessionOtp = (String)httpSession.getAttribute("otp");

        if(sessionOtp.equals(otp)){
            User user = (User) httpSession.getAttribute("user");
            userService.saveUser(user);
            Message message = Message.builder().content("Registration Suceessful").type(MessageType.green).build();
            httpSession.setAttribute("message", message);
            //redirect login page
//            return "redirect:/register";
//            System.out.println("if ran");
            return "redirect:/register";

        }
        else{
            Message message = Message.builder().content("Wrong OTP").type(MessageType.red).build();
            httpSession.setAttribute("message", message);
//            return "redirect:/verify-email";
            return "redirect:/verify-otp";

        }

//        System.out.println("verify mail ran");
    }
}
