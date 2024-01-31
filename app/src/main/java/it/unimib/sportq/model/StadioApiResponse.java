package it.unimib.sportq.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Class to represent the API response of NewsAPI.org (https://newsapi.org)
 * associated with the endpoint "Top headlines" - /v2/top-headlines.
 */
public class StadioApiResponse extends StadioResponse {
    private String status;
    private int totalResults;

    public StadioApiResponse() {
        super();
    }

    public StadioApiResponse(String status, int totalResults, List<Stadio> stadi) {
        super(stadi);
        this.status = status;
        this.totalResults = totalResults;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    @Override
    public String toString() {
        return "StadioApiResponse{" +
                "status='" + status + '\'' +
                ", totalResults=" + totalResults +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.status);
        dest.writeInt(this.totalResults);
    }

    public void readFromParcel(Parcel source) {
        super.readFromParcel(source);
        this.status = source.readString();
        this.totalResults = source.readInt();
    }

    protected StadioApiResponse(Parcel in) {
        super(in);
        this.status = in.readString();
        this.totalResults = in.readInt();
    }

    public static final Creator<StadioApiResponse> CREATOR = new Creator<StadioApiResponse>() {
        @Override
        public StadioApiResponse createFromParcel(Parcel source) {
            return new StadioApiResponse(source);
        }

        @Override
        public StadioApiResponse[] newArray(int size) {
            return new StadioApiResponse[size];
        }
    };
}

