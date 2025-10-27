package org.example.solvroapi.controllers;

import lombok.RequiredArgsConstructor;
import org.example.solvroapi.DTO.CreateCocktailRequest;
import org.example.solvroapi.Entity.KoktailEntity;
import org.example.solvroapi.services.KoktailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/koktails")
@RequiredArgsConstructor
public class KoktalisController {

    @Autowired
    private final KoktailService service;

    @GetMapping
    public ResponseEntity<Page<KoktailEntity>> getFilteredKoktails(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String ingredient,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortOrder
    ) {
        Sort sort = sortOrder.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Page<KoktailEntity> result = service.getFilteredKoktails(  Optional.ofNullable(name),   Optional.ofNullable(category), Optional.ofNullable(ingredient), page, size, sort);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/{id}")
    public ResponseEntity<KoktailEntity> getKoktailById(@PathVariable Long id) {
        KoktailEntity this_koktail = service.getById(id);
        return ResponseEntity.ok(this_koktail);
    }





    @PostMapping
    public ResponseEntity<KoktailEntity> postKoktails(@RequestBody CreateCocktailRequest koktail) {
        KoktailEntity response = service.create(koktail);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KoktailEntity> putKoktails(@PathVariable Long id, @RequestBody CreateCocktailRequest koktail) {
        KoktailEntity response = service.update(id, koktail);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<KoktailEntity> deleteKoktails(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
