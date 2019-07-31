package com.example.ceaser007.jo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ceaser007 on 6/22/2016.
 */
public class PacesListAdapter extends BaseAdapter {
    private Context context;
    private List<Place> places;
    private LayoutInflater layoutInflater;
    private int drawable_int;
    PacesListAdapter(Context c,List<Place> places,int i){
        this.places=places;
        this.context=c;
        layoutInflater=LayoutInflater.from(context);
        drawable_int=i;
    }
    @Override
    public int getCount() {
        return places.size();
    }

    @Override
    public Place getItem(int position) {
        return places.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Place currentPlace=getItem(position);
        ViewHolder holder;
        if(convertView==null) {
            convertView = layoutInflater.inflate(R.layout.place_view, parent, false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(currentPlace.getName());
        holder.view.setBackgroundResource(drawable_int);

        return convertView;
    }
    private class ViewHolder{
         ImageView view;
         TextView textView;
       public ViewHolder(View v){
           view=(ImageView)v.findViewById(R.id.place_image);
           textView=(TextView)v.findViewById(R.id.place_name);
       }

    }
}
