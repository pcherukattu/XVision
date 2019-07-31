package com.example.ceaser007.jo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;

/**
 * Created by Ceaser007 on 10/17/2016.
 */
public class CameraActivity extends Activity {
    private Uri mUriPhotoTaken;

    static final int REQUEST_IMAGE=1;
    private String TAG = "com.android.joe2";
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        takePhoto();
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        Log.d(TAG,"on Activity result called at camera Activity "+requestCode);
        switch (requestCode){
            case REQUEST_IMAGE:
                if(resultCode== RESULT_OK){
                    Log.d(TAG,"ISIDE OK RESULT IN camera Activity");
                    Log.d(TAG,"the image uri is "+mUriPhotoTaken);
                    Uri imageUri=mUriPhotoTaken;
                    Intent intent = new Intent();
                    intent.setData(imageUri);
                    setResult(RESULT_OK, intent);
                    finish();

                }
        }


    }
    public void takePhoto(){


        Intent intent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager())!=null){
            File storageDir=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            try {
                File file = File.createTempFile("IMG_", ".jpg", storageDir);
                mUriPhotoTaken=Uri.fromFile(file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,mUriPhotoTaken);
                startActivityForResult(intent, REQUEST_IMAGE);

            }
            catch (Exception e){
                Log.d(TAG,"Exception on getting image ");

            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri",mUriPhotoTaken);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        mUriPhotoTaken = savedInstanceState.getParcelable("file_uri");
    }
}
