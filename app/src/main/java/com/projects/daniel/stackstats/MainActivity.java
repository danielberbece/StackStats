package com.projects.daniel.stackstats;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.projects.daniel.stackstats.adapters.UsersAdapter;
import com.projects.daniel.stackstats.models.User;
import com.projects.daniel.stackstats.tasks.UsersAsyncTask;
import com.projects.daniel.stackstats.utils.NetworkUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.users_recycler_view) RecyclerView mUsersRecyclerView;
    private ArrayList<User> mUsersList;
    @BindView(R.id.error_text_view)
    TextView errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mUsersList = new ArrayList<>();

        mUsersRecyclerView.setAdapter(new UsersAdapter(this, mUsersList));
        mUsersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        UsersAsyncTask usersAsyncTask =
                new UsersAsyncTask((UsersAdapter) mUsersRecyclerView.getAdapter(), errorTextView);

        usersAsyncTask.execute(NetworkUtils.getUsersQueryUrl());

    }

}
