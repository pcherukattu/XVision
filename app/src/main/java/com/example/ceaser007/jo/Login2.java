package com.example.ceaser007.jo;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Ceaser007 on 4/6/2016.
 */
  public class Login2 extends AsyncTask<String, Void, String[]> {
    //  private static String data=null;
    private MainActivity context;
    private WikiFrag wikiFrag;
    private String[] words;
     private String matter=null;
    private String matter2=null;

    // private  static HttpURLConnection urlCon=null;
    Login2(MainActivity c) {
        context = c;
        this.wikiFrag=wikiFrag;
    }


    @Override
    protected String[] doInBackground(String... params) {
        matter=null;
        String data1=null;
        HttpURLConnection urlCon1=null;
        String data = null;
        HttpURLConnection urlCon = null;
         matter = params[0];
        String matter1 = "";
        String TAG = "com.android.joe2";
        String urlstr = "http://en.wikipedia.org/w/api.php";
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
            URL url = new URL(urlstr);
            urlCon = (HttpURLConnection) url.openConnection();
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
        words= Parse3.Calleb(context);
        try {
            data1= URLEncoder.encode("format","UTF-8")+"="+ URLEncoder.encode("json","UTF-8");
            data1+="&"+ URLEncoder.encode("action","UTF-8")+"="+ URLEncoder.encode("opensearch","UTF-8");
            data1+="&"+URLEncoder.encode("limit","UTF-8")+"="+URLEncoder.encode("1","UTF-8");
            data1+="&"+URLEncoder.encode("prop","UTF-8")+"="+URLEncoder.encode("extracts","UTF-8");
            data1+="&"+URLEncoder.encode("redirects","UTF-8")+"="+URLEncoder.encode("resolve","UTF-8");
            //data+="&"+URLEncoder.encode("exintro","UTF-8")+"="+URLEncoder.encode("explaintext","UTF-8");
            data1+="&"+URLEncoder.encode("search","UTF-8")+"="+URLEncoder.encode(matter,"UTF-8");


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            URL url = new URL(urlstr);
            urlCon1=(HttpURLConnection) url.openConnection();
            urlCon1.setDoOutput(true);
            urlCon1.setRequestMethod("POST");
            urlCon1.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlCon1.setRequestProperty("Content-Language", "en-US");
            OutputStreamWriter os=new OutputStreamWriter(urlCon1.getOutputStream());
            os.write(data1);
            os.close();
            BufferedReader br1=new BufferedReader(new InputStreamReader(urlCon1.getInputStream()));
            String line="";
            OutputStreamWriter outputStreamWriter1 = new OutputStreamWriter(context.openFileOutput("config4.txt", Context.MODE_PRIVATE));
            while((line=br1.readLine())!=null){
                Log.d(TAG,line);
                outputStreamWriter1.write(line);

            }
            outputStreamWriter1.close();
            br1.close();

        }
        catch (Exception e) {
            Log.d(TAG, "Display" + e);


        }
        String s=Parser2.main1(context);
        matter2=s;
        Log.d(TAG,"Matter orginal "+s);
        return words;



    }

    @Override
    protected void onPostExecute(String[] result) {
     context.WikiSet(matter2,words);
      this.wikiFrag=null;

    }
}