package org.example.solvroapi.services;

import lombok.RequiredArgsConstructor;
import org.example.solvroapi.DTO.CreateCocktailRequest;
import org.example.solvroapi.Entity.KoktajlEntity;
import org.example.solvroapi.Entity.KoktajlSkladnikEntity;
import org.example.solvroapi.Entity.SkladnikiEntity;
import org.example.solvroapi.repositories.KocktajlsRepo;
import org.example.solvroapi.repositories.KoktajlSkladnikRepo;
import org.example.solvroapi.repositories.SkladnikiRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class KoktajlService {

    @Autowired
    private final KocktajlsRepo kocktajlsRepo;
    @Autowired
    private final SkladnikiRepo skladnikiRepo;

    @Autowired
    private KoktajlSkladnikRepo koktajlSkladnikRepo;

    @Transactional
    public KoktajlEntity create(CreateCocktailRequest dto) {
        KoktajlEntity cocktail = new KoktajlEntity();
        cocktail.setName(dto.getName());
        cocktail.setCategory(dto.getCategory());
        cocktail.setInstruction(dto.getInstruction());

        List<KoktajlSkladnikEntity> ingredients = dto.getIngredients().stream().map(item -> {
            SkladnikiEntity ingredient = skladnikiRepo.findById(item.getIngredientId())
                    .orElseThrow(() -> new RuntimeException("Ingredient not found: " + item.getIngredientId()));

            KoktajlSkladnikEntity ci = new KoktajlSkladnikEntity();
            ci.setCocktail(cocktail);
            ci.setIngredient(ingredient);
            ci.setAmount(item.getAmount());
            ci.setUnit(item.getUnit());

            return ci;
        }).toList();

        cocktail.setIngredients(ingredients);
        return kocktajlsRepo.save(cocktail);
    }

    public List<KoktajlEntity> getAllFiltered(String name, String category, Boolean alcoholic) {
        if (name != null) {
            return kocktajlsRepo.findByNameContaining(name);
        }
        if (category != null) {
            return kocktajlsRepo.findAllByCategory(category);
        }
        return kocktajlsRepo.findAll();
    }

    public List<KoktajlEntity> getAllPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return kocktajlsRepo.findAll(pageable).getContent();
    }


    public List<KoktajlEntity> getAllSorted(String sortBy, String sortOrder) {
        Sort sort = Sort.by(sortOrder.equalsIgnoreCase("desc") ? Sort.Order.desc(sortBy) : Sort.Order.asc(sortBy));
        return kocktajlsRepo.findAll(sort);
    }


    public Page<KoktajlEntity> getFilteredKoktajls(
            Optional<String> name,
            Optional<String> category,
            Optional<String> ingredientName,
            int page,
            int size,
            Sort sort
    ) {
        Pageable pageable = PageRequest.of(page, size, sort);


        List<KoktajlEntity> all = kocktajlsRepo.findAll(sort);


        List<KoktajlEntity> filtered = all.stream()
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
        List<KoktajlEntity> paged = filtered.subList(start, end);

        return new PageImpl<>(paged, pageable, filtered.size());
    }

    @Transactional
    public KoktajlSkladnikEntity updateIngredientAmount(Long cocktailId, Long ingredientId, KoktajlSkladnikEntity request) {
        KoktajlSkladnikEntity relation = koktajlSkladnikRepo
                .findByCompositeId(cocktailId, ingredientId)
                .orElseThrow(() -> new RuntimeException(
                        "Ingredient with ID " + ingredientId + " not found in cocktail " + cocktailId));

        relation.setAmount(request.getAmount());
        relation.setUnit(request.getUnit());

        return  koktajlSkladnikRepo.save(relation);
    }

    @Transactional
    public void deleteIngredientAmount(Long cocktailId, Long ingredientId) {
        KoktajlSkladnikEntity relation = koktajlSkladnikRepo
                .findByCompositeId(cocktailId, ingredientId)
                .orElseThrow(() -> new RuntimeException(
                        "Ingredient with ID " + ingredientId + " not found in cocktail " + cocktailId));

       koktajlSkladnikRepo.delete(relation);
    }





    @Transactional
    public KoktajlEntity update(Long id, CreateCocktailRequest dto) {
        KoktajlEntity cocktail = kocktajlsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cocktail not found"));


        cocktail.setName(dto.getName());
        cocktail.setCategory(dto.getCategory());
        cocktail.setInstruction(dto.getInstruction());


        cocktail.getIngredients().clear();
        kocktajlsRepo.saveAndFlush(cocktail);


        List<KoktajlSkladnikEntity> newIngredients = dto.getIngredients().stream()
                .map(item -> {
                    SkladnikiEntity ingredient = skladnikiRepo.findById(item.getIngredientId())
                            .orElseThrow(() -> new RuntimeException("Ingredient not found: " + item.getIngredientId()));

                    KoktajlSkladnikEntity ci = new KoktajlSkladnikEntity();
                    ci.setCocktail(cocktail);
                    ci.setIngredient(ingredient);
                    ci.setAmount(item.getAmount());
                    ci.setUnit(item.getUnit());
                    return ci;
                })
                .toList();


        cocktail.getIngredients().addAll(newIngredients);

        return kocktajlsRepo.save(cocktail);
    }




    public List<KoktajlEntity> getAll() {
        return kocktajlsRepo.findAll();
    }

    public KoktajlEntity getById(Long id) {
        return kocktajlsRepo.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        kocktajlsRepo.deleteById(id);
    }
}
