package org.example.solvroapi.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "cocktails")
@Getter @Setter
public class CocktailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String category;

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
