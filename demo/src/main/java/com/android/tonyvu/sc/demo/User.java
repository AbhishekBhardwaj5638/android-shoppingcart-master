package com.android.tonyvu.sc.demo;

import java.io.Serializable;

/**
 * Created by macbookpro on 2017-04-18.
 */

public class User implements Serializable {

    private String email,password;
    private String number;

    public void setEmail(String email){
        this.email=email;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public void setNumber(String number){
        this.number=number;
    }
    public String getEmail()
    {
        return email;

    }
    public String getPassword(){
        return password;
    }
    public String getNumber(){
        return this.number;
    }
}
