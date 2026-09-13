//package
package com.carmen.smartpantrymanager;

//imports
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

//this class handles connecting all the pantry data to the views in each RecycleView row
public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {
    //list of pantry items to display
    private ArrayList<PantryItem> pantryItems;

    //constructor
    public PantryAdapter(ArrayList<PantryItem> pantryItems) {
        this.pantryItems = pantryItems;
    }
    //this class stores the views for one pantry item
    public static class PantryViewHolder extends RecyclerView.ViewHolder {
        TextView tvIngredientName;
        TextView tvQuantity;
        TextView tvExpiryDate;

        public PantryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIngredientName = itemView.findViewById(R.id.tvIngredientName);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvExpiryDate = itemView.findViewById(R.id.tvExpiryDate);
        }
    }
    //this creates a view so that it can be displayed
    @Override
    @NonNull
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pantry_item, parent, false);

        return new PantryViewHolder(view);
    }
    //this method gets the pantry item for the current row for the display
    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {

        PantryItem item = pantryItems.get(position);

        //puts the item data into the views from pantry_item
        holder.tvIngredientName.setText(item.getIngredientName());
        holder.tvQuantity.setText(
                "Quantity: " + item.getQuantity() + " " + item.getUnit());
        holder.tvExpiryDate.setText(
                "Expiry Date: " + item.getExpiryDate());
    }
    //tells RecycleViewer how many rows to display
    @Override
    public int getItemCount() {
        return pantryItems.size();
    }


}
