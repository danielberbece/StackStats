package com.projects.daniel.stackstats;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.projects.daniel.stackstats.adapters.UsersAdapter;
import com.projects.daniel.stackstats.models.User;
import com.projects.daniel.stackstats.tasks.DatabaseAsyncTask;
import com.projects.daniel.stackstats.tasks.NetworkAsyncTask;
import com.projects.daniel.stackstats.utils.AfterExecution;
import com.projects.daniel.stackstats.utils.NetworkUtils;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AfterExecution{

    private long lastUpdate = 0;
    private long halfHour = 1800000;
    private long tenMinutes = 600000;
    public static final String LAST_UPDATE_KEY = "last_update";
    public static final String SAVED_USERS_KEY = "users_key";
    @BindView(R.id.users_recycler_view) RecyclerView mUsersRecyclerView;
    private ArrayList<User> mUsersList;
    @BindView(R.id.error_text_view)
    TextView errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ArrayList<Parcelable> parcelUsers = new ArrayList<>();
        lastUpdate = 0;
        if(savedInstanceState != null) {
            if(savedInstanceState.containsKey(LAST_UPDATE_KEY)) {
                lastUpdate = savedInstanceState.getLong(LAST_UPDATE_KEY);
                parcelUsers = savedInstanceState.getParcelableArrayList(SAVED_USERS_KEY);
            }
        }

        mUsersList = new ArrayList<>();
        getData(mUsersList, lastUpdate, parcelUsers);

        mUsersRecyclerView.setAdapter(new UsersAdapter(this, mUsersList));
        mUsersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getData(ArrayList<User> mUsersList, long lastUpdate, ArrayList<Parcelable> parcelUsers) {
        if(System.currentTimeMillis() - lastUpdate <= tenMinutes) {
            for(Parcelable parcelUser : parcelUsers) {
                mUsersList.add((User) Parcels.unwrap(parcelUser));
            }
            Log.d("RAM UPDATE", "yes");

        } else if(System.currentTimeMillis() - lastUpdate <= halfHour) {
            new DatabaseAsyncTask(this, mUsersList, this).execute();
            Log.d("DISK UPDATE", "yes");
        } else {
            new NetworkAsyncTask(this, mUsersList, errorTextView,this)
                    .execute(NetworkUtils.getUsersQueryUrl());
            Log.d("NETWORK UPDATE", "YES");
            this.lastUpdate = System.currentTimeMillis();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(LAST_UPDATE_KEY, lastUpdate);
        ArrayList<Parcelable> arrayList= new ArrayList<>();
        for(User user : mUsersList) {
            arrayList.add(Parcels.wrap(user));
        }

        outState.putParcelableArrayList(SAVED_USERS_KEY, arrayList);
    }

    @Override
    public void onAfterExecution() {
        mUsersRecyclerView.getAdapter().notifyDataSetChanged();
        if(mUsersList.isEmpty()) {
            errorTextView.setVisibility(View.VISIBLE);
        } else {
            errorTextView.setVisibility(View.INVISIBLE);
        }
    }
}
