package com.example.ceaser007.jo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

/**
 * Created by Ceaser007 on 4/6/2016.
 */
  public class WikiFrag extends Fragment {
   private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private ListView listView=null;

    String words[]=null;
    String matter;
    String work[];
    private String TAG = "com.android.joe2";
    Context c1;
    String s=null;
   public WikiFrag (){
   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "on Create view ");
        View view=inflater.inflate(R.layout.wikifrag,container,false);
         textView1=(TextView)view.findViewById(R.id.txtSpeechInput1);
         textView2=(TextView)view.findViewById(R.id.matter1);
        textView3=(TextView)view.findViewById(R.id.did);
         listView=(ListView)view.findViewById(R.id.list1);
        Log.d(TAG,"on Create view ended");
        return view;
    }
    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


   public void updater(String matter, final String[] words){

       Log.d(TAG, "AT wikifrag" + matter);
       if(!matter.equals("ERROR")) {
           textView2.setText(matter);
           textView1.setText(words[0]);
       }
       else{
           textView1.setText("Ooops!");
           textView2.setText("");
       }
       if(words!=null) {
           textView3.setText("Did you mean");
       }
       //  textView1.setText("Probability");
       if (listView != null&&words!=null) {


           listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_2, android.R.id.text1,words));
           listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   Log.d(TAG, "clicked");
                   Wikimedia2 wikimedia2=new Wikimedia2(getActivity());
                   try {
                       String s = wikimedia2.execute(words[position]).get();
                       textView2.setText(s);
                       textView1.setText(words[position]);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   } catch (ExecutionException e) {
                       e.printStackTrace();
                   }
               }
           });
       }
       else {
           textView1.setText("Ooops!");
           listView.setAdapter(null);


       }



   }
        }







