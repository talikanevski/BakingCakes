package com.example.bakingcakes.Models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unchecked")
public class Cake implements Parcelable {
    private int cakeId;
    private String cakeName;
    private List<Ingredient> cakeIngredients;
    private List<Step> steps;
    private final String servings;
    private Bitmap cakeImage;

    public Cake(int cakeId, String cakeName, List<Ingredient> cakeIngredients, Step[] steps, String servings, Bitmap cakeImage) {
        this.cakeId = cakeId;
        this.cakeName = cakeName;
        this.cakeIngredients = cakeIngredients;
        this.steps = Arrays.asList(steps);
        this.servings = servings;
        this.cakeImage = cakeImage;
    }

    private Cake(Parcel in) {//Bitmap implements Parcelable by definition
        cakeId = in.readInt();
        cakeName = in.readString();
        servings = in.readString();
        cakeIngredients = in.readArrayList(Ingredient.class.getClassLoader());
        steps = in.readArrayList(Ingredient.class.getClassLoader());
    }

    public static final Creator<Cake> CREATOR = new Creator<Cake>() {
        @Override
        public Cake createFromParcel(Parcel in) {
            return new Cake(in);
        }

        @Override
        public Cake[] newArray(int size) {
            return new Cake[size];
        }
    };

    public String getCakeName() {
        return cakeName;
    }

    public List<Ingredient> getCakeIngredients() {
        return cakeIngredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public Bitmap getCakeImage() {
        return cakeImage;
    }

    public String getServings() {
        return servings;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cakeId);
        dest.writeString(cakeName);
        dest.writeString(servings);
        dest.writeList(cakeIngredients);
        dest.writeList(steps);
    }
}
