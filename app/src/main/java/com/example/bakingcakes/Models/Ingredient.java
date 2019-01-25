package com.example.bakingcakes.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {
    private String ingredientQuantity;
    private String ingredientMeasure;
    private String ingredientName;

    public Ingredient(String ingredientQuantity, String ingredientMeasure, String ingredientName) {
        this.ingredientQuantity = ingredientQuantity;
        this.ingredientMeasure = ingredientMeasure;
        this.ingredientName = ingredientName;
    }

    protected Ingredient(Parcel in) {
        ingredientQuantity = in.readString();
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

    public String setIngredientQuantity(String ingredientQuantity) {
        this.ingredientQuantity = ingredientQuantity;
        return ingredientQuantity;
    }

    public String getIngredientQuantity() {
        return ingredientQuantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ingredientQuantity);
        dest.writeString(ingredientMeasure);
        dest.writeString(ingredientName);
    }
}
