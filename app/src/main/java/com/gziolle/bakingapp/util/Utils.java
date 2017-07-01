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
    public static final String RECIPE_EXTRA = "recipe";
    public static final String STEPS_EXTRA = "steps";
    public static final String INGREDIENTS_EXTRA = "ingredients";
    public static final String SELECTED_STEP_EXTRA = "selected_step";
    public static final String RECIPE_NAME_EXTRA = "recipe_name";

    public static final String ACTION_UPDATE_WIDGET = "com.gziolle.bakingapp.UPDATE_WIDGET";

    public static String formatIngredient(Context context, Ingredient ingredient) {
        StringBuilder sb = new StringBuilder();
        double quantity = ingredient.getQuantity();
        String quantityString;
        int integerQuantity = (int) ingredient.getQuantity();
        if (quantity - integerQuantity == 0) {
            quantityString = Integer.toString(integerQuantity);
        } else {
            quantityString = Double.toString(quantity);
        }

        String readableMeasure = getReadableMeasure(ingredient.getMeasure(), quantity);

        StringBuilder builder = new StringBuilder();
        builder.append(quantityString + " ");
        if (readableMeasure != "") {
            builder.append(readableMeasure);
        }
        builder.append(ingredient.getIngredient());

        return builder.toString();
    }

    public static String formatProgressString(Context context, int current, int total) {
        return String.format(context.getString(R.string.step_progress, current, total));
    }

    public static String getReadableMeasure(String measure, double quantity) {
        if ("G".equals(measure)) {
            return "grams of ";
        } else if ("K".equals(measure)) {
            return "kg of ";
        } else if ("TBLSP".equals(measure)) {
            if (quantity > 1) {
                return "tablespoons of ";
            } else {
                return "tablespoon of ";
            }
        } else if ("TSP".equals(measure)) {
            if (quantity > 1) {
                return "teaspoons of ";
            } else {
                return "teaspoon of ";
            }
        } else if ("CUP".equals(measure)) {
            if (quantity > 1) {
                return "cups of ";
            } else {
                return "cup of ";
            }
        } else if ("OZ".equals(measure)) {
            return "oz. of ";
        }
        return "";
    }
}
