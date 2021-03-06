/*
 * BakingApp
 * Created by Guilherme Ziolle on 20/06/2017.
 * gziolle@gmail.com
 * Copyright (c) 2017. All rights reserved
 */

package com.gziolle.bakingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.gziolle.bakingapp.model.Recipe;
import com.gziolle.bakingapp.util.NetworkUtils;
import com.gziolle.bakingapp.util.Utils;

import java.util.ArrayList;

/**
 * Hosts the list of recipes retrieved from the Baking App web service.
 */

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Recipe>>,
        RecipeAdapter.GridItemClickListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int RECIPE_LOADER_ID = 101;

    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private ArrayList<Recipe> mRecipes;
    private RecipeAdapter mRecipeAdapter;
    public ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int spanCount = calculateSpanCount();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recipeRecyclerView);
        mGridLayoutManager = new GridLayoutManager(this, spanCount);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        if (savedInstanceState != null) {
            mRecipes = savedInstanceState.getParcelableArrayList(Utils.RECIPES_EXTRA);
        } else {
            mRecipes = new ArrayList<>();
        }

        mRecipeAdapter = new RecipeAdapter(this, mRecipes, this);
        mRecyclerView.setAdapter(mRecipeAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Loader loader = getSupportLoaderManager().getLoader(RECIPE_LOADER_ID);
        if (loader == null) {
            Log.i(LOG_TAG, "loader == null");
            getSupportLoaderManager().initLoader(RECIPE_LOADER_ID, null, this);
        }
    }

    @Override
    public void onItemSelected(int position) {
        Recipe recipe = mRecipes.get(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Utils.RECIPES_EXTRA, recipe);

        updateIngredientWidget(recipe);

        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(Utils.RECIPES_EXTRA, mRecipes);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mRecipes = savedInstanceState.getParcelableArrayList(Utils.RECIPES_EXTRA);
    }

    /**
     * Displays a dialog to inform the user which informs the user that recipes are being loaded.
     */
    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.setMessage(getString(R.string.progress_dialog_loading));
        mProgressDialog.show();
    }

    /**
     * Determines how many columns will be displayed in the RecycleView.
     */
    public int calculateSpanCount() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float dpWidth = metrics.widthPixels / metrics.density;
        return (int) (dpWidth / 266);
    }

    /**
     * Displays a message when there are no movies to display.
     */
    private void displayErrorMessages() {
        TextView textView = (TextView) findViewById(R.id.tvNoRecipes);
        textView.setVisibility(View.VISIBLE);
        textView.setText(getString(R.string.no_recipes_to_display));
    }

    /**
     * Updates all widgets when the user accesses a new recipe.
     *
     * @param recipe A recipe which will be displayed by a widget.
     */
    private void updateIngredientWidget(Recipe recipe) {
        Intent intent = new Intent();
        intent.setAction(Utils.ACTION_UPDATE_WIDGET);
        intent.putExtra(Utils.RECIPE_EXTRA, recipe);
        sendBroadcast(intent);
    }

    @Override
    public Loader<ArrayList<Recipe>> onCreateLoader(int id, Bundle args) {

        return new AsyncTaskLoader<ArrayList<Recipe>>(this) {
            @Override
            protected void onStartLoading() {
                showProgressDialog();
                forceLoad();
            }
            @Override
            public ArrayList<Recipe> loadInBackground() {
                return NetworkUtils.fetchRecipes();
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Recipe>> loader, ArrayList<Recipe> data) {
        if (mProgressDialog != null) {
            mProgressDialog.hide();
        }
        if (data != null && data.size() != 0) {
            if (mRecipes.size() != 0) {
                mRecipes.clear();
            }
            mRecipes.addAll(data);
            mRecipeAdapter.notifyDataSetChanged();
        } else {
            displayErrorMessages();
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
    }
}
