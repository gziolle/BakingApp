package com.gziolle.bakingapp.model;

import java.util.List;

/**
 * BakingApp
 * Created by Guilherme Ziolle on 20/06/2017.
 * gziolle@gmail.com
 */

public class Recipe {
    long id;
    String name;
    List<Ingredient> ingredients;
    List<Step> steps;
    int servings;
    String imageUrl;

    public Recipe(long mId, String mName, List<Ingredient> mIngredients, List<Step> mSteps, int mServings, String mImageUrl) {
        this.id = mId;
        this.name = mName;
        this.ingredients = mIngredients;
        this.steps = mSteps;
        this.servings = mServings;
        this.imageUrl = mImageUrl;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public int getServings() {
        return servings;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
