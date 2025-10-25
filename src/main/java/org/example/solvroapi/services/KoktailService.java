package org.example.solvroapi.services;

import lombok.RequiredArgsConstructor;
import org.example.solvroapi.Entity.KoktailEntity;
import org.example.solvroapi.repositories.KocktailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class KoktailService {

    @Autowired
    private final KocktailsRepo kocktailsRepo;

    public KoktailEntity create(KoktailEntity c) {
        c.setCreatedAt(LocalDateTime.now());
        return kocktailsRepo.save(c);
    }

    public List<KoktailEntity> getAllFiltered(String name, String category, Boolean alcoholic) {
        if (name != null) {
            return kocktailsRepo.findByNameContaining(name);
        }
        if (category != null) {
            return kocktailsRepo.findAllByCategory(category);
        }
        return kocktailsRepo.findAll();
    }

    public List<KoktailEntity> getAllPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return kocktailsRepo.findAll(pageable).getContent();
    }


    public List<KoktailEntity> getAllSorted(String sortBy, String sortOrder) {
        Sort sort = Sort.by(sortOrder.equalsIgnoreCase("desc") ? Sort.Order.desc(sortBy) : Sort.Order.asc(sortBy));
        return kocktailsRepo.findAll(sort);
    }


    public Page<KoktailEntity> getFilteredKoktails(
            Optional<String> name,
            Optional<String> category,
            Optional<String> ingredientName,
            int page,
            int size,
            Sort sort
    ) {
        Pageable pageable = PageRequest.of(page, size, sort);


        List<KoktailEntity> all = kocktailsRepo.findAll(sort);


        List<KoktailEntity> filtered = all.stream()
                .filter(c -> name.map(n ->
                        c.getName() != null && c.getName().toLowerCase().contains(n.toLowerCase())
                ).orElse(true))
                .filter(c -> category.map(cat ->
                        c.getCategory() != null && c.getCategory().equalsIgnoreCase(cat)
                ).orElse(true))
                .filter(c -> ingredientName.map(iName ->
                        c.getIngredients().stream()
                                .anyMatch(ing -> ing.getName().toLowerCase().contains(iName.toLowerCase()))
                ).orElse(true))
                .collect(Collectors.toList());

        int start = Math.min(page * size, filtered.size());
        int end = Math.min(start + size, filtered.size());
        List<KoktailEntity> paged = filtered.subList(start, end);

        return new PageImpl<>(paged, pageable, filtered.size());
    }



    public KoktailEntity update(Long id, KoktailEntity c) {

        KoktailEntity cocktail = kocktailsRepo.findById(id).orElse(null);
        if(cocktail != null) {

            cocktail.setName(c.getName());
            cocktail.setCategory(c.getCategory());
            cocktail.setIngredients(c.getIngredients());
            cocktail.setInstructions(c.getInstructions());
            cocktail.setUpdatedAt(LocalDateTime.now());

            //cocktail = c;
            cocktail.setUpdatedAt(LocalDateTime.now());

            return kocktailsRepo.save(cocktail);
        }
       return null;
    }

    public List<KoktailEntity> getAll() {
        return kocktailsRepo.findAll();
    }

    public KoktailEntity getById(Long id) {
        return kocktailsRepo.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        kocktailsRepo.deleteById(id);
    }
}
