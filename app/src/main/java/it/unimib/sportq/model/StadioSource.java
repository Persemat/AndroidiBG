package it.unimib.sportq.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class StadioSource implements Parcelable {
    private String name;

    public StadioSource() {
    }

    public StadioSource(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StadioSource that = (StadioSource) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "StadioSource{" +
                "name ='" + name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }

    public void readFromParcel(Parcel source) {
        this.name = source.readString();
    }

    protected StadioSource(Parcel in) {
        this.name = in.readString();
    }

    public static final Creator<StadioSource> CREATOR = new Creator<StadioSource>() {
        @Override
        public StadioSource createFromParcel(Parcel source) {
            return new StadioSource(source);
        }

        @Override
        public StadioSource[] newArray(int size) {
            return new StadioSource[size];
        }
    };
}

