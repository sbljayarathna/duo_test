package com.sameera.duotest.dto.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.sameera.duotest.dto.BeanEmail;
import com.sameera.duotest.dto.BeanPhone;

/**
 * Created by Sameera on 6/18/17.
 */

public class BeanProfileRes implements Parcelable {
    private String firstname, lastname, gender, avatar, birthday;
    private BeanPhone phoneNumber;
    private BeanEmail email;

    protected BeanProfileRes(Parcel in) {
        firstname = in.readString();
        lastname = in.readString();
        gender = in.readString();
        avatar = in.readString();
        birthday = in.readString();
        phoneNumber = (BeanPhone)in.readValue(this.getClass().getClassLoader());
        email = (BeanEmail)in.readValue(this.getClass().getClassLoader());
    }

    public static final Creator<BeanProfileRes> CREATOR = new Creator<BeanProfileRes>() {
        @Override
        public BeanProfileRes createFromParcel(Parcel in) {
            return new BeanProfileRes(in);
        }

        @Override
        public BeanProfileRes[] newArray(int size) {
            return new BeanProfileRes[size];
        }
    };

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getGender() {
        return gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getBirthday() {
        return birthday;
    }

    public BeanPhone getPhoneNumber() {
        return phoneNumber;
    }

    public BeanEmail getEmail() {
        return email;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstname);
        dest.writeString(lastname);
        dest.writeString(gender);
        dest.writeString(avatar);
        dest.writeString(birthday);
        dest.writeValue(phoneNumber);
        dest.writeValue(email);
    }
}
