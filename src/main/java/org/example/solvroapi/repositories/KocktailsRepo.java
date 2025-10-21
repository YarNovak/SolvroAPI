package org.example.solvroapi.repositories;

import org.example.solvroapi.Entity.CocktailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KocktailsRepo extends JpaRepository<CocktailEntity, Long> {

}
