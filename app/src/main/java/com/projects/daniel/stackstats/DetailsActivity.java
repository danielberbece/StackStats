package com.projects.daniel.stackstats;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.projects.daniel.stackstats.models.User;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    public static final String INTENT_OBJECT_KEY = "object_intent_key";

    @BindView(R.id.detail_user_image)
    ImageView userImageView;
    @BindView(R.id.detail_username)
    TextView usernameTextView;
    @BindView(R.id.detail_user_location)
    TextView userLocationTextView;
    @BindView(R.id.detail_bronze_badges)
    TextView userBronzeBadgesTextView;
    @BindView(R.id.detail_silver_badges)
    TextView userSilverBadgesTextView;
    @BindView(R.id.detail_gold_badges)
    TextView userGoldBadgesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        User user = Parcels.unwrap(getIntent().getParcelableExtra(INTENT_OBJECT_KEY));

        Picasso.with(this)
                .load(user.getProfileImageLink())
                .error(R.drawable.ic_error_outline_red)
                .placeholder(R.drawable.ic_insert_photo_black)
                .resize(80, 80)
                .into(userImageView);
        usernameTextView.setText(user.getName());
        userLocationTextView.setText(user.getLocation());
        userBronzeBadgesTextView.setText(String.valueOf(user.getBadgeCounts().getBronze()));
        userSilverBadgesTextView.setText(String.valueOf(user.getBadgeCounts().getSilver()));
        userGoldBadgesTextView.setText(String.valueOf(user.getBadgeCounts().getGold()));
    }
}
