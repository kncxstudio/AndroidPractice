package com.iboxshare.ipctest1_1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by KN on 16/10/15.
 */

public class Book implements Parcelable {
    public int id;
    public String name;
    public float price;

    public Book(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readFloat();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeFloat(price);
    }



}
