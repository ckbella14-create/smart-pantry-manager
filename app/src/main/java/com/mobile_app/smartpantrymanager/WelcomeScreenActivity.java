package com.mobile_app.smartpantrymanager;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WelcomeScreenActivity extends AppCompatActivity {

    //button to enter the My Pantry screen and start
    private Button getStartedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome_screen);

        //connects the get started button to the layout
        getStartedButton = findViewById(R.id.getStartedButton);

        // opens the My Pantry screen when the button is clicked
        getStartedButton.setOnClickListener(view -> {
            Intent toMyPantry = new Intent(WelcomeScreenActivity.this,
                    MainActivity.class);
            startActivity(toMyPantry);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}