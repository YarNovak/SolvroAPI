package org.example.solvroapi.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.example.solvroapi.Entity.Huita.CocktailIngredientId;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cocktail_ingredients")
public class KoktailSkladnikEntity {
    @EmbeddedId
    private CocktailIngredientId id = new CocktailIngredientId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("cocktailId")
    @JsonIgnore
    private KoktailEntity cocktail;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ingredientId")
    @JsonIgnore
    private SkladnikiEntity ingredient;

    private double amount;
    private String unit;
}

