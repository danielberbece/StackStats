package com.projects.daniel.stackstats.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.projects.daniel.stackstats.data.UsersDbContract.UserEntry;

/**
 * Created by Daniel on 3/11/2018.
 */

public class UsersDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "usersDb.db";

    private static final int VERSION = 1;

    public UsersDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE "  + UserEntry.TABLE_NAME + " (" +
                UserEntry._ID                   + " INTEGER PRIMARY KEY, " +
                UserEntry.COLUMN_USER_NAME      + " TEXT NOT NULL, " +
                UserEntry.COLUMN_IMAGE_LINK     + " TEXT NOT NULL, " +
                UserEntry.COLUMN_LOCATION       + " TEXT NOT NULL, " +
                UserEntry.COLUMN_BRONZE_BADGES  + " INTEGER NOT NULL, " +
                UserEntry.COLUMN_SILVER_BADGES  + " INTEGER NOT NULL, " +
                UserEntry.COLUMN_GOLD_BADGES    + " INTEGER NOT NULL" +
                ");";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME);
        onCreate(db);
    }
}
