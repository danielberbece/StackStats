package com.projects.daniel.stackstats.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


/**
 * Created by Daniel on 3/11/2018.
 */


@org.parceler.Parcel
public class User {

    @SerializedName("display_name") String name;
    @SerializedName("profile_image") String profileImageLink;
    @SerializedName("location") String location;
    @SerializedName("badge_counts") BadgeCounts badgeCounts;

    public User() {
    }

    public User(String name, String profileImageLink, String location, BadgeCounts badgeCounts) {
        this.name = name;
        this.profileImageLink = profileImageLink;
        this.location = location;
        this.badgeCounts = badgeCounts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImageLink() {
        return profileImageLink;
    }

    public void setProfileImageLink(String profileImageLink) {
        this.profileImageLink = profileImageLink;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BadgeCounts getBadgeCounts() {
        return badgeCounts;
    }

    public void setBadgeCounts(BadgeCounts badgeCounts) {
        this.badgeCounts = badgeCounts;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", profileImageLink='" + profileImageLink + '\'' +
                ", location='" + location + '\'' +
                ", badgeCounts=" + badgeCounts +
                '}';
    }
}
