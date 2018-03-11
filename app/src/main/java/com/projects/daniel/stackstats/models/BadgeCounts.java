package com.projects.daniel.stackstats.models;

import org.parceler.Parcel;

/**
 * Created by Daniel on 3/11/2018.
 */
@Parcel
public class BadgeCounts {

    int gold;
    int silver;
    int bronze;

    public BadgeCounts() {
    }

    public BadgeCounts(int gold, int silver, int bronze) {
        this.gold = gold;
        this.silver = silver;
        this.bronze = bronze;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getSilver() {
        return silver;
    }

    public void setSilver(int silver) {
        this.silver = silver;
    }

    public int getBronze() {
        return bronze;
    }

    public void setBronze(int bronze) {
        this.bronze = bronze;
    }
}
