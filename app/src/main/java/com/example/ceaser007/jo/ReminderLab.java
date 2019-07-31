package com.example.ceaser007.jo;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Ceaser007 on 5/18/2016.
 */
public class ReminderLab {
    private static final String TAG = "com.android.joe2";
    private static final String FILENAME = "reminder.json";
    private final int[]type2={R.drawable.play2,R.drawable.play1};
    private final int[]type3={R.drawable.pill};
    private final int[]type4={R.drawable.party2};
    private final int[]type1 = { R.drawable.test3,R.drawable.trial};
    private int j1=0,k1=0,l1=0,m1=0;
    private ArrayList<Reminder> reminders = new ArrayList<>();
    private JSONserialiser nserialiser;
    private static ReminderLab reminderLab;
    private Context context;
    private int j = 0;

    private ReminderLab(Context c) {
        context = c;
        nserialiser = new JSONserialiser(c, FILENAME);
        try {
            reminders = nserialiser.loadReminders();
        } catch (Exception e) {
            Log.d(TAG, "Error in Loading" + e);
        }

    }

    public static ReminderLab get(Context c) {
        if (reminderLab == null) {
            reminderLab = new ReminderLab(c);
        }
        return reminderLab;
    }

    public ArrayList<Reminder> getReminders() {
        ArrayList<Reminder>reminders1=new ArrayList<>();
        Reminder r=new Reminder(null,null);
        r.type=6;
        long b=System.currentTimeMillis();
        for (Reminder reminder : reminders){
           if((reminder.cal.getTimeInMillis()>b)||reminder.type==6 ){

               reminders1.add(reminder);
            }

        }
        Log.d(TAG,"giving reminders");
        reminders.clear();
        reminders=reminders1;
        reminders.add(0,r);
        //reminders.add(1,r);
        setBackground();
        return reminders1;
    }

    public Reminder getReminder(int id) {
        for (Reminder r : reminders) {
            if (r.getIdno()==id) {
                return r;
            }
        }
        return null;
    }

    public void addReminder(Reminder r) {
        reminders.add(r);
    }

    public boolean saveReminder() {
        try {
                nserialiser.saveReminders(reminders);
                return true;

        } catch (Exception e) {
            Log.d(TAG, "ERROR IN REMINDER SAVING IN LAB" + e);

        }
        return false;
    }
    public void deleteReminder(Reminder r){

        reminders.remove(r);
        Log.d(TAG, "Removed r");
    }
    private void setBackground() {
        for (Reminder r : reminders) {
            if(r.type==6){
                r.a=R.drawable.morning;
            }
            else if (r.type == 1) {
                r.a=type1[j1++];
                if (j1 >= type1.length) {
                    j1 = 0;
                }
            } else if (r.type==2){
                r.a=type2[k1++];
                if(k1>=type2.length){
                    k1=0;
                }
            }
            else if(r.type==3){
                r.a=type3[l1++];
                if(l1>=type3.length){
                    l1=0;
                }
                else {
                    r.a=type4[m1++];
                    if(m1>=type4.length){
                        m1=0;
                    }

                }
            }

        }
    }
}





