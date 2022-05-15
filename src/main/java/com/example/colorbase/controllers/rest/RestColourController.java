package com.example.colorbase.controllers.rest;

import com.example.colorbase.dto.Collection;
import com.example.colorbase.dto.Colour;
import com.example.colorbase.dto.Role;
import com.example.colorbase.dto.users.User;
import com.example.colorbase.services.CollectionService;
import com.example.colorbase.services.ColourService;
import com.example.colorbase.services.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class RestColourController {
    private final UserService userService;
    private final CollectionService collectionService;

    private final ColourService colourService;

    @ResponseBody
    @RequestMapping(value = {"/addColourToCollection"}, method = RequestMethod.POST)
    public ResponseEntity addColourFromCollection(@RequestBody Map<String, Integer> map, Principal principal){

        Optional<Colour> colour = colourService.getById(map.get("colour_id"));
        Optional<Collection> collection = collectionService.getById(map.get("collection_id"));

        ResponseEntity response = checkColour(colour, principal);

        if (response!=null)
            return response;

        response = checkCollection(colour, collection, principal);
        if (response!=null)
            return response;
        else{

            try {
                colour.get().getCollections().add(collection.get());
                colourService.editColour(colour.get());
            } catch (Exception e){
                System.out.println(e.getClass());
                return ResponseEntity.ok().body("colour is already in collection");
//                return ResponseEntity.badRequest().body("colour is already in collection");
            }

            return ResponseEntity.ok().body("colour added to collection");
        }
    }
    @ResponseBody
    @RequestMapping(value = {"/removeColourFromCollection"}, method = RequestMethod.DELETE)
    public ResponseEntity removeColourFromCollection(@RequestBody Map<String, Integer> map, Principal principal){

        Optional<Colour> colour = colourService.getById(map.get("colour_id"));
        Optional<Collection> collection = collectionService.getById(map.get("collection_id"));
        ResponseEntity response = checkColour(colour, principal);

        if (response!=null)
            return response;

        response = checkCollection(colour, collection, principal);
        if (response!=null)
            return response;
        else{
//            collection.get().getColours().remove(colour.get());

            colour.get().getCollections().remove(collection.get());
            colourService.editColour(colour.get());
//            collectionService.editCollection(collection.get());

            return ResponseEntity.ok().body("removed");

        }


    }

    @ResponseBody
    @RequestMapping(value = {"/approveColour"}, method = RequestMethod.PUT)
    public ResponseEntity approveColour(@RequestBody Map<String, Integer> map, Principal principal){

        Optional<Colour> colour = colourService.getById(map.get("colour_id"));

        ResponseEntity response = checkColour(colour, principal);

        if (response!=null)
            return response;

        Optional<User> user = userService.getUserByLogin(principal.getName());
        if(!user.get().getRole().getRole().equals(Role.RoleName.ADMIN.toString())){
            return ResponseEntity.badRequest().body("user is not admin");
        }else if(colour.get().getApproved()){
            return ResponseEntity.badRequest().body("colour is already approved");
        }
        else{
            colour.get().setApproved(true);
            colourService.editColour(colour.get());

            return ResponseEntity.ok().body("approved");
        }
    }

    @ResponseBody
    @RequestMapping(value = {"/removeColour"}, method = RequestMethod.PUT)
    public ResponseEntity removeColour(@RequestBody Map<String, Integer> map, Principal principal){
        Optional<Colour> colour = colourService.getById(map.get("colour_id"));

        ResponseEntity response = checkColour(colour, principal);

        if (response!=null)
            return response;

        Optional<User> user = userService.getUserByLogin(principal.getName());
        if(!user.get().getRole().getRole().equals(Role.RoleName.ADMIN.toString())){
            return ResponseEntity.badRequest().body("user is not admin");
        }
        else{

            colourService.removeColourById(colour.get().getId());

            return ResponseEntity.ok().body("removed");
        }
    }

    private ResponseEntity checkColour(Optional<Colour> colour, Principal principal) {

        if(!colour.isPresent()){
            return ResponseEntity.badRequest().body("wrong colour id");
        }
        else if (principal == null){
            return ResponseEntity.badRequest().body("user is not authorised");
        }

        Optional<User> user = userService.getUserByLogin(principal.getName());
        if(!user.isPresent()){
            return ResponseEntity.badRequest().body("user does not exist");
        }
        return null;
    }

    private ResponseEntity checkCollection(Optional<Colour> colour, Optional<Collection> collection, Principal principal) {

        if (!collection.isPresent()){
            return ResponseEntity.badRequest().body("wrong collection id");
        }
        Optional<User> user = userService.getUserByLogin(principal.getName());
        if(collection.get().getUser().getId() != user.get().getId()){
            return ResponseEntity.badRequest().body("collection doesn't belong to current user");
        }
        return null;
    }

}
