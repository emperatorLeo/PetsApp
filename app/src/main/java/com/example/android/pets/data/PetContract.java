package com.example.android.pets.data;

import android.provider.BaseColumns;

/**
 * Created by emperator on 17/05/2017.
 */

public final class PetContract {
    public final static String TABLE_NAME="pets";
    public final static String _ID= BaseColumns._ID;
    public final static String COLUMN_NAME="name";
    public final static String COLUMN_BREED="breed";
    public final static String COLUMN_GENDER="gender";
    public final static String COLUMN_WEIGHT="weight";

    public final static int GENDER_UNKNOW = 0;
    public final static int GENDER_MALE   = 1;
    public final static int GENDER_FEMALE = 2;



}
