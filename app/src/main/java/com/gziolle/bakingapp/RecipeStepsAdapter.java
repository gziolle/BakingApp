package com.gziolle.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gziolle.bakingapp.model.Step;

import java.util.ArrayList;

/**
 * BakingApp
 * Created by Guilherme Ziolle on 24/06/2017.
 * gziolle@gmail.com
 */

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.StepViewHolder> {

    private Context mContext;
    private StepClickListener mStepClickListener;
    private ArrayList<Step> mSteps;

    public interface StepClickListener {
        void onStepClicked(int position);
    }

    public RecipeStepsAdapter(Context context, ArrayList<Step> steps, StepClickListener listener) {
        this.mContext = context;
        this.mSteps = steps;
        this.mStepClickListener = listener;
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(
                R.layout.step_grid_item, parent, false);
        return new StepViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        Step step = mSteps.get(position);
        holder.mStepShortDescription.setText(step.getShortDescription());

    }

    @Override
    public int getItemCount() {
        if (mSteps == null) return 0;
        return mSteps.size();
    }

    public void addSteps(ArrayList<Step> steps) {
        mSteps = steps;
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mStepShortDescription;

        public StepViewHolder(View itemView) {
            super(itemView);
            mStepShortDescription = (TextView) itemView.findViewById(R.id.tv_step_short_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mStepClickListener.onStepClicked(getAdapterPosition());
        }
    }
}
