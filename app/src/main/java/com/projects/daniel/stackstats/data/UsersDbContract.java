package com.projects.daniel.stackstats.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Daniel on 3/11/2018.
 */

public class UsersDbContract {

    public static final String AUTHORITY = "com.projects.daniel.stackstats";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_USERS = "users";

    public static final class UserEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendEncodedPath(PATH_USERS).build();
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_USER_NAME = "user_name";
        public static final String COLUMN_IMAGE_LINK = "image_link";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_GOLD_BADGES = "gold_badges";
        public static final String COLUMN_SILVER_BADGES = "silver_badges";
        public static final String COLUMN_BRONZE_BADGES = "bronze_badges";
    }
}
