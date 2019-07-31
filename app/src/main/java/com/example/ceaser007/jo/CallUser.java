package com.example.ceaser007.jo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

/**
 * Created by Ceaser007 on 2/13/2016.
 */
public class CallUser extends AsyncTask<Void,Void,Void> {
    Context context =null;
    private String TAG="com.android.joe2";
    String name=null;

    public CallUser(Context context, String name){
        this.context=context;
        this.name=name;

    }
    @Override
    protected Void doInBackground(Void... params) {
        Intent intent=new Intent(Intent.ACTION_CALL);
        String phonenumber=getNumber(name,context);
        intent.setData(Uri.parse("tel:"+phonenumber));
        Log.d(TAG,phonenumber);
            context.startActivity(intent);

            return null;
    }
    public String getNumber(String name,Context context){
        String ret=null;
        String selection= ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" like'"+ name+"'";
        String[] projection =new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor c =context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,projection,selection,null,null);
         if (c.moveToFirst()){
            ret=c.getString(0);
        }
        c.close();
        if(ret==null){
            ret="unsaved";
        }
        return ret;
        }
    }

