package in.anukool.weatherapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class Place implements Parcelable {

    @SerializedName("lat")
    Double latitude;

    @SerializedName("lon")
    Double longitude;

    @SerializedName("name")
    String name;

    @SerializedName("region")
    String region;

    @SerializedName("country")
    String country;


    Place() {}

    public Place(String name, String region, String country, double latitude, double longitude) {
        this.name = name;
        this.region = region;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Place(Parcel source) {
        this.latitude = source.readDouble();
        this.longitude = source.readDouble();
        this.name = source.readString();
        this.region = source.readString();
        this.country = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeString(name);
        parcel.writeString(region);
        parcel.writeString(country);
    }

    public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Place){
            Place other = (Place) o;
            return this.getLongitude() == other.getLongitude()
                    && this.getLatitude() == other.getLatitude();
        }
        return false;
    }
}
