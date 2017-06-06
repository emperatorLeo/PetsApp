package com.example.android.pets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.pets.data.PetContract.PetEntry;
/**
 * Created by emperator on 05/06/2017.
 */

public class PetDbHelper extends SQLiteOpenHelper {

    private static final String DATA_BASE_NAME="Shelter.db";
    private static final int DATA_BASE_VERSION = 1;

    public PetDbHelper(Context context){
        super(context,DATA_BASE_NAME,null,DATA_BASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String  SQL_CREATE_PETS_TABLE = "CREATE TABLE " + PetEntry.TABLE_NAME + "("
                + PetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PetEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + PetEntry.COLUMN_BREED + " TEXT, "
                + PetEntry.COLUMN_GENDER + " INTEGER NOT NULL, "
                + PetEntry.COLUMN_WEIGHT + " INTEGER NOT NULL DEFAULT 0);";

       db.execSQL(SQL_CREATE_PETS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase Db, int i, int i1) {

    }
}
