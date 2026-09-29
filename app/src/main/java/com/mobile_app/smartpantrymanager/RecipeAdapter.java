package com.mobile_app.smartpantrymanager;

//imports
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

//this class will handle connecting all the recipe data to the views in each RecyclerView row
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    //list of recipes to display
    private ArrayList<Recipe> recipes;
    private Context context;

    // the constructor
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
        Button editRecipeButton;
        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            recipeNameTextView = itemView.findViewById(R.id.recipeNameTextView);
            recipeIngredientCountTextView = itemView.findViewById(R.id.recipeIngredientCountTextView);
            ingredientMatchTextView = itemView.findViewById(R.id.ingredientMatchTextView);
            viewRecipeButton = itemView.findViewById(R.id.viewRecipeButton);
            editRecipeButton = itemView.findViewById(R.id.editRecipeButton);
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

        // this will let the user edit the recipe
        holder.editRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View editRecipe) {
            Intent editRecipeIntent = new Intent(context, AddEditRecipeActivity.class);

            editRecipeIntent.putExtra("recipeId", recipe.getId());
            editRecipeIntent.putExtra("recipeName", recipe.getRecipeName());
            editRecipeIntent.putExtra("ingredient1", recipe.getIngredient1());
            editRecipeIntent.putExtra("ingredient2", recipe.getIngredient2());
            editRecipeIntent.putExtra("ingredient3", recipe.getIngredient3());
            editRecipeIntent.putExtra("ingredient4", recipe.getIngredient4());
            editRecipeIntent.putExtra("ingredient5", recipe.getIngredient5());
            editRecipeIntent.putExtra("ingredient6", recipe.getIngredient6());
            editRecipeIntent.putExtra("ingredient7", recipe.getIngredient7());
            editRecipeIntent.putExtra("ingredient8", recipe.getIngredient8());
            editRecipeIntent.putExtra("ingredient9", recipe.getIngredient9());
            editRecipeIntent.putExtra("ingredient10", recipe.getIngredient10());
            editRecipeIntent.putExtra("instructions", recipe.getInstructions());

            context.startActivity(editRecipeIntent);

            }
        });

            //this will let the user view the recipe
        holder.viewRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewRecipe) {
                Intent viewRecipeIntent = new Intent(context, AddEditRecipeActivity.class);

                viewRecipeIntent.putExtra("recipeId", recipe.getId());
                viewRecipeIntent.putExtra("recipeName", recipe.getRecipeName());
                viewRecipeIntent.putExtra("ingredient1", recipe.getIngredient1());
                viewRecipeIntent.putExtra("ingredient2", recipe.getIngredient2());
                viewRecipeIntent.putExtra("ingredient3", recipe.getIngredient3());
                viewRecipeIntent.putExtra("ingredient4", recipe.getIngredient4());
                viewRecipeIntent.putExtra("ingredient5", recipe.getIngredient5());
                viewRecipeIntent.putExtra("ingredient6", recipe.getIngredient6());
                viewRecipeIntent.putExtra("ingredient7", recipe.getIngredient7());
                viewRecipeIntent.putExtra("ingredient8", recipe.getIngredient8());
                viewRecipeIntent.putExtra("ingredient9", recipe.getIngredient9());
                viewRecipeIntent.putExtra("ingredient10", recipe.getIngredient10());
                viewRecipeIntent.putExtra("instructions", recipe.getInstructions());
                viewRecipeIntent.putExtra("viewOnly", true);

                context.startActivity(viewRecipeIntent);
            }
        });
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