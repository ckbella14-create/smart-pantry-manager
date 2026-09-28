package com.mobile_app.smartpantrymanager;

import android.os.Bundle;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.CheckBox;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsActivity extends AppCompatActivity {

    //attributes for the bottom navigation
    private LinearLayout pantryNavigation;
    private LinearLayout recipesNavigation;
    private CheckBox expiryAlertCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);

        //connects the bottom navigation to the layout and opens the other screens
        pantryNavigation = findViewById(R.id.pantryNavigation);
        recipesNavigation = findViewById(R.id.recipesNavigation);

        pantryNavigation.setOnClickListener(view -> {Intent pantryIntent = new Intent(
                SettingsActivity.this, MainActivity.class);
            startActivity(pantryIntent);
        });
        recipesNavigation.setOnClickListener(view -> {
            Intent recipesIntent = new Intent(SettingsActivity.this,
                    RecipesActivity.class);
            startActivity(recipesIntent);
        });
        //connects the expiry option to the layout
        expiryAlertCheckBox = findViewById(R.id.expiryAlertCheckBox);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}