package com.projects.daniel.stackstats;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.projects.daniel.stackstats.adapters.UsersAdapter;
import com.projects.daniel.stackstats.models.Badge;
import com.projects.daniel.stackstats.models.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView mUsersRecyclerView;
    private ArrayList<User> mUsersList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUsersList = new ArrayList<>();
        mUsersList.add(new User("ana", "http://www.gravatar.com/avatar/6d8ebb117e8d83d74ea95fbdd0f87e13?s=128&d=identicon&r=PG", "Istanbul", new Badge(Badge.BadgeTypes.Bronze, 12)));

        mUsersRecyclerView = findViewById(R.id.users_recycler_view);
        mUsersRecyclerView.setAdapter(new UsersAdapter(this, mUsersList, this));
        mUsersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "item click", Toast.LENGTH_SHORT).show();
    }
}
