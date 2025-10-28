package org.example.solvroapi.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.example.solvroapi.Entity.Huita.KoktajlsIngredientId;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cocktail_ingredients")
public class KoktajlSkladnikEntity {
    @EmbeddedId
    private KoktajlsIngredientId id = new KoktajlsIngredientId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("cocktailId")
    @JsonIgnore
    private KoktajlEntity cocktail;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ingredientId")
    @JsonIgnore
    private SkladnikiEntity ingredient;

    private double amount;
    private String unit;
}

