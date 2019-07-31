package com.example.ceaser007.jo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Ceaser007 on 6/22/2016.
 */
public class PlacesFrag extends Activity {
    private ImageView place_type_view;
    private ListView listView;
    private List<Place> places=null;
    private String TAG = "com.android.joe2";
    public PlacesFrag( ){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int i=0;
        setContentView(R.layout.place_list);
        place_type_view=(ImageView)findViewById(R.id.type_image);
        places=getIntent().getParcelableArrayListExtra("places1");
        String type=getIntent().getStringExtra("place_type");
        Log.d(TAG,"THE PLACE TYPE IN places frsg is "+type);
        if(type.equals("restaurant")){
            place_type_view.setBackgroundResource(R.drawable.restaurant_view);
            i=R.drawable.restaurant_icon;
        }
        else if(type.equals("hospital")){
            place_type_view.setBackgroundResource(R.drawable.hospital_best);
            i=R.drawable.hospital_icon;
        }
        else if(type.equals("gas_station")){
            place_type_view.setBackgroundResource(R.drawable.petrol_back);
        }
        listView = (ListView) findViewById(R.id.places_list);
        PacesListAdapter adapter = new PacesListAdapter(this, places,i);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(PlacesFrag.this,MapActivity.class);
                intent.putExtra("place",places.get(position));
                startActivity(intent);
            }
        });
        Log.d(TAG, "Places frag");
    }


}
