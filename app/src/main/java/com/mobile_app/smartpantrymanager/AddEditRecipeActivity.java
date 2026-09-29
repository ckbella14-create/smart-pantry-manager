package com.mobile_app.smartpantrymanager;

//imports needed
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//this class is a screen and uses activity_add_edit_recipe.xml design
public class AddEditRecipeActivity extends AppCompatActivity {

    //attributes in the order of what's on the form
    private EditText enterRecipeName;
    private EditText enterIngredient1;
    private EditText enterIngredient2;
    private EditText enterIngredient3;
    private EditText enterIngredient4;
    private EditText enterIngredient5;
    private EditText enterIngredient6;
    private EditText enterIngredient7;
    private EditText enterIngredient8;
    private EditText enterIngredient9;
    private EditText enterIngredient10;
    private EditText enterInstructions;

    //for the save recipe button
    private Button saveRecipeButton;

    //used to identify an existing recipe when editing
    private int recipeId = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_recipe);

        //saves the recipe details
        enterRecipeName = findViewById(R.id.enterRecipeName);
        enterIngredient1 = findViewById(R.id.enterIngredient1);
        enterIngredient2 = findViewById(R.id.enterIngredient2);
        enterIngredient3 = findViewById(R.id.enterIngredient3);
        enterIngredient4 = findViewById(R.id.enterIngredient4);
        enterIngredient5 = findViewById(R.id.enterIngredient5);
        enterIngredient6 = findViewById(R.id.enterIngredient6);
        enterIngredient7 = findViewById(R.id.enterIngredient7);
        enterIngredient8 = findViewById(R.id.enterIngredient8);
        enterIngredient9 = findViewById(R.id.enterIngredient9);
        enterIngredient10 = findViewById(R.id.enterIngredient10);
        enterInstructions = findViewById(R.id.enterInstructions);
        saveRecipeButton = findViewById(R.id.saveRecipeButton);

        //checks if an existing recipe was selected for editing
        if (getIntent().hasExtra("recipeId")) {
            recipeId = getIntent().getIntExtra("recipeId", -1);

            String recipeName = getIntent().getStringExtra("recipeName");
            String ingredient1 = getIntent().getStringExtra("ingredient1");
            String ingredient2 = getIntent().getStringExtra("ingredient2");
            String ingredient3 = getIntent().getStringExtra("ingredient3");
            String ingredient4 = getIntent().getStringExtra("ingredient4");
            String ingredient5 = getIntent().getStringExtra("ingredient5");
            String ingredient6 = getIntent().getStringExtra("ingredient6");
            String ingredient7 = getIntent().getStringExtra("ingredient7");
            String ingredient8 = getIntent().getStringExtra("ingredient8");
            String ingredient9 = getIntent().getStringExtra("ingredient9");
            String ingredient10 = getIntent().getStringExtra("ingredient10");
            String instructions = getIntent().getStringExtra("instructions");

            enterRecipeName.setText(recipeName);
            enterIngredient1.setText(ingredient1);
            enterIngredient2.setText(ingredient2);
            enterIngredient3.setText(ingredient3);
            enterIngredient4.setText(ingredient4);
            enterIngredient5.setText(ingredient5);
            enterIngredient6.setText(ingredient6);
            enterIngredient7.setText(ingredient7);
            enterIngredient8.setText(ingredient8);
            enterIngredient9.setText(ingredient9);
            enterIngredient10.setText(ingredient10);
            enterInstructions.setText(instructions);
        }

        /* this method to use the same edit recipe screen but without showing all the blank fields
        and not letting the recipe be edited by accident when viewing  */

        boolean viewOnly = getIntent().getBooleanExtra("viewOnly", false);

        if (viewOnly) {
            enterRecipeName.setEnabled(false);

            if (enterIngredient1.getText().toString().trim().isEmpty()) {
                enterIngredient1.setVisibility(View.GONE);
            }
            else {
                enterIngredient1.setEnabled(false);
            }

            if (enterIngredient2.getText().toString().trim().isEmpty()) {
                enterIngredient2.setVisibility(View.GONE);
            }
            else {
                enterIngredient2.setEnabled(false);
            }

            if (enterIngredient3.getText().toString().trim().isEmpty()) {
                enterIngredient3.setVisibility(View.GONE);
            }
            else {
                enterIngredient3.setEnabled(false);
            }

            if (enterIngredient4.getText().toString().trim().isEmpty()) {
                enterIngredient4.setVisibility(View.GONE);
            }
            else {
                enterIngredient4.setEnabled(false);
            }

            if (enterIngredient5.getText().toString().trim().isEmpty()) {
                enterIngredient5.setVisibility(View.GONE);
            }
            else {
                enterIngredient5.setEnabled(false);
            }

            if (enterIngredient6.getText().toString().trim().isEmpty()) {
                enterIngredient6.setVisibility(View.GONE);
            }
            else {
                enterIngredient6.setEnabled(false);
            }

            if (enterIngredient7.getText().toString().trim().isEmpty()) {
                enterIngredient7.setVisibility(View.GONE);
            }
            else {
                enterIngredient7.setEnabled(false);
            }

            if (enterIngredient8.getText().toString().trim().isEmpty()) {
                enterIngredient8.setVisibility(View.GONE);
            }
            else {
                enterIngredient8.setEnabled(false);
            }

            if (enterIngredient9.getText().toString().trim().isEmpty()) {
                enterIngredient9.setVisibility(View.GONE);
            }
            else {
                enterIngredient9.setEnabled(false);
            }

            if (enterIngredient10.getText().toString().trim().isEmpty()) {
                enterIngredient10.setVisibility(View.GONE);
            }
            else
            {
                enterIngredient10.setEnabled(false);
            }

            enterInstructions.setEnabled(false);
            saveRecipeButton.setVisibility(View.GONE);
        }

        //saves the recipe when the save button is clicked
        saveRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String recipeName = enterRecipeName.getText().toString().trim();
                String ingredient1 = enterIngredient1.getText().toString().trim();
                String ingredient2 = enterIngredient2.getText().toString().trim();
                String ingredient3 = enterIngredient3.getText().toString().trim();
                String ingredient4 = enterIngredient4.getText().toString().trim();
                String ingredient5 = enterIngredient5.getText().toString().trim();
                String ingredient6 = enterIngredient6.getText().toString().trim();
                String ingredient7 = enterIngredient7.getText().toString().trim();
                String ingredient8 = enterIngredient8.getText().toString().trim();
                String ingredient9 = enterIngredient9.getText().toString().trim();
                String ingredient10 = enterIngredient10.getText().toString().trim();
                String instructions = enterInstructions.getText().toString().trim();

                //checks that a recipe name was entered
                if (recipeName.isEmpty()) {
                    enterRecipeName.setError("Recipe name needed.");
                    return;
                }

                //creates a new Recipe object
                Recipe newRecipe = new Recipe();
                newRecipe.setRecipeName(recipeName);
                newRecipe.setIngredient1(ingredient1);
                newRecipe.setIngredient2(ingredient2);
                newRecipe.setIngredient3(ingredient3);
                newRecipe.setIngredient4(ingredient4);
                newRecipe.setIngredient5(ingredient5);
                newRecipe.setIngredient6(ingredient6);
                newRecipe.setIngredient7(ingredient7);
                newRecipe.setIngredient8(ingredient8);
                newRecipe.setIngredient9(ingredient9);
                newRecipe.setIngredient10(ingredient10);
                newRecipe.setInstructions(instructions);

                //opens the database connection
                RecipesDataSource recipesDataSource = new RecipesDataSource(AddEditRecipeActivity.this);
                recipesDataSource.open();

                //checks to see if there is an actual recipe id and if there is one, it edits it
                if (recipeId != -1) {
                    newRecipe.setId(recipeId);
                    boolean updated = recipesDataSource.updateRecipe(newRecipe);
                    recipesDataSource.close();

                    if (updated) {
                        Toast.makeText(AddEditRecipeActivity.this, "Your recipe was updated!", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                } else {
                    //adds a new recipe
                    long result = recipesDataSource.addRecipe(newRecipe);
                    recipesDataSource.close();

                    if (result != -1) {
                        Toast.makeText(AddEditRecipeActivity.this, "Added your recipe!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });
}
}