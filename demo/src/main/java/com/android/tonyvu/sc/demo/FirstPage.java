package com.android.tonyvu.sc.demo;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tonyvu.sc.demo.model.Product;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;

public class FirstPage extends AppCompatActivity {


    private ImageView logo;
    private ConstraintLayout constraintLayout, cs;
    private EditText email, password, guestEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_first_page);

        cs = (ConstraintLayout) findViewById(R.id.constraintLayout);
        constraintLayout = (ConstraintLayout) findViewById(R.id.containerConstraintLayout);

        cs.setVisibility(View.INVISIBLE);


        new CountDownTimer(50000, 2000) {

            public void onTick(long millisUntilFinished) {
                // bookITextView.setVisibility(GONE);
                //loadingProgressBar.setVisibility(GONE);

                constraintLayout.setBackgroundColor(ContextCompat.getColor(FirstPage.this, R.color.colorSplashText));
                logo = (ImageView) findViewById(R.id.logo);
                logo.setImageResource(R.drawable.gkg_logo);


                // mechanism of animation
                startAnimation();

            }

            public void onFinish() {
                //mTextField.setText("done!");
            }
        }.start();

        guestEmail = (EditText) findViewById(R.id.guestMail);


        // private TextView newUser,guestUser;
        TextView newUser = (TextView) findViewById(R.id.newUser);
        newUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent goTONewUser = new Intent(FirstPage.this, NewUser.class);
                startActivity(goTONewUser);


            }
        });
        Button login = (Button) findViewById(R.id.bLogin);
        this.email = (EditText) findViewById(R.id.etEmail);
        password = (EditText) findViewById(R.id.etPassword);

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                List<User> userList = loadUser();
                Toast.makeText(FirstPage.this, "Button is Clicked", Toast.LENGTH_SHORT).show();

                String uEmail = email.getText().toString();
                String uPassword = password.getText().toString();


                if (userList.size() == 0) {
                    Toast.makeText(FirstPage.this, "Database is Empty", Toast.LENGTH_SHORT).show();
                }

                Log.d("user12 entries", uEmail + "        " + uPassword);


                if (isValidEmail(uEmail)  ) {


                    for (int i = 0; i < userList.size(); i++) {
                        String userEmail = userList.get(i).getEmail();
                        String userPassword = userList.get(i).getPassword();
                        Log.d("database entries", userEmail + "        " + userPassword);


                        if (uEmail.equals(userEmail) && uPassword.equals(userPassword)) {
                            Intent goToProducts = new Intent(getBaseContext(), MainActivity.class);
                            goToProducts.putExtra("email", uEmail);
                            startActivity(goToProducts);
                            finish();

                        }
                        else{
                            showError("mismatch");
                        }
                    }
                } else {
                    showError("email");
                    Toast.makeText(FirstPage.this, "Enter valid email address!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

// validating password

    private void showError(String field) {
        Animation shake = AnimationUtils.loadAnimation(this,R.anim.shake);

        switch (field){
            case "email":
                email.startAnimation(shake);
                email.setError("Invalid Email Address");
            case "guest":
                guestEmail.startAnimation(shake);
                guestEmail.setError("Invalid Guest Email Address");
            case "mismatch":
                email.startAnimation(shake);
                email.setError("email/password is incorrect");
                password.startAnimation(shake);
                password.setError("email/password is incorrect");
        }

    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public void goForGuest(View v) {
        String guestMail=guestEmail.getText().toString();
        if(isValidEmail(guestMail)){
        Intent goToGuestProduct=new Intent(FirstPage.this,MainActivity.class);
        goToGuestProduct.putExtra("guestMail",guestMail);
        startActivity(goToGuestProduct);}
        else{
            showError("guest");
        }
    }

    public List<User> loadUser(){
        C0699653TEST1 userDatabase=new C0699653TEST1(this);
        List<User> users=userDatabase.dbSearch();
        userDatabase.close();
        return users;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_form,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_form_ok:

               /* if (ActivityCompat.checkSelfPermission(FirstPage.this, Manifest.permission.CALL_PHONE)!PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(FirstPage.this,new String[]{
                        Manifest.permission.CALL_PHONE},123);
            }
            else{
*/

                Intent itemCall = new Intent(Intent.ACTION_VIEW);
                itemCall.setData(Uri.parse("tel:" + 123456789));
                startActivity(itemCall);
            }
  //      }

        return super.onOptionsItemSelected(item);
    }
    private void startAnimation() {
        ViewPropertyAnimator viewPropertyAnimator = logo.animate();
        viewPropertyAnimator.x(300f);
       // viewPropertyAnimator.y(200f);
        viewPropertyAnimator.setDuration(750);
        viewPropertyAnimator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                cs.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

}
