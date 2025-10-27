package org.example.solvroapi.services;

import lombok.RequiredArgsConstructor;
import org.example.solvroapi.DTO.CreateCocktailRequest;
import org.example.solvroapi.Entity.KoktailEntity;
import org.example.solvroapi.Entity.KoktailSkladnikEntity;
import org.example.solvroapi.Entity.SkladnikiEntity;
import org.example.solvroapi.repositories.KocktailsRepo;
import org.example.solvroapi.repositories.KoktailSkladnikRepo;
import org.example.solvroapi.repositories.SkladnikiRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class KoktailService {

    @Autowired
    private final KocktailsRepo kocktailsRepo;
    @Autowired
    private final SkladnikiRepo skladnikiRepo;

    @Autowired
    private KoktailSkladnikRepo koktailSkladnikRepo;

    @Transactional
    public KoktailEntity create(CreateCocktailRequest dto) {
        KoktailEntity cocktail = new KoktailEntity();
        cocktail.setName(dto.getName());
        cocktail.setCategory(dto.getCategory());
        cocktail.setInstruction(dto.getInstruction());

        List<KoktailSkladnikEntity> ingredients = dto.getIngredients().stream().map(item -> {
            SkladnikiEntity ingredient = skladnikiRepo.findById(item.getIngredientId())
                    .orElseThrow(() -> new RuntimeException("Ingredient not found: " + item.getIngredientId()));

            KoktailSkladnikEntity ci = new KoktailSkladnikEntity();
            ci.setCocktail(cocktail);
            ci.setIngredient(ingredient);
            ci.setAmount(item.getAmount());
            ci.setUnit(item.getUnit());

            return ci;
        }).toList();

        cocktail.setIngredients(ingredients);
        return kocktailsRepo.save(cocktail);
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
                                .anyMatch(ing -> ing.getIngredient().getName().toLowerCase().contains(iName.toLowerCase()))
                ).orElse(true))
                .collect(Collectors.toList());

        int start = Math.min(page * size, filtered.size());
        int end = Math.min(start + size, filtered.size());
        List<KoktailEntity> paged = filtered.subList(start, end);

        return new PageImpl<>(paged, pageable, filtered.size());
    }



    @Transactional
    public KoktailEntity update(Long id, CreateCocktailRequest dto) {
        KoktailEntity cocktail = kocktailsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cocktail not found"));


        cocktail.setName(dto.getName());
        cocktail.setCategory(dto.getCategory());
        cocktail.setInstruction(dto.getInstruction());


        cocktail.getIngredients().clear();
        kocktailsRepo.saveAndFlush(cocktail);


        List<KoktailSkladnikEntity> newIngredients = dto.getIngredients().stream()
                .map(item -> {
                    SkladnikiEntity ingredient = skladnikiRepo.findById(item.getIngredientId())
                            .orElseThrow(() -> new RuntimeException("Ingredient not found: " + item.getIngredientId()));

                    KoktailSkladnikEntity ci = new KoktailSkladnikEntity();
                    ci.setCocktail(cocktail);
                    ci.setIngredient(ingredient);
                    ci.setAmount(item.getAmount());
                    ci.setUnit(item.getUnit());
                    return ci;
                })
                .toList();


        cocktail.getIngredients().addAll(newIngredients);

        return kocktailsRepo.save(cocktail);
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
