package com.example.lab4androiod;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity

public class Country implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    int id;
    String country;
    String language;
    String currency;
//    int population;
//    String capital;
    String flag;

    public Country() {

    }

    protected Country(Parcel in) {
        id =in.readInt();
        country = in.readString();
        language = in.readString();
        currency = in.readString();
//       population = in.readInt();
//       capital = in.readString();
        flag = in.readString();
    }

    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
           return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }

//    public int getpopulation() {
//        return population;
//    }
//    public void setpopulation(String population) {
//        this.population = Integer.parseInt(population);
//    }
//
//    public String getcapital() {
//        return capital;
//    }
//    public void setcapital(String capital) {
//        this.capital = capital;
//    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(country);
        dest.writeString(language);
        dest.writeString(currency);
        dest.writeString(flag);
//        dest.writeInt((population));
//        dest.writeString(capital);
    }
}
