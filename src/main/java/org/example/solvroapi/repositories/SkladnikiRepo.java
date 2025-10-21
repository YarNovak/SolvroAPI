package org.example.solvroapi.repositories;

import org.example.solvroapi.Entity.SkladnikiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkladnikiRepo extends JpaRepository<SkladnikiEntity, Long> {
    List<SkladnikiEntity> findAllById(Long id);
    Optional<SkladnikiEntity> findById(Long id);
}
