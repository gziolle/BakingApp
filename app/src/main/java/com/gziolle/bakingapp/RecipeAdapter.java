package com.gziolle.bakingapp;

/**
 * BakingApp
 * Created by Guilherme Ziolle on 20/06/2017.
 * gziolle@gmail.com
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gziolle.bakingapp.model.Recipe;

import java.util.ArrayList;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Context mContext;
    private ArrayList<Recipe> mRecipes;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipes) {
        this.mContext = context;
        this.mRecipes = recipes;
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
        holder.mRecipeTitle.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        if (mRecipes == null) return 0;
        return mRecipes.size();
    }

    Recipe getRecipe(int position) {
        return mRecipes.get(position);
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {
        private ImageView mRecipeImage;
        private TextView mRecipeTitle;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            mRecipeImage = (ImageView) itemView.findViewById(R.id.ivRecipeImage);
            mRecipeTitle = (TextView) itemView.findViewById(R.id.tvRecipeTitle);
        }
    }
}
