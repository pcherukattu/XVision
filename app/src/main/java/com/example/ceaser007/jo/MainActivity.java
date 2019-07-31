package com.example.ceaser007.jo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.location.Location;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;


public class MainActivity extends FragmentActivity{
    //private  MainActivity mainActivity=MainActivity.this;
    public SpeechRecognizer speechRecognizer;
    public Intent speechRecognizerIntent;
    //private Boolean misListening;
    private String TAG = "com.android.joe2";
    private static final String MUSIC_AP = "music";
    private static final String ALARM_CLK = "alarm";
    private static final String CALL_USER = "call";
    private static final String FB_STATUS = "fb_status";
    private static final String PLACE_STATUS = "place_point";
    private static final String Vision_Api="vision_api";
    private static final String Read_Api = "read_api";
    private static final String TRANSLATOR="translator";
    private int FB_UPDATE = 0;
    private AlarmClk alarmClk = null;
    private TextToSpeech textToSpeech;
    private String STATUS = null;
    final AIRequest aiRequest = new AIRequest();
    private int flag = 0;
    public ImageButton listen;
    private MainFrag mainFrag;
    private MainFrag2 mainFrag2 = null;
    private WikiFrag wikiFrag = null;
    private Timer timer = new Timer();
    private AudioManager amanager;
    private android.os.Handler handler = null;
    private GoogleApiClient googleApiClient;
    private Location mLocation;
    private static final int REQUEST_SELECT_IMAGE = 0;
    private int COGNATIVE_SERVICE=0;

    private BroadcastReceiver onNotificationTriggered = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d(TAG, "INSIDE RECEIVER");
            mainFrag.upDate();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        if (findViewById(R.id.fragment_container) != null) {
            mainFrag = new MainFrag();
            wikiFrag = new WikiFrag();
            mainFrag2 = new MainFrag2();
            textToSpeech = new TextToSpeech(getApplicationContext(),  new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status != TextToSpeech.ERROR) {
                        textToSpeech.setLanguage(Locale.UK);
                         Log.d(TAG,"The status of text to speech inside the main activity "+status);
                    }
                }
            });
            amanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, mainFrag).addToBackStack(null);
            fragmentTransaction.commit();
        }

        listen = (ImageButton) findViewById(R.id.btnSpeak);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 4500);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 1800);
        SpeechRecognitionListener listener = new SpeechRecognitionListener();
        speechRecognizer.setRecognitionListener(listener);
        Log.d(TAG, "here itself");
        listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Log.d(TAG, "Clicked");
                    flag=1;
          speechRecognizer.startListening(speechRecognizerIntent);
           // mainFrag.fbLikes();
        } catch (Exception e) {
            Log.d(TAG,"here"+e);


        }

         }
});

         }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_SELECT_IMAGE:
                if(resultCode ==RESULT_OK){
                    Log.d(TAG,"The image uri at activity result in main actvty"+data.toString());
                    Uri imageUri=data.getData();
                    Bitmap mBitmap=ImageHalper.loadSizeLimitedBitmapFromUri(imageUri,getContentResolver());
                    ComputerVision computerVision=new ComputerVision(imageUri,mBitmap,this,COGNATIVE_SERVICE,textToSpeech);
                    computerVision.execute();



                }
        }


    }
    public void translatedSet(String text){
        android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        if(wikiFrag!=null&&wikiFrag.isAdded()){
            fragmentTransaction.hide(wikiFrag);
        }
        if(mainFrag!=null){
            fragmentTransaction.show(mainFrag);
        }
        else{
            mainFrag=new MainFrag();
            fragmentTransaction.add(R.id.fragment_container, mainFrag).addToBackStack(null);
        }
        fragmentTransaction.commit();
        mainFrag.changeText("well its "+text);
    }
    public void placeSet(ArrayList<Place> places, String types){
        Log.d(TAG, "beggining trasaction in placeset");
        Intent i=new Intent(MainActivity.this,PlacesFrag.class);
        i.putParcelableArrayListExtra("places1", places);
        i.putExtra("place_type",types);
        Log.d(TAG, "Ending trasaction in placeset");
        startActivity(i);

    }
    public void WikiSet(String s,String[] words){
        Log.d(TAG, "matter  at main" + s);
       // Log.d(TAG,"words  at main"+words[0]);
        flag=2;
        if(s.equals(null)){
            Log.d(TAG,"matter is null at main");
        }
        android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                if(wikiFrag==null) {
                    wikiFrag = new WikiFrag();
                }
        try {


            if(wikiFrag.isAdded()){
                fragmentTransaction.show(wikiFrag);
                fragmentTransaction.hide(mainFrag);
               if((mainFrag2!=null)&&(mainFrag2.isAdded())){ fragmentTransaction.hide(mainFrag2);}

            }
            else {

                fragmentTransaction.add(R.id.fragment_container, wikiFrag);
                fragmentTransaction.hide(mainFrag);

            }
            fragmentTransaction.commit();


        } catch (Exception e) {
            Log.d(TAG,"What mann");
        }
        wikiFrag.updater(s, words);
   }

    public void work(Result d) {

        if((FB_UPDATE==1)&&(d.getAction().equals("Confirmation"))){
            FB_UPDATE=0;
            mainFrag.fbStatusUpdate();
        }
        else {


           switch(d.getAction()) {
               case "Wiki":    Wikimedia wikimedia = new Wikimedia("Hellow", this);
                                try {
                                String k = "temp";
                                k.replaceAll(" ", "_");
                                    Log.d(TAG,"the wiki function is called");
                                String s1 = wikimedia.execute("Albert_Einstein").get(); /* for testing*/
                                    Log.d(TAG, "in main " + s1);
                                if (mainFrag2 == null) {
                                    mainFrag2 = new MainFrag2();
                                }
                                flag = 1;

                                 } catch (Exception e)

                               {
                               Log.d(TAG, "in main " + e);
                              }
                                break;


               case ALARM_CLK:   String time = "time";
                                 alarmClk = new AlarmClk(MainActivity.this, time);
                                 alarmClk.execute();
                                 break;
               case Vision_Api:
               case Read_Api :

                                   COGNATIVE_SERVICE=0;
                                   if(d.getAction().equals("vision_api")){
                                       COGNATIVE_SERVICE=1;
                                   }
                                   else{
                                       COGNATIVE_SERVICE=2;
                                   }

                                   Intent intent1 =new Intent(MainActivity.this,CameraActivity.class);

                                   Log.d(TAG,"Call camera activity");
                                   startActivityForResult(intent1,REQUEST_SELECT_IMAGE);
                                   break;



               case MUSIC_AP:    try {
                                 Intent intent = new Intent(MediaStore.INTENT_ACTION_MEDIA_PLAY_FROM_SEARCH);
                                 intent.putExtra(MediaStore.EXTRA_MEDIA_FOCUS, "vnd.android.cursor.item/*");
                                 startActivity(intent);
                                 Log.d(TAG, "here");
                                 } catch (Exception e) {
                                      Log.d(TAG, "Error");
                                 }
                                 break;
               case CALL_USER:String name = "temp";
                              CallUser callUser = new CallUser(MainActivity.this, name);
                              callUser.execute();
                              break;
                   //Intent intent=new Intent(MainActivity.this,CallerActivity.class);
                   //intent.putExtra(CallerActivity.EXTRA_NAME,name);
                   //startActivity(intent);
               case "Status_Update" :Log.d(TAG, "INSIDE FBSTATUS "+ d.getParameters());
                              STATUS = "Hellow Everyone";

                               mainFrag.fbConfirm(d.getStringParameter("text"));
                               FB_UPDATE = 1;
                                break;
               case "Quick_Reminder":

                   Reminder r=new Reminder(d.getStringParameter("matter"),d.getStringParameter("time"));
                   AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                   Intent notification=new Intent(this,BroadCastReceiver.class);
                   notification.putExtra("Content", r.getMatter());
                   PendingIntent broadcast=PendingIntent.getBroadcast(this,r.idno, notification,0);
                   alarmManager.set(AlarmManager.RTC_WAKEUP, r.cal.getTimeInMillis(), broadcast);
                   mainFrag.mainAdd(r);
                                        break;
               case PLACE_STATUS:
                   GPSTracker tracker = new GPSTracker(this);
                   LoadPlaces  loadPlaces=new LoadPlaces(this,tracker.getLatitude(),tracker.getLongitude(),d.getStringParameter("place_type"));

                   if (!tracker.canGetLocation) {
                       tracker.showAlertDialog();
                       break;
                   }
                   try {
                       loadPlaces.execute();
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
                   break;
               case TRANSLATOR:

                   Translator translator=new Translator(d.getStringParameter("text"),d.getStringParameter("lang"),this,textToSpeech);
                   translator.execute();
                   break;
               default:   Log.d(TAG, "ERROR");
                   // 100012003073312_123965614680207/likes
                            break;
           }
        }
       // LoginManager.getInstance().logInWithReadPermissions(mainActivity, Arrays.asList("user_status","user_posts"));
        //mainFrag.fbLikes();

    }
    protected class SpeechRecognitionListener implements RecognitionListener {
        private String result=new String();
        android.os.Handler.Callback callback = new android.os.Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
               // work(result);
                 aiExecute(result);
                return false;
            }
        };

        class SmallDelay extends TimerTask {
            public void run() {
                handler.sendEmptyMessage(0);

            }
        }


        @Override
        public void onReadyForSpeech(Bundle params) {
           // amanager.setStreamMute(AudioManager.STREAM_MUSIC,false);

        }

        @Override
        public void onBeginningOfSpeech() {

        }

        @Override
        public void onRmsChanged(float rmsdB) {

        }

        @Override
        public void onBufferReceived(byte[] buffer) {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onError(int error) {
            speechRecognizer.startListening(speechRecognizerIntent);


        }

        @Override
        public void onResults(Bundle results) {

            handler=new Handler(callback);
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            result = matches.get(0);
            Log.d(TAG, "Value of flag "+flag);
            if(flag!=0) {

                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().show(mainFrag);
               if((mainFrag2!=null)&&mainFrag2.isAdded()){ fragmentTransaction.hide(mainFrag2);}
                if( wikiFrag.isAdded()){fragmentTransaction.hide(wikiFrag);}
                fragmentTransaction.commit();
            }
                mainFrag.changeText(result);
            timer.schedule(new SmallDelay(), 100);

        }


        @Override
        public void onPartialResults(Bundle partialResults) {

        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        speechRecognizer.destroy();
        unregisterReceiver(onNotificationTriggered);

    }
    class SmallDelay extends TimerTask {
        public void run() {
            handler.sendEmptyMessage(0);

        }
    }
    private void aiExecute(String s){
       final AIResponse response;
        aiRequest.setQuery(s);
        AIExecution execution=new AIExecution(this);
        execution.execute(aiRequest);

    }
    @Override
    public void onResume(){
        super.onResume();
        IntentFilter filter=new IntentFilter(BroadCastReceiver.ACTION_SHOWN_NOTIFICATION);
        registerReceiver(onNotificationTriggered, filter);
    }

}

