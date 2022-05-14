package com.example.colorbase.controllers.web;


import com.example.colorbase.dto.Colour;
import com.example.colorbase.dto.Set;
import com.example.colorbase.dto.users.User;
import com.example.colorbase.services.ColourService;
import com.example.colorbase.services.SetService;
import com.example.colorbase.services.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class WebController {
    private final UserService userService;
    private final ColourService colourService;

    private final SetService setService;

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

    @RequestMapping(value = {"/colours"}, method = RequestMethod.GET)
    public String colours(Model model, Principal principal){
        model.addAttribute("colours", colourService.getApprovedColours());
        return "colours";
    }

    @RequestMapping("/colour/{id}")
    public String colour(@PathVariable(value="id") Integer id, Model model, Principal principal) {

//        System.out.println(principal.getName());
        List <User> users = userService.getAll();
//        System.out.println(users.size());

//        System.out.println(userService.getUserByLogin("login3").get().getPassword());

        System.out.println(userService.getUserByLogin("login").get().getRole());
        Optional<Colour> colourOptional = colourService.getById(id);
        if (colourOptional.isPresent()){
            model.addAttribute("colour", colourOptional.get());



            return "colour";
        }
        else{
            return "error/no_access";
        }
    }

    @RequestMapping(value = {"/sets"}, method = RequestMethod.GET)
    public String sets(Model model, Principal principal){
        model.addAttribute("sets", setService.getSets());
        return "sets";
    }

    @RequestMapping("/set/{id}")
    public String set(@PathVariable(value="id") Integer id, Model model) {
        Optional<Set> setOptional = setService.getById(id);
        if (setOptional.isPresent()){
            model.addAttribute("set", setOptional.get());
            return "set";
        }
        else{
            return "error/no_access";
        }
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
