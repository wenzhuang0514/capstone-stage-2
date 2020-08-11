package com.capstoneproject.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Score implements Parcelable {

    public static final Parcelable.Creator<Score> CREATOR = new Parcelable.Creator<Score>() {
        @Override
        public Score createFromParcel(Parcel parcel) {
            return new Score(parcel);
        }

        @Override
        public Score[] newArray(int i) {
            return new Score[i];
        }
    };

    public int id;
    public String name;
    public int scores;
    public String date;
    public String level;

    public Score() {
    }

    public Score(int id, String name, int scores, String date, String level) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.date = date;
        this.scores = scores;
    }

    protected Score(Parcel in) {
        name = in.readString();
        scores = in.readInt();
        date = in.readString();
        level = in.readString();
        id = in.readInt();
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getScores() {
        return scores;
    }

    public String getDate() {
        return date;
    }

    public String getLevel() {
        return level;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeString(level);
        parcel.writeInt(scores);
        parcel.writeInt(id);
        parcel.writeString(date);
    }

}
