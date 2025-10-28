package org.example.solvroapi.repositories;

import org.example.solvroapi.Entity.KoktajlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KocktajlsRepo extends JpaRepository<KoktajlEntity, Long> {

        List<KoktajlEntity> findByNameContaining(String name);
        List<KoktajlEntity> findAllByCategory(String category);

}
