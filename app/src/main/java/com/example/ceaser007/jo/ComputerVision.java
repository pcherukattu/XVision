package com.example.ceaser007.jo;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.google.gson.Gson;
import com.microsoft.projectoxford.emotion.EmotionServiceClient;
import com.microsoft.projectoxford.emotion.EmotionServiceRestClient;
import com.microsoft.projectoxford.emotion.contract.RecognizeResult;
import com.microsoft.projectoxford.emotion.rest.EmotionServiceException;
import com.microsoft.projectoxford.vision.VisionServiceClient;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.microsoft.projectoxford.vision.contract.AnalysisResult;
import com.microsoft.projectoxford.vision.contract.Caption;
import com.microsoft.projectoxford.vision.contract.Face;
import com.microsoft.projectoxford.vision.contract.LanguageCodes;
import com.microsoft.projectoxford.vision.contract.Line;
import com.microsoft.projectoxford.vision.contract.OCR;
import com.microsoft.projectoxford.vision.contract.Region;
import com.microsoft.projectoxford.vision.contract.Word;
import com.microsoft.projectoxford.vision.rest.VisionServiceException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ceaser007 on 10/18/2016.
 */

public class ComputerVision extends AsyncTask {
    private Uri imageUri;
    private Bitmap bitmap;
    private VisionServiceClient client=null;
    private EmotionServiceClient client1=null;
    private int COGNATIVE_SERVICE=0;
    private MainActivity mainActivity;
    private List<RecognizeResult> result1 = null;
    private String emotionResult =null;
    private TextToSpeech textToSpeech=null;
    private AnalysisResult analysisResult =null;
    private String result=null;
    private String TAG = "com.android.joe2";
    private String visionSpeech=null;


    ComputerVision(Uri imageUri, Bitmap bitmap, MainActivity mainActivity, int a, TextToSpeech textToSpeech){
        this.bitmap=bitmap;
        this.imageUri=imageUri;
        this.mainActivity=mainActivity;
        this.COGNATIVE_SERVICE=a;
        client= new VisionServiceRestClient(mainActivity.getString(R.string.vision_id));
        if(a==1){
            client1= new EmotionServiceRestClient(mainActivity.getString(R.string.emotion_id));
        }
        this.textToSpeech=textToSpeech;

    }
    @Override
    protected Object doInBackground(Object[] params) {
        String result3=process();
        return null;
    }
    private String process(){
        Log.d(TAG,"process started");
        String features[]={"Faces","Description"};
        String description[]={};

        Gson gson=new Gson();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,output);
        ByteArrayInputStream inputStream=new ByteArrayInputStream(output.toByteArray());
        try {
            if(COGNATIVE_SERVICE==1) {
                analysisResult = this.client.analyzeImage(inputStream,features,description);
                 result=gson.toJson(analysisResult);
                Log.d(TAG,"THE RESULT OF VISION "+result);
                setVision();
                ByteArrayOutputStream output1 = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,output1);
                ByteArrayInputStream inputStream1=new ByteArrayInputStream(output1.toByteArray());
                result1=this.client1.recognizeImage(inputStream1);

                 emotionResult=gson.toJson(result1);
                  if(!emotionResult.equals("[]")){
                      sortEmotion(1);
                  }
                  else{
                      sortEmotion(2);
                  }
                Log.d(TAG,"THE VALUE OF EMOTION "+emotionResult);

            }
            else {
                OCR readResult = this.client.recognizeText(inputStream, LanguageCodes.AutoDetect, true);
                result = gson.toJson(readResult);
                String result = "";
                for(Region reg:readResult.regions){
                    for(Line line:reg.lines){
                        for(Word word:line.words){
                            result += word.text + " ";
                        }
                        result += ";";
                    }
                }
                textToSpeech.speak(result,TextToSpeech.QUEUE_ADD, null);
            }
            Log.d(TAG,"The result of the analysis is"+result);

        } catch (EmotionServiceException e){
            Log.d(TAG,"The exception at EmotionVision is "+e);
        }
        catch (VisionServiceException e) {
            Log.d(TAG,"The exception at ConputerVision is "+e);
        } catch (IOException e) {
            Log.d(TAG,"The exception at ConputerVision is "+e);
        }
        return  result;
    }
    private void sortEmotion(int code) {
        int i=0;
        class Emotion implements Comparable<Emotion> {
            public double value;
            public String emotion;

            Emotion(double a, String b) {
                this.value = a;
                this.emotion = b;

            }

            @Override
            public int compareTo(Emotion another) {

                return Double.compare(another.value, this.value);
            }
        }
        if(code==1) {
            Log.d(TAG, "Sorting started");
            ArrayList<Emotion> values = new ArrayList<>();
            String emotionSpeech = "I also feel that a ";
            for (RecognizeResult r : result1) {
                Emotion temp1 = new Emotion(r.scores.anger, "angry");
                values.add(temp1);
                Emotion temp2 = new Emotion(r.scores.happiness, "happy");
                values.add(temp2);
                Emotion temp3 = new Emotion(r.scores.neutral, "happy");
                values.add(temp3);
                Emotion temp4 = new Emotion(r.scores.sadness, "sad");
                values.add(temp4);
                Emotion temp5 = new Emotion(r.scores.surprise, "suprised");
                values.add(temp5);
                Collections.sort(values);
                emotionSpeech = emotionSpeech + " person is " + values.get(0).emotion;
                i++;
                if (i >= 1 && i < result1.size()) {
                    emotionSpeech = emotionSpeech + " and that another ";
                }


                values.clear();
            }
            //visionSpeech=visionSpeech+"."+emotionSpeech;
            textToSpeech.speak(emotionSpeech,TextToSpeech.QUEUE_ADD, null);
        }
            //textToSpeech.speak(, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void setVision(){
        Gson gson=new Gson();
        int flag=0;
        int i=0;
        AnalysisResult result3=gson.fromJson(result,AnalysisResult.class);
        List<Caption> captions=result3.description.captions;
         visionSpeech="I see "+captions.get(0).text;
        String ageSpeech=null;
        List<Face> faces=result3.faces;
        if(faces.size()!=0) {
            ageSpeech=" and I also sense a person ";
            flag=1;
            for (Face temp : faces) {
                ageSpeech = ageSpeech + " of age about " + temp.age;
                i++;
                if (i >= 1 && i < faces.size()) {
                    ageSpeech = ageSpeech + " and";
                }
            }
        }
        if(flag!=0){
            visionSpeech=visionSpeech+ageSpeech;
        }



        textToSpeech.speak(visionSpeech,TextToSpeech.QUEUE_ADD,null);
        Log.d(TAG,"Speaking ended");
    }
  /*  private void speakText(String data){
        textToSpeech.speak(data);
    }*/


}
