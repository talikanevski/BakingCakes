package com.example.bakingcakes.Models;

import java.util.Arrays;
import java.util.List;

public class Cake { //TODO? implements Parcelable ???
    private int cakeId;
    private String cakeName;
    private List<Ingredient> cakeIngredients = null;
    private List<Step> steps = null;
    private String servings;
    private String cakeImage;

    public Cake(int cakeId, String cakeName, Ingredient[] cakeIngredients, Step[] steps, String servings, String cakeImage) {
        this.cakeId = cakeId;
        this.cakeName = cakeName;
        this.cakeIngredients = Arrays.asList(cakeIngredients);
        this.steps = Arrays.asList(steps);
        this.servings = servings;
        this.cakeImage = cakeImage;
    }

    public int getCakeId() {return cakeId;}
    public String getCakeName() {return cakeName;}
    public List<Ingredient> getCakeIngredients() {
        return cakeIngredients;
    }
    public List<Step> getSteps() {
        return steps;
    }
    public String getCakeImage() {return cakeImage;}
    public void setCakeId(int cakeId) {
        this.cakeId = cakeId;
    }
    public void setCakeName(String cakeName) {
        this.cakeName = cakeName;
    }
    public void setCakeIngredients(List<Ingredient> cakeIngredients) {
        this.cakeIngredients = cakeIngredients;
    }
    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
    public String getServings() {
        return servings;
    }
    public void setCakeImage(String cakeImage) {
        this.cakeImage = cakeImage;
    }
}
