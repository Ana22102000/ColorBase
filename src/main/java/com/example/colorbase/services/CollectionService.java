package com.example.colorbase.services;

import com.example.colorbase.dto.Collection;
import com.example.colorbase.dto.Colour;
import com.example.colorbase.dto.Set;
import com.example.colorbase.repos.CollectionRepo;
import com.example.colorbase.repos.ColourRepo;
import com.example.colorbase.repos.SetRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CollectionService {

    private final CollectionRepo repo;

    public List<Collection> getCollections(){
        return repo.findAll();
    }

    public Optional<Collection> getById(int id){
        return repo.findById(id);
    }

    public void removeCollectionById(Integer id) {
        repo.deleteById(id);
    }

    public Collection addCollection(Collection collection) {
        repo.saveAndFlush(collection);
        return collection;
    }

    public Collection editCollection(Collection collection) {
        repo.saveAndFlush(collection);
        return collection;
    }

    public List<Collection> getCollectionsByUserId(int userId){
        return repo.getAllByUserId(userId);
    }

}