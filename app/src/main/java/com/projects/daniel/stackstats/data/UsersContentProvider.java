package com.projects.daniel.stackstats.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.projects.daniel.stackstats.data.UsersDbContract.UserEntry.TABLE_NAME;

/**
 * Created by Daniel on 3/11/2018.
 */

public class UsersContentProvider extends ContentProvider {

    public static final int USERS = 100;
    public static final int USER_ID = 101;

    private UsersDbHelper usersDbHelper;

    private static final UriMatcher uriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(UsersDbContract.AUTHORITY, UsersDbContract.PATH_USERS, USERS);
        uriMatcher.addURI(UsersDbContract.AUTHORITY, UsersDbContract.PATH_USERS + "/#", USER_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        usersDbHelper = new UsersDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = usersDbHelper.getReadableDatabase();

        int match = uriMatcher.match(uri);
        Cursor retCursor;

        switch (match) {
            case USERS:
                retCursor =  db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            // Default exception
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = usersDbHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);
        Uri returnUri;
        long id;

        switch (match) {
            case USERS:
                id = db.insert(TABLE_NAME, null, values);
                if(id > 0) {
                    returnUri = ContentUris.withAppendedId(UsersDbContract.UserEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = usersDbHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);
        int tasksDeleted;

        switch (match) {
            case USER_ID:
                String id = uri.getPathSegments().get(1);
                tasksDeleted = db.delete(TABLE_NAME, "movie_id=?", new String[]{id});
                break;
            case USERS:
                tasksDeleted = db.delete(TABLE_NAME, null, null);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (tasksDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return tasksDeleted;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
