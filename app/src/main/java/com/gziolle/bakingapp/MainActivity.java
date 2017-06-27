package com.gziolle.bakingapp;

/**
 * BakingApp
 * Created by Guilherme Ziolle on 20/06/2017.
 * gziolle@gmail.com
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.gziolle.bakingapp.model.Recipe;
import com.gziolle.bakingapp.util.NetworkUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Recipe>>,
        RecipeAdapter.GridItemClickListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int RECIPE_LOADER_ID = 101;

    public static final String RECIPES = "recipes";
    public static final String STEPS = "steps";
    public static final String INGREDIENTS = "ingredients";

    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private ArrayList<Recipe> mRecipes;
    private RecipeAdapter mRecipeAdapter;
    public ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recipeRecyclerView);
        mGridLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        if (savedInstanceState != null) {
            mRecipes = savedInstanceState.getParcelableArrayList(RECIPES);
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
        bundle.putParcelableArrayList(INGREDIENTS, recipe.getIngredients());
        bundle.putParcelableArrayList(STEPS, recipe.getSteps());

        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    /*
        * Displays the progress dialog to inform the user that movies are being loaded.
        * */
    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.setMessage(getString(R.string.progress_dialog_loading));
        mProgressDialog.show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(RECIPES, mRecipes);
        super.onSaveInstanceState(outState);
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
            mRecipes.addAll(data);
            mRecipeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
    }
}
