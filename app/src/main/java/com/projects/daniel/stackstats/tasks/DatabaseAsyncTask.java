package com.projects.daniel.stackstats.tasks;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.projects.daniel.stackstats.data.UsersDbContract.UserEntry;
import com.projects.daniel.stackstats.models.BadgeCounts;
import com.projects.daniel.stackstats.models.User;
import com.projects.daniel.stackstats.utils.AfterExecution;

import java.net.URI;
import java.util.ArrayList;

/**
 * Created by Daniel on 3/11/2018.
 */

public class DatabaseAsyncTask extends AsyncTask<Void, Void, Cursor> {

    private Context context;
    private ArrayList<User> users;
    private AfterExecution afterExecution;

    public DatabaseAsyncTask(Context context, ArrayList<User> users, AfterExecution afterExecution) {
        this.context = context;
        this.users = users;
        this.afterExecution = afterExecution;
    }

    @Override
    protected Cursor doInBackground(Void... voids) {
        try {
            return context.getContentResolver().query(UserEntry.CONTENT_URI,
                    null,
                    null,
                    null,
                    null);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);
        if(cursor == null || cursor.getCount() == 0) {
            return;
        }

        users.clear();
        int usernameIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_NAME);
        int userImageIndex = cursor.getColumnIndex(UserEntry.COLUMN_IMAGE_LINK);
        int userLocationIndex = cursor.getColumnIndex(UserEntry.COLUMN_LOCATION);
        int userBronzeBadgesIndex = cursor.getColumnIndex(UserEntry.COLUMN_BRONZE_BADGES);
        int userSilverBadgesIndex = cursor.getColumnIndex(UserEntry.COLUMN_SILVER_BADGES);
        int userGoldBadgesIndex = cursor.getColumnIndex(UserEntry.COLUMN_GOLD_BADGES);

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            String username = cursor.getString(usernameIndex);
            String userImageLink = cursor.getString(userImageIndex);
            String userLocation = cursor.getString(userLocationIndex);
            int userBronzeBadges = cursor.getInt(userBronzeBadgesIndex);
            int userSilverBadges = cursor.getInt(userSilverBadgesIndex);
            int userGoldBadges = cursor.getInt(userGoldBadgesIndex);

            User user = new User(username, userImageLink, userLocation,
                    new BadgeCounts(userGoldBadges, userSilverBadges, userBronzeBadges));
            users.add(user);
        }
        cursor.close();
        afterExecution.onAfterExecution();
    }
}
