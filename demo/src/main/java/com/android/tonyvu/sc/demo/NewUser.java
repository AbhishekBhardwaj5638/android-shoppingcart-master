package com.android.tonyvu.sc.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewUser extends AppCompatActivity {

    Button bRegister;
     EditText etEmail,etPassword;
    EditText etNumber;
    Context c=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_new_user);

        etEmail=(EditText)findViewById(R.id.etNewEmail);
        etPassword=(EditText)findViewById(R.id.etNewPassword);
        etNumber=(EditText)findViewById(R.id.etNumber);
        bRegister=(Button)findViewById(R.id.bRegister);
        bRegister.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                
                User user= new User();
                String uEmail=etEmail.getText().toString();
                String uPassword=etPassword.getText().toString();
                String number=etNumber.getText().toString();
                if (isValidEmail(uEmail)) {
                    if(isPasswordValid(uPassword)){
                        if (number.length() == 10) {


                            user.setEmail(uEmail);
                            user.setPassword(uPassword);
                            user.setNumber(number);
                            C0699653TEST1 userDatabase = new C0699653TEST1(c);
                            userDatabase.dbInsert(user);
                            Toast.makeText(NewUser.this, user.getEmail(), Toast.LENGTH_SHORT).show();
                            userDatabase.close();

                            finish();


                        }else{
                            showError("number");
                        }
                    }else{
                        showError("password");
                    }
                }
                else{
                    showError("email");
                    Toast.makeText(NewUser.this, "Enter Valid Email Address!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isPasswordValid(String uPassword) {

        int numOfSpecial = 0;
        int numOfLetters = 0;
        int numOfUpperLetters = 0;
        int numOfLowerLetters = 0;
        int numOfDigits = 0;

        byte[] bytes = uPassword.getBytes();
        for (byte tempByte : bytes) {
            if (tempByte >= 33 && tempByte <= 47) {
                numOfSpecial++;
            }

            char tempChar = (char) tempByte;
            if (Character.isDigit(tempChar)) {
                numOfDigits++;
            }

            if (Character.isLetter(tempChar)) {
                numOfLetters++;
            }

            if (Character.isUpperCase(tempChar)) {
                numOfUpperLetters++;
            }

            if (Character.isLowerCase(tempChar)) {
                numOfLowerLetters++;
            }
        }


        if (numOfDigits>0 && numOfLetters>0 && numOfLowerLetters>0 && numOfSpecial>0 && numOfUpperLetters>0 && uPassword.length()>=8){
            return true;
        }else {
            return false;
        }

    }

    private void showError(String field) {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);

        switch (field) {
            case "email":
                etEmail.startAnimation(shake);
                etEmail.setError("Invalid Email Address");
            case "password":
                etPassword.startAnimation(shake);
                etPassword.setError("Password must contain 1 lowercase,1 uppercase, 1 special char,1 digit and atlest 8 characters ");
            case "number":
                etNumber.startAnimation(shake);
                etNumber.setError("Invalid Number");


        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

}
