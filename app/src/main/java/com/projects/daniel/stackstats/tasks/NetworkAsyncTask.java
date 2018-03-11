package com.projects.daniel.stackstats.tasks;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.projects.daniel.stackstats.adapters.UsersAdapter;
import com.projects.daniel.stackstats.data.UsersDbContract;
import com.projects.daniel.stackstats.data.UsersDbContract.UserEntry;
import com.projects.daniel.stackstats.models.User;
import com.projects.daniel.stackstats.utils.AfterExecution;
import com.projects.daniel.stackstats.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Daniel on 3/11/2018.
 */

public class NetworkAsyncTask extends AsyncTask<URL, Void, String> {

    private Context context;
    private ArrayList<User> users;
    private TextView errorTextView;
    private AfterExecution afterExecution;

    public NetworkAsyncTask(Context context, ArrayList<User> users, TextView errorTextView, AfterExecution afterExecution) {
        this.context = context;
        this.users = users;
        this.errorTextView = errorTextView;
        this.afterExecution = afterExecution;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(URL... urls) {
        URL url = urls[0];
        if(url == null) return null;

        String response = null;
        try {
            response = NetworkUtils.getResponseFromHttpUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(s == null || s.isEmpty()) {
            errorTextView.setVisibility(View.VISIBLE);
        } else {
            errorTextView.setVisibility(View.INVISIBLE);
            ArrayList<User> users = new ArrayList<>();
            context.getContentResolver().delete(UserEntry.CONTENT_URI, null, null);

            try {
                JSONArray jsonUsers = new JSONObject(s).getJSONArray("items");
                Gson gson = new Gson();
                for(int i = 0; i < jsonUsers.length(); i++) {
                    User user = gson.fromJson(jsonUsers.getJSONObject(i).toString(), User.class);

                    ContentValues userContent = new ContentValues();
                    userContent.put(UserEntry.COLUMN_USER_NAME, user.getName());
                    userContent.put(UserEntry.COLUMN_IMAGE_LINK, user.getProfileImageLink());
                    userContent.put(UserEntry.COLUMN_LOCATION, user.getLocation());
                    userContent.put(UserEntry.COLUMN_BRONZE_BADGES, user.getBadgeCounts().getBronze());
                    userContent.put(UserEntry.COLUMN_SILVER_BADGES, user.getBadgeCounts().getSilver());
                    userContent.put(UserEntry.COLUMN_GOLD_BADGES, user.getBadgeCounts().getGold());

                    context.getContentResolver().insert(UserEntry.CONTENT_URI, userContent);
                    users.add(user);
                }
                this.users.clear();
                this.users.addAll(users);
                afterExecution.onAfterExecution();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
