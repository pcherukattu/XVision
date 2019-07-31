package com.example.ceaser007.jo;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.AlarmClock;
import android.util.Log;


public class AlarmClk extends AsyncTask<Void,Void,Void> {
    private String time=null;
    private String TAG = "com.android.joe2";
    private Context context=null;
    public static String EXTRA_TIME = "com.android.joe.time";
    public AlarmClk(Context context, String time){
        this.context=context;
        this.time=time;
    }

    @Override
    public Void doInBackground(Void... params) {

        Integer t = 12;
        Integer m = 15;
        Log.d(TAG, time);
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM).putExtra(AlarmClock.EXTRA_HOUR, t).putExtra(AlarmClock.EXTRA_MINUTES, m);
        context.startActivity(intent);
          return null;

    }
}