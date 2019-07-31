package com.example.ceaser007.jo;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Ceaser007 on 5/19/2016.
 */
public class ReminderAdapter extends BaseAdapter{
    private String TAG = "com.android.joe2";
    ArrayList<Reminder> reminders=new ArrayList<>();
    Context c;
    LayoutInflater layoutInflater;

    public ReminderAdapter(Context c,ArrayList<Reminder> reminders){

               this.reminders=reminders;
               layoutInflater=LayoutInflater.from(c);
                this.c=c;
    }

      @Override
    public int getCount() {
          Log.d("TAG","count is ok");
       return reminders.size();
    }

    @Override
    public Reminder getItem(int position) {
        return reminders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG,"inside adapter");
        int b = 0;
        ViewHolder holder;
        Reminder currentReminder = getItem(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.reminder_list_view, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
        if(currentReminder.type==6){
           Calendar calendar= Calendar.getInstance();
            int s=calendar.get(Calendar.MONTH);
            int d=calendar.get(Calendar.DAY_OF_MONTH);
            holder.matter.setTextColor(Color.parseColor("#000000"));
            holder.matter2.setTextColor(Color.parseColor("#000000"));
            holder.matter.setTextSize(80);
            holder.matter2.setTextSize(80);
            holder.matter.setText(setter(d));
            holder.matter2.setText(setter(s));
        }

        else {
            b = currentReminder.getHour();
            Log.d(TAG, "HERE ACTUALLY " + currentReminder.getMatter());
            if (b >= 12) {
                if (b > 12) {
                    b = b - 12;
                }
                holder.matter.setTextColor(Color.parseColor("#000000"));
                holder.matter.setTextSize(40);
                holder.matter2.setText(null);

                holder.matter.setText(currentReminder.getMatter() + " at " + b + ":" + currentReminder.getMin() + "pm");
            } else {
                holder.matter.setText(currentReminder.getMatter() + " at " + currentReminder.getHour() + ":" + currentReminder.getMin() + "am");
            }
        }

                convertView.setBackgroundResource(currentReminder.a);

        return convertView;
    }

        private class ViewHolder {
            TextView matter;
            TextView matter2;

            public ViewHolder(View v) {
                matter = (TextView) v.findViewById(R.id.reminder);
               matter2=(TextView)v.findViewById(R.id.month);
            }

        }
    String setter(int a){
        String s=String.valueOf(a);
        if(a<10){
            s="0"+s;
        }
        return s;
    }
    }

