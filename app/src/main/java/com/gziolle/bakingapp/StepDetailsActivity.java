package com.gziolle.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gziolle.bakingapp.model.Step;
import com.gziolle.bakingapp.util.Utils;

import java.util.ArrayList;


/**
 * BakingApp
 * Created by Guilherme Ziolle on 24/06/2017.
 * gziolle@gmail.com
 */
//TODO Search for ViewPager for StepDetailsFragment
public class StepDetailsActivity extends AppCompatActivity {

    private static final String LOG_TAG = StepDetailsActivity.class.getSimpleName();

    private TextView mStepProgress;

    private ArrayList<Step> mSteps;
    private int mCurrentStep;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(Utils.STEPS_EXTRA) && intent.hasExtra(Utils.SELECTED_STEP_EXTRA)) {
                mSteps = intent.getParcelableArrayListExtra(Utils.STEPS_EXTRA);
                mCurrentStep = intent.getIntExtra(Utils.SELECTED_STEP_EXTRA, 0);

                StepDetailsInnerFragment fragment = new StepDetailsInnerFragment();
                fragment.addStep(mSteps.get(mCurrentStep));
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.innerContainer, fragment)
                        .commit();
                TextView stepProgressTextView = (TextView) findViewById(R.id.tvStepProgress);
                stepProgressTextView.setText(
                        Utils.formatProgressString(this, mCurrentStep, mSteps.size()));
            }

            final ImageView nextImageView = (ImageView) findViewById(R.id.nextButton);
            final ImageView previousImageView = (ImageView) findViewById(R.id.previousButton);

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
    }

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
}