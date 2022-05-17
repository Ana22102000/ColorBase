package com.example.colorbase.controllers.rest;

import com.example.colorbase.dto.*;
import com.example.colorbase.dto.users.User;
import com.example.colorbase.services.BrandService;
import com.example.colorbase.services.CollectionService;
import com.example.colorbase.services.ColourService;
import com.example.colorbase.services.SetService;
import com.example.colorbase.services.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    private final SetService setService;

    private final BrandService brandService;


    @ResponseBody
    @RequestMapping(value = {"/addColourToCollection"}, method = RequestMethod.POST)
    public ResponseEntity addColourToCollection(@RequestBody Map<String, Integer> map, Principal principal){

        Optional<Colour> colour = colourService.getById(map.get("colour_id"));
        Optional<Collection> collection = collectionService.getById(map.get("collection_id"));

        ResponseEntity response = checkColour(colour);

        if (response!=null)
            return response;

        if (!colour.get().getApproved())
            return ResponseEntity.ok().body("colour is not approved");

        response = checkUser(principal);
        if (response!=null)
            return response;

        response = checkCollection(collection, principal);
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
        ResponseEntity response = checkColour(colour);

        if (response!=null)
            return response;

        response = checkUser(principal);
        if (response!=null)
            return response;

        response = checkCollection(collection, principal);
        if (response!=null)
            return response;
        else{
            colour.get().getCollections().remove(collection.get());
            colourService.editColour(colour.get());
            return ResponseEntity.ok().body("removed");

        }


    }


    @ResponseBody
    @RequestMapping(value = {"/addColourToSet"}, method = RequestMethod.POST)
    public ResponseEntity addColourToSet(@RequestBody Map<String, Integer> map, Principal principal){

        Optional<Colour> colour = colourService.getById(map.get("colour_id"));
        Optional<Set> set = setService.getById(map.get("set_id"));

        ResponseEntity response = checkColour(colour);

        if (response!=null)
            return response;

        if (!colour.get().getApproved())
            return ResponseEntity.ok().body("colour is not approved");

        response = checkUser(principal);
        if (response!=null)
            return response;

        response = checkSet(set);
        if (response!=null)
            return response;

        response = checkUserIsAdmin(principal);
        if (response!=null)
            return response;
        else{


            try {
                colour.get().getSets().add(set.get());
                colourService.editColour(colour.get());
            } catch (Exception e){
                System.out.println(e.getClass());
                return ResponseEntity.ok().body("colour is already in set");
//                return ResponseEntity.badRequest().body("colour is already in collection");
            }

            return ResponseEntity.ok().body("colour added to set");
        }
    }

    @ResponseBody
    @RequestMapping(value = {"/removeColourFromSet"}, method = RequestMethod.POST)
    public ResponseEntity removeColourFromSet(@RequestBody Map<String, Integer> map, Principal principal){

        Optional<Colour> colour = colourService.getById(map.get("colour_id"));
        Optional<Set> set = setService.getById(map.get("set_id"));

        ResponseEntity response = checkColour(colour);

        if (response!=null)
            return response;

        response = checkUser(principal);
        if (response!=null)
            return response;

        response = checkSet(set);
        if (response!=null)
            return response;

        response = checkUserIsAdmin(principal);
        if (response!=null)
            return response;
        else{
            colour.get().getSets().remove(set.get());
            colourService.editColour(colour.get());
            return ResponseEntity.ok().body("removed");

        }
    }

    @ResponseBody
    @RequestMapping(value = {"/approveColour"}, method = RequestMethod.PUT)
    public ResponseEntity approveColour(@RequestBody Map<String, Integer> map, Principal principal){

        Optional<Colour> colour = colourService.getById(map.get("colour_id"));

        ResponseEntity response = checkColour(colour);

        if (response!=null)
            return response;

        response = checkUser(principal);
        if (response!=null)
            return response;

        response = checkUserIsAdmin(principal);
        if (response!=null)
            return response;
        else if(colour.get().getApproved()){
            return ResponseEntity.badRequest().body("colour is already approved");
        }
        else{
            colour.get().setApproved(true);
            colourService.editColour(colour.get());

            return ResponseEntity.ok().body("approved");
        }
    }

    @ResponseBody
    @RequestMapping(value = {"/unapproveColour"}, method = RequestMethod.PUT)
    public ResponseEntity unapproveColour(@RequestBody Map<String, Integer> map, Principal principal){

        Optional<Colour> colour = colourService.getById(map.get("colour_id"));

        ResponseEntity response = checkColour(colour);

        if (response!=null)
            return response;

        response = checkUser(principal);
        if (response!=null)
            return response;

        response = checkUserIsAdmin(principal);
        if (response!=null)
            return response;
        else if(!colour.get().getApproved()){
            return ResponseEntity.badRequest().body("colour is already unapproved");
        }
        else{
            colour.get().setApproved(false);
            colourService.editColour(colour.get());

            return ResponseEntity.ok().body("unapproved");
        }
    }

    @ResponseBody
    @RequestMapping(value = {"/removeColour"}, method = RequestMethod.PUT)
    public ResponseEntity removeColour(@RequestBody Map<String, Integer> map, Principal principal){
        Optional<Colour> colour = colourService.getById(map.get("colour_id"));

        ResponseEntity response = checkColour(colour);

        if (response!=null)
            return response;

        response = checkUser(principal);
        if (response!=null)
            return response;
        response = checkUserIsAdmin(principal);
        if (response!=null)
            return response;

        else{

            colourService.removeColourById(colour.get().getId());

            return ResponseEntity.ok().body("removed");
        }
    }


    @RequestMapping(value = "/addColour", method = RequestMethod.POST)
    public ResponseEntity addColour(@RequestBody @Valid Colour colour, Principal principal) {


        ResponseEntity response = checkUser(principal);
        if (response!=null)
            return response;
        Boolean approved = false;

        response = checkUserIsAdmin(principal);
        if (response==null)
            approved = true;

        colour.setApproved(approved);
        return ResponseEntity.ok().body(colourService.addColour(colour));
    }

    @RequestMapping(value = "/editColour", method = RequestMethod.PUT)
    public ResponseEntity editColour(@RequestBody @Valid Colour colour, Principal principal) {

        ResponseEntity response = checkUserIsAdmin(principal);
        if (response!=null)
            return response;

        else{
            return ResponseEntity.ok().body(colourService.editColour(colour));

        }
    }


    @RequestMapping(value = "/getAllColours", method = RequestMethod.GET)
    public ResponseEntity getAllColours() {
        return ResponseEntity.ok().body(colourService.getColours());
    }

    @RequestMapping(value = "/getApprovedColours", method = RequestMethod.GET)
    public ResponseEntity getApprovedColours() {
        return ResponseEntity.ok().body(colourService.getApprovedColours());
    }

    @RequestMapping(value = "/getUnapprovedColours", method = RequestMethod.GET)
    public ResponseEntity getUnapprovedColours() {
        return ResponseEntity.ok().body(colourService.getUnapprovedColours());
    }

    @RequestMapping(value = "/getColoursByBrandId", method = RequestMethod.GET)
    public ResponseEntity getColoursByBrandId(@RequestBody Map<String, Integer> map) {
        Optional<Brand> brand = brandService.getById(map.get("brand_id"));

        if(brand.isEmpty())
            return ResponseEntity.badRequest().body("wrong brand id");

        return ResponseEntity.ok().body(colourService.getColoursByBrandId(brand.get().getId()));
    }

//    @RequestMapping(value = "/getColoursBySetId", method = RequestMethod.GET)
//    public ResponseEntity getColoursBySetId(@RequestBody Map<String, Integer> map) {
//        Optional<Set> set = setService.getById(map.get("set_id"));
//        ResponseEntity response = checkSet(set);
//        if (response!=null)
//            return response;
//
//        return ResponseEntity.ok().body(colourService.getColoursBySetId(set.get().getId()));
//    }

//    @RequestMapping(value = "/getColoursByCollectionId", method = RequestMethod.GET)
//    public ResponseEntity getColoursByCollectionId(@RequestBody Map<String, Integer> map, Principal principal) {
//        Optional<Collection> collection = collectionService.getById(map.get("collection_id"));
//        ResponseEntity response = checkCollection(collection, principal);
//        if (response!=null)
//            return response;
//
//        return ResponseEntity.ok().body(colourService.getColoursByCollectionId(collection.get().getId()));
//    }

    @RequestMapping(value = "/getColourById", method = RequestMethod.GET)
    public ResponseEntity getColour(@RequestBody Map<String, Object> map) {
        Integer colourId = (Integer) map.getOrDefault("colour_id", 1);
        Optional<Colour> colour = colourService.getById(colourId);

        ResponseEntity response = checkColour(colour);
        if (response!=null)
            return response;

        return ResponseEntity.ok().body(colour.get());
    }


    private ResponseEntity checkColour(Optional<Colour> colour) {

        if(!colour.isPresent()){
            return ResponseEntity.badRequest().body("wrong colour id");
        }
        else return null;
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

    private ResponseEntity checkSet(Optional<Set> set) {

        if (!set.isPresent()){
            return ResponseEntity.badRequest().body("wrong set id");
        }
        return null;
    }

}
