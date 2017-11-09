package com.example.shenhaichen.bakingapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shenhaichen on 09/11/2017.
 */

public class BakingDBHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "baking.db";

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 1;

    public BakingDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_BAKING_TABLE = "create table baking ( " +
                DataBaseConstract.BAKING_ID + " integer primary key AUTOINCREMENT, " +
                DataBaseConstract.BAKING_NAME + " text, " +
//                DataBaseConstract.BAKING_INGREDIENTS + " integer, " +
//                DataBaseConstract.BAKING_STEPS + " integer, " +
                DataBaseConstract.BAKING_SERVINGS + " integer, " +
                DataBaseConstract.BAKING_IMAGE + " text);";

        final String SQL_CREATE_INGREDIENTS_TABLE = "create table ingredients ( " +
                DataBaseConstract.INGREDIENTS_ID + " integer primary key, " +
                DataBaseConstract.BAKING_ID + " integer , " +
                DataBaseConstract.INGREDIENTS_QUANTITY + " integer, " +
                DataBaseConstract.INGREDIENTS_MEASURE + " text, " +
                DataBaseConstract.INGREDIENTS_INGREDIENT + " text);";

        final String SQL_CREATE_STEPS_TABLE = "create table steps ( " +
                DataBaseConstract.STEPS_ID + " integer primary key, " +
                DataBaseConstract.BAKING_ID + " integer, " +
                DataBaseConstract.STEPS_SHORT_DESCRIPTION + " text, " +
                DataBaseConstract.STEPS_DESCRIPTION + " text, " +
                DataBaseConstract.STEPS_VIDEOURL + " text, " +
                DataBaseConstract.STEPS_THUMBNAILURL + " text);";

        db.execSQL(SQL_CREATE_BAKING_TABLE);
        db.execSQL(SQL_CREATE_INGREDIENTS_TABLE);
        db.execSQL(SQL_CREATE_STEPS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
