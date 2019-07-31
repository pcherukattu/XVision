package com.example.ceaser007.jo;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

//import com.facebook.login.LoginManager;

/**
 * Created by Ceaser007 on 5/7/2016.
 */
public class AlertDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        return new android.app.AlertDialog.Builder(getActivity()).setTitle("Alert").setMessage("Please authorize Jo to post to your account")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //LoginManager.getInstance().logInWithPublishPermissions(getActivity(), Arrays.asList("publish_actions"));
                    }
                }).create();
    }
}
