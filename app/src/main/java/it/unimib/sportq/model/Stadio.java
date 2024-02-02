package it.unimib.sportq.model;
import android.graphics.Bitmap;
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


public class Stadio implements Serializable {
    private String id_stadium;
    private String stadiumName;
    private String address;
    private double latitude;
    private double longitude;
    private boolean isFavorite;
    private Bitmap fotoStadio;
    public Stadio() {
    }


    public Stadio(String id_stadium, String stadiumName, String address, double latitude, double longitude, Bitmap fotoStadio) {
        this.id_stadium = id_stadium;
        this.stadiumName = stadiumName;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.fotoStadio = fotoStadio;
        this.isFavorite = false;
    }

    public Stadio(String stadiumName, double latitude, double longitude) {
        this.stadiumName = stadiumName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId_stadium() {
        return id_stadium;
    }

    public void setId_stadium(String id_stadium) {this.id_stadium = id_stadium;}

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

    public Bitmap getFotoStadio() {
        return fotoStadio;
    }

    public void setFotoStadio(Bitmap fotoStadio) {
        this.fotoStadio = fotoStadio;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}

