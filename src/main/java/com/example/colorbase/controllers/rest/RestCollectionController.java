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
    public ResponseEntity removeColourFromCollection(@RequestBody Map<String, Integer> map, Principal principal){

        Optional<Collection> collection = collectionService.getById(map.get("collection_id"));

        Optional<User> user = userService.getUserByLogin(principal.getName());
        if (!collection.isPresent()){
            return ResponseEntity.badRequest().body("wrong collection id");
        }
        if (!user.isPresent()){
            return ResponseEntity.badRequest().body("user is not authorised");
        }
        else if(collection.get().getUser().getId() != user.get().getId()){
            return ResponseEntity.badRequest().body("collection doesn't belong to current user");
        }
        else{
            collectionService.removeCollectionById(collection.get().getId());
            return ResponseEntity.ok().body("removed");

        }

    }

}
