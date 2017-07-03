/*
 * BakingApp
 * Created by Guilherme Ziolle on 26/06/2017.
 * gziolle@gmail.com
 * Copyright (c) 2017. All rights reserved
 */

package com.gziolle.bakingapp.util;

import android.content.Context;

import com.gziolle.bakingapp.R;
import com.gziolle.bakingapp.model.Ingredient;

/**
 * Provides helper methods to display information properly.
 */

public class Utils {

    public static final String RECIPES_EXTRA = "recipes";
    public static final String RECIPE_EXTRA = "recipe";
    public static final String STEPS_EXTRA = "steps";
    public static final String INGREDIENTS_EXTRA = "ingredients";
    public static final String SELECTED_STEP_EXTRA = "selected_step";

    public static final String ACTION_UPDATE_WIDGET = "com.gziolle.bakingapp.UPDATE_WIDGET";

    /**
     * Returns a readable string for a ingredient
     *
     * @param context    The application's context
     * @param ingredient A recipe's ingredient
     * @return The ingredient's quantity, measure and name as a readable string
     */
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

    /**
     * Returns a string that represents the current step's position in the list of steps.
     * @param context The application's context.
     * @param current The current step.
     * @param total The number of steps.
     * @return A string representing the current step's position.
     */
    public static String formatProgressString(Context context, int current, int total) {
        return String.format(context.getString(R.string.step_progress, current, total));
    }

    /**
     * Returns the ingredient's measure in a readable string.
     * @param measure The ingredient's measure.
     * @param quantity The ingredient's quantity.
     * @return A readable string for the ingredient's measure.
     */
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
