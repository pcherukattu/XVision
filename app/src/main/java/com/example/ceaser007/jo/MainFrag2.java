package com.example.ceaser007.jo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Ceaser007 on 4/1/2016.
 */
public class MainFrag2 extends Fragment {
    private TextView textspeech;
    private TextView matter;
    private String TAG = "com.android.joe2";
    private String s1;
    private String s2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.main2, container, false);
        matter = (TextView) view.findViewById(R.id.matter);
        textspeech = (TextView) view.findViewById(R.id.txtSpeechInput);
        return view;
    }
    public void changeText1(String s){
       matter.setText(s);
    }
    public void changeText2(String s){
       textspeech.setText(s);
    }
    public class BaseAdapter extends android.widget.BaseAdapter{

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }

}
