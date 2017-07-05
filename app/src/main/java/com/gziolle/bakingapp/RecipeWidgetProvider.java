/*
 * BakingApp
 * Created by Guilherme Ziolle on 01/07/2017.
 * gziolle@gmail.com
 * Copyright (c) 2017. All rights reserved
 */

package com.gziolle.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.gziolle.bakingapp.model.Ingredient;
import com.gziolle.bakingapp.model.Recipe;
import com.gziolle.bakingapp.util.Utils;

import java.util.ArrayList;

/**
 * Represents a widget in which the user can check the ingredients for a recipe.
 */

public class RecipeWidgetProvider extends AppWidgetProvider {

    private static final String LOG_TAG = RecipeWidgetProvider.class.getSimpleName();

    /**
     * Update a widget with new information.
     *
     * @param context          The application's context
     * @param appWidgetManager An instance of {@link AppWidgetManager}
     * @param appWidgetId      The widget's ID.
     * @param recipe           The {@link Recipe} used to update the widget.
     */
    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId, Recipe recipe) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
        Intent intent;
        if (recipe != null) {
            String recipeName = recipe.getName();
            views.setTextViewText(R.id.appwidget_text, recipeName);

            ArrayList<Ingredient> ingredients = recipe.getIngredients();
            if (ingredients != null) {
                views.setViewVisibility(R.id.tvEmptyWidget, View.GONE);
                StringBuilder ingredientResult = new StringBuilder();
                for (Ingredient ing : ingredients) {
                    ingredientResult.append(Utils.formatIngredient(context, ing) + "\n");
                }
                Log.d(LOG_TAG, "ingredientResult = " + ingredientResult.toString());
                views.setTextViewText(R.id.tvIngredientList, ingredientResult.toString());
            }
            Bundle bundle = new Bundle();
            bundle.putParcelable(Utils.RECIPES_EXTRA, recipe);

            intent = new Intent(context, RecipeActivity.class);
            intent.putExtras(bundle);

        } else {
            views.setTextViewText(R.id.appwidget_text, context.getString(R.string.baking_app));
            intent = new Intent(context, MainActivity.class);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widgetLayout, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.d(LOG_TAG, "onUpdate");
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, null);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //If the user selected a different recipe, all widgets should be updated accordingly.
        if (Utils.ACTION_UPDATE_WIDGET.equals(intent.getAction())) {
            Recipe recipe = intent.getParcelableExtra(Utils.RECIPE_EXTRA);
            Log.d(LOG_TAG, "name = " + recipe.getName());
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
            ComponentName thisWidget = new ComponentName(context.getApplicationContext(), RecipeWidgetProvider.class);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
            if (appWidgetIds != null && appWidgetIds.length > 0) {
                for (int appId : appWidgetIds) {
                    updateAppWidget(
                            context,
                            appWidgetManager,
                            appId,
                            recipe);
                }

            }
        } else {
            super.onReceive(context, intent);
        }
    }
}