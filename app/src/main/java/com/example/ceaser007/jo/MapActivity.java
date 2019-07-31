package com.example.ceaser007.jo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Ceaser007 on 6/24/2016.
 */
public class MapActivity extends Activity implements OnMapReadyCallback {
    GoogleMap m_Map;
    boolean mapReady=false;
    MarkerOptions point;
    CameraPosition position;
    TextView place_name;
    TextView place_address;
     public MapActivity(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_place);
        place_name=(TextView)findViewById(R.id.place_name_2);
        place_address=(TextView)findViewById(R.id.adress_place);
        Place place= (Place)getIntent().getParcelableExtra("place");
        position=CameraPosition.builder().target(new LatLng(place.getLatitude(),place.getLongitude())).zoom(10).bearing(0).tilt(45).build();
        point=new MarkerOptions().position(new LatLng(place.getLatitude(), place.getLongitude())).title(place.getName());
        place_name.setText(place.getName());
        place_address.setText(place.getVicinity());
        MapFragment fragment=(MapFragment)getFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
       mapReady=true;
        m_Map=googleMap;
        m_Map.addMarker(point);
        m_Map.moveCamera(CameraUpdateFactory.newCameraPosition(position));
    }
}
