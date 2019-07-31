package com.example.ceaser007.jo;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Ceaser007 on 3/25/2016.
 */
public class Parser {
    private static String ans;
    private static String receiveString = "";
    private static StringBuilder stringBuilder=null;
    private static String TAG = "com.android.joe2";
    private static String ret=null;
    public static String setter(Context context) {
        String[] words=null;
        Log.d(TAG, "at parser");
        int flag = 0;
        int k = 0;
        int d2=0;
        String a2=null;

        ans = "";

        try {

            InputStream inputStream = context.openFileInput("config.txt");

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
            while ((receiveString = bufferedReader.readLine()) != null){
                String a=receiveString;
                Log.d(TAG,"Received at parse "+a);
                int c=a.indexOf("(");
                int c2=a.indexOf("(",c+1);
                int d=a.indexOf(")");
                if(d>c2){
                    d2=a.indexOf(")",d+1);
                }
                else{
                    d2=d;
                }
                if(d2>=0) {

                    Log.d(TAG,"First");
                    String s2 = a.substring(d2 + 1);
                    String s1 = a.substring(0, c);
                    a2 = s1 + s2;
                    words = a2.split("\\s+");
                    //  System.out.println(a);
                }
                else{
                    a2=a;
                    words = a2.split("\\s+");
                }
            }
            inputStream.close();

            int a3=a2.length();
            Log.d(TAG,"At parser the values  "+a2.length()+" "+a2.indexOf(":",a3-1) );
            if(a3<600) {
                if (a2.indexOf(":", a3 - 1) == (a3 - 1)) {
                    ans = "ERROR";
                    Log.d(TAG, "matter is null");
                }
            }
            if(!(ans.equals("ERROR"))){
                a2="";
            for (int i = 0; i < words.length; i++) {
                    int b=words[i].indexOf("[");
                    int f=words[i].indexOf(".");
                    if(f>=0){
                        k++;
                    }
                    if(b>=0){
                        String d=words[i].substring(0, b);
                        ans+=d+" ";
                    }
                    else{
                        ans+=words[i]+" ";
                    }




                if(k>3){
                    break;
                }

            }}

        } catch (Exception e) {
            Log.d(TAG, "File" +e);
        }
        try {

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write("");
            outputStreamWriter.close();

        } catch (Exception e) {
            Log.d(TAG, "here" + e);
        }

        return ans;
    }
    public static String fbStatusId(String s){
        String e=null;
        int a=s.indexOf("\"message\":");
        if(a>0){
        String q=s.substring(0, a);
        int b=q.indexOf("\"id\":");
        int c=q.indexOf("\"created_time\":");
         e=q.substring(b+6,c-2);
        }
        Log.d(TAG,"Ans in parser "+e);
        return e;
    }
}

