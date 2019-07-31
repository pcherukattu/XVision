package com.example.ceaser007.jo;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONObject;
/**
 * Created by Ceaser007 on 6/19/2016.
 */
public class Place  implements Parcelable{
    private String id;
    private String name;
    private String reference;
    private String icon;
    private String vicinity;
    private Double latitude;
    private Double longitude;
    private String formatted_address;
    private String formatted_phone_number;
    private String TAG = "com.android.joe2";
    public Place(){

    }

    protected Place(Parcel in) {
        latitude=in.readDouble();
        longitude=in.readDouble();
        //id = in.readString();
        name = in.readString();
        //reference = in.readString();
        //icon = in.readString();
        vicinity = in.readString();
        //formatted_address = in.readString();
        //formatted_phone_number = in.readString();
        //TAG = in.readString();
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }
    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }


    public String getFormatted_phone_number() {
        return formatted_phone_number;
    }

    public void setFormatted_phone_number(String formatted_phone_number) {
        this.formatted_phone_number = formatted_phone_number;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }


    @Override
    public String toString() {
        return name + " - " + id + " - " + reference;
    }
    public static Place jsonToPontoReferencia(JSONObject pontoReferencia) {
        try{
            Place result=new Place();
            JSONObject geometry=(JSONObject) pontoReferencia.get("geometry");
            JSONObject location= (JSONObject) geometry.get("location");
            result.setLatitude((Double) location.get("lat"));
            result.setLongitude((Double) location.get("lng"));
            result.setIcon(pontoReferencia.getString("icon"));
            result.setName(pontoReferencia.getString("name"));
            result.setVicinity(pontoReferencia.getString("vicinity"));
            result.setId(pontoReferencia.getString("id"));
            return result;
        }catch (Exception e){
              Log.d("tejas","the error is in places "+e);

        }
     return  null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(name);
       // dest.writeString(id);
        //dest.writeString(reference);
        //dest.writeString(icon);
        dest.writeString(vicinity);
        //dest.writeString(formatted_address);
        //dest.writeString(formatted_phone_number);

    }
}
