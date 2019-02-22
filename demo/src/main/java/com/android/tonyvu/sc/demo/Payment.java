package com.android.tonyvu.sc.demo;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.internal.widget.ButtonBarLayout;
import android.telephony.SmsManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Payment extends AppCompatActivity {

    TextView switchStatus;
    Switch mySwitch;
    EditText cardNumber,cvv,pin;
    Button pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Toast.makeText(this, "Guest Mail"+getIntent().getStringExtra("guestMail"), Toast.LENGTH_SHORT).show();

        String email=getIntent().getStringExtra("email");
        String store=getIntent().getStringExtra("store");
        String totalprice=getIntent().getStringExtra("totalprice");

        cardNumber=(EditText)findViewById(R.id.etCardNumber);
        cvv=(EditText)findViewById(R.id.etCVVNumber);
        pin=(EditText)findViewById(R.id.etPin);

        switchStatus=(TextView)findViewById(R.id.cardStatus);
        mySwitch=(Switch)findViewById(R.id.mySwitch);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if(isChecked){
                    switchStatus.setText("Enter the Credit Card Credentials");
                   /* pin.setInputType(InputType.TYPE_NULL);
                    pin.setKeyListener(null);*/
                   pin.setFocusableInTouchMode(false);
                   pin.setFocusable(false);
                }
                else{
                    switchStatus.setText("Enter Debit Card Credentials");
                    pin.setFocusableInTouchMode(true);
                    pin.setFocusable(true);
                }
            }
        });

        pay=(Button)findViewById(R.id.bPayNow);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cardNum = cardNumber.getText().toString();
                String cvvNum = cvv.getText().toString();
                String pinNum = pin.getText().toString();

                if (cardNum.length()==16) {
                    if (!cvvNum.isEmpty()) {

                        if (!pinNum.isEmpty()) {


                            if (getIntent().getStringExtra("email") != null) {
                                productDatabase pd = new productDatabase(Payment.this);
                                Order o = new Order();

                                o.setEmail(getIntent().getStringExtra("email"));


                                o.setPrice(Float.parseFloat(getIntent().getStringExtra("totalprice")));
                                Toast.makeText(Payment.this, "Price is " + getIntent().getStringExtra("totalprice"), Toast.LENGTH_SHORT).show();
                                pd.dbInsert(o);
                                pd.close();
                            }



                            sendEmail();
                            String message = "Your Order has been placed and payment has been done of " + getIntent().getStringExtra("totalprice") +
                                    " dollars, Through card number " + cardNumber.getText().toString() + " Pick your item from" + getIntent().getStringExtra("store");
                            if (getIntent().getStringExtra("email") != null) {

                                String number = "";
                                C0699653TEST1 userData = new C0699653TEST1(Payment.this);
                                List<User> allUserData = userData.dbSearch();

                                for (int i = 0; i < allUserData.size(); i++) {
                                    if (getIntent().getStringExtra("email").equals(allUserData.get(i).getEmail())) {
                                        number = allUserData.get(i).getNumber();
                                    }
                                }
                                Toast.makeText(Payment.this, "Number is " + number, Toast.LENGTH_SHORT).show();

                                if (!number.equals("")) {
                                    sendSMS(number, message);
                                } else {
                                    Toast.makeText(Payment.this, "Guest User can not get sms service", Toast.LENGTH_SHORT).show();
                                }


                            }




                        } else {
                            showError("pin");

                        }
                    } else {
                        showError("cvv");
                    }
                }
                else {
                    showError("cardNum");

                }
            }
        });




    }

    private void showError(String field) {
        Animation shake = AnimationUtils.loadAnimation(this,R.anim.shake);

        switch (field){
            case "cardNum":
                cardNumber.startAnimation(shake);
                cardNumber.setError("Enter Card Number");
            case "cvv":
                cvv.startAnimation(shake);
                cvv.setError("Enter CVV Number");
            case "pin":
                pin.startAnimation(shake);
                pin.setError("Enter Security Pin");

        }

    }


    public void sendSMS(String phoneNo, String msg) {
        /*try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }*/

        if (phoneNo.length()>0 && msg.length()>0)
        {
            // call the sms manager
            PendingIntent pi = PendingIntent.getActivity(this, 0,
                    new Intent(this, Payment.class), 0);
            SmsManager sms = SmsManager.getDefault();
            // this is the function that does all the magic
            sms.sendTextMessage(phoneNo, null, msg, pi, null);
        }
        else
        {
            // display message if text fields are empty
            Toast.makeText(getBaseContext(),"All field are required",Toast.LENGTH_SHORT).show();
        }

    }

    private void sendEmail() {
        //Getting content for email
         String email=getIntent().getStringExtra("email");


        if(getIntent().getStringExtra("email")!=null){

             }
             else{
            email=getIntent().getStringExtra("guestMail");
            Log.d("after Condition",email);
            Toast.makeText(this, "Send Mail  to "+email, Toast.LENGTH_SHORT).show();

        }
        String subject = "Ghar Ka Khana Order's Payment";
        String message ="Your Order has been placed and payment has been done of "+getIntent().getStringExtra("totalprice")+
                " dollars, Through card number "+cardNumber.getText().toString()+" Pick your item from"+getIntent().getStringExtra("store");

        //Creating SendMail object
        SendMail sm = new SendMail(this, email, subject, message);
        //Executing sendmail to send email
        sm.execute();
    }

}
