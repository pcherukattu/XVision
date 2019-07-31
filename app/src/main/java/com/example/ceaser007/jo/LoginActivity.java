package com.example.ceaser007.jo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

/**
 * Created by Ceaser007 on 4/27/2016.
 */
public class LoginActivity extends Activity {
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private AccessToken accessToken;
    private LoginActivity loginActivity= this;
    private final static String TAG= "com.android.joe2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext(), new FacebookSdk.InitializeCallback() {
            @Override
            public void onInitialized() {
                accessToken = AccessToken.getCurrentAccessToken();
                Log.d(TAG, "Access token here" + accessToken);
                if (accessToken == null) {
                    Log.d(TAG, "user not logged yet");

                } else {
                    Log.d(TAG, "Logged in");
                    Intent main = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(main);
                }
            }
        });

        setContentView(R.layout.activity_login);
        ImageButton loginButton=(ImageButton)findViewById(R.id.login_button);
        Button button=(Button)findViewById(R.id.skip_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(main);
            }
        });

        callbackManager=CallbackManager.Factory.create();

        accessTokenTracker=new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                accessToken=currentAccessToken;
            }
        };
      final LoginManager manager = LoginManager.getInstance();


                manager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        accessToken = loginResult.getAccessToken();
                        Log.d(TAG, "Logged in");
                        Intent main = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(main);
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });


         loginButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 manager.logInWithReadPermissions(loginActivity, Arrays.asList("user_birthday"));


             }
         });

        //LoginButton loginButton=(LoginButton)findViewById(R.id.login_button);

       // List<String> permissionNeeds= Arrays.asList("publish_actions","user_birthday");
        //loginButton.setPublishPermissions("publish_actions");
       // loginButton.setReadPermissions("user_birthday");

    }
    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);
        //Facebook login
      callbackManager.onActivityResult(requestCode, responseCode, intent);


    }
    @Override
    protected void onPause() {

        super.onPause();
    }
    protected void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
    }
}
