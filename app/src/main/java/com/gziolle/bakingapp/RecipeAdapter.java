/*
 * BakingApp
 * Created by Guilherme Ziolle on 20/06/2017.
 * gziolle@gmail.com
 * Copyright (c) 2017. All rights reserved
 */

package com.gziolle.bakingapp;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gziolle.bakingapp.model.Recipe;

import java.util.ArrayList;

/**
 * Represents an adapter for the list of recipes.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Context mContext;
    private ArrayList<Recipe> mRecipes;

    private final GridItemClickListener mClickListener;

    interface GridItemClickListener {
        void onItemSelected(int position);
    }

    public RecipeAdapter(Context context, ArrayList<Recipe> recipes, GridItemClickListener clickListener) {
        this.mContext = context;
        this.mRecipes = recipes;
        this.mClickListener = clickListener;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(
                R.layout.recipe_grid_item, parent, false);
        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe recipe = mRecipes.get(position);
        Glide.with(mContext).load(recipe.getImageUrl()).error(R.drawable.food).fitCenter().into(holder.mRecipeImage);
        holder.mRecipeTitle.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        if (mRecipes == null) return 0;
        return mRecipes.size();
    }

    /**
     * Helper class to access the recipes's views
     */
    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mRecipeImage;
        private TextView mRecipeTitle;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            mRecipeImage = (ImageView) itemView.findViewById(R.id.ivRecipeImage);
            mRecipeTitle = (TextView) itemView.findViewById(R.id.tvRecipeTitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickListener.onItemSelected(getAdapterPosition());
        }
    }
}
