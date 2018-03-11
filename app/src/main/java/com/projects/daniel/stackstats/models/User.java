package com.projects.daniel.stackstats.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


/**
 * Created by Daniel on 3/11/2018.
 */


@org.parceler.Parcel
public class User  {

    String name;
    String profileImageLink;
    String location;
    Badge badges;

    public User() {
    }

    public User(String name, String profileImageLink, String location, Badge badges) {
        this.name = name;
        this.profileImageLink = profileImageLink;
        this.location = location;
        this.badges = badges;
    }

    public String getName() {
        return name;
    }

    public String getProfileImageLink() {
        return profileImageLink;
    }

    public String getLocation() {
        return location;
    }

    public Badge getBadges() {
        return badges;
    }
}
