package com.example.ceaser007.jo;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by Ceaser007 on 5/18/2016.
 */
public class JSONserialiser {
    private Context context=null;
    private String mFilename=new String();
    private static final String TAG = "com.android.joe2";

    public JSONserialiser(Context c, String f) {
        context = c;
        mFilename = f;
    }

    public void saveReminders(ArrayList<Reminder> reminders) throws JSONException, IOException {

        JSONArray array = new JSONArray();
        try {

            for (Reminder r : reminders) {
                array.put(r.toJson());
            }
        } catch (Exception e) {
            Log.d(TAG, "Actual cause of errror");
        }
        OutputStreamWriter writer=null;
        try {
            OutputStream out = context.openFileOutput(mFilename, Context.MODE_PRIVATE);
             writer = new OutputStreamWriter(out);
            writer.write(array.toString());
            Log.d(TAG, "CONTROLE HRERE 1 " + array.toString());

        }finally {
            if (writer != null) {
            writer.close();
            }
        }


    }
    public ArrayList<Reminder> loadReminders()throws IOException,JSONException{
        StringBuilder jsonString=new StringBuilder();
            ArrayList<Reminder> reminders=new ArrayList<>();

        try{
            InputStream in =context.openFileInput(mFilename);
            BufferedReader reader=new BufferedReader(new InputStreamReader(in));

            String line=new String();
            while((line=reader.readLine())!=null){
                jsonString.append(line);
            }}

        catch (Exception e) {
        Log.d(TAG,"First Exception");
        }
        Log.d(TAG,"the json strong "+jsonString.toString());
          try{  JSONArray array=(JSONArray)new JSONTokener(jsonString.toString()).nextValue();
            int j=0;
            for(int i=0;i<array.length();i++){
                Reminder reminder=new Reminder(array.getJSONObject(i));
                //if(reminder.cal.after(Calendar.getInstance())){
                    reminders.add(reminder);

                //}
            }
        }catch (Exception e){
            Log.d(TAG,"EXCEPTION ON RETRIVING"+e);
        }
        return reminders;
    }
}