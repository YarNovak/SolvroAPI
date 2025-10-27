package org.example.solvroapi.controllers;

import lombok.RequiredArgsConstructor;
import org.example.solvroapi.Entity.SkladnikiEntity;
import org.example.solvroapi.services.SkladnikiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/skladniki")
@RequiredArgsConstructor
public class SkladnikiController {

    @Autowired
    private SkladnikiService service;
/*
    @GetMapping
    public ResponseEntity<List<SkladnikiEntity>> getSkladniki() {
        List<SkladnikiEntity> skladniki = service.getAllSkladniki();
        return  ResponseEntity.ok(skladniki);
    }

    *

 */

    @GetMapping
    public ResponseEntity<Page<SkladnikiEntity>> getSkladniki(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean alcoholic,
            @RequestParam(required = false) Double minQuantity,
            @RequestParam(required = false) Double maxQuantity,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

        Page<SkladnikiEntity> skladnikiPage = service.getFilteredSkladniki(
                Optional.ofNullable(name),
                Optional.ofNullable(alcoholic),
                Optional.ofNullable(minQuantity),
                Optional.ofNullable(maxQuantity),
                page,
                size,
                Sort.by(direction, sortBy)
        );

        return ResponseEntity.ok(skladnikiPage);
    }



    @GetMapping("/{id}")
    public ResponseEntity<SkladnikiEntity> getSkladnikiById(@PathVariable Long id) {
        SkladnikiEntity skladnik = service.getSkladnik(id);
        return ResponseEntity.ok(skladnik);
    }

    @PostMapping
    public ResponseEntity<SkladnikiEntity> postSkladniki(@RequestBody SkladnikiEntity skladnikData) {
        SkladnikiEntity response = service.createSkladnik(skladnikData);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkladnikiEntity> putSkladniki(@PathVariable Long id, @RequestBody SkladnikiEntity skladnikData) {
        SkladnikiEntity sk = service.updateSkladnik(id, skladnikData);
        //mam nadzieje ze ok))
        return ResponseEntity.ok(sk);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSkladniki(@PathVariable Long id) {

        service.deleteSkladnik(id);
        return ResponseEntity.ok("ok");
    }
}