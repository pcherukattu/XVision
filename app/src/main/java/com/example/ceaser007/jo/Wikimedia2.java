package com.example.ceaser007.jo;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Ceaser007 on 4/17/2016.
 */
public class Wikimedia2 extends AsyncTask<String, Void, String> {
    private String wikistring = null;
    private String TAG = "com.android.joe2";
    private String ret = null;
    private int k = 0;
    private String url = null;
    private String htmlText = null;
    private Context context;
    private WikiFrag wikiFrag;
    private String[] words;
    private String matter = null;
    private String matter2 = null;

    public Wikimedia2(Context c) {
        context = c;
    }

    @Override
    protected String doInBackground(String... params) {
        String text = null;
        Log.d(TAG,"Wikimedia2 started");

        String matter=params[0];
        String target = "https://en.wikipedia.org/wiki/" + matter;


        Log.d(TAG, "Started");
        URL url = null;
        try {
            url = new URL(target);
        } catch (MalformedURLException e) {
            Log.d(TAG, "Maformed URLException");
        }

        try {

            htmlText = new Scanner(url.openStream(), "UTF-8").useDelimiter("\\A").next();
            Document doc = Jsoup.parse(htmlText);
            text = doc.body().getElementsByTag("p").text();
            Log.d(TAG,"wikimedia2 "+text);
            String path = context.getFilesDir().toString();
            Log.d(TAG, text);
        } catch (Exception e) {
            Log.d(TAG, "Got the exception here");
            text = null;


        }

        if (text != null) {
            try {

                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
                outputStreamWriter.write(text);
                outputStreamWriter.close();

            } catch (Exception e) {
                Log.d(TAG, "here" + e);
            }


            ret = Parser.setter(context);
            Log.d(TAG, "AT wikimedia " + ret);
        }

            return ret;
        }

    }
