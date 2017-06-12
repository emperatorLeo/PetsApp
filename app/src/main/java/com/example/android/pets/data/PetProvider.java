package com.example.android.pets.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.android.pets.R;
import com.example.android.pets.data.PetContract.PetEntry;

import java.util.IllegalFormatException;

/**
 * Created by leosantana on 07/06/17.
 */

public class PetProvider extends ContentProvider {
    /** Tag for the log messages */
    public static final String LOG_TAG = PetProvider.class.getSimpleName();
    PetDbHelper helper;
    public static final int PETS = 100;
    public static final int PETS_ID = 101;

    public static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(PetContract.CONTENT_AUTHORITY,PetContract.PATH_PETS,PETS);
        sUriMatcher.addURI(PetContract.CONTENT_AUTHORITY,PetContract.PATH_PETS+"/#",PETS_ID);
    }
    /**
     * Initialize the provider and the database helper object.
     */
    @Override
    public boolean onCreate() {
         helper = new PetDbHelper(getContext());

        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase database = helper.getReadableDatabase();
        //to hold the database result
        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match){
            case PETS:
                cursor=database.query(PetEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                Log.i("PetProvider","in the case PETS");
                break;
            case PETS_ID:
                selection=PetEntry._ID+"=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor=database.query(PetEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            default: throw new IllegalArgumentException("Uri desconocida :"+uri);
        }
        return cursor;
    }

    /**
     * Insert new data into the provider with the given ContentValues.
     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case PETS:
                return insertPet(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }

    }

    /**
     * Insert a pet into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertPet(Uri uri, ContentValues values) {
        //Sanity checks to data to be inserted
        String name = values.getAsString(PetEntry.COLUMN_NAME);
        if(name.isEmpty()){
            throw new IllegalArgumentException("you have to insert the pet's name");
        }
        String breed = values.getAsString(PetEntry.COLUMN_BREED);
        if(breed.isEmpty()){
            throw new IllegalArgumentException("you have to insert the pet's breed");
        }
        String weight = values.getAsString(PetEntry.COLUMN_WEIGHT);
        if(weight == "0"){
            throw new IllegalArgumentException("you have to put some weight'value");
        }
        Log.i("checked","all fine");
        SQLiteDatabase db = helper.getWritableDatabase();
      //we obtaind the values through the contentValues and we past it to the method
        long id = db.insert(PetEntry.TABLE_NAME,null,values);
        Resources res = getContext().getResources();

        if (id == -1){
            Toast.makeText(getContext(),R.string.not_saved,Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getContext(),res.getString(R.string.saved,id),Toast.LENGTH_LONG).show();
        }
        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it

        return ContentUris.withAppendedId(uri, id);
    }
    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS:
                return updatePet(uri, contentValues, selection, selectionArgs);
            case PETS_ID:
                // For the PET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = PetEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updatePet(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updatePet(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        //Sanity checks to data to be inserted
        if(values.containsKey(PetEntry._ID)==false){
            throw new IllegalArgumentException("that Id doesn't exist");
        }
        String name = values.getAsString(PetEntry.COLUMN_NAME);
        if(name.isEmpty()){
            throw new IllegalArgumentException("you have to insert the pet's name");
        }
        String breed = values.getAsString(PetEntry.COLUMN_BREED);
        if(breed.isEmpty()){
            throw new IllegalArgumentException("you have to insert the pet's breed");
        }
        String weight = values.getAsString(PetEntry.COLUMN_WEIGHT);
        if(weight == "0"){
            throw new IllegalArgumentException("you have to put some weight'value");
        }
        SQLiteDatabase db = helper.getWritableDatabase();
        int rowsUpdated = db.update(PetEntry.TABLE_NAME,values,selection,selectionArgs);
        return rowsUpdated;
    }
    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS:
                // Delete all rows that match the selection and selection args
                return db.delete(PetEntry.TABLE_NAME, selection, selectionArgs);
            case PETS_ID:
                // Delete a single row given by the ID in the URI
                selection = PetEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return db.delete(PetEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
             }
    }

    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(Uri uri) {

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS:
                return PetEntry.CONTENT_LIST_TYPE;
            case PETS_ID:
                return PetEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

}
