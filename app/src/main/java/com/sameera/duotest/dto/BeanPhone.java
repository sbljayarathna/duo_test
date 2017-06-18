package com.sameera.duotest.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sameera on 6/18/17.
 */

public class BeanPhone implements Parcelable{
    private String contact;

    protected BeanPhone(Parcel in) {
        contact = in.readString();
    }

    public static final Creator<BeanPhone> CREATOR = new Creator<BeanPhone>() {
        @Override
        public BeanPhone createFromParcel(Parcel in) {
            return new BeanPhone(in);
        }

        @Override
        public BeanPhone[] newArray(int size) {
            return new BeanPhone[size];
        }
    };

    public String getContact() {
        return contact;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(contact);
    }
}
