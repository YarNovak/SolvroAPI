package org.example.solvroapi.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "koktails")
@Getter @Setter
public class KoktailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String category;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @Column(columnDefinition = "text")
    private String instructions;

    @ManyToMany
    @JoinTable(
            name = "cocktail_ingredients",
            joinColumns = @JoinColumn(name = "cocktail_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<SkladnikiEntity> ingredients = new ArrayList<>();
}
