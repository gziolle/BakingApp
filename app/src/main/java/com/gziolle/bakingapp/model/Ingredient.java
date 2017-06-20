package com.gziolle.bakingapp.model;

/**
 * BakingApp
 * Created by Guilherme Ziolle on 20/06/2017.
 * gziolle@gmail.com
 */

public class Ingredient {
    private int mQuantity;
    private String mMeasure;
    private String mIngredientName;


    public Ingredient(int quantity, String measure, String ingredientName) {
        this.mQuantity = quantity;
        this.mMeasure = measure;
        this.mIngredientName = ingredientName;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public String getIngredientName() {
        return mIngredientName;
    }
}
