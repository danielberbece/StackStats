package com.projects.daniel.stackstats.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.projects.daniel.stackstats.R;
import com.projects.daniel.stackstats.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Daniel on 3/11/2018.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private Context context;
    private ArrayList<User> users;
    private View.OnClickListener onClickListener;

    public UsersAdapter(Context context, ArrayList<User> users, View.OnClickListener onClickListener) {
        this.context = context;
        this.users = users;
        this.onClickListener = onClickListener;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View viewHolder = layoutInflater.inflate(R.layout.user_list_item, parent, false);

        return new UserViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User currentUser = users.get(position);
        holder.usernameTextView.setText(currentUser.getName());

        Picasso.with(context)
                .load(currentUser.getProfileImageLink())
                .error(R.drawable.ic_error_outline_red)
                .placeholder(R.drawable.ic_insert_photo_black)
                .resize(50, 50)
                .into(holder.userImageView);

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        ImageView userImageView;
        TextView usernameTextView;

        public UserViewHolder(View itemView) {
            super(itemView);

            userImageView = itemView.findViewById(R.id.list_item_user_image_view);
            usernameTextView = itemView.findViewById(R.id.list_item_user_name_text_view);
            itemView.setOnClickListener(onClickListener);
        }
    }
}
