package com.example.bakingcakes.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.List;

public class Cake implements Parcelable {
    private int cakeId;
    private String cakeName;
    private List<Ingredient> cakeIngredients = null;
    private List<Step> steps = null;
    private String servings;
    private String cakeImage;

    public Cake(int cakeId, String cakeName, List<Ingredient> cakeIngredients, Step[] steps, String servings, String cakeImage) {
        this.cakeId = cakeId;
        this.cakeName = cakeName;
        this.cakeIngredients = cakeIngredients;
        this.steps = Arrays.asList(steps);
        this.servings = servings;
        this.cakeImage = cakeImage;
    }

    protected Cake(Parcel in) {
        cakeId = in.readInt();
        cakeName = in.readString();
        servings = in.readString();
        cakeImage = in.readString();
        cakeIngredients = in.readArrayList(Ingredient.class.getClassLoader());
        //
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

    public int getCakeId() {
        return cakeId;
    }

    public String getCakeName() {
        return cakeName;
    }

    public List<Ingredient> getCakeIngredients() {
        return cakeIngredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public String getCakeImage() {
        return cakeImage;
    }

    public void setCakeId(int cakeId) {
        this.cakeId = cakeId;
    }

    public void setCakeName(String cakeName) {
        this.cakeName = cakeName;
    }

    public List<Ingredient> setCakeIngredients(List<Ingredient> cakeIngredients) {
        this.cakeIngredients = cakeIngredients;
        return cakeIngredients;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cakeId);
        dest.writeString(cakeName);
        dest.writeString(servings);
        dest.writeString(cakeImage);
        dest.writeList(cakeIngredients);
        dest.writeList(steps);
    }
}
