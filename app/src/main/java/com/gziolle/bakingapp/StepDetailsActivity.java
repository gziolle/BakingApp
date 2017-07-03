/*
 * BakingApp
 * Created by Guilherme Ziolle on 24/06/2017.
 * gziolle@gmail.com
 * Copyright (c) 2017. All rights reserved
 */

package com.gziolle.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gziolle.bakingapp.model.Step;
import com.gziolle.bakingapp.util.Utils;

import java.util.ArrayList;

/**
 * Hosts the steps details, such as the step's description and a video, if available.
 */

public class StepDetailsActivity extends AppCompatActivity {

    private static final String LOG_TAG = StepDetailsActivity.class.getSimpleName();

    private TextView mStepProgress;

    private ArrayList<Step> mSteps;
    private int mCurrentStep;
    private String mRecipeName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState != null) {
            mSteps = savedInstanceState.getParcelableArrayList(Utils.STEPS_EXTRA);
            mCurrentStep = savedInstanceState.getInt(Utils.SELECTED_STEP_EXTRA);
            mRecipeName = savedInstanceState.getString(Utils.RECIPE_NAME_EXTRA);
        } else {
            Intent intent = getIntent();
            if (intent != null) {
                if (intent.hasExtra(Utils.STEPS_EXTRA) && intent.hasExtra(Utils.SELECTED_STEP_EXTRA)) {
                    mSteps = intent.getParcelableArrayListExtra(Utils.STEPS_EXTRA);
                    mCurrentStep = intent.getIntExtra(Utils.SELECTED_STEP_EXTRA, 0);
                    mRecipeName = intent.getStringExtra(Utils.RECIPE_NAME_EXTRA);
                }
            }
        }

        if (actionBar != null) {
            actionBar.setTitle(mRecipeName);
        }
        StepDetailsInnerFragment fragment = new StepDetailsInnerFragment();
        fragment.addStep(mSteps.get(mCurrentStep));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.innerContainer, fragment)
                .commit();

        final ImageView nextImageView = (ImageView) findViewById(R.id.nextButton);
        final ImageView previousImageView = (ImageView) findViewById(R.id.previousButton);

        if (mCurrentStep == 0 && previousImageView != null) {
            previousImageView.setVisibility(View.INVISIBLE);
        } else if (mCurrentStep == mSteps.size() - 1 && nextImageView != null) {
            nextImageView.setVisibility(View.INVISIBLE);
        }

        //If the user has reached the end of the Step list, the "next" button is hidden.
        if (nextImageView != null) {
            nextImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (previousImageView.getVisibility() == View.INVISIBLE) {
                        previousImageView.setVisibility(View.VISIBLE);
                    }
                    if (mCurrentStep + 1 <= mSteps.size() - 1) {
                        mCurrentStep++;
                    }
                    if (mCurrentStep == mSteps.size() - 1) {
                        nextImageView.setVisibility(View.INVISIBLE);
                    }
                    replaceStep();
                }
            });

            //If the user is in the beginning of the Step list, the "previous" button is hidden.
            previousImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (nextImageView.getVisibility() == View.INVISIBLE) {
                        nextImageView.setVisibility(View.VISIBLE);
                    }
                    if (mCurrentStep - 1 > -1) {
                        mCurrentStep--;
                    }
                    if (mCurrentStep == 0) {
                        previousImageView.setVisibility(View.INVISIBLE);
                    }
                    replaceStep();
                }
            });

        }
        TextView stepProgressTextView = (TextView) findViewById(R.id.tvStepProgress);
        if (stepProgressTextView != null) {
            stepProgressTextView.setText(
                    Utils.formatProgressString(this, mCurrentStep + 1, mSteps.size()));
        }
    }


    /**
     * Replace the current {@link StepDetailsInnerFragment} with the previous or the next one.
     */
    private void replaceStep() {
        TextView stepProgressTextView = (TextView) findViewById(R.id.tvStepProgress);
        stepProgressTextView.setText(
                Utils.formatProgressString(this, mCurrentStep + 1, mSteps.size()));
        StepDetailsInnerFragment fragment = new StepDetailsInnerFragment();
        fragment.addStep(mSteps.get(mCurrentStep));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.innerContainer, fragment)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(Utils.STEPS_EXTRA, mSteps);
        outState.putInt(Utils.SELECTED_STEP_EXTRA, mCurrentStep);
        outState.putString(Utils.RECIPE_NAME_EXTRA, mRecipeName);
        super.onSaveInstanceState(outState);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
