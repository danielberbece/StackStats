package com.projects.daniel.stackstats.models;

import org.parceler.Parcel;

/**
 * Created by Daniel on 3/11/2018.
 */
@Parcel
public class Badge {
    public enum BadgeTypes {Bronze, Silver, Gold};
    BadgeTypes badgeType;
    int numberOfBadges;

    public Badge() {
    }

    public Badge(BadgeTypes badgeType, int numberOfBadges) {
        this.badgeType = badgeType;
        this.numberOfBadges = numberOfBadges;
    }

    public BadgeTypes getBadgeType() {
        return badgeType;
    }

    public int getNumberOfBadges() {
        return numberOfBadges;
    }
}
