package com.gziolle.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gziolle.bakingapp.model.Ingredient;
import com.gziolle.bakingapp.model.Step;
import com.gziolle.bakingapp.util.Utils;

import java.util.ArrayList;

/**
 * BakingApp
 * Created by Guilherme Ziolle on 24/06/2017.
 * gziolle@gmail.com
 */

//TODO Pick all steps that come from RecipeActivity and add them to the RecyclerView's adapter
public class RecipeStepsFragment extends Fragment implements RecipeStepsAdapter.StepClickListener {

    private static final String LOG_TAG = RecipeStepsFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecipeStepsAdapter mAdapter;
    private ArrayList<Step> mSteps;

    OnStepClickListener mCallbackListener;

    public interface OnStepClickListener {
        void onStepSelected(int position);
    }

    public RecipeStepsFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallbackListener = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.stepsRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new RecipeStepsAdapter(getActivity(), mSteps, this);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void update(ArrayList<Ingredient> ingredients, ArrayList<Step> steps) {
        addIngredientsToView(ingredients);
        addStepsToAdapter(steps);
    }

    public void addStepsToAdapter(ArrayList<Step> steps) {
        this.mSteps = steps;
        mAdapter.addSteps(mSteps);
        mAdapter.notifyDataSetChanged();
    }

    public void addIngredientsToView(ArrayList<Ingredient> ingredients) {
        LinearLayout linearLayout = (LinearLayout) getActivity().findViewById(R.id.ingredientLayout);

        for (Ingredient ingredient : ingredients) {
            View ingredientView = LayoutInflater.from(getActivity()).inflate(
                    R.layout.ingredient_item, linearLayout, false);

            TextView ingredientTextView = (TextView) ingredientView.findViewById(R.id.tvIngredient);
            ingredientTextView.setText(Utils.formatIngredient(getActivity(), ingredient));
            linearLayout.addView(ingredientView);
        }
    }

    @Override
    public void onStepClicked(int position) {
        mCallbackListener.onStepSelected(position);
    }
}
