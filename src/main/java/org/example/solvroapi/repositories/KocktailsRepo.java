package org.example.solvroapi.repositories;

import org.example.solvroapi.Entity.KoktailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KocktailsRepo extends JpaRepository<KoktailEntity, Long> {

        List<KoktailEntity> findByNameContaining(String name);
        List<KoktailEntity> findAllByCategory(String category);

}
