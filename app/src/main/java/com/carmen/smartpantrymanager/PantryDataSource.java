//package
package com.carmen.smartpantrymanager;

//imports needed
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;

//this class handles access to pantry data stored in SQLite
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

    //this will add a new pantry item to the db
    //ContentValues object stores column/value pairs
    public long addPantryItem(PantryItem item){
        ContentValues cv = new ContentValues();

        cv.put(PantryDBHelper.COL2_INGREDIENT_NAME,
                item.getIngredientName());

        cv.put(PantryDBHelper.COL3_QUANTITY,
                item.getQuantity());

        cv.put(PantryDBHelper.COL4_UNIT,
                item.getUnit());

        cv.put(PantryDBHelper.COL5_EXPIRY_DATE,
                item.getExpiryDate());

        return database.insert(
                PantryDBHelper.PANTRY_TABLE_NAME,
                null,
                cv);

    }
    //method to get all pantry items from the database
    public ArrayList<PantryItem> getAllPantryItems() {

        ArrayList<PantryItem> pantryItems = new ArrayList<>();

        //selects all pantry items and sorts them by ingredient name
        String query = "SELECT * FROM " +
                PantryDBHelper.PANTRY_TABLE_NAME +
                " ORDER BY " +
                PantryDBHelper.COL2_INGREDIENT_NAME + " ASC";

        //runs the query and stores the results in a cursor
        Cursor cursor = database.rawQuery(query, null);

        //reads each row returned by the cursor
        if (cursor.moveToFirst()) {
            do {
                PantryItem item = new PantryItem();

                item.setId(cursor.getInt(
                        cursor.getColumnIndexOrThrow(PantryDBHelper.COL1_ID)));

                item.setIngredientName(cursor.getString(
                        cursor.getColumnIndexOrThrow(PantryDBHelper.COL2_INGREDIENT_NAME)));

                item.setQuantity(cursor.getDouble(
                        cursor.getColumnIndexOrThrow(PantryDBHelper.COL3_QUANTITY)));

                item.setUnit(cursor.getString(
                        cursor.getColumnIndexOrThrow(PantryDBHelper.COL4_UNIT)));

                item.setExpiryDate(cursor.getString(
                        cursor.getColumnIndexOrThrow(PantryDBHelper.COL5_EXPIRY_DATE)));

                pantryItems.add(item);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return pantryItems;
    }
    //this method is to update an existing pantry item using the PantryItem object
    public boolean updatePantryItem(PantryItem item) {
        ContentValues cv = new ContentValues();

        cv.put(PantryDBHelper.COL2_INGREDIENT_NAME,
                item.getIngredientName());

        cv.put(PantryDBHelper.COL3_QUANTITY,
                item.getQuantity());

        cv.put(PantryDBHelper.COL4_UNIT,
                item.getUnit());

        cv.put(PantryDBHelper.COL5_EXPIRY_DATE,
                item.getExpiryDate());

        int rowsUpdated = database.update(
                PantryDBHelper.PANTRY_TABLE_NAME,
                cv,
                PantryDBHelper.COL1_ID + " = ?",
                new String[]{String.valueOf(item.getId())});

        return rowsUpdated > 0;
    }

    //this method is to delete an existing pantry item using the PantryItem object
    public boolean deletePantryItem(PantryItem item) {

        int rowsDeleted = database.delete(
                PantryDBHelper.PANTRY_TABLE_NAME,
                PantryDBHelper.COL1_ID + " = ?",
                new String[]{String.valueOf(item.getId())});

        return rowsDeleted > 0;
    }

}