package it.unimib.sportq.model;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "stadio")
public class Stadio implements Parcelable{
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String stadiumName;
    private String address;
    private float lat;
    private float lng;

    @Embedded(prefix = "source_")
    private StadioSource source;

    @ColumnInfo(name = "is_favorite")
    private boolean isFavorite;

    @ColumnInfo(name = "is_synchronized")
    private boolean isSynchronized;

    public Stadio() {
    }

    public Stadio(long id, String stadiumName, String address, float lat, float lng, StadioSource source, boolean isFavorite, boolean isSynchronized) {
        this.id = id;
        this.stadiumName = stadiumName;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.source = source;
        this.isFavorite = isFavorite;
        this.isSynchronized = isSynchronized;
    }

    public Stadio(long id, String stadiumName, String address, float lat, float lng, StadioSource source) {
       this(id,stadiumName,address, lat, lng, source, false, false);
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

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public StadioSource getSource() {
        return source;
    }

    public void setSource(StadioSource source) {
        this.source = source;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isSynchronized() {
        return isSynchronized;
    }

    public void setSynchronized(boolean aSynchronized) {
        isSynchronized = aSynchronized;
    }

    @Override
    public String toString() {
        return "Stadio{" +
                "id=" + id +
                ", stadiumName='" + stadiumName + '\'' +
                ", address='" + address + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", source=" + source +
                ", isFavorite=" + isFavorite +
                ", isSynchronized=" + isSynchronized +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stadio stadio = (Stadio) o;
        return Objects.equals(id, stadio.id) && Objects.equals(stadiumName, stadio.stadiumName) && Objects.equals(address, stadio.address)&& Objects.equals(source, stadio.source) && Objects.equals(lat, stadio.lat) && Objects.equals(lng, stadio.lng);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stadiumName, address, source, lat, lng);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.stadiumName);
        dest.writeString(this.address);
        dest.writeParcelable(this.source, flags);
        dest.writeFloat(this.lat);
        dest.writeFloat(this.lng);
        dest.writeByte(this.isFavorite ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isSynchronized ? (byte) 1 : (byte) 0);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readLong();
        this.stadiumName = source.readString();
        this.address = source.readString();
        this.source = source.readParcelable(StadioSource.class.getClassLoader());
        this.lat = source.readFloat();
        this.lng = source.readFloat();
        this.isFavorite = source.readByte() != 0;
        this.isSynchronized = source.readByte() != 0;
    }

    protected Stadio(Parcel in) {
        this.id = in.readLong();
        this.stadiumName = in.readString();
        this.address = in.readString();
        this.source = in.readParcelable(StadioSource.class.getClassLoader());
        this.lat = in.readFloat();
        this.lng = in.readFloat();
        this.isFavorite = in.readByte() != 0;
        this.isSynchronized = in.readByte() != 0;
    }
    public static final Creator<Stadio> CREATOR = new Creator<Stadio>() {
        @Override
                public Stadio createFromParcel(Parcel source) {return new Stadio(source);}

    @Override
    public Stadio[] newArray(int size) {
        return new Stadio[size];
    }
    };
}

