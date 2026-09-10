//package
package com.carmen.smartpantrymanager;

//imports needed
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*this class manages the pantry database and connection to it, inherits
built in sqlite db function*/
public class PantryDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SmartPantry.db";
    private static final int DATABASE_VERSION = 1; //to track changes to db structure

    //columns of the pantry table
    public static final String PANTRY_TABLE_NAME = "PantryItems";

    //column constants
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_INGREDIENT_NAME = "ingredient_name";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_UNIT = "unit";
    public static final String COLUMN_EXPIRY_DATE = "expiry_date";

    //sql statement to create the pantry table with above information
    private static final String CREATE_PANTRY_TABLE =
            "CREATE TABLE " + PANTRY_TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_INGREDIENT_NAME + " TEXT NOT NULL, " +
                    COLUMN_QUANTITY + " REAL NOT NULL, " +
                    COLUMN_UNIT + " TEXT NOT NULL, " +
                    COLUMN_EXPIRY_DATE + " TEXT " + ")";

    //this passes db details to sqliteopenhelper parent class
    public PantryDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //overrides android's onCreate method to create a database table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PANTRY_TABLE);
    }

    //this is to update the db version if changes are made
    // by creating a temp table to store the existing pantry data first
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(
                "CREATE TEMPORARY TABLE pantry_backup AS " + "SELECT " +
                        COLUMN_ID + ", " +
                        COLUMN_INGREDIENT_NAME + ", " +
                        COLUMN_QUANTITY + ", " +
                        COLUMN_UNIT + ", " +
                        COLUMN_EXPIRY_DATE +
                        " FROM " + PANTRY_TABLE_NAME );

        // this deletes the old pantry table, creates new table and inserts old data
        db.execSQL("DROP TABLE IF EXISTS " + PANTRY_TABLE_NAME);
        onCreate(db);

        db.execSQL(
                "INSERT INTO " + PANTRY_TABLE_NAME + " (" +
                        COLUMN_ID + ", " +
                        COLUMN_INGREDIENT_NAME + ", " +
                        COLUMN_QUANTITY + ", " +
                        COLUMN_UNIT + ", " +
                        COLUMN_EXPIRY_DATE + ") " +
                        "SELECT " + COLUMN_ID + ", " +
                        COLUMN_INGREDIENT_NAME + ", " +
                        COLUMN_QUANTITY + ", " +
                        COLUMN_UNIT + ", " +
                        COLUMN_EXPIRY_DATE + " " +
                        "FROM pantry_backup"
        );

        // delete the temporary backup table
        db.execSQL("DROP TABLE pantry_backup");
    }
}