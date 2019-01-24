package com.example.bakingcakes.Models;

public class Ingredient {
    private String ingredientQuantity;
    private String ingredientMeasure;
    private String ingredientName;

    public Ingredient(String ingredientQuantity, String ingredientMeasure, String ingredientName) {
        this.ingredientQuantity = ingredientQuantity;
        this.ingredientMeasure = ingredientMeasure;
        this.ingredientName = ingredientName;
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

    public String setIngredientQuantity(String ingredientQuantity) {
        this.ingredientQuantity = ingredientQuantity;
        return ingredientQuantity;
    }

    public String getIngredientQuantity() {
        return ingredientQuantity;
    }
}
