//package
package com.mobile_app.smartpantrymanager;

//my imports needed to run application
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.view.View;

//this class manages the main pantry screen
public class MainActivity extends AppCompatActivity {

    private RecyclerView ingredientDisplay;
    private TextView noIngredientsText;
    private PantryDataSource pantryDataSource;
    private ArrayList<Ingredient> ingredientList;
    private IngredientAdapter ingredientAdapter;

    //search fields for pantry ingredients search
    private EditText searchIngredients;
    private ImageView searchIngredientsButton;
    private Button addIngredientButton;

    private LinearLayout recipesNavigation;
    private LinearLayout settingsNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // to find the views
        searchIngredients = findViewById(R.id.searchIngredients);
        searchIngredientsButton = findViewById(R.id.searchIngredientsButton);
        ingredientDisplay = findViewById(R.id.ingredientDisplay);
        noIngredientsText = findViewById(R.id.noIngredientsText);
        addIngredientButton = findViewById(R.id.addIngredientButton);
        recipesNavigation = findViewById(R.id.recipesNavigation);
        settingsNavigation = findViewById(R.id.settingsNavigation);

        //gets the saved pantry ingredients
        pantryDataSource = new PantryDataSource(this);
        pantryDataSource.open();
        ingredientList = pantryDataSource.getAllIngredients();

        ingredientAdapter = new IngredientAdapter(ingredientList, MainActivity.this);
        ingredientDisplay.setLayoutManager(new LinearLayoutManager(this));
        ingredientDisplay.setAdapter(ingredientAdapter);

        updateEmptyPantryMessage();

        //This is for the search /add buttons and navigation
        searchIngredientsButton.setOnClickListener(view -> {
            String searchWords = searchIngredients.getText().toString().trim().toLowerCase();
            ArrayList<Ingredient> foundIngredients = new ArrayList<>();

            for (Ingredient ingredient : ingredientList) {
                if (ingredient.getIngredientName().toLowerCase().contains(searchWords)) {
                    foundIngredients.add(ingredient);
                }
            }

            ingredientAdapter.updateIngredientList(foundIngredients);
        });

        addIngredientButton.setOnClickListener(view -> {
            Intent toAddIngredient = new Intent(MainActivity.this, AddEditIngredientActivity.class);
            startActivity(toAddIngredient);
        });

        recipesNavigation.setOnClickListener(view -> {
            Intent toSuggestedRecipes = new Intent(MainActivity.this, RecipesActivity.class);
            startActivity(toSuggestedRecipes);
        });

        settingsNavigation.setOnClickListener(view -> {
            Intent toSettings = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(toSettings);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //shows no items message when pantry is empty and tells user to add ingredients to show
    private void updateEmptyPantryMessage() {
        if (ingredientList.isEmpty()) {
            noIngredientsText.setVisibility(View.VISIBLE);
            ingredientDisplay.setVisibility(View.GONE);
        } else {
            noIngredientsText.setVisibility(View.GONE);
            ingredientDisplay.setVisibility(View.VISIBLE);
        }
    }

    //used after the last ingredient is deleted
    public void showEmptyPantry() {
        noIngredientsText.setVisibility(View.VISIBLE);
        ingredientDisplay.setVisibility(View.GONE);
    }

    //this is for refreshing pantry when user goes back to pantry screen
    @Override
    protected void onResume() {
        super.onResume();

        if (pantryDataSource != null && ingredientAdapter != null) {
            ingredientList = pantryDataSource.getAllIngredients();
            ingredientAdapter.updateIngredientList(ingredientList);
            updateEmptyPantryMessage();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (pantryDataSource != null) {
            pantryDataSource.close();
        }
    }
}