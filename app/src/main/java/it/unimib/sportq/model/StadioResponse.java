package it.unimib.sportq.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StadioResponse implements Parcelable {

    private boolean isLoading;

    @SerializedName("stadiums")
    private List<Stadio> stadiumList;

    public StadioResponse() {}

    public StadioResponse(List<Stadio> stadiumList) {
        this.stadiumList = stadiumList;
    }

    public List<Stadio> getStadiumList() {
        return stadiumList;
    }

    public void setStadiumList(List<Stadio> stadiumList) {
        this.stadiumList = stadiumList;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    @Override
    public String toString() {
        return "StadiumResponse{" +
                "stadiumList=" + stadiumList +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isLoading ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.stadiumList);
    }

    public void readFromParcel(Parcel source) {
        this.isLoading = source.readByte() != 0;
        this.stadiumList = source.createTypedArrayList(Stadio.CREATOR);
    }

    protected StadioResponse(Parcel in) {
        this.isLoading = in.readByte() != 0;
        this.stadiumList = in.createTypedArrayList(Stadio.CREATOR);
    }

    public static final Creator<StadioResponse> CREATOR = new Creator<StadioResponse>() {
        @Override
        public StadioResponse createFromParcel(Parcel source) {
            return new StadioResponse(source);
        }

        @Override
        public StadioResponse[] newArray(int size) {
            return new StadioResponse[size];
        }
    };

}
