package com.example.colorbase.controllers.rest;

import com.example.colorbase.dto.*;
import com.example.colorbase.dto.users.User;
import com.example.colorbase.services.BrandService;
import com.example.colorbase.services.CollectionService;
import com.example.colorbase.services.ColourService;
import com.example.colorbase.services.SetService;
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
public class RestSetController {
    private final UserService userService;
    private final SetService setService;

    private final ColourService colourService;

    private final BrandService brandService;

    @ResponseBody
    @RequestMapping(value = {"/removeSet"}, method = RequestMethod.DELETE)
    public ResponseEntity removeCollection(@RequestBody Map<String, Integer> map, Principal principal){

        Optional<Set> set = setService.getById(map.get("set_id"));

        ResponseEntity response = checkUserIsAdmin(principal);
        if (response!=null)
            return response;
        response = checkSet(set);
        if (response!=null)
            return response;

        else{
            setService.removeSetById(set.get().getId());
            return ResponseEntity.ok().body("removed");

        }

    }

    @RequestMapping(value = "/getAllSets", method = RequestMethod.GET)
    public ResponseEntity getAllSets() {
        return ResponseEntity.ok().body(setService.getSets());
    }

    @RequestMapping(value = "/getSetsByBrandId", method = RequestMethod.GET)
    public ResponseEntity getSetsByBrandId(@RequestBody Map<String, Integer> map) {
        Optional<Brand> brand = brandService.getById(map.get("brand_id"));

        if(brand.isEmpty())
            return ResponseEntity.badRequest().body("wrong brand id");

        return ResponseEntity.ok().body(setService.getSetsByBrandId(brand.get().getId()));
    }

    @RequestMapping(value = "/getSetById", method = RequestMethod.GET)
    public ResponseEntity getSetById(@RequestBody Map<String, Object> map, Principal principal) {
        Integer setId = (Integer) map.getOrDefault("set_id", 1);
        Optional<Set> set = setService.getById(setId);

        ResponseEntity response = checkSet(set);
        if (response!=null)
            return response;

        return ResponseEntity.ok().body(set.get());
    }


    @RequestMapping(value = "/addSet", method = RequestMethod.POST)
    public ResponseEntity addSet(@RequestBody @Valid Set set, Principal principal) {

        ResponseEntity response = checkUserIsAdmin(principal);
        if (response!=null)
            return response;

        else{
            return ResponseEntity.ok().body(setService.addSet(set));
        }
    }

    @RequestMapping(value = "/editSet", method = RequestMethod.PUT)
    public ResponseEntity editSet(@RequestBody @Valid Set set, Principal principal) {

        ResponseEntity response = checkUserIsAdmin(principal);
        if (response!=null)
            return response;

        Optional<Set> setOptional = setService.getById(set.getId());
        response = checkSet(setOptional);
        if (response!=null)
            return response;

        else{
            return ResponseEntity.ok().body(setService.editSet(set));

        }
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


    private ResponseEntity checkUserIsAdmin(Principal principal) {
        ResponseEntity response = checkUser(principal);
        if(response!=null)
            return response;

        Optional<User> user = userService.getUserByLogin(principal.getName());
        if(!user.get().getRole().getRole().equals(Role.RoleName.ADMIN.toString())){
            return ResponseEntity.badRequest().body("user is not admin");
        }
        return null;

    }


    private ResponseEntity checkSet(Optional<Set> set) {

        if (!set.isPresent()){
            return ResponseEntity.badRequest().body("wrong set id");
        }
        return null;
    }

}
