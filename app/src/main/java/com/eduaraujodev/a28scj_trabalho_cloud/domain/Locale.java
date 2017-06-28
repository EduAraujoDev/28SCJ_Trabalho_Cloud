package com.eduaraujodev.a28scj_trabalho_cloud.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Locale implements Parcelable {
    public long id;
    public String dataHora;
    public String log;
    public String lat;

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(dataHora);
        dest.writeString(log);
        dest.writeString(lat);
    }

    public void readFromParcel(Parcel parcel) {
        this.id = parcel.readLong();
        this.dataHora = parcel.readString();
        this.log = parcel.readString();
        this.lat = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Locale> CREATOR = new Creator<Locale>() {
        @Override
        public Locale createFromParcel(Parcel p) {
            Locale locale = new Locale();
            locale.readFromParcel(p);
            return locale;
        }

        @Override
        public Locale[] newArray(int size) {
            return new Locale[size];
        }
    };
}