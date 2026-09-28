package com.mobile_app.smartpantrymanager;

import android.os.Bundle;
import android.content.Intent;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SuggestedRecipesActivity extends AppCompatActivity {

    //attributes for the bottom navigation
    private LinearLayout pantryNavigation;
    private LinearLayout settingsNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_suggested_recipes);

        //connects the bottom navigation to the layout
        pantryNavigation = findViewById(R.id.pantryNavigation);
        settingsNavigation = findViewById(R.id.settingsNavigation);

        //opens the My Pantry screen
        pantryNavigation.setOnClickListener(view -> {
            Intent pantryIntent =
                    new Intent(SuggestedRecipesActivity.this, MainActivity.class);
            startActivity(pantryIntent);
        });

        //opens the Settings screen
        settingsNavigation.setOnClickListener(view -> {
            Intent settingsIntent =
                    new Intent(SuggestedRecipesActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}