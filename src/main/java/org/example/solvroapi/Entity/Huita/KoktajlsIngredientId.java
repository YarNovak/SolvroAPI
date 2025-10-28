package org.example.solvroapi.Entity.Huita;

import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KoktajlsIngredientId implements Serializable {
    private Long cocktailId;
    private Long ingredientId;
}

