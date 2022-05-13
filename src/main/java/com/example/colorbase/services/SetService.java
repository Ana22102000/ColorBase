package com.example.colorbase.services;

import com.example.colorbase.dto.Colour;
import com.example.colorbase.dto.Set;
import com.example.colorbase.repos.ColourRepo;
import com.example.colorbase.repos.SetRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SetService {

    private final SetRepo repo;

    public List<Set> getSets(){
        return repo.findAll();
    }

    public List<Set> getSetsByBrandId(int brandId){
        return repo.getAllByBrandId(brandId);
    }


    public Optional<Set> getById(int id){
        return repo.findById(id);
    }

    public void removeSetById(Integer id) {
        repo.deleteById(id);
    }

    public Set addSet(Set set) {
        repo.saveAndFlush(set);
        return set;
    }

    public Set editSet(Set set) {
        repo.saveAndFlush(set);
        return set;
    }
}