package com.example.ceaser007.jo;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Ceaser007 on 6/29/2016.
 */
public class SlideView extends PagerAdapter{
    ArrayList<Reminder> reminders;
    Context context;
    LayoutInflater inflater;
    SlideView(ArrayList<Reminder> reminders,Context context){
        this.reminders=reminders;
        this.context=context;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return reminders.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==((LinearLayout)object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        int b = 0;
        Reminder currentReminder = reminders.get(position);
        View v=inflater.inflate(R.layout.reminder_list_view, container, false);
        TextView matter = (TextView) v.findViewById(R.id.reminder);
        TextView matter2=(TextView)v.findViewById(R.id.month);

        if(currentReminder.type==6){
            Calendar calendar= Calendar.getInstance();
            int s=calendar.get(Calendar.MONTH);
            int d=calendar.get(Calendar.DAY_OF_MONTH);
            matter.setTextColor(Color.parseColor("#000000"));
            matter2.setTextColor(Color.parseColor("#000000"));
            matter.setTextSize(80);
            matter2.setTextSize(80);
            matter.setText(setter(d));
            matter2.setText(setter(s));
        }

        else {
            b = currentReminder.getHour();
            if (b >= 12) {
                if (b > 12) {
                    b = b - 12;
                }
                matter.setTextColor(Color.parseColor("#000000"));
                matter.setTextSize(40);
                matter2.setText(null);
                matter.setText(currentReminder.getMatter() + " at " + b + ":" + currentReminder.getMin() + "pm");
            } else {
                matter.setText(currentReminder.getMatter() + " at " + currentReminder.getHour() + ":" + currentReminder.getMin() + "am");
            }
        }

        v.setBackgroundResource(currentReminder.a);
        container.addView(v);
        return v;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    String setter(int a){
        String s=String.valueOf(a);
        if(a<10){
            s="0"+s;
        }
        return s;
    }
}

