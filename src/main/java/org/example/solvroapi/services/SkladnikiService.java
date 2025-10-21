package org.example.solvroapi.services;

import lombok.RequiredArgsConstructor;
import org.example.solvroapi.Entity.SkladnikiEntity;
import org.example.solvroapi.repositories.SkladnikiRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SkladnikiService {
    @Autowired
    private SkladnikiRepo repo;

    public SkladnikiEntity getSkladnik(Long id) {
        return repo.findById(id).orElse(null);
    }
    public List<SkladnikiEntity> getAllSkladniki() {

        return repo.findAll();

    }
    public SkladnikiEntity createSkladnik(SkladnikiEntity s) {
        s.setCreatedAt(LocalDateTime.now());
        s.setUpdatedAt(LocalDateTime.now());
        return repo.save(s);
    }
    public SkladnikiEntity updateSkladnik(SkladnikiEntity s) {
        s.setUpdatedAt(LocalDateTime.now());
        return repo.save(s);
    }
    public void deleteSkladnik(Long s) {
        repo.deleteById(s);
    }
}
