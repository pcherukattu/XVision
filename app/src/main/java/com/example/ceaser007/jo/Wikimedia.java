package com.example.ceaser007.jo;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

/**
 * Created by Ceaser007 on 2/28/2016.
 */
public class Wikimedia extends AsyncTask<String, Void, String> {
    private String wikistring = null;
    private String TAG = "com.android.joe2";
    private String ret = null;
    private int k = 0;
    private String url = null;
    private String htmlText = null;
    private MainActivity context;
    private WikiFrag wikiFrag;
    private String[] words;
    private String matter = null;
    private String matter2 = null;

    public Wikimedia(String w, MainActivity c) {
        context = c;
        wikistring = w;
    }

    @Override
    protected String doInBackground(String... params) {
        String text = null;
        matter = null;
        String data1 = null;
        HttpURLConnection urlCon1 = null;
        String data = null;
        HttpURLConnection urlCon = null;
        // matter = params[0];
        String matter1 = "";
        String TAG = "com.android.joe2";
        String urlstr = "http://en.wikipedia.org/w/api.php";
        String matter = params[0];



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
              //  String path = context.getFilesDir().toString();
                Log.d(TAG, text);
            } catch (Exception e) {
                Log.d(TAG, "Got the exception here");
                text = "ERROR";
                ret="ERROR";


            }

            if (!text.equals("ERROR")) {
                try {

                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
                    outputStreamWriter.write(text);
                    outputStreamWriter.close();
                    ret = Parser.setter(context);

                } catch (Exception e) {
                    Log.d(TAG, "here" + e);
                }


               // ret = Parser.setter(context);
                Log.d(TAG,"AT wikimedia " +ret);
            }
            try {
                data = URLEncoder.encode("format", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8");
                data += "&" + URLEncoder.encode("action", "UTF-8") + "=" + URLEncoder.encode("opensearch", "UTF-8");
                data += "&" + URLEncoder.encode("prop", "UTF-8") + "=" + URLEncoder.encode("extracts", "UTF-8");
                data += "&" + URLEncoder.encode("exintro", "UTF-8") + "=" + URLEncoder.encode("explaintext", "UTF-8");
                // data+="&"+URLEncoder.encode("redirects","UTF-8")+"="+URLEncoder.encode("resolve","UTF-8");
                data += "&" + URLEncoder.encode("search", "UTF-8") + "=" + URLEncoder.encode(matter, "UTF-8");


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                URL url1 = new URL(urlstr);
                urlCon = (HttpURLConnection) url1.openConnection();
                urlCon.setDoOutput(true);
                urlCon.setRequestMethod("POST");
                urlCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlCon.setRequestProperty("Content-Language", "en-US");
                OutputStreamWriter os = new OutputStreamWriter(urlCon.getOutputStream());
                os.write(data);
                os.close();
                BufferedReader br = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
                String line = "";
                //  OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));

                while ((line = br.readLine()) != null) {
                    matter1 += line;

                    Log.d(TAG, matter1);
                }
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("configy.txt", Context.MODE_PRIVATE));
                outputStreamWriter.write(matter1);
                outputStreamWriter.close();


            } catch (Exception e) {
                Log.d(TAG, "Display here" + e);


            }
           words = Parse3.Calleb(context);
        //Log.d(TAG,"words before postexecute"+words[0]);
   //     Log.d(TAG,"words before postexecute"+words[0]);
            return ret;
        }
    @Override
    protected void onPostExecute(String result) {
        try {
            //Log.d(TAG,"Matter at postexecute"+ret);
          //  Log.d(TAG,"words at postexecute"+words[0]);
            context.WikiSet(ret,words);
            //this.wikiFrag=null;
        }catch(Exception e){
            Log.d(TAG,"error at wikimwdia postexecution"+e);
        }
    }


    }
