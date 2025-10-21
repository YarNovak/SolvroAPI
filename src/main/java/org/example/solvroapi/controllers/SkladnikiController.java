package org.example.solvroapi.controllers;

import lombok.RequiredArgsConstructor;
import org.example.solvroapi.Entity.SkladnikiEntity;
import org.example.solvroapi.services.SkladnikiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/skladniki")
@RequiredArgsConstructor
public class SkladnikiController {

    @Autowired
    private SkladnikiService service;

    @GetMapping
    public ResponseEntity<List<SkladnikiEntity>> getSkladniki() {
        List<SkladnikiEntity> skladniki = service.getAllSkladniki();
        return  ResponseEntity.ok(skladniki);
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
        service.updateSkladnik(skladnikData);
//        HUJ ZNA
        return ResponseEntity.ok(service.getSkladnik(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSkladniki(@PathVariable Long id) {
        service.deleteSkladnik(id);
        return ResponseEntity.ok("ok");
    }
}