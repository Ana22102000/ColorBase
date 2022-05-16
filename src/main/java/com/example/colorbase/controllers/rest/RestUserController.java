package com.example.colorbase.controllers.rest;

import com.example.colorbase.dto.Collection;
import com.example.colorbase.dto.Colour;
import com.example.colorbase.dto.Role;
import com.example.colorbase.dto.users.User;
import com.example.colorbase.services.CollectionService;
import com.example.colorbase.services.users.RoleService;
import com.example.colorbase.services.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class RestUserController {
    private final UserService userService;
    private final RoleService roleService;

    private final CollectionService collectionService;



    @ResponseBody
    @RequestMapping(value = {"/editUser"}, method = RequestMethod.PUT)
    public ResponseEntity editUser(@RequestBody @Valid User user, Principal principal){
        try{

            if(!user.getId().equals(userService.getUserByLogin(
                    principal.getName()
            ).get().getId()))
                return ResponseEntity.badRequest().body("can't edit other user");


            return ResponseEntity.ok().body(userService.editUser(user));
        } catch (Exception e){
            return ResponseEntity.badRequest().body("login is in usage");

        }
    }

    @ResponseBody
    @RequestMapping(value = {"/makeUserAdmin"}, method = RequestMethod.PUT)
    public ResponseEntity makeUserAdmin(@RequestBody Map<String, Integer> map, Principal principal){
        return setUserRole(map, principal, Role.RoleName.ADMIN);
    }

    @ResponseBody
    @RequestMapping(value = {"/makeUserClient"}, method = RequestMethod.PUT)
    public ResponseEntity makeUserClient(@RequestBody Map<String, Integer> map, Principal principal){
        return setUserRole(map, principal, Role.RoleName.CLIENT);
    }

    private ResponseEntity setUserRole(Map<String, Integer> map, Principal principal, Role.RoleName roleName) {
        Optional<User> user = userService.getById(map.get("user_id"));

        ResponseEntity response = checkUser(user);

        if (response!=null)
            return response;

        response = checkUserIsOwner(principal);
        if (response!=null)
            return response;
        else if(user.get().getRole().getRole().equals(roleName.toString())){
            return ResponseEntity.badRequest().body("user already has that role");
        }
        else{
            user.get().setRole(roleService.findRoleByRole(roleName).get());
            userService.editUser(user.get());

            return ResponseEntity.ok().body("user role changed");
        }
    }

    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
    public ResponseEntity getAllUsers() {
        return ResponseEntity.ok().body(userService.getAll());
    }

    @RequestMapping(value = "/getUserById", method = RequestMethod.GET)
    public ResponseEntity getUserById(@RequestBody Map<String, Object> map) {
        Integer userId = (Integer) map.getOrDefault("user_id", 1);
        Optional<User> user = userService.getById(userId);

        ResponseEntity response = checkUser(user);
        if (response!=null)
            return response;

        return ResponseEntity.ok().body(user.get());
    }

    @RequestMapping(value = "/getUserByLogin", method = RequestMethod.GET)
    public ResponseEntity getUserByLogin(@RequestBody Map<String, Object> map) {
        String userLogin = (String) map.getOrDefault("user_login", 1);
        Optional<User> user = userService.getUserByLogin(userLogin);

        ResponseEntity response = checkUser(user);
        if (response!=null)
            return response;

        return ResponseEntity.ok().body(user.get());
    }


    @ResponseBody
    @RequestMapping(value = {"/signup"}, method = RequestMethod.POST)
    public User signup(@RequestBody @Valid User user){
        user.setRole(roleService.findRoleByRole(Role.RoleName.CLIENT).get());
        Collection collection = new Collection();
        collection.setName("Selected");
        User createdUser = userService.createUser(user);
        collection.setUser(createdUser);
        collectionService.addCollection(collection);

        createdUser.setCollections(new ArrayList<Collection>(Arrays.asList(collection)));

        System.out.println(createdUser.getCollections().size());
        return createdUser;
    }

    private ResponseEntity checkUser(Optional<User> user) {
        if(!user.isPresent()){
            return ResponseEntity.badRequest().body("user does not exist");
        }
        return null;
    }

    private ResponseEntity checkUser(Principal principal) {
        if (principal == null){
            return ResponseEntity.badRequest().body("user is not authorised");
        }

        Optional<User> user = userService.getUserByLogin(principal.getName());
        if(!user.isPresent()){
            return ResponseEntity.badRequest().body("user does not exist");
        }
        return null;
    }

    private ResponseEntity checkUserIsOwner(Principal principal) {
        ResponseEntity response = checkUser(principal);
        if(response!=null)
            return response;

        Optional<User> user = userService.getUserByLogin(principal.getName());
        if(!user.get().getRole().getRole().equals(Role.RoleName.OWNER.toString())){
            return ResponseEntity.badRequest().body("user is not admin");
        }
        return null;


    }

}
