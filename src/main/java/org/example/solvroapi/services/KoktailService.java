package org.example.solvroapi.services;

import lombok.RequiredArgsConstructor;
import org.example.solvroapi.Entity.CocktailEntity;
import org.example.solvroapi.repositories.KocktailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class KoktailService {

    @Autowired
    private final KocktailsRepo kocktailsRepo;

    public CocktailEntity create(CocktailEntity c) {
        return kocktailsRepo.save(c);
    }

    public CocktailEntity update(CocktailEntity c) {
        return kocktailsRepo.save(c);
    }

    public List<CocktailEntity> getAll() {
        return kocktailsRepo.findAll();
    }

    public CocktailEntity getById(Long id) {
        return kocktailsRepo.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        kocktailsRepo.deleteById(id);
    }
}
