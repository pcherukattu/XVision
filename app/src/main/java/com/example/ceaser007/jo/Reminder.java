package com.example.ceaser007.jo;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.jar.JarException;

/**
 * Created by Ceaser007 on 5/18/2016.
 */
public class Reminder {
    private int flag=1;

    public UUID getUuid() {
        return uuid;
    }

    private UUID uuid;
    public int a;
    public int type;
    private int min;
    private int hour;
    private int month;
    private int year;
    private int day;

    public int getIdno() {
        return idno;
    }

    public void setIdno(int idno) {
        this.idno = idno;
    }

    public int idno;
    private String matter;
    private Random random=new Random();
    public  Calendar cal=Calendar.getInstance();
    private static final String TAG = "com.android.joe2";
    private static final String JSON_INT="a";
    private static final String JSON_INT2="idno";
    private static final String JSON_MIN="min";
    private static final String JSON_HOUR="hour";
    private static final String JSON_YEAR="year";
    private static final String JSON_DAY="day";
    private static final String JSON_MONTH="month";
    private static final String JSON_ID="uuid";
    private static final String JSON_MATTER="matter";
    private static final String JSON_FLAG="flag";
    private static final String JSON_TYPE="type";
    public String getMatter() {
        return matter;
    }
    public int getMin() {
        return min;
    }

    public int getHour() {
        return hour;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getDay() {
        return day;
    }


    public JSONObject toJson()throws JarException{
        JSONObject object=new JSONObject();
        try {
            object.put(JSON_FLAG,String.valueOf(flag));
            object.put(JSON_INT,String.valueOf(a));
            object.put(JSON_MATTER,matter);
            object.put(JSON_DAY, String.valueOf(day));
            object.put(JSON_YEAR, String.valueOf(year));
            object.put(JSON_MONTH, String.valueOf(month));
            object.put(JSON_MIN,String.valueOf(min));
            object.put(JSON_HOUR,String.valueOf(hour));
            object.put(JSON_ID,uuid.toString());
            object.put(JSON_INT2,String.valueOf(idno));
            object.put(JSON_TYPE,String.valueOf(type));

        } catch (JSONException e) {
            Log.d(TAG,"JSON ERROR");
        }
        return object;
    }
   public Reminder(String s,String d){
       idno=random.nextInt(10000);
       year=cal.get(Calendar.YEAR);
       month=cal.get(Calendar.MONTH);
       day=29;                //cal.get(Calendar.DAY_OF_MONTH);
       matter=s;
       uuid=UUID.randomUUID();
       if(d!=null) {
           setterTime(d);
           calSetter();
       }
       typeSetter();
     }
    public Reminder(String s,String d,String e){
        idno=random.nextInt(10000);
        flag=0;
        matter=s;
        uuid=UUID.randomUUID();
        if((d!=null)&&(e!=null)) {
            setterTime(d);
            setterDate(e);
            calSetter();
        }
        typeSetter();
    }
    public Reminder(JSONObject jsonObject){
        try {
            a=Integer.valueOf(jsonObject.getString(JSON_INT));
            flag=Integer.valueOf(jsonObject.getString(JSON_FLAG));
            matter=jsonObject.getString(JSON_MATTER);
            uuid=UUID.fromString(jsonObject.getString(JSON_ID));
            min=Integer.valueOf(jsonObject.getString(JSON_MIN));
            hour=Integer.valueOf(jsonObject.getString(JSON_HOUR));
            year=Integer.valueOf(jsonObject.getString(JSON_YEAR));
            month=Integer.valueOf(jsonObject.getString(JSON_MONTH));
            day=Integer.valueOf(jsonObject.getString(JSON_DAY));
            idno=Integer.valueOf(jsonObject.getString(JSON_INT2));
            type=Integer.valueOf(jsonObject.getString(JSON_TYPE));
            calSetter();
        } catch (JSONException e){

        }
    }
    private void setterTime(String d){
        String s=d;

        int a=s.indexOf(":");
        String k=s.substring(0, a);
        int b=s.indexOf(":", a + 1);
        String l=s.substring(a+1,b);
        hour=Integer.parseInt(k);
        min=Integer.parseInt(l);
    }
   private void setterDate(String d){
          String s=d;
       int a=s.indexOf("-");
       String k=s.substring(0, a);
       int b=s.indexOf("-", a + 1);
       String l=s.substring(a + 1, b);
       String e=s.substring(b+1);
       year=Integer.parseInt(k);
       month=Integer.parseInt(l);
       day=Integer.parseInt(e);
   }
private void calSetter(){

    cal.set(Calendar.DAY_OF_MONTH, day);
    cal.set(Calendar.MONTH, month);
    cal.set(Calendar.YEAR, year);
    cal.set(Calendar.MINUTE, min);
    cal.set(Calendar.HOUR_OF_DAY, hour);
}
    private void typeSetter() {
        if (matter != null) {
            String words[] = matter.toLowerCase().split("\\s+");
            List<String> arrays = Arrays.asList(words);
            if (arrays.contains("meeting") || arrays.contains("meet")||arrays.contains("call")) {
                type = 1;
            } else if (arrays.contains("play") || arrays.contains("match") || arrays.contains("against") || arrays.contains("vs")) {
                type = 2;
            } else if (arrays.contains("pills") || arrays.contains("pill") || arrays.contains("medicine") || arrays.contains("syrup")) {
                type = 3;
            }
            else if(arrays.contains("party")||arrays.contains("celebration")||arrays.contains("celebrations")){
                type=4;
            }
            else{
                type =5;
            }
        }
    }
}