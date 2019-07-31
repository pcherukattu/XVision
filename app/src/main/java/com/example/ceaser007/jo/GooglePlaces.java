package com.example.ceaser007.jo;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Ceaser007 on 6/20/2016.
 */
@SuppressWarnings("deprecation")
public class GooglePlaces {
    private String TAG = "com.android.joe2";
    private static final String API_KEY ="xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
    private static final String SERVER_KEY= "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
    private static final String PLACES_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/search/json?";
    private static final String PLACES_TEXT_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/search/json?";
    private static final String PLACES_DETAILS_URL = "https://maps.googleapis.com/maps/api/place/details/json?";
    private double _latitiude;
    private double _longitude;
    private double _radius;
    public ArrayList<Place> search(double latitiude,double longitude,double radius,String types)throws Exception{
        _latitiude=latitiude;
        _longitude=longitude;
        _radius=radius;
        try{
            StringBuilder urlString=new StringBuilder(PLACES_SEARCH_URL);
            urlString.append("&location=");
            urlString.append(Double.toString(latitiude));
            urlString.append(",");
            urlString.append(Double.toString(longitude));
            urlString.append("&radius=" + _radius);
            urlString.append("&types=" + types);
            urlString.append("&sensor=false&key=" + SERVER_KEY);
            urlString.append("&userIp=11.22.33.44");
            String json=getJson(urlString.toString());
            JSONObject object=new JSONObject(json);
            JSONArray array=object.getJSONArray("results");
            JSONArray array1=object.getJSONArray("results");
            Log.d(TAG,"the list " +array1.toString());
            ArrayList<Place> arrayList=new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                try {
                    Place place = Place.jsonToPontoReferencia((JSONObject) array.get(i));
                    Log.d("Places Services ", "" + place.toString());
                    arrayList.add(place);
                } catch (Exception e) {
                    Log.d(TAG, "    Error in place");
                }
            }
            return arrayList;

        }catch (Exception e){
            Log.d(TAG,"ERROR IN SEARCH FUNCT google PLaces "+e);
            return null;
        }
    }
   protected String getJson(String theUrl){
       StringBuilder content=new StringBuilder();
       try{
           URL url=new URL(theUrl);
           Log.d(TAG,url.toString());
           URLConnection urlConnection=url.openConnection();
           BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(urlConnection.getInputStream()),8);
           String line;
           while ((line = bufferedReader.readLine()) != null) {
               content.append(line + "\n");
           }
           bufferedReader.close();
       }catch (Exception e){

       }
       return content.toString();
   }

}
