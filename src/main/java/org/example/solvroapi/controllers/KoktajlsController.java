package org.example.solvroapi.controllers;

import lombok.RequiredArgsConstructor;
import org.example.solvroapi.DTO.CreateCocktailRequest;
import org.example.solvroapi.Entity.KoktajlEntity;
import org.example.solvroapi.Entity.KoktajlSkladnikEntity;
import org.example.solvroapi.services.KoktajlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/koktajls")
@RequiredArgsConstructor
public class KoktajlsController {

    @Autowired
    private final KoktajlService service;

    @GetMapping
    public ResponseEntity<Page<KoktajlEntity>> getFilteredKoktajls(

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

        Page<KoktajlEntity> result = service.getFilteredKoktajls(  Optional.ofNullable(name),   Optional.ofNullable(category), Optional.ofNullable(ingredient), page, size, sort);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/{id}")
    public ResponseEntity<KoktajlEntity> getKoktailById(@PathVariable Long id) {
        KoktajlEntity this_koktail = service.getById(id);
        return ResponseEntity.ok(this_koktail);
    }





    @PostMapping
    public ResponseEntity<KoktajlEntity> postKoktails(@RequestBody CreateCocktailRequest koktail) {
        KoktajlEntity response = service.create(koktail);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KoktajlEntity> putKoktails(@PathVariable Long id, @RequestBody CreateCocktailRequest koktail) {
        KoktajlEntity response = service.update(id, koktail);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{cocktail_id}/skladnik/{skladnik_id}")
    public ResponseEntity<KoktajlSkladnikEntity> putSkladnik_into_cocktail(@PathVariable Long cocktail_id, @PathVariable Long skladnik_id,
                                                                           @RequestBody KoktajlSkladnikEntity cocktailIngredient) {

        KoktajlSkladnikEntity response =  service.updateIngredientAmount(cocktail_id, skladnik_id, cocktailIngredient);

        return ResponseEntity.ok(response);

    }
    @DeleteMapping("/{cocktail_id}/skladnik/{skladnik_id}")
    public ResponseEntity<KoktajlSkladnikEntity> delete_Skladnik_from_cocktail(@PathVariable Long cocktail_id, @PathVariable Long skladnik_id) {

        service.deleteIngredientAmount(cocktail_id, skladnik_id);
        return ResponseEntity.noContent().build();

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<KoktajlEntity> deleteKoktails(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
