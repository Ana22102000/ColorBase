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
public class RestBrandController {
    private final UserService userService;
    private final BrandService brandService;


    @ResponseBody
    @RequestMapping(value = {"/removeBrand"}, method = RequestMethod.DELETE)
    public ResponseEntity removeBrand(@RequestBody Map<String, Integer> map, Principal principal){

        Optional<Brand> brand = brandService.getById(map.get("brand_id"));

        ResponseEntity response = checkUserIsAdmin(principal);
        if (response!=null)
            return response;
        response = checkBrand(brand);
        if (response!=null)
            return response;

        else{
            brandService.removeBrandById(brand.get().getId());
            return ResponseEntity.ok().body("removed");

        }

    }

    @RequestMapping(value = "/getAllBrands", method = RequestMethod.GET)
    public ResponseEntity getAllBrands() {
        return ResponseEntity.ok().body(brandService.getBrands());
    }


    @RequestMapping(value = "/getBrandById", method = RequestMethod.GET)
    public ResponseEntity getBrandById(@RequestBody Map<String, Object> map, Principal principal) {
        Integer brandId = (Integer) map.getOrDefault("brand_id", 1);
        Optional<Brand> brand = brandService.getById(brandId);

        ResponseEntity response = checkBrand(brand);
        if (response!=null)
            return response;

        return ResponseEntity.ok().body(brand.get());
    }


    @RequestMapping(value = "/addBrand", method = RequestMethod.POST)
    public ResponseEntity addBrand(@RequestBody @Valid Brand brand, Principal principal) {

        ResponseEntity response = checkUserIsAdmin(principal);
        if (response!=null)
            return response;

        else{
            return ResponseEntity.ok().body(brandService.addBrand(brand));
        }
    }

    @RequestMapping(value = "/editBrand", method = RequestMethod.PUT)
    public ResponseEntity editBrand(@RequestBody @Valid Brand brand, Principal principal) {

        ResponseEntity response = checkUserIsAdmin(principal);
        if (response!=null)
            return response;

        Optional<Brand> brandOptional = brandService.getById(brand.getId());
        response = checkBrand(brandOptional);
        if (response!=null)
            return response;

        else{
            return ResponseEntity.ok().body(brandService.editBrand(brand));

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


    private ResponseEntity checkBrand(Optional<Brand> brand) {

        if (!brand.isPresent()){
            return ResponseEntity.badRequest().body("wrong brand id");
        }
        return null;
    }

}
