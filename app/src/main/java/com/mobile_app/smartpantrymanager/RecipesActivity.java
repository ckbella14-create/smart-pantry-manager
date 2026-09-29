package com.mobile_app.smartpantrymanager;

import android.os.Bundle;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RecipesActivity extends AppCompatActivity {

    //attributes for the bottom navigation and the button
    private LinearLayout pantryNavigation;
    private LinearLayout settingsNavigation;
    private Button addRecipeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipes);

        //connects the Add Recipe button from the layout to this activity
        addRecipeButton = findViewById(R.id.addRecipeButton);

        //connects the bottom navigation to the layout and the other screens
        pantryNavigation = findViewById(R.id.pantryNavigation);
        settingsNavigation = findViewById(R.id.settingsNavigation);
        addRecipeButton.setOnClickListener(view -> {
            Intent addRecipeIntent = new Intent(RecipesActivity.this, AddEditRecipeActivity.class);
            startActivity(addRecipeIntent);
        });
        settingsNavigation.setOnClickListener(view -> {
            Intent settingsIntent = new Intent(RecipesActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}