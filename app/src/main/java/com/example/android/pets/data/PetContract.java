package com.example.android.pets.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by emperator on 17/05/2017.
 */

public final class PetContract {
    public static final String CONTENT_AUTHORITY="com.example.android.pets";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String PATH_PETS="/pets";
    public static final String PATH_PETS_ID="/pets/#";

    public void PetContract(){

    }
    public static final class PetEntry implements BaseColumns{
        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of pets.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;

        public static final Uri CONTENT_URI= Uri.withAppendedPath(BASE_CONTENT_URI,PATH_PETS);
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




}
