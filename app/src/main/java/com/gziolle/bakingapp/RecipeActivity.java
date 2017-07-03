/*
 * BakingApp
 * Created by Guilherme Ziolle on 20/06/2017.
 * gziolle@gmail.com
 * Copyright (c) 2017. All rights reserved
 */

package com.gziolle.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.gziolle.bakingapp.model.Ingredient;
import com.gziolle.bakingapp.model.Recipe;
import com.gziolle.bakingapp.model.Step;
import com.gziolle.bakingapp.util.Utils;

import java.util.ArrayList;

/**
 * Hosts the steps and ingredients for a recipe.
 */

public class RecipeActivity extends AppCompatActivity implements RecipeStepsFragment.OnStepClickListener {

    private static final String LOG_TAG = RecipeActivity.class.getSimpleName();

    private Recipe mRecipe;
    private ArrayList<Step> mSteps;
    private ArrayList<Ingredient> mIngredients;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        mSteps = new ArrayList<>();

        if (bundle != null) {
            mRecipe = bundle.getParcelable(Utils.RECIPES_EXTRA);
            mIngredients = mRecipe.getIngredients();
            mSteps = mRecipe.getSteps();
            if (actionBar != null) {
                actionBar.setTitle(mRecipe.getName());
            }
        }

        if (mIngredients == null) {
            mIngredients = new ArrayList<>();
        }

        if (findViewById(R.id.step_details_container) != null) {
            mTwoPane = true;

            if (savedInstanceState == null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                StepDetailsInnerFragment fragment = new StepDetailsInnerFragment();
                fragment.addStep(mSteps.get(0));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.step_details_container, fragment)
                        .commit();
            }
        } else {
            mTwoPane = false;
        }

        RecipeStepsFragment stepsFragment = (RecipeStepsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.recipe_steps_fragment);
        stepsFragment.update(mIngredients, mSteps);
    }

    @Override
    public void onStepSelected(int position) {
        if (mTwoPane) {
            StepDetailsInnerFragment fragment = new StepDetailsInnerFragment();
            fragment.addStep(mSteps.get(position));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_details_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, StepDetailsActivity.class);
            intent.putParcelableArrayListExtra(Utils.STEPS_EXTRA, mSteps);
            intent.putExtra(Utils.SELECTED_STEP_EXTRA, position);
            intent.putExtra(Utils.RECIPE_NAME_EXTRA, mRecipe.getName());
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                // This activity is NOT part of this app's task, so create a new task
                // when navigating up, with a synthesized back stack.
                TaskStackBuilder.create(this)
                        // Add all of this activity's parents to the back stack
                        .addNextIntentWithParentStack(upIntent)
                        // Navigate up to the closest parent
                        .startActivities();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(Utils.INGREDIENTS_EXTRA, mIngredients);
        outState.putParcelableArrayList(Utils.STEPS_EXTRA, mSteps);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (mIngredients.size() == 0 && mSteps.size() == 0) {
            mIngredients = savedInstanceState.getParcelableArrayList(Utils.INGREDIENTS_EXTRA);
            mSteps = savedInstanceState.getParcelableArrayList(Utils.STEPS_EXTRA);
        }
    }
}
