package com.example.ceaser007.jo;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ceaser007 on 6/20/2016.
 */
public class LoadPlaces extends AsyncTask<Void, Void, ArrayList<Place>> {
    Double longitude;
    Double latitude;
    String type;
    String types=null;
    private String TAG = "com.android.joe2";
    MainActivity mainActivity;

    LoadPlaces(MainActivity activity,Double latitude,Double longitude,String type){
        mainActivity=activity;
        this.latitude=latitude;
        this.longitude=longitude;
        this.type=type;
    }
    @Override
    protected ArrayList<Place> doInBackground(Void... params) {
        GooglePlaces googlePlaces=new GooglePlaces();
        Log.d(TAG,"here in do in background "+type);
        String[] array=type.split("\\s+");
        List<String> list= Arrays.asList(array);
        if(list.contains("food")||list.contains("restaurant")||list.contains("eat")){
             Log.d(TAG,"its has entered in type");
            types="restaurant";

        }
        else if(list.contains("hospital")|list.contains("aid")){
            types="hospital";
        }
        else if(list.contains("petrol")|list.contains("gas")){
            types="gas_station";
        }
        else if(list.contains("pharmacy")|list.contains("medicine")){
            types="pharmacy";
        }
        else if(list.contains("bus")){
            types="bus_station";
        }
        else if(list.contains("polics")|list.contains("cop")|list.contains("copes")){
            types="police";
        }
        else if(list.contains("movie")|list.contains("film")){
            types="movie_theater";
        }
        try {
            ArrayList<Place> places=googlePlaces.search(latitude, longitude, 4000, types);

            return places;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(ArrayList<Place> places) {
        Log.d(TAG,"the size of list in post execute in LoadPlaces "+places.size());
        mainActivity.placeSet(places,types);


    }
    }

