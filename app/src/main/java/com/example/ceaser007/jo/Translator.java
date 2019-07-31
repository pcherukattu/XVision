package com.example.ceaser007.jo;

import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

/**
 * Created by Ceaser007 on 6/27/2016.
 */
public class Translator extends AsyncTask<Void,Void,Void> {
    private static final String CLIENT_ID="33b2d3be-7261-4e71-a13a-2247adba6e50";
    private static final String CLIENT_SECRET="nMRK3vy+QzxXqIFVActTAUIr90qjUqUVyRxUM+lSK/I=";
    private String TAG = "com.android.joe2";
    private String text;
    private String lang;
    private MainActivity activity;
    private String translatedText;
    private TextToSpeech textToSpeech;
    Translator(String text,String lang,MainActivity activity,TextToSpeech textToSpeech){
        this.text=text;
        this.lang=lang;
        this.activity=activity;
        this.textToSpeech=textToSpeech;
    }
    @Override
    protected Void doInBackground(Void... params) {
        Translate.setClientId(CLIENT_ID);
        Translate.setClientSecret(CLIENT_SECRET);
        try {
            if(lang.toLowerCase().equals("french")) {
                 translatedText = Translate.execute(text, Language.FRENCH);
            }
            else if(lang.toLowerCase().equals("spanish")){
                 translatedText = Translate.execute(text,Language.SPANISH);
            }
            else if(lang.toLowerCase().equals("russian")){
                 translatedText = Translate.execute(text,Language.RUSSIAN);
            }
            else if(lang.toLowerCase().equals("german")){
                 translatedText = Translate.execute(text,Language.GERMAN);
            }
            else if(lang.toLowerCase().equals("chinese")){
                 translatedText = Translate.execute(text,Language.CHINESE_SIMPLIFIED);
            }
            else if(lang.toLowerCase().equals("polish")){
                 translatedText = Translate.execute(text, Language.POLISH);
            }
            else if(lang.toLowerCase().equals("portuguese")){
                translatedText = Translate.execute(text, Language.PORTUGUESE);
            }
            else if(lang.toLowerCase().equals("japanese")){
                 translatedText = Translate.execute(text,Language.JAPANESE);
            }
            else{
                translatedText=null;
            }
            textToSpeech.speak(translatedText,TextToSpeech.QUEUE_ADD,null);
        } catch (Exception e) {
            Log.d(TAG,"exception in translator "+e);
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void Result) {
              activity.translatedSet(translatedText);
    }
}
