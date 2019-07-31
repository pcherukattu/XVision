/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ceaser007.jo;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 *
 * @author Ceaser007
 */
public class Parser2 {


    public static String main1(Context context) {
        // TODO code application logic here

        String[] words=null;
       int  flag=0;
        int flag2=0;

        String ans=null;


        try {
            InputStream inputStream = context.openFileInput("config4.txt");

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String readLine="";
            while((readLine=bufferedReader.readLine())!=null){
                String a=readLine;
               int v= a.indexOf(",");

               int c=a.indexOf(",",v+1);
               String r=a.substring(v + 3, c - 2);

               int g=a.indexOf("]", c);
                int r1= a.indexOf("is");

               if((c+2)!=(g)){
               String s=a.substring(r1,g).replaceAll("\"", "");
                   ans=r+" "+s;

              if(s.indexOf(":")==(s.length()-1)){
                   flag =1;

              }
        }
               else {
                   flag2=1;
               }
               }
        }catch (Exception ex) {
           System.out.println("The exceptio is "+ex);
        }
        if((flag2==1)||(flag==1)){
            return "ERROR";
        }
    return ans;
}
}

