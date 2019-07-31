/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ceaser007.jo;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Ceaser007
 */
 class Parse3 {



    public static String[] Calleb (Context context){
           FileInputStream inputStream1;
         String TAG = "com.android.joe2";
        String[] words1=null;


        try {
            InputStream inputStream = context.openFileInput("configy.txt");

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String readLine = "";
            while ((readLine = bufferedReader.readLine()) != null) {
                int s = readLine.indexOf(",");
                int d = readLine.indexOf("]", s);
                int r = readLine.indexOf("[", 3);
                if (d != (r + 1)) {
                    String s1 = readLine.substring(s + 2, d - 1).replaceAll("\"", "");
                    words1 = s1.split("\\,");

                }
            else{
                    Log.d(TAG,"ERRO IN PARSER");
                //words1="ERROR";
            }
        }
    }catch(Exception e){

}
        //Log.d(TAG,"BEFORE RETURN FROM  PARSER"+words1[0]);
   return words1; }

}
