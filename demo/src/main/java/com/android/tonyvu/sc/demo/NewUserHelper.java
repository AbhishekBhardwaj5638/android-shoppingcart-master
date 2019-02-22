package com.android.tonyvu.sc.demo;

import android.widget.EditText;

/**
 * Created by macstudent on 2017-04-20.
 */

public class NewUserHelper  {

   private final EditText emailField;
    private final EditText passwordField;

    public NewUserHelper(NewUser activity){
        emailField=(EditText)activity.findViewById(R.id.etNewEmail);
        passwordField=(EditText)activity.findViewById(R.id.etNewPassword);

    }


}
