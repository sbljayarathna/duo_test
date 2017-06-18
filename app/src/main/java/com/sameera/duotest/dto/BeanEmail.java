package com.sameera.duotest.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sameera on 6/18/17.
 */

public class BeanEmail implements Parcelable{
    private String contact;

    protected BeanEmail(Parcel in) {
        contact = in.readString();
    }

    public static final Creator<BeanEmail> CREATOR = new Creator<BeanEmail>() {
        @Override
        public BeanEmail createFromParcel(Parcel in) {
            return new BeanEmail(in);
        }

        @Override
        public BeanEmail[] newArray(int size) {
            return new BeanEmail[size];
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
