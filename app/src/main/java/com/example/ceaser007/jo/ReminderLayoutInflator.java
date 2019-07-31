package com.example.ceaser007.jo;

import android.content.Context;
import android.view.LayoutInflater;

/**
 * Created by Ceaser007 on 5/20/2016.
 */
public class ReminderLayoutInflator {
    Context context;
    LayoutInflater inflater;
    public ReminderLayoutInflator(Context c){


        inflater=LayoutInflater.from(c);
        context=c;
    }

}
