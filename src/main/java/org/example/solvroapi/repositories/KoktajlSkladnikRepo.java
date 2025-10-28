package org.example.solvroapi.repositories;

import org.example.solvroapi.Entity.KoktajlSkladnikEntity;
import org.example.solvroapi.Entity.SkladnikiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KoktajlSkladnikRepo extends JpaRepository<KoktajlSkladnikEntity, Long> {
    @Query("""
        SELECT ci FROM KoktajlSkladnikEntity ci
        WHERE ci.id.cocktailId = :cocktailId AND ci.id.ingredientId = :ingredientId
    """)
    Optional<KoktajlSkladnikEntity> findByCompositeId(
            @Param("cocktailId") Long cocktailId,
            @Param("ingredientId") Long ingredientId
    );
    void deleteAllByIngredient(SkladnikiEntity s);
    void deleteAllByCocktailId(Long cocktailId);
}
