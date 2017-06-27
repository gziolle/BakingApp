package com.gziolle.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * BakingApp
 * Created by Guilherme Ziolle on 20/06/2017.
 * gziolle@gmail.com
 */

public class Ingredient implements Parcelable {
    private double quantity;
    private String measure;
    private String ingredient;

    public Ingredient(Parcel in) {
        this.quantity = in.readDouble();
        this.measure = in.readString();
        this.ingredient = in.readString();
    }

    public Ingredient(int quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(getQuantity());
        dest.writeString(getMeasure());
        dest.writeString(getIngredient());
    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
