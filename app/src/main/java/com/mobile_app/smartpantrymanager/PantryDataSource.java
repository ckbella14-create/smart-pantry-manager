package com.mobile_app.smartpantrymanager;

//imports needed
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

//this class handles access to pantry ingredients data stored in SQLite db
public class PantryDataSource {

    //database and database helper
    private SQLiteDatabase database;
    private PantryDBHelper dbHelper;

    //constructor creates the database helper
    public PantryDataSource(Context context) {
        dbHelper = new PantryDBHelper(context);
    }

    //methods to open and close a connection to sqlite
    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    //this will add a new ingredient to the db
    //ContentValues object stores column/value pairs
    public long addIngredient(Ingredient ingredient) {

        ContentValues ingredientValues = new ContentValues();

        ingredientValues.put(PantryDBHelper.COL2_INGREDIENT_NAME,
                ingredient.getIngredientName());

        ingredientValues.put(PantryDBHelper.COL3_QUANTITY, ingredient.getQuantity());

        ingredientValues.put(PantryDBHelper.COL4_UNIT, ingredient.getUnit());

        ingredientValues.put(PantryDBHelper.COL5_EXPIRY_DATE, ingredient.getExpiryDate());

        return database.insert(PantryDBHelper.PANTRY_TABLE_NAME, null, ingredientValues);
    }

    //method to get all ingredients from the database
    public ArrayList<Ingredient> getAllIngredients() {

        ArrayList<Ingredient> ingredients = new ArrayList<>();

        //selects all ingredients and sorts them by ingredient name
        String query = "SELECT * FROM " + PantryDBHelper.PANTRY_TABLE_NAME +
                " ORDER BY " + PantryDBHelper.COL2_INGREDIENT_NAME + " ASC";

        //runs the query and stores the results in a cursor
        Cursor cursor = database.rawQuery(query, null);

        //reads each row returned by the cursor as it loops through the rows
        if (cursor.moveToFirst()) {
            do {
                Ingredient row = new Ingredient();

                row.setId(cursor.getInt(
                        cursor.getColumnIndexOrThrow(PantryDBHelper.COL1_ID)));

                row.setIngredientName(cursor.getString(
                        cursor.getColumnIndexOrThrow(PantryDBHelper.COL2_INGREDIENT_NAME)));

                row.setQuantity(cursor.getDouble(
                        cursor.getColumnIndexOrThrow(PantryDBHelper.COL3_QUANTITY)));

                row.setUnit(cursor.getString(
                        cursor.getColumnIndexOrThrow(PantryDBHelper.COL4_UNIT)));

                row.setExpiryDate(cursor.getString(
                        cursor.getColumnIndexOrThrow(PantryDBHelper.COL5_EXPIRY_DATE)));

                ingredients.add(row);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return ingredients;
    }

    //this method updates an existing ingredient by putting the changed values into the table
    public boolean updateIngredient(Ingredient ingredient) {

        ContentValues ingredientValues = new ContentValues();

        ingredientValues.put(PantryDBHelper.COL2_INGREDIENT_NAME,
                ingredient.getIngredientName());

        ingredientValues.put(PantryDBHelper.COL3_QUANTITY, ingredient.getQuantity());

        ingredientValues.put(PantryDBHelper.COL4_UNIT, ingredient.getUnit());

        ingredientValues.put(PantryDBHelper.COL5_EXPIRY_DATE, ingredient.getExpiryDate());

        int rowsUpdated = database.update(PantryDBHelper.PANTRY_TABLE_NAME,
                ingredientValues, PantryDBHelper.COL1_ID + " = ?",
                new String[]{String.valueOf(ingredient.getId())});

        return rowsUpdated > 0;
    }

    //this method deletes an existing ingredient
    public boolean deleteIngredient(Ingredient ingredient) {

        int rowsDeleted = database.delete(PantryDBHelper.PANTRY_TABLE_NAME,
                PantryDBHelper.COL1_ID + " = ?",
                new String[]{String.valueOf(ingredient.getId())});

        return rowsDeleted > 0;
    }
}