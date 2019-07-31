package com.example.ceaser007.jo;

import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class MainFrag extends Fragment {
    public static int i=0;
    private ImageButton listen;
    private String s=null;
    private TextView textView;
    private TextView textview2;
    private ImageButton.OnClickListener listener;
    private String TAG = "com.android.joe2";
    private String s2;
    private android.os.Handler handler=null;
    private ReminderLab reminderLab=null;
    private ArrayList<Reminder> reminders;
    private SlideView adapter;
    private ViewPager listView;
    private TextToSpeech textToSpeech;
    private Timer timer=new Timer();
    private String STATUS=new String();
   // private Reminder r=new Reminder("hellow guyz today call sundher at 12","14:00:00");
  //  private Reminder r1=new Reminder("Today guyz","13:50:00");
    private AlertDialog alertDialog=new AlertDialog();
    private static final String ALERT_DIALOG="alert_log";

    int page=0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        reminderLab=ReminderLab.get(getActivity());
        View view = inflater.inflate(R.layout.activity_main2, container, false);
        listView= (ViewPager)view.findViewById(R.id.ActivityList);
        //horizontalScrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        reminders=reminderLab.getReminders();
         adapter=new SlideView(reminders,getActivity());
        listView.setAdapter(adapter);
        pageSwitcher(6);
        textview2 = (TextView) view.findViewById(R.id.txtSpeechInput);
        textToSpeech =new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status!=TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.UK);

                }
            }
        });


        return view;
    }
    @Override
    public void onViewCreated(View view,Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

    }
    public void changeText(String s){

        textview2.setText(s);
        Log.d(TAG,"here "+i);
    }
    @Override
    public void onResume(){
        super.onResume();
    }
    public void fbConfirm(final String s){
        STATUS=pareStatus(s);
        android.os.Handler.Callback callback1= new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                textview2.setText("Do you want to update your status to " + "\"" + s + "\"");
                textToSpeech.speak("Do you want to update your status to "+ s, TextToSpeech.QUEUE_FLUSH, null);
                return false;
            }
        } ;


        handler=new Handler(callback1);
        timer.schedule(new SmallDelay(),2000);

    }
    class SmallDelay extends TimerTask {
        public void run() {
            handler.sendEmptyMessage(0);

        }
    }
    public void fbStatusUpdate(){
        GraphRequest request = GraphRequest.newPostRequest(AccessToken.getCurrentAccessToken(), "me/feed", null, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                String s = response.getRawResponse();
                Log.d(TAG, "RESPONSE FROM SERVER" + s);
                FacebookRequestError error = response.getError();
                if (error != null) {
                    AlertDialog alertDialog = new AlertDialog();
                    int a = error.getErrorCode();
                    Log.d(TAG, "ERROR CODE " + a);
                    if (a == 200) {
                        FragmentManager fm = getActivity().getFragmentManager();
                        alertDialog.show(fm, ALERT_DIALOG);
                    }
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("message", STATUS);
        request.setParameters(parameters);
        request.executeAsync();
        STATUS=null;


    }
    private String pareStatus(String s){
        String w=s.substring(0,1).toUpperCase()+s.substring(1);

        String[] words=s.toLowerCase().split("\\s+");
       List<String> arrays= Arrays.asList(words);
        if(arrays.contains("enjoy")||arrays.contains("enjoying")||arrays.contains("happy")||arrays.contains("great")||arrays.contains("good")||arrays.contains("goodmorning")||arrays.contains("goodnight")){
            w=w+" !";
        }
       return  w;
    }
    public void fbLikes(){


        GraphRequest batch=new GraphRequest(AccessToken.getCurrentAccessToken(),"me/posts",null, HttpMethod.GET, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                FacebookRequestError error=response.getError();
                Log.d(TAG,"HERE THE ERROR "+error);
                String s1=response.getJSONObject().toString();
                 s =Parser.fbStatusId(s1)+"/likes";
                Log.d(TAG,"HERE THE ID "+ s);
                Log.d(TAG,"HERE RESPONSE "+response.getJSONObject().toString());
                Log.d(TAG,"server response "+response.toString());
                if(error!=null){
                    // LoginManager.getInstance().logInWithPublishPermissions(mainActivity, Arrays.asList("user_status","user_posts"));
                }
                fbLikeCount(s);
            }
        });
        batch.executeAsync();

    }
    private void fbLikeCount(String s){
        Bundle params=new Bundle();
        params.putString("summary", "total_count");
        GraphRequest batch=new GraphRequest(AccessToken.getCurrentAccessToken(),s,params,HttpMethod.GET, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                FacebookRequestError error=response.getError();
                Log.d(TAG,"HERE THE ERROR "+error);
                String s1=response.getJSONObject().toString();
                try {
                    String s2=response.getJSONObject().optString("summary","total_count");
                    Log.d(TAG,"the count is "+s2);
                } catch (Exception e) {
                    Log.d(TAG,"EROR IN JSON OBJECT");
                }

                Log.d(TAG,"HERE RESPONSE "+response.getJSONObject().toString());
                Log.d(TAG, "server response " + response.toString());
                if(error!=null){
                    // LoginManager.getInstance().logInWithPublishPermissions(mainActivity, Arrays.asList("user_status","user_posts"));
                }
            }
        });
        batch.executeAsync();
    }
    public void mainAdd(Reminder r){

        reminderLab.addReminder(r);
        reminderLab.saveReminder();
        swap(reminderLab.getReminders());
    }
   public void swap(ArrayList<Reminder>reminders){
       this.reminders=reminders;
       adapter=new SlideView(reminders,getActivity());
       listView= (ViewPager)getView().findViewById(R.id.ActivityList);
       listView.setAdapter(adapter);
   }
    public void upDate() {
        swap(reminderLab.getReminders());
    }
    @Override
    public void onDestroy(){
        reminderLab.saveReminder();
    }
    public void pageSwitcher(int seconds) {
        page=0;
        timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1000); // delay
        // in
        // milliseconds
    }

    // this is an inner class...
    class RemindTask extends TimerTask {

        @Override
        public void run() {

            // As the TimerTask run on a seprate thread from UI thread we have
            // to call runOnUiThread to do work on UI thread.
            getActivity().runOnUiThread(new Runnable() {
                public void run() {

                    if (page > 2) { // In my case the number of pages are 5
                        timer.cancel();
                        pageSwitcher(6);
                        // Showing a toast for just testing purpose

                    } else {
                        listView.setCurrentItem(page++);
                    }
                }
            });

        }

    }

}