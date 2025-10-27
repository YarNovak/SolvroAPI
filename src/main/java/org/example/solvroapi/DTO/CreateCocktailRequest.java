package org.example.solvroapi.DTO;

import lombok.Data;

import java.util.List;

@Data
public class CreateCocktailRequest {
    private String name;
    private String category;
    private String instruction;
    private List<IngredientAmount> ingredients;

    @Data
    public static class IngredientAmount {
        private Long ingredientId;
        private double amount;
        private String unit;
    }
}
