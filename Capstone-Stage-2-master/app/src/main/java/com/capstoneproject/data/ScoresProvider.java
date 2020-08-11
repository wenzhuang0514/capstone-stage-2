package com.capstoneproject.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class ScoresProvider extends ContentProvider {

    static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int SCORE_LIST = 1;
    static final String LOG_TAG = "Database";

    static {
        sUriMatcher.addURI(ScoreContract.AUTHORITY, "scores", SCORE_LIST);
    }

    ScoreDBHelper DBHelper;
    SQLiteDatabase database;

    public ScoresProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int count = 0;
        count = database.delete(ScoreContract.ScoreEntry.TABLE_NAME, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        return null;
        // throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        Uri returnUri;
        long _id = database.insert(ScoreContract.ScoreEntry.TABLE_NAME, null, values);
        Log.v(LOG_TAG, "Inserted - id = " + _id);
        if (_id > 0) {
            returnUri = ContentUris.withAppendedId(ScoreContract.CONTENT_URI, _id);
            getContext().getContentResolver().notifyChange(returnUri, null);
            return returnUri;
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        DBHelper = new ScoreDBHelper(getContext());
        database = DBHelper.getWritableDatabase();
        return true;

    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        Cursor retCursor;
        if (sortOrder == null) sortOrder = ScoreContract.ScoreEntry.COLUMN_ID;
        retCursor = database.query(
                ScoreContract.ScoreEntry.TABLE_NAME, projection, selection,
                selectionArgs, null, null, sortOrder);

        //throw new UnsupportedOperationException("Not yet implemented");
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not need to be implemented");
    }

}
