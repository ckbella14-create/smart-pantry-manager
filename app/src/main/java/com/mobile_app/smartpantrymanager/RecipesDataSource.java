package com.mobile_app.smartpantrymanager;

//imports needed
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

//this class handles access to recipe data stored in SQLite db
public class RecipesDataSource {

    //database and database helper
    private SQLiteDatabase database;
    private PantryDBHelper dbHelper;

    //constructor creates the database helper
    public RecipesDataSource(Context context) {
        dbHelper = new PantryDBHelper(context);
    }

    //methods to open and close a connection to sqlite
    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    //this will add a new recipe to the db
    //ContentValues object stores column/value pairs
    public long addRecipe(Recipe recipe) {

        ContentValues recipeValues = new ContentValues();

        recipeValues.put(PantryDBHelper.RECIPE_COL2_NAME, recipe.getRecipeName());
        recipeValues.put(PantryDBHelper.RECIPE_COL3_INGREDIENT1, recipe.getIngredient1());
        recipeValues.put(PantryDBHelper.RECIPE_COL4_INGREDIENT2, recipe.getIngredient2());
        recipeValues.put(PantryDBHelper.RECIPE_COL5_INGREDIENT3, recipe.getIngredient3());
        recipeValues.put(PantryDBHelper.RECIPE_COL6_INGREDIENT4, recipe.getIngredient4());
        recipeValues.put(PantryDBHelper.RECIPE_COL7_INGREDIENT5, recipe.getIngredient5());
        recipeValues.put(PantryDBHelper.RECIPE_COL8_INGREDIENT6, recipe.getIngredient6());
        recipeValues.put(PantryDBHelper.RECIPE_COL9_INGREDIENT7, recipe.getIngredient7());
        recipeValues.put(PantryDBHelper.RECIPE_COL10_INGREDIENT8, recipe.getIngredient8());
        recipeValues.put(PantryDBHelper.RECIPE_COL11_INGREDIENT9, recipe.getIngredient9());
        recipeValues.put(PantryDBHelper.RECIPE_COL12_INGREDIENT10, recipe.getIngredient10());
        recipeValues.put(PantryDBHelper.RECIPE_COL13_INSTRUCTIONS, recipe.getInstructions());

        return database.insert(PantryDBHelper.RECIPE_TABLE_NAME, null, recipeValues);
    }

    //this one gets all recipes from the database
    public ArrayList<Recipe> getAllRecipes() {

        ArrayList<Recipe> recipes = new ArrayList<>();

        //selects all recipes and sorts them by recipe name
        String query = "SELECT * FROM " + PantryDBHelper.RECIPE_TABLE_NAME +
                " ORDER BY " + PantryDBHelper.RECIPE_COL2_NAME + " ASC";

        //runs the query and stores the results in a cursor
        Cursor cursor = database.rawQuery(query, null);

        //reads each row returned by the cursor during the looping
        if (cursor.moveToFirst()) {
            do {
                Recipe row = new Recipe();
                row.setId(cursor.getInt(cursor.getColumnIndexOrThrow(PantryDBHelper.RECIPE_COL1_ID)));
                row.setRecipeName(cursor.getString(cursor.getColumnIndexOrThrow(PantryDBHelper.RECIPE_COL2_NAME)));
                row.setIngredient1(cursor.getString(cursor.getColumnIndexOrThrow(PantryDBHelper.RECIPE_COL3_INGREDIENT1)));
                row.setIngredient2(cursor.getString(cursor.getColumnIndexOrThrow(PantryDBHelper.RECIPE_COL4_INGREDIENT2)));
                row.setIngredient3(cursor.getString(cursor.getColumnIndexOrThrow(PantryDBHelper.RECIPE_COL5_INGREDIENT3)));
                row.setIngredient4(cursor.getString(cursor.getColumnIndexOrThrow(PantryDBHelper.RECIPE_COL6_INGREDIENT4)));
                row.setIngredient5(cursor.getString(cursor.getColumnIndexOrThrow(PantryDBHelper.RECIPE_COL7_INGREDIENT5)));
                row.setIngredient6(cursor.getString(cursor.getColumnIndexOrThrow(PantryDBHelper.RECIPE_COL8_INGREDIENT6)));
                row.setIngredient7(cursor.getString(cursor.getColumnIndexOrThrow(PantryDBHelper.RECIPE_COL9_INGREDIENT7)));
                row.setIngredient8(cursor.getString(cursor.getColumnIndexOrThrow(PantryDBHelper.RECIPE_COL10_INGREDIENT8)));
                row.setIngredient9(cursor.getString(cursor.getColumnIndexOrThrow(PantryDBHelper.RECIPE_COL11_INGREDIENT9)));
                row.setIngredient10(cursor.getString(cursor.getColumnIndexOrThrow(PantryDBHelper.RECIPE_COL12_INGREDIENT10)));
                row.setInstructions(cursor.getString(cursor.getColumnIndexOrThrow(PantryDBHelper.RECIPE_COL13_INSTRUCTIONS)));

                recipes.add(row);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return recipes;
    }

    //this method updates an existing recipe
    public boolean updateRecipe(Recipe recipe) {

        ContentValues recipeValues = new ContentValues();

        recipeValues.put(PantryDBHelper.RECIPE_COL2_NAME, recipe.getRecipeName());
        recipeValues.put(PantryDBHelper.RECIPE_COL3_INGREDIENT1, recipe.getIngredient1());
        recipeValues.put(PantryDBHelper.RECIPE_COL4_INGREDIENT2, recipe.getIngredient2());
        recipeValues.put(PantryDBHelper.RECIPE_COL5_INGREDIENT3, recipe.getIngredient3());
        recipeValues.put(PantryDBHelper.RECIPE_COL6_INGREDIENT4, recipe.getIngredient4());
        recipeValues.put(PantryDBHelper.RECIPE_COL7_INGREDIENT5, recipe.getIngredient5());
        recipeValues.put(PantryDBHelper.RECIPE_COL8_INGREDIENT6, recipe.getIngredient6());
        recipeValues.put(PantryDBHelper.RECIPE_COL9_INGREDIENT7, recipe.getIngredient7());
        recipeValues.put(PantryDBHelper.RECIPE_COL10_INGREDIENT8, recipe.getIngredient8());
        recipeValues.put(PantryDBHelper.RECIPE_COL11_INGREDIENT9, recipe.getIngredient9());
        recipeValues.put(PantryDBHelper.RECIPE_COL12_INGREDIENT10, recipe.getIngredient10());
        recipeValues.put(PantryDBHelper.RECIPE_COL13_INSTRUCTIONS, recipe.getInstructions());

        int rowsUpdated = database.update(PantryDBHelper.RECIPE_TABLE_NAME, recipeValues,
                PantryDBHelper.RECIPE_COL1_ID + " = ?", new String[]{String.valueOf(recipe.getId())});
        return rowsUpdated > 0;
    }
}