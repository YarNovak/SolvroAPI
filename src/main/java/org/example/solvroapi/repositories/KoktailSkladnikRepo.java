package org.example.solvroapi.repositories;

import org.example.solvroapi.Entity.KoktailEntity;
import org.example.solvroapi.Entity.KoktailSkladnikEntity;
import org.example.solvroapi.Entity.SkladnikiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KoktailSkladnikRepo extends JpaRepository<KoktailSkladnikEntity, Long> {

    void deleteAllByIngredient(SkladnikiEntity s);
    void deleteAllByCocktailId(Long cocktailId);
}
