//package
package com.mobile_app.smartpantrymanager;

//imports needed
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*this class manages the pantry database and connections to the tables and inherits
the built in sqlite db function*/
public class PantryDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SmartPantry.db";
    //as per study guide, this lets us track changes to db structure
    private static final int DATABASE_VERSION = 3;

    //columns of the pantry table
    public static final String PANTRY_TABLE_NAME = "PantryItems";
    public static final String COL1_ID = "_id";
    public static final String COL2_INGREDIENT_NAME = "ingredient_name";
    public static final String COL3_QUANTITY = "quantity";
    public static final String COL4_UNIT = "unit";
    public static final String COL5_EXPIRY_DATE = "expiry_date";


    //columns of the recipes table to store recipes
    public static final String RECIPE_TABLE_NAME = "Recipes";
    public static final String RECIPE_COL1_ID = "_id";
    public static final String RECIPE_COL2_NAME = "recipe_name";
    public static final String RECIPE_COL3_INGREDIENT1 = "ingredient1";
    public static final String RECIPE_COL4_INGREDIENT2 = "ingredient2";
    public static final String RECIPE_COL5_INGREDIENT3 = "ingredient3";
    public static final String RECIPE_COL6_INGREDIENT4 = "ingredient4";
    public static final String RECIPE_COL7_INGREDIENT5 = "ingredient5";
    public static final String RECIPE_COL8_INGREDIENT6 = "ingredient6";
    public static final String RECIPE_COL9_INGREDIENT7 = "ingredient7";
    public static final String RECIPE_COL10_INGREDIENT8 = "ingredient8";
    public static final String RECIPE_COL11_INGREDIENT9 = "ingredient9";
    public static final String RECIPE_COL12_INGREDIENT10 = "ingredient10";
    public static final String RECIPE_COL13_INSTRUCTIONS = "instructions";

    //sql statement to create the pantry table
    private static final String CREATE_PANTRY_TABLE =
            "CREATE TABLE " + PANTRY_TABLE_NAME + "(" +
                    COL1_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL2_INGREDIENT_NAME + " TEXT NOT NULL, " +
                    COL3_QUANTITY + " REAL NOT NULL, " +
                    COL4_UNIT + " TEXT NOT NULL, " +
                    COL5_EXPIRY_DATE + " TEXT " +")";

    //sql statement to create the recipes table with the max 10 ingredient fields
    private static final String CREATE_RECIPE_TABLE =
            "CREATE TABLE " + RECIPE_TABLE_NAME + "(" +
                    RECIPE_COL1_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    RECIPE_COL2_NAME + " TEXT NOT NULL, " +
                    RECIPE_COL3_INGREDIENT1 + " TEXT, " +
                    RECIPE_COL4_INGREDIENT2 + " TEXT, " +
                    RECIPE_COL5_INGREDIENT3 + " TEXT, " +
                    RECIPE_COL6_INGREDIENT4 + " TEXT, " +
                    RECIPE_COL7_INGREDIENT5 + " TEXT, " +
                    RECIPE_COL8_INGREDIENT6 + " TEXT, " +
                    RECIPE_COL9_INGREDIENT7 + " TEXT, " +
                    RECIPE_COL10_INGREDIENT8 + " TEXT, " +
                    RECIPE_COL11_INGREDIENT9 + " TEXT, " +
                    RECIPE_COL12_INGREDIENT10 + " TEXT, " +
                    RECIPE_COL13_INSTRUCTIONS + " TEXT" + ")";

    //this passes db details to sqliteopenhelper parent class
    public PantryDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //overrides android's onCreate method to create a database so that both tables are created
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PANTRY_TABLE);
        db.execSQL(CREATE_RECIPE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 3) {
            db.execSQL(CREATE_RECIPE_TABLE);
        }
    }
}