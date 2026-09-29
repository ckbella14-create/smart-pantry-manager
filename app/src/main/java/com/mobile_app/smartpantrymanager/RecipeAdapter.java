package com.mobile_app.smartpantrymanager;

//imports
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//this class handles connecting all the recipe data to the views in each RecyclerView row
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    //list of recipes to display
    private ArrayList<Recipe> recipes;
    private Context context;

    //constructor
    public RecipeAdapter(ArrayList<Recipe> recipes, Context context) {
        this.recipes = recipes;
        this.context = context;
    }

    //this class stores the views for one recipe
    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView recipeNameTextView;
        TextView recipeIngredientCountTextView;
        TextView ingredientMatchTextView;
        Button viewRecipeButton;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            recipeNameTextView = itemView.findViewById(R.id.recipeNameTextView);
            recipeIngredientCountTextView = itemView.findViewById(R.id.recipeIngredientCountTextView);
            ingredientMatchTextView = itemView.findViewById(R.id.ingredientMatchTextView);
            viewRecipeButton = itemView.findViewById(R.id.viewRecipeButton);
        }
    }

    //this creates a recipe row so that it can be displayed
    @Override
    @NonNull
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recipe_row, parent, false);

        return new RecipeViewHolder(view);
    }

    //gets the recipe for the current row and displays its details
    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {

        Recipe recipe = recipes.get(position);

        //puts the recipe name into the recipe row
        holder.recipeNameTextView.setText(recipe.getRecipeName());

        //counts how many ingredients have been entered for this recipe
        int ingredientCount = 0;

        if (recipe.getIngredient1() != null && !recipe.getIngredient1().isEmpty()) {
            ingredientCount++;
        }
        if (recipe.getIngredient2() != null && !recipe.getIngredient2().isEmpty()) {
            ingredientCount++;
        }
        if (recipe.getIngredient3() != null && !recipe.getIngredient3().isEmpty()) {
            ingredientCount++;
        }
        if (recipe.getIngredient4() != null && !recipe.getIngredient4().isEmpty()) {
            ingredientCount++;
        }
        if (recipe.getIngredient5() != null && !recipe.getIngredient5().isEmpty()) {
            ingredientCount++;
        }
        if (recipe.getIngredient6() != null && !recipe.getIngredient6().isEmpty()) {
            ingredientCount++;
        }
        if (recipe.getIngredient7() != null && !recipe.getIngredient7().isEmpty()) {
            ingredientCount++;
        }
        if (recipe.getIngredient8() != null && !recipe.getIngredient8().isEmpty()) {
            ingredientCount++;
        }
        if (recipe.getIngredient9() != null && !recipe.getIngredient9().isEmpty()) {
            ingredientCount++;
        }
        if (recipe.getIngredient10() != null && !recipe.getIngredient10().isEmpty()) {
            ingredientCount++;
        }

        holder.recipeIngredientCountTextView.setText(ingredientCount + " ingredients");

        //ingredient matching will be added next
        holder.ingredientMatchTextView.setText("");
    }

    //tells RecyclerView how many recipe rows to display
    @Override
    public int getItemCount() {
        return recipes.size();
    }

    //updates the recipe list with the recipes that need to be displayed
    public void updateRecipeList(ArrayList<Recipe> foundRecipes) {
        recipes = foundRecipes;
        notifyDataSetChanged();
    }
}