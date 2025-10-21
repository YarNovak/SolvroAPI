package org.example.solvroapi.controllers;

import lombok.RequiredArgsConstructor;
import org.example.solvroapi.Entity.CocktailEntity;
import org.example.solvroapi.services.KoktailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/koktails")
@RequiredArgsConstructor
public class KoktalisController {

    @Autowired
    private final KoktailService service;

    @GetMapping
    public ResponseEntity<List<CocktailEntity>> getKoktails() {
        List<CocktailEntity> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<CocktailEntity> postKoktails(@RequestBody CocktailEntity koktail) {
        CocktailEntity response = service.create(koktail);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CocktailEntity> putKoktails(@PathVariable Long id, @RequestBody CocktailEntity koktail) {
        CocktailEntity response = service.update(koktail);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CocktailEntity> deleteKoktails(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
