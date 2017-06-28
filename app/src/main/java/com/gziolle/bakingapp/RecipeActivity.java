package com.gziolle.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.gziolle.bakingapp.model.Ingredient;
import com.gziolle.bakingapp.model.Step;
import com.gziolle.bakingapp.util.Utils;

import java.util.ArrayList;


public class RecipeActivity extends AppCompatActivity implements RecipeStepsFragment.OnStepClickListener {

    private static final String LOG_TAG = RecipeActivity.class.getSimpleName();

    private ArrayList<Step> mSteps;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        mSteps = new ArrayList<>();

        if (bundle != null) {
            ingredients = bundle.getParcelableArrayList(Utils.INGREDIENTS_EXTRA);
            mSteps = bundle.getParcelableArrayList(Utils.STEPS_EXTRA);
        }

        if (findViewById(R.id.step_details_container) != null) {
            mTwoPane = true;

            if (savedInstanceState == null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
            }
        } else {
            mTwoPane = false;
        }

        RecipeStepsFragment stepsFragment = (RecipeStepsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.recipe_steps_fragment);
        stepsFragment.update(ingredients, mSteps);
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
            startActivity(intent);
        }
    }
}
