package com.example.ceaser007.jo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Ceaser007 on 6/28/2016.
 */
public class ReminderView extends LinearLayout {
    TextView textView;
    TextView textView1;

    public ReminderView(Context context,String s) {
        super(context);
        inflater(context,s);
    }
    public void inflater(Context context,String text){
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.reminder_list_view,this);
        this.textView=(TextView)view.findViewById(R.id.reminder);
        this.textView1=(TextView)view.findViewById(R.id.month);
        textView.setText(text);
        view.setBackgroundResource(R.drawable.restaurant_view);
    }

}
