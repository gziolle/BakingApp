package com.gziolle.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.gziolle.bakingapp.model.Ingredient;
import com.gziolle.bakingapp.util.Utils;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    private static final String LOG_TAG = RecipeWidgetProvider.class.getSimpleName();

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId, String recipeName, ArrayList<Ingredient> ingredients) {

        Log.d(LOG_TAG, "updateAppWidget");

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
        if (recipeName == null || recipeName.equals(""))
            recipeName = context.getString(R.string.app_name);
        views.setTextViewText(R.id.appwidget_text, recipeName);
        Log.d(LOG_TAG, "passou por aqui");

        if (ingredients != null) {
            views.setViewVisibility(R.id.tvEmptyWidget, View.GONE);
            StringBuilder ingredientResult = new StringBuilder();
            for (Ingredient ing : ingredients) {
                ingredientResult.append(Utils.formatIngredient(context, ing) + "\n");
            }
            Log.d(LOG_TAG, "ingredientResult = " + ingredientResult.toString());
            views.setTextViewText(R.id.tvIngredientList, ingredientResult.toString());
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.d(LOG_TAG, "onUpdate");
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, context.getString(R.string.app_name), null);
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
        Log.d(LOG_TAG, "onReceive()");
        super.onReceive(context, intent);
        if (Utils.ACTION_UPDATE_WIDGET.equals(intent.getAction())) {
            String recipeName = intent.getStringExtra(Utils.RECIPE_NAME_EXTRA);
            ArrayList<Ingredient> ingredients = intent.getParcelableArrayListExtra(Utils.INGREDIENTS_EXTRA);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
            ComponentName thisWidget = new ComponentName(context.getApplicationContext(), RecipeWidgetProvider.class);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
            if (appWidgetIds != null && appWidgetIds.length > 0) {
                for (int appId : appWidgetIds) {
                    updateAppWidget(context, appWidgetManager, appId, recipeName, ingredients);
                }

            }
        }
    }
}