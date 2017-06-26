package com.gziolle.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * BakingApp
 * Created by Guilherme Ziolle on 20/06/2017.
 * gziolle@gmail.com
 */

public class Recipe implements Parcelable {
    private long id;
    private String name;
    ArrayList<Ingredient> ingredients;
    ArrayList<Step> steps;
    private int servings;
    private String imageUrl;

    private Recipe(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();

        this.ingredients = new ArrayList<Ingredient>();
        in.readTypedList(this.ingredients, Ingredient.CREATOR);
        this.steps = new ArrayList<Step>();
        in.readTypedList(this.steps, Step.CREATOR);

        this.servings = in.readInt();
        this.imageUrl = in.readString();
    }

    public Recipe(long mId, String mName, ArrayList<Ingredient> mIngredients, ArrayList<Step> mSteps, int mServings, String mImageUrl) {
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

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public int getServings() {
        return servings;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(getId());
        dest.writeString(getName());
        dest.writeTypedList(getIngredients());
        dest.writeTypedList(getSteps());
        dest.writeInt(getServings());
        dest.writeString(getImageUrl());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
