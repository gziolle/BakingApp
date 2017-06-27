package com.gziolle.bakingapp.util;

import android.content.Context;

import com.gziolle.bakingapp.model.Ingredient;

/**
 * BakingApp
 * Created by Guilherme Ziolle on 26/06/2017.
 * gziolle@gmail.com
 */

public class Utils {
    public static String formatIngredient(Context context, Ingredient ingredient) {
        return ingredient.getQuantity() + " " + ingredient.getMeasure() +
                " of " + ingredient.getIngredient();
    }
}
