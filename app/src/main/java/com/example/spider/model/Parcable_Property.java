package com.example.spider.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Parcable_Property implements Parcelable {

    int acc_item_positon;
    int payment_item_positon;
    String type;

    public Parcable_Property(Parcel parcel) {

        acc_item_positon=parcel.readInt();
        payment_item_positon=parcel.readInt();
        type=parcel.readString();


    }

    public Parcable_Property() {
    }

    public int getAcc_item_positon() {
        return acc_item_positon;
    }

    public void setAcc_item_positon(int acc_item_positon) {
        this.acc_item_positon = acc_item_positon;
    }

    public int getPayment_item_positon() {
        return payment_item_positon;
    }

    public void setPayment_item_positon(int payment_item_positon) {
        this.payment_item_positon = payment_item_positon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(acc_item_positon);
        parcel.writeInt(payment_item_positon);
        parcel.writeString(type);

    }
    public static final Creator<Parcable_Property> CREATOR = new Creator<Parcable_Property>() {
        @Override
        public Parcable_Property createFromParcel(Parcel in) {
            return new Parcable_Property(in);
        }

        @Override
        public Parcable_Property[] newArray(int size) {
            return new Parcable_Property[size];
        }
    };
}
