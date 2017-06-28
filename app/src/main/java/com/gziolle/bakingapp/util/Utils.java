package com.gziolle.bakingapp.util;

import android.content.Context;

import com.gziolle.bakingapp.R;
import com.gziolle.bakingapp.model.Ingredient;

/**
 * BakingApp
 * Created by Guilherme Ziolle on 26/06/2017.
 * gziolle@gmail.com
 */

public class Utils {

    public static final String RECIPES_EXTRA = "recipes";
    public static final String STEPS_EXTRA = "steps";
    public static final String INGREDIENTS_EXTRA = "ingredients";
    public static final String SELECTED_STEP_EXTRA = "selected_step";

    public static String formatIngredient(Context context, Ingredient ingredient) {
        return ingredient.getQuantity() + " " + ingredient.getMeasure() +
                " of " + ingredient.getIngredient();
    }

    public static String formatProgressString(Context context, int current, int total) {
        return String.format(context.getString(R.string.step_progress, current, total));
    }
}
