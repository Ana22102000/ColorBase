package com.example.colorbase.controllers.rest;

import com.example.colorbase.dto.Collection;
import com.example.colorbase.dto.Colour;
import com.example.colorbase.dto.users.User;
import com.example.colorbase.services.CollectionService;
import com.example.colorbase.services.ColourService;
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
public class RestCollectionController {
    private final UserService userService;
    private final CollectionService collectionService;

    private final ColourService colourService;


    @ResponseBody
    @RequestMapping(value = {"/removeCollection"}, method = RequestMethod.DELETE)
    public ResponseEntity removeCollection(@RequestBody Map<String, Integer> map, Principal principal){

        Optional<Collection> collection = collectionService.getById(map.get("collection_id"));

        ResponseEntity response = checkUser(principal);
        if (response!=null)
            return response;
        response = checkCollection(collection, principal);
        if (response!=null)
            return response;

        else{
            collectionService.removeCollectionById(collection.get().getId());
            return ResponseEntity.ok().body("removed");

        }

    }

    @RequestMapping(value = "/getCollectionsForUser", method = RequestMethod.GET)
    public ResponseEntity getCollectionsForUser(@RequestBody Map<String, Object> map) {
        Integer userId = (Integer) map.getOrDefault("user_id", 1);


        Optional<User> user = userService.getById(userId);
        if (user.isEmpty()){
            return ResponseEntity.badRequest().body("wrong user_id");
        }
        return ResponseEntity.ok().body(collectionService.getCollectionsByUserId(userId));
    }

    @RequestMapping(value = "/getAllCollections", method = RequestMethod.GET)
    public ResponseEntity getAllCollections() {
        return ResponseEntity.ok().body(collectionService.getCollections());
    }

    @RequestMapping(value = "/getCollectionById", method = RequestMethod.GET)
    public ResponseEntity getCollection(@RequestBody Map<String, Object> map, Principal principal) {
        Integer collectionId = (Integer) map.getOrDefault("collection_id", 1);
        Optional<Collection> collection = collectionService.getById(collectionId);

        ResponseEntity response = checkCollection(collection, principal);
        if (response!=null)
            return response;

        return ResponseEntity.ok().body(collection.get());
    }


    @RequestMapping(value = "/addCollection", method = RequestMethod.POST)
    public ResponseEntity addCollection(@RequestBody @Valid Collection collection, Principal principal) {

        ResponseEntity response = checkUser(principal);
        if (response!=null)
            return response;

        else{
            collection.setUser(userService.getUserByLogin(principal.getName()).get());
            return ResponseEntity.ok().body(collectionService.addCollection(collection));

        }
    }

    @RequestMapping(value = "/editCollection", method = RequestMethod.PUT)
    public ResponseEntity editCollection(@RequestBody @Valid Collection collection, Principal principal) {

        ResponseEntity response = checkUser(principal);
        if (response!=null)
            return response;

        Optional<Collection> collectionOptional = collectionService.getById(collection.getId());
        response = checkCollection(collectionOptional, principal);
        if (response!=null)
            return response;

        else{
            collection.setUser(userService.getUserByLogin(principal.getName()).get());
            return ResponseEntity.ok().body(collectionService.editCollection(collection));

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


    private ResponseEntity checkCollection(Optional<Collection> collection, Principal principal) {

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
