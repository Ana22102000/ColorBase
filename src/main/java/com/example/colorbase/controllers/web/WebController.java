package com.example.colorbase.controllers.web;


import com.example.colorbase.services.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebController {
    private final UserService userService;

    @RequestMapping(value = {"/", "/colorbase"}, method = RequestMethod.GET)
    public String index(Model model, Principal principal){
//        if(principal==null)
//            model.addAttribute("projects",null);
//        else
//            model.addAttribute("projects", userService.getUserByLogin(
//                    principal.getName()
//            ).get().getProjects());

        return "index";
    }



    @RequestMapping(value = {"/signup"}, method = RequestMethod.GET)
    public String signup(){
        return "signup";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value = {"/error/no_access"}, method = RequestMethod.GET)
    public String no_access(){
        return "error/no_access";
    }

}
