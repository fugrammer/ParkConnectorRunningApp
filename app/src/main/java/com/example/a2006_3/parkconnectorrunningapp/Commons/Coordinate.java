package com.example.a2006_3.parkconnectorrunningapp.Commons;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by thamt on 28/9/2017.
 */

public class Coordinate implements Parcelable {
    public float lat;
    public float lng;

    public Coordinate(float lat, float lng){
        this.lat = lat;
        this.lng = lng;
    }

    protected Coordinate(Parcel in) {
        lat = in.readFloat();
        lng = in.readFloat();
    }

    public static final Creator<Coordinate> CREATOR = new Creator<Coordinate>() {
        @Override
        public Coordinate createFromParcel(Parcel in) {
            return new Coordinate(in);
        }

        @Override
        public Coordinate[] newArray(int size) {
            return new Coordinate[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(lat);
        dest.writeFloat(lng);
    }
}
