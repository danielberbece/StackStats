package com.projects.daniel.stackstats.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.projects.daniel.stackstats.DetailsActivity;
import com.projects.daniel.stackstats.R;
import com.projects.daniel.stackstats.models.User;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.projects.daniel.stackstats.DetailsActivity.INTENT_OBJECT_KEY;

/**
 * Created by Daniel on 3/11/2018.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private Context context;
    private ArrayList<User> users;

    public UsersAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
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
        holder.user = currentUser;
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

    public void setUsers(ArrayList<User> users) {
        this.users.clear();
        this.users.addAll(users);
        notifyDataSetChanged();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        User user;
        @BindView(R.id.list_item_user_image_view)
        ImageView userImageView;
        @BindView(R.id.list_item_user_name_text_view)
        TextView usernameTextView;

        public UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent detailActivityIntent = new Intent(context, DetailsActivity.class);
            detailActivityIntent.putExtra(INTENT_OBJECT_KEY, Parcels.wrap(user));
            context.startActivity(detailActivityIntent);
        }
    }
}
