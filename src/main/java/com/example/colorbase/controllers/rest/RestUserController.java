package com.example.colorbase.controllers.rest;

import com.example.colorbase.dto.users.User;
import com.example.colorbase.services.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RestUserController {
    private final UserService userService;

    @ResponseBody
    @RequestMapping(value = {"/edit_user"}, method = RequestMethod.PUT)
    public ResponseEntity<User> edit_user(@RequestBody @Valid User user, Principal principal){
        try{

            if(!user.getId().equals(userService.getUserByLogin(
                    principal.getName()
            ).get().getId()))
                return ResponseEntity.badRequest().header("error", "Can't be edited").body(null);

            return ResponseEntity.ok().body(userService.editUser(user));
        } catch (Exception e){
            return ResponseEntity.badRequest().header("error", "login is in usage").body(null);
        }
    }


//    @ResponseBody
//    @RequestMapping(value = {"/invite_user"}, method = RequestMethod.PUT)
//    public ResponseEntity<String> invite_user(@RequestBody Map<String, String> map, Principal principal){
//        try{
//
//            User user = userService.getUserByLogin(
//                    principal.getName()
//            ).get();
//            Integer project_id = new Integer(map.get("project"));
//
//
//            User inviteUser = userService.getUserByLogin(map.get("login")).get();
//
//            if(userToProjectService.findByUserAndProject(inviteUser.getId(), project_id).isPresent())
//                return ResponseEntity.badRequest().header("error", "This user is already in this project").body(null);
//
//
//            userToProjectService.inviteUser(project_id, inviteUser.getId());
//
//            return ResponseEntity.ok().body("{\"removed\":\"removed\"}");
//        } catch (Exception e){
//            return ResponseEntity.badRequest().header("error", "User doesn't exist").body(null);
//        }
//    }


    @ResponseBody
    @RequestMapping(value = {"/signup"}, method = RequestMethod.POST)
    public User signup(@RequestBody @Valid User user){
        return userService.createUser(user);
    }

}
