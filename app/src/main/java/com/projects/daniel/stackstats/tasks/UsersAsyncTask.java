package com.projects.daniel.stackstats.tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.projects.daniel.stackstats.adapters.UsersAdapter;
import com.projects.daniel.stackstats.models.User;
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

public class UsersAsyncTask extends AsyncTask<URL, Void, String> {

    private UsersAdapter usersAdapter;
    private TextView errorTextView;

    public UsersAsyncTask(UsersAdapter usersAdapter, TextView errorTextView) {
        this.usersAdapter = usersAdapter;
        this.errorTextView = errorTextView;
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

            try {
                JSONArray jsonUsers = new JSONObject(s).getJSONArray("items");
                Gson gson = new Gson();
                for(int i = 0; i < jsonUsers.length(); i++) {

                    users.add(gson.fromJson(jsonUsers.getJSONObject(i).toString(), User.class));
                    Log.d("USER ADD", users.get(i).toString());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            usersAdapter.setUsers(users);
        }
    }
}
