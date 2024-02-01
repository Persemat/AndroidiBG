package it.unimib.sportq.model;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.PhotoMetadata;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity(tableName = "stadio")
public class Stadio implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String stadiumName;
    private String address;
    private double latitude;
    private double longitude;
    public Stadio() {
    }

    public Stadio(long id, String stadiumName, String address, double latitude, double longitude) {
        this.id = id;
        this.stadiumName = stadiumName;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Stadio(String stadiumName, String address, double latitude, double longitude) {
        this.stadiumName = stadiumName;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

