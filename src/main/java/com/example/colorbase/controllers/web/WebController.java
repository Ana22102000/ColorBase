package com.example.colorbase.controllers.web;


import com.example.colorbase.dto.Colour;
import com.example.colorbase.dto.Role;
import com.example.colorbase.dto.Set;
import com.example.colorbase.dto.users.User;
import com.example.colorbase.services.CollectionService;
import com.example.colorbase.services.ColourService;
import com.example.colorbase.services.SetService;
import com.example.colorbase.services.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class WebController {
    private final UserService userService;
    private final ColourService colourService;

    private final SetService setService;
    private final CollectionService collectionService;


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
        try{
            Optional<User> user = userService.getUserByLogin(principal.getName());

            if(user.get().getRole().getRole().equals(Role.RoleName.ADMIN.toString())) {
                model.addAttribute("admin", true);
            }

        } catch (Exception e){
            //user not logined
        }

        model.addAttribute("colours", colourService.getApprovedColours());
        return "colour/colours";
    }

    @RequestMapping(value = {"/unapproved_colours"}, method = RequestMethod.GET)
    public String unapprovedColours(Model model, Principal principal){
        //only admin has access

        try{
            Optional<User> user = userService.getUserByLogin(principal.getName());

            if(user.get().getRole().getRole().equals(Role.RoleName.ADMIN.toString())) {
                model.addAttribute("colours", colourService.getUnapprovedColours());
                return "colour/colours";
            }
            else
                return "error/no_access";

        } catch (Exception e){
            //user not logined
            return "error/no_access";

        }
    }

    @RequestMapping("/colour/{id}")
    public String colour(@PathVariable(value="id") Integer id, Model model, Principal principal) {
        Optional<Colour> colourOptional = colourService.getById(id);
        if (colourOptional.isPresent()){
            model.addAttribute("colour", colourOptional.get());

            model.addAttribute("approved", colourOptional.get().getApproved());

            if(principal!=null){
                Optional<User> user = userService.getUserByLogin(principal.getName());
                if(user.isPresent()){
                    model.addAttribute("collections", user.get().getCollections());
                }
            }

            boolean isAdmin = false;
//            if(!colourOptional.get().getApproved()){
                try{
                    //add admin  access
                    Optional<User> user = userService.getUserByLogin(principal.getName());

                    if(user.get().getRole().getRole().equals(Role.RoleName.ADMIN.toString())) {
                        model.addAttribute("admin", true);
                        isAdmin = true;

//                        return "colour/colour";
                    }

//                    else
//                        return "error/no_access";

                } catch (Exception e){
                    //user not logined
//                    return "error/no_access";

                }

                if(!isAdmin && !colourOptional.get().getApproved())
                    return "error/no_access";







            return "colour/colour";
        }
        else{
            return "error/no_access";
        }
    }

    @RequestMapping("/collections")
    public String collections(Model model, Principal principal) {

        System.out.println(principal);
        if(principal!=null){

            int userId = userService.getUserByLogin(principal.getName()).get().getId();
            model.addAttribute("collections", collectionService.getCollectionsByUserId(userId));
            return "collection/collections";
        }
        else{
            return "error/no_access";
        }
    }

    @RequestMapping("/add_collection")
    public String addCollection(Principal principal) {
        if(principal!=null){
            return "collection/add_collection";
        }
        else{
            return "error/no_access";
        }
    }

    @RequestMapping("/add_colour")
    public String addColour(Principal principal) {
        if(principal!=null){
            return "colour/add_colour";
        }
        else{
            return "error/no_access";
        }
    }

    @RequestMapping(value = {"/sets"}, method = RequestMethod.GET)
    public String sets(Model model, Principal principal){
        model.addAttribute("sets", setService.getSets());
        return "set/sets";
    }

    @RequestMapping("/set/{id}")
    public String set(@PathVariable(value="id") Integer id, Model model) {
        Optional<Set> setOptional = setService.getById(id);
        if (setOptional.isPresent()){
            model.addAttribute("set", setOptional.get());
            return "set/set";
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
