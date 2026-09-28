package com.mobile_app.smartpantrymanager;

//imports needed
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.EditText;
import android.view.View;
import android.widget.AdapterView;
import java.util.Calendar;
import android.app.DatePickerDialog;
import android.widget.Button;
import android.widget.Toast;

//this class is a screen and uses activity_add_edit_ingredient.xml design
public class AddEditIngredientActivity extends AppCompatActivity {

    //attributes in the order of what's on the form
    private EditText enterIngredientName;
    private EditText enterQuantity;

    //this one is for a unit entered by the user when Other is selected
    private EditText enterOtherUnit;
    private Spinner unitDropDown;
    private EditText enterExpiryDate;

    //for the save ingredient button
    private Button saveIngredientBtn;

    //used when an existing ingredient is being edited
    private int ingredientId = -1;
    private boolean editingIngredient = false;

    //tells program when the add button is clicked to create a new entry
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        //saves the ingredients details
        enterIngredientName = findViewById(R.id.enterIngredientName);
        enterQuantity = findViewById(R.id.enterQuantity);
        unitDropDown = findViewById(R.id.unitDropDown);
        enterOtherUnit = findViewById(R.id.enterOtherUnit);
        enterExpiryDate = findViewById(R.id.enterExpiryDate);

        saveIngredientBtn = findViewById(R.id.saveIngredientBtn);

        ArrayAdapter<CharSequence> unitDropDownAdapter =
                ArrayAdapter.createFromResource(
                        this,
                        R.array.measurement_units,
                        android.R.layout.simple_spinner_item);

        unitDropDownAdapter.setDropDownViewResource(R.layout.unit_dropdown);
        unitDropDown.setAdapter(unitDropDownAdapter);

        //checks if an existing ingredient was selected for editing
        if (getIntent().hasExtra("ingredientId")) {

            editingIngredient = true;
            ingredientId = getIntent().getIntExtra("ingredientId", -1);

            String ingredientName = getIntent().getStringExtra("ingredientName");
            double quantity = getIntent().getDoubleExtra("quantity", 0);
            String unit = getIntent().getStringExtra("unit");
            String expiryDate = getIntent().getStringExtra("expiryDate");

            enterIngredientName.setText(ingredientName);
            enterQuantity.setText(String.valueOf(quantity));
            enterExpiryDate.setText(expiryDate);

            int unitPosition = unitDropDownAdapter.getPosition(unit);

            if (unitPosition >= 0) {
                unitDropDown.setSelection(unitPosition);
            } else {
                int otherPosition = unitDropDownAdapter.getPosition("Other");
                unitDropDown.setSelection(otherPosition);
                enterOtherUnit.setVisibility(View.VISIBLE);
                enterOtherUnit.setText(unit);
            }
        }

        //listener to watch for a user clicking 'other' from the dropdown and
        //shows if selected, does nothing if it's not selected
        unitDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                String selectedUnit = parent.getItemAtPosition(position).toString();

                if (selectedUnit.equals("Other")) {
                    enterOtherUnit.setVisibility(View.VISIBLE);
                } else {
                    enterOtherUnit.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //opens a date picker when the expiry date field is clicked
        enterExpiryDate.setOnClickListener(view -> {

            //gets today's date
            Calendar today = Calendar.getInstance();

            int year = today.get(Calendar.YEAR);
            int month = today.get(Calendar.MONTH);
            int day = today.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog =
                    new DatePickerDialog(
                            AddEditIngredientActivity.this,
                            (datePicker, selectedYear, selectedMonth, selectedDay) -> {

                                //month starts at 0 in Android so a 1 is added
                                String selectedDate =
                                        selectedDay + "/" +(selectedMonth + 1) + "/"+ selectedYear;

                                //shows the selected date in the expiry date field
                                enterExpiryDate.setText(selectedDate);
                            }, year, month, day);

            datePickerDialog.show();
        });

        //saves the ingredient when the save button is clicked after cleaning the text
        saveIngredientBtn.setOnClickListener(view -> {

            String ingredientName = enterIngredientName.getText().toString().trim();
            String quantityText = enterQuantity.getText().toString().trim();
            String expiryDate = enterExpiryDate.getText().toString().trim();
            String selectedUnit = unitDropDown.getSelectedItem().toString();

            //checks that an ingredient name was entered
            if (ingredientName.isEmpty()) {
                enterIngredientName.setError("Enter the name of ingredient");
                return;
            }

            //checks that a quantity was entered
            if (quantityText.isEmpty()) {
                enterQuantity.setError("Enter the quantity");
                return;
            }

            //checks That a unit was selected and is not the default setting
            if (selectedUnit.equals("Select unit")) {
                Toast.makeText(this, "Please select a unit", Toast.LENGTH_SHORT).show();
                return;
            }

            //uses the unit entered by the user if Other was selected
            if (selectedUnit.equals("Other")) {
                selectedUnit = enterOtherUnit.getText().toString().trim();

                if (selectedUnit.isEmpty()) {
                    enterOtherUnit.setError("Enter a unit");
                    return;
                }
            }

            //converts the quantity from text to a number because getText brings string values
            double quantity = Double.parseDouble(quantityText);

            //creates a new Ingredient object
            Ingredient newIngredient = new Ingredient();

            newIngredient.setIngredientName(ingredientName);
            newIngredient.setQuantity(quantity);
            newIngredient.setUnit(selectedUnit);
            newIngredient.setExpiryDate(expiryDate);

            //This opens the database connection and saves the item
            PantryDataSource pantryDataSource =
                    new PantryDataSource(AddEditIngredientActivity.this);

            pantryDataSource.open();

            //updates the ingredient if this screen was opened from edit
            if (editingIngredient) {

                newIngredient.setId(ingredientId);

                boolean updated =
                        pantryDataSource.updateIngredient(newIngredient);

                pantryDataSource.close();

                if (updated) {
                    Toast.makeText(this,
                            "Ingredient updated!",
                            Toast.LENGTH_SHORT).show();

                    finish();
                }

            } else {

                long result = pantryDataSource.addIngredient(newIngredient);
                pantryDataSource.close();

                //checks whether the ingredient saved to the pantry
                if (result != -1) {
                    Toast.makeText(this, "Added to pantry!", Toast.LENGTH_SHORT).show();

                    enterIngredientName.setText("");
                    enterQuantity.setText("");
                    enterExpiryDate.setText("");
                    unitDropDown.setSelection(0);
                    enterOtherUnit.setText("");
                    enterOtherUnit.setVisibility(View.GONE);

                    finish();
                }
            }
        });
    }
}