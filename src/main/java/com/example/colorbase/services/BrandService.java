package com.example.colorbase.services;

import com.example.colorbase.dto.Brand;
import com.example.colorbase.dto.Collection;
import com.example.colorbase.dto.Colour;
import com.example.colorbase.dto.Set;
import com.example.colorbase.repos.BrandRepo;
import com.example.colorbase.repos.CollectionRepo;
import com.example.colorbase.repos.ColourRepo;
import com.example.colorbase.repos.SetRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepo repo;

    public List<Brand> getBrands(){
        return repo.findAll();
    }

    public Optional<Brand> getById(int id){
        return repo.findById(id);
    }

    public void removeBrandById(Integer id) {
        repo.deleteById(id);
    }

    public Brand addBrand(Brand brand) {
        repo.saveAndFlush(brand);
        return brand;
    }

    public Brand editBrand(Brand brand) {
        repo.saveAndFlush(brand);
        return brand;
    }


}