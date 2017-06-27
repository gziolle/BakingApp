package com.gziolle.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.gziolle.bakingapp.model.Ingredient;
import com.gziolle.bakingapp.model.Step;

import java.util.ArrayList;


public class RecipeActivity extends AppCompatActivity implements RecipeStepsFragment.OnStepClickListener {

    private static final String LOG_TAG = RecipeActivity.class.getSimpleName();

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ArrayList<Step> steps = new ArrayList<>();

        if (bundle != null) {
            ingredients = bundle.getParcelableArrayList(MainActivity.INGREDIENTS);
            steps = bundle.getParcelableArrayList(MainActivity.STEPS);
        }

        if (findViewById(R.id.step_details_container) != null) {
            mTwoPane = true;

            if (savedInstanceState == null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                StepDetailsFragment stepsFragment = new StepDetailsFragment();
            }
        } else {
            mTwoPane = false;
        }

        RecipeStepsFragment stepsFragment = (RecipeStepsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.recipe_steps_fragment);
        stepsFragment.update(ingredients, steps);
    }

    @Override
    public void onStepSelected(int position) {
        if (mTwoPane) {

        } else {

        }
    }
}
