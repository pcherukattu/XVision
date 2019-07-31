package com.example.ceaser007.jo;

import android.os.AsyncTask;
import android.util.Log;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;

/**
 * Created by Ceaser007 on 5/13/2016.
 */
public class AIExecution extends AsyncTask<AIRequest, Void, AIResponse> {
    private String TAG = "com.android.joe2";
    private MainActivity mainActivity=null;
    final AIConfiguration config = new AIConfiguration("b176a6a4158b4651a8cda12ebbaf9a99",
            AIConfiguration.SupportedLanguages.English,
            AIConfiguration.RecognitionEngine.System);
    AIExecution(MainActivity context){
        mainActivity=context;
    }


    @Override
    protected AIResponse doInBackground(AIRequest... params) {
        final AIDataService aiDataService = new AIDataService(mainActivity,config);
        final AIRequest request = params[0];
        try {
            final AIResponse   response = aiDataService.request(request);
            return response;
        } catch (AIServiceException e) {
        }
        return null;

    }
    @Override
    protected void onPostExecute(AIResponse aiResponse) {
        if (aiResponse != null) {
            Result result=aiResponse.getResult();
            Log.d(TAG,"THE ACTION IS "+result.getAction());
            mainActivity.work(result);

        }
    }
}
