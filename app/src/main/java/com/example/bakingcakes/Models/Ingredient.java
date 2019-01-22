package com.example.bakingcakes.Models;

public class Ingredient {
    private Double ingredientQuantity;
    private String ingredientMeasure;
    private String ingredientName;

    public Ingredient(Double ingredientQuantity, String ingredientMeasure, String ingredientName) {
    }

    public String setIngredientMeasure(String ingredientMeasure) {
        this.ingredientMeasure = ingredientMeasure;
        return ingredientMeasure;
    }

    public String getIngredientMeasure() {
        return ingredientMeasure;
    }

    public String setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
        return ingredientName;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public Double setIngredientQuantity(Double ingredientQuantity) {
        this.ingredientQuantity = ingredientQuantity;
        return ingredientQuantity;
    }

    public Double getIngredientQuantity() {
        return ingredientQuantity;
    }
}
