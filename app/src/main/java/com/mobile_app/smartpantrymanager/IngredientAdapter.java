//package
package com.mobile_app.smartpantrymanager;

//imports
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

//this class handles connecting all the pantry data to the views in each RecycleView row
public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    //list of pantry items to display
    private ArrayList<Ingredient> ingredients;
    private Context context;

    //constructor
    public IngredientAdapter(ArrayList<Ingredient> ingredients, Context context) {
        this.ingredients = ingredients;
        this.context = context;
    }

    //this class stores the views for one pantry item
    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientNameTextView;
        TextView quantityTextView;
        TextView expiryDateTextView;
        Button editIngredientButton;
        Button deleteIngredientButton;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);

            ingredientNameTextView = itemView.findViewById(R.id.ingredientNameTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            expiryDateTextView = itemView.findViewById(R.id.expiryDateTextView);
            editIngredientButton = itemView.findViewById(R.id.editIngredientButton);
            deleteIngredientButton = itemView.findViewById(R.id.deleteIngredientButton);
        }
    }

    //this creates a view so that it can be displayed
    @Override
    @NonNull
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.ingredient_row, parent, false);

        return new IngredientViewHolder(view);
    }

    // this method gets the pantry item for the current row for the display
    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {

        Ingredient ingredient = ingredients.get(position);

        //puts the item data into the views from pantry_item
        holder.ingredientNameTextView.setText(ingredient.getIngredientName());

        holder.quantityTextView.setText("Quantity: " + ingredient.getQuantity() + " " +
                ingredient.getUnit());

        holder.expiryDateTextView.setText("Expiry Date: " + ingredient.getExpiryDate());

        //opens the selected ingredient so that it can be edited
        holder.editIngredientButton.setOnClickListener(view -> {

            Intent toEditIngredient = new Intent(context, AddEditIngredientActivity.class);

            toEditIngredient.putExtra("ingredientId", ingredient.getId());
            toEditIngredient.putExtra("ingredientName", ingredient.getIngredientName());
            toEditIngredient.putExtra("quantity", ingredient.getQuantity());
            toEditIngredient.putExtra("unit", ingredient.getUnit());
            toEditIngredient.putExtra("expiryDate", ingredient.getExpiryDate());

            context.startActivity(toEditIngredient);
        });

        //deletes the ingredient selected by user
        holder.deleteIngredientButton.setOnClickListener(view -> {

            PantryDataSource pantryDataSource = new PantryDataSource(context);
            pantryDataSource.open();

            boolean deleted = pantryDataSource.deleteIngredient(ingredient);

            pantryDataSource.close();

            if (deleted) {

                Toast.makeText(context, "Ingredient deleted", Toast.LENGTH_SHORT).show();

                if (ingredients.isEmpty() && context instanceof MainActivity) {
                    ((MainActivity) context).showEmptyPantry();
                }
            }
        });
    }

    // tells RecyclerViewer how many rows to display
    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    //updates the pantry list with the items that match the search exacty as required
    public void updateIngredientList(ArrayList<Ingredient> foundIngredients) {
        ingredients = foundIngredients;
        notifyDataSetChanged();
    }
}