package com.capstoneproject.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class LeaderBoardScores implements Parcelable {

    public static final Parcelable.Creator<LeaderBoardScores> CREATOR = new Parcelable.Creator<LeaderBoardScores>() {
        @Override
        public LeaderBoardScores createFromParcel(Parcel parcel) {
            return new LeaderBoardScores(parcel);
        }

        @Override
        public LeaderBoardScores[] newArray(int i) {
            return new LeaderBoardScores[i];
        }
    };

    public String userid;
    public String user_name;
    public String name;
    public int scores;
    public String date;
    public String level;

    public LeaderBoardScores() {
    }

    public LeaderBoardScores(String userid, String user_name, String name, int scores, String date, String level) {
        this.userid = userid;
        this.user_name = user_name;
        this.name = name;
        this.level = level;
        this.date = date;
        this.scores = scores;
    }

    protected LeaderBoardScores(Parcel in) {
        name = in.readString();
        scores = in.readInt();
        date = in.readString();
        level = in.readString();
        userid = in.readString();
        user_name = in.readString();
    }

    public String getName() {
        return name;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUserid() {
        return userid;
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
        parcel.writeString(userid);
        parcel.writeString(user_name);
        parcel.writeString(date);
    }

}
