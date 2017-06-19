package com.pavelsemak.weatherapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class City implements Parcelable {
    private String name;
    private double latitude;
    private double longitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public City createFromParcel(Parcel in) {
            City city = new City();

            city.setName(in.readString());
            city.setLatitude(in.readDouble());
            city.setLongitude(in.readDouble());

            return city;
        }

        public City[] newArray(int size) {
            return new City[size];
        }
    };
}
