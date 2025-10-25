package org.example.solvroapi.services;

import lombok.RequiredArgsConstructor;
import org.example.solvroapi.Entity.SkladnikiEntity;
import org.example.solvroapi.repositories.SkladnikiRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public SkladnikiEntity updateSkladnik(Long id, SkladnikiEntity s) {

        SkladnikiEntity skladnik = repo.findById(id).orElse(null);
        if (skladnik != null) {

            skladnik.setUpdatedAt(LocalDateTime.now());
            skladnik.setName(s.getName());
            skladnik.setDescription(s.getDescription());
            skladnik.setAlcoholic(s.isAlcoholic());
            skladnik.setPhotoUrl(s.getPhotoUrl());
            skladnik.setQuantity(s.getQuantity());

            //  skladnik = s;
            skladnik.setUpdatedAt(LocalDateTime.now());

            return repo.save(skladnik);

        }

        return null;
    }
    public void deleteSkladnik(Long s) {
        repo.deleteById(s);
    }

    public Page<SkladnikiEntity> getFilteredSkladniki(
            Optional<String> name,
            Optional<Boolean> alcoholic,
            Optional<Double> minQuantity,
            Optional<Double> maxQuantity,
            int page,
            int size,
            Sort sort
    ) {
        Pageable pageable = PageRequest.of(page, size, sort);

        List<SkladnikiEntity> all = repo.findAll(sort);

        List<SkladnikiEntity> filtered = all.stream()
                .filter(s -> name.map(n -> s.getName().toLowerCase().contains(n.toLowerCase())).orElse(true))
                .filter(s -> alcoholic.map(a -> s.isAlcoholic() == a).orElse(true))
                .filter(s -> minQuantity.map(min -> s.getQuantity() >= min).orElse(true))
                .filter(s -> maxQuantity.map(max -> s.getQuantity() <= max).orElse(true))
                .collect(Collectors.toList());


        int start = Math.min(page * size, filtered.size());
        int end = Math.min(start + size, filtered.size());
        List<SkladnikiEntity> paged = filtered.subList(start, end);

        return new PageImpl<>(paged, pageable, filtered.size());
    }
}