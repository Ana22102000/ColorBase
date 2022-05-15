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
public class RestColourController {
    private final UserService userService;
    private final CollectionService collectionService;

    private final ColourService colourService;


    @ResponseBody
    @RequestMapping(value = {"/removeColourFromCollection"}, method = RequestMethod.DELETE)
    public ResponseEntity removeColourFromCollection(@RequestBody Map<String, Integer> map){

        Optional<Colour> colour = colourService.getById(map.get("colour_id"));
        Optional<Collection> collection = collectionService.getById(map.get("collection_id"));

        if(!colour.isPresent()){
            return ResponseEntity.badRequest().body("wrong colour id");
        }
        else if (!collection.isPresent()){
            return ResponseEntity.badRequest().body("wrong collection id");
        }
        else{

            colour.get().getCollections().remove(collection.get());
            collection.get().getColours().remove(colour.get());
            collectionService.editCollection(collection.get());
            colourService.editColour(colour.get());

            return ResponseEntity.ok().body("removed");

        }

    }

}
