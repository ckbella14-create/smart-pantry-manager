//the package
package com.mobile_app.smartpantrymanager;

//imports needed
import android.os.Bundle;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

//this manages recipes screen including searching and finding recipes that match ingredients available
public class RecipesActivity extends AppCompatActivity {

    //these are attributes for the recipe list specifically
    private RecyclerView recipesListView;
    private ArrayList<Recipe> recipes;
    private RecipeAdapter recipeAdapter;
    private RecipesDataSource recipesDataSource;

    //attributes for recipe search, filtering only and shows suggestions and message for no matches
    private EditText searchRecipes;
    private ImageView searchRecipesButton;
    private LinearLayout recipeSearchContainer;
    private Button suggestedRecipesButton;
    private Button viewAllRecipesButton;
    private TextView noSuggestedRecipesText;
    private TextView suggestedRecipesText;

    // these attributes are for the bottom navigation and the button
    private LinearLayout pantryNavigation;
    private LinearLayout settingsNavigation;
    private Button addRecipeButton;

    //this overrides the built-in onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipes);

        //used to find the views by the ids created in the xml layout
        recipesListView = findViewById(R.id.recipesListView);
        searchRecipes = findViewById(R.id.searchRecipes);
        searchRecipesButton = findViewById(R.id.searchRecipesButton);
        recipeSearchContainer = findViewById(R.id.recipeSearchContainer);
        suggestedRecipesButton = findViewById(R.id.suggestedRecipesButton);
        viewAllRecipesButton = findViewById(R.id.viewAllRecipesButton);
        noSuggestedRecipesText = findViewById(R.id.noSuggestedRecipesText);
        suggestedRecipesText = findViewById(R.id.suggestedRecipesText);
        addRecipeButton = findViewById(R.id.addRecipeButton);
        pantryNavigation = findViewById(R.id.pantryNavigation);
        settingsNavigation = findViewById(R.id.settingsNavigation);

        /*gets all the saved recipes and the adapter connects the saved recipe list to the
        RecyclerView so the recipes can be displayed*/
        recipesDataSource = new RecipesDataSource(this);
        recipesDataSource.open();
        recipes = recipesDataSource.getAllRecipes();
        recipesDataSource.close();

        recipeAdapter = new RecipeAdapter(recipes, this);
        recipesListView.setLayoutManager(new LinearLayoutManager(this));
        recipesListView.setAdapter(recipeAdapter);

        //watches for the user to click and then checks the words entered for a match
        searchRecipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchWords = searchRecipes.getText().toString().trim().toLowerCase();
                ArrayList<Recipe> foundRecipes = new ArrayList<>();

                for (Recipe recipe : recipes) {
                    if (recipe.getRecipeName().toLowerCase().contains(searchWords)) {
                        foundRecipes.add(recipe);
                    }
                }
                suggestedRecipesText.setVisibility(View.GONE);
                noSuggestedRecipesText.setVisibility(View.GONE);
                recipeAdapter.updateRecipeList(foundRecipes);
            }
        });

        //shows only recipes where all ingredients are available with 100% match as per rule
        suggestedRecipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipeSearchContainer.setVisibility(View.GONE);
                searchRecipes.setText("");

                ArrayList<Recipe> suggestedRecipes = new ArrayList<>();
                PantryDataSource pantryDataSource = new PantryDataSource(RecipesActivity.this);

                pantryDataSource.open();
                ArrayList<Ingredient> foundIngredients = pantryDataSource.getAllIngredients();
                pantryDataSource.close();

                for (Recipe recipe : recipes) {
                    if (recipeMatched(recipe, foundIngredients)) {
                        suggestedRecipes.add(recipe);
                    }
                }
                suggestedRecipesText.setVisibility(View.VISIBLE);

                if (suggestedRecipes.isEmpty()) {
                    noSuggestedRecipesText.setVisibility(View.VISIBLE);
                }
                else {
                    noSuggestedRecipesText.setVisibility(View.GONE);
                }
                recipeAdapter.updateRecipeList(suggestedRecipes);
            }
        });

        /*these listeners watch for users clicking the view all, add recipe and bottom nav buttons
        and tells app what to do like hiding or making text visible */
        viewAllRecipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipeSearchContainer.setVisibility(View.VISIBLE);
                searchRecipes.setText("");
                suggestedRecipesText.setVisibility(View.GONE);
                noSuggestedRecipesText.setVisibility(View.GONE);
                recipeAdapter.updateRecipeList(recipes);
            }
        });

        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addRecipeIntent = new Intent(RecipesActivity.this,
                        AddEditRecipeActivity.class);
                startActivity(addRecipeIntent);
            }
        });

        pantryNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pantryIntent = new Intent(RecipesActivity.this,
                        MainActivity.class);
                startActivity(pantryIntent);
            }
        });

        settingsNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsIntent = new Intent(RecipesActivity.this,
                        SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //checks whether the pantry has the ingredients needed for the recipe one at a time
    private boolean recipeMatched(Recipe recipe, ArrayList<Ingredient> foundIngredients) {
        String[] ingredientsNeeded = {
                recipe.getIngredient1(), recipe.getIngredient2(), recipe.getIngredient3(),
                recipe.getIngredient4(), recipe.getIngredient5(), recipe.getIngredient6(),
                recipe.getIngredient7(), recipe.getIngredient8(), recipe.getIngredient9(),
                recipe.getIngredient10()
        };

        int numIngredients = 0;
        int numMatched = 0;

        for (String ingredientNeeded : ingredientsNeeded) {
            if (ingredientNeeded != null && !ingredientNeeded.trim().isEmpty()) {
                numIngredients++;

                if (ingredientCheck(ingredientNeeded, foundIngredients)) {
                    numMatched++;
                }
            }
        }

        if (numIngredients == 0) {
            return false;
        }

        if (numMatched == numIngredients) {
            return true;
        } else {
            return false;
        }
    }

    //checks the ingredient name, quantity and unit compared to pantry stock
    //bug fix: changed split character to comma-using a dash was time-consuming when entering ingredients
    private boolean ingredientCheck(String ingredientNeeded, ArrayList<Ingredient> foundIngredients) {
        String[] ingredientDetails = ingredientNeeded.split(",", 3);

        if (ingredientDetails.length != 3) {
            return false;
        }

        String ingredientName = ingredientDetails[0].trim();
        String quantity = ingredientDetails[1].trim();
        String unit = ingredientDetails[2].trim();

        double recipeQuantity;

        //try to parse quantity as a double as it's currently a string
        try {
            recipeQuantity = Double.parseDouble(quantity);
        } catch (NumberFormatException e) {
            return false;
        }

        for (Ingredient pantryIngredient : foundIngredients) {
            boolean nameMatches = pantryIngredient.getIngredientName().trim().equalsIgnoreCase(ingredientName);
            boolean unitMatches = pantryIngredient.getUnit().trim().equalsIgnoreCase(unit);
            boolean quantityMatches = pantryIngredient.getQuantity() >= recipeQuantity;

            if (nameMatches && unitMatches && quantityMatches) {
                return true;
            }
        }

        return false;
    }

    //displays the recipes when the user comes back, then calls recipe methods
    @Override
    protected void onResume() {
        super.onResume();

        if (recipeAdapter != null) {
            recipesDataSource.open();
            recipes = recipesDataSource.getAllRecipes();
            recipesDataSource.close();
            recipeAdapter.updateRecipeList(recipes);
            noSuggestedRecipesText.setVisibility(View.GONE);
            suggestedRecipesText.setVisibility(View.GONE);
        }
    }
}