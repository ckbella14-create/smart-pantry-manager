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

//this class manages ...
public class MainActivity extends AppCompatActivity {

    //attributes for the pantry list and add ingredients.button
    private RecyclerView ingredientDisplay;
    private TextView noIngredientsText;

    //this is used for the pantry data and the adapter to connect them
    private PantryDataSource pantryDataSource;
    private ArrayList<Ingredient> ingredientList;
    private IngredientAdapter ingredientAdapter;
    private LinearLayout recipesNavigation;
    private LinearLayout settingsNavigation;

    //search field for pantry ingredients
    private EditText searchIngredients;
    private ImageView searchIngredientsButton;
    private Button addIngredientButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        //tells Recyclerview to show pantry items in the vertical list formatted in activity main
        setContentView(R.layout.activity_main);

        ingredientDisplay = findViewById(R.id.ingredientDisplay);
        ingredientDisplay.setLayoutManager(new LinearLayoutManager(this));
        recipesNavigation = findViewById(R.id.recipesNavigation);
        settingsNavigation = findViewById(R.id.settingsNavigation);
        searchIngredients = findViewById(R.id.searchIngredients);
        searchIngredientsButton = findViewById(R.id.searchIngredientsButton);
        addIngredientButton = findViewById(R.id.addIngredientButton);
        noIngredientsText = findViewById(R.id.noIngredientsText);

        //creates open connection to the pantry database in sqlite
        pantryDataSource = new PantryDataSource(this);
        pantryDataSource.open();

        //to get all the pantry items/records
        ingredientList = pantryDataSource.getAllIngredients();

        //  this part connects the items to the Recycle view through the adapter
        ingredientAdapter = new IngredientAdapter(ingredientList, MainActivity.this);
        ingredientDisplay.setAdapter(ingredientAdapter);

        //shows message if there are no ingredients in the pantry
        updateEmptyPantryMessage();

        //searches the whole pantry for certain ingredient when search button is clicked
        searchIngredientsButton.setOnClickListener(view -> {

            String searchText = searchIngredients.getText().toString().trim().toLowerCase();
            ArrayList<Ingredient> foundIngredients = new ArrayList<>();

            // checks each ingredient to see if its name matches the search

            for (Ingredient ingredient : ingredientList) {

                if (ingredient.getIngredientName().toLowerCase().contains(searchText)) {
                    foundIngredients.add(ingredient);
                }
            }

            //this will send matching items to the adapter

            ingredientAdapter.updateIngredientList(foundIngredients);
        });

        //when user clicks button code runs
        addIngredientButton.setOnClickListener(view -> {
            Intent toAddIngredient = new Intent(MainActivity.this,
                    AddEditIngredientActivity.class);
            startActivity(toAddIngredient);
        });

        //  to open the recipes screen
        recipesNavigation.setOnClickListener(view -> {
            Intent toSuggestedRecipes = new Intent(MainActivity.this,
                    RecipesActivity.class);
            startActivity(toSuggestedRecipes);
        });

        //opens  the settings screen
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

    //refreshes pantry when returning from adding or editing an ingredient
    @Override
    protected void onResume() {
        super.onResume();

        if (pantryDataSource != null && ingredientAdapter != null) {
            ingredientList = pantryDataSource.getAllIngredients();
            ingredientAdapter.updateIngredientList(ingredientList);
            updateEmptyPantryMessage();
        }
    }

    //shows no items message when pantry is empty
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (pantryDataSource != null) {
            pantryDataSource.close();
        }
    }
}