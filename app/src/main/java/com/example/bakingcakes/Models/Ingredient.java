package com.example.bakingcakes.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {
    private Double ingredientQuantity;
    private String ingredientMeasure;
    private String ingredientName;

    public Ingredient(Double ingredientQuantity, String ingredientMeasure, String ingredientName) {
        this.ingredientQuantity = ingredientQuantity;
        this.ingredientMeasure = ingredientMeasure;
        this.ingredientName = ingredientName;
    }

    Ingredient(Parcel in) {
        ingredientQuantity = in.readDouble();
        ingredientMeasure = in.readString();
        ingredientName = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(ingredientQuantity);
        dest.writeString(ingredientMeasure);
        dest.writeString(ingredientName);
    }
}
