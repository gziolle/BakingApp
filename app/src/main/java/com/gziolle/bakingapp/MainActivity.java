package com.gziolle.bakingapp;

/**
 * BakingApp
 * Created by Guilherme Ziolle on 20/06/2017.
 * gziolle@gmail.com
 */

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.gziolle.bakingapp.model.Recipe;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Recipe>> {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int RECIPE_LOADER_ID = 101;

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

        mRecipes = new ArrayList<>();
        mRecipeAdapter = new RecipeAdapter(this, mRecipes);
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
