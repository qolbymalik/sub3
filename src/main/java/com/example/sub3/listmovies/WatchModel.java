package com.example.sub3.listmovies;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

public class WatchModel implements Parcelable {
    private String title;
    private String overview;
    private String poster;

    public static final Parcelable.Creator<WatchModel> CREATOR = new Parcelable.Creator<WatchModel>() {
        @Override
        public WatchModel createFromParcel(Parcel source) {
            return new WatchModel(source);
        }

        @Override
        public WatchModel[] newArray(int size) {
            return new WatchModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeValue(this.poster);
    }

    private WatchModel(Parcel in) {
        this.title = in.readString();
        this.overview = in.readString();
        this.poster = (String) in.readValue(Integer.class.getClassLoader());
    }
}
