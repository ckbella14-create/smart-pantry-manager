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

//this class manages adding and editing pantry ingredients
public class AddEditIngredientActivity extends AppCompatActivity {

    private EditText enterIngredientName;
    private EditText enterQuantity;
    private Spinner unitDropDown;
    private EditText enterOtherUnit;
    private EditText enterExpiryDate;
    private Button saveIngredientBtn;

    //used when an existing ingredient is being edited
    private int ingredientId = -1;
    private boolean editingIngredient = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        // used to find the views by the ids created in the xml layout
        enterIngredientName = findViewById(R.id.enterIngredientName);
        enterQuantity = findViewById(R.id.enterQuantity);
        unitDropDown = findViewById(R.id.unitDropDown);
        enterOtherUnit = findViewById(R.id.enterOtherUnit);
        enterExpiryDate = findViewById(R.id.enterExpiryDate);
        saveIngredientBtn = findViewById(R.id.saveIngredientBtn);

        //this gets the measurement units and puts them into the unit dropdown
        ArrayAdapter<CharSequence> unitDropDownAdapter = ArrayAdapter.createFromResource(
                this, R.array.measurement_units, android.R.layout.simple_spinner_item);
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

        //shows a field if the user selects Other as the unit
        unitDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
            Calendar today = Calendar.getInstance();
            int year = today.get(Calendar.YEAR);
            int month = today.get(Calendar.MONTH);
            int day = today.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AddEditIngredientActivity.this,
                    (datePicker, selectedYear, selectedMonth, selectedDay) -> {
                        //month starts at 0 in Android so a 1 is added
                        String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        enterExpiryDate.setText(selectedDate);
                    }, year, month, day);

            datePickerDialog.show();
        });

        //saves the ingredient when the save button is clicked
        saveIngredientBtn.setOnClickListener(view -> {
            String ingredientName = enterIngredientName.getText().toString().trim();
            String quantityText = enterQuantity.getText().toString().trim();
            String expiryDate = enterExpiryDate.getText().toString().trim();
            String selectedUnit = unitDropDown.getSelectedItem().toString();

            if (ingredientName.isEmpty()) {
                enterIngredientName.setError("Enter the name of ingredient");
                return;
            }

            if (quantityText.isEmpty()) {
                enterQuantity.setError("Enter the quantity");
                return;
            }

            if (selectedUnit.equals("Select unit")) {
                Toast.makeText(this, "Please select a unit", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedUnit.equals("Other")) {
                selectedUnit = enterOtherUnit.getText().toString().trim();

                if (selectedUnit.isEmpty()) {
                    enterOtherUnit.setError("Enter a unit");
                    return;
                }
            }

            double quantity = Double.parseDouble(quantityText);

            Ingredient newIngredient = new Ingredient();
            newIngredient.setIngredientName(ingredientName);
            newIngredient.setQuantity(quantity);
            newIngredient.setUnit(selectedUnit);
            newIngredient.setExpiryDate(expiryDate);

            PantryDataSource pantryDataSource = new PantryDataSource(AddEditIngredientActivity.this);
            pantryDataSource.open();

            //updates the ingredient if this screen was opened from edit
            if (editingIngredient) {
                newIngredient.setId(ingredientId);
                boolean updated = pantryDataSource.updateIngredient(newIngredient);
                pantryDataSource.close();

                if (updated) {
                    Toast.makeText(this, "Ingredient updated!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } else {
                long result = pantryDataSource.addIngredient(newIngredient);
                pantryDataSource.close();

                if (result != -1) {
                    Toast.makeText(this, "Added to pantry!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}