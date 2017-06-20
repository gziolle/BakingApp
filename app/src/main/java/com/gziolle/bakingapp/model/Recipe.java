package com.gziolle.bakingapp.model;

import java.util.List;

/**
 * BakingApp
 * Created by Guilherme Ziolle on 20/06/2017.
 * gziolle@gmail.com
 */

public class Recipe {
    private long mId;
    private String mName;
    private List<Ingredient> mIngredients;
    private List<Step> mSteps;
    private int mServings;
    private String mImageUrl;

    public Recipe(long mId, String mName, List<Ingredient> mIngredients, List<Step> mSteps, int mServings, String mImageUrl) {
        this.mId = mId;
        this.mName = mName;
        this.mIngredients = mIngredients;
        this.mSteps = mSteps;
        this.mServings = mServings;
        this.mImageUrl = mImageUrl;
    }

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public List<Ingredient> getIngredients() {
        return mIngredients;
    }

    public List<Step> getSteps() {
        return mSteps;
    }

    public int getServings() {
        return mServings;
    }

    public String getImageUrl() {
        return mImageUrl;
    }
}
