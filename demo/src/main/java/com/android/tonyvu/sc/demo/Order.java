package com.android.tonyvu.sc.demo;

import java.io.Serializable;

/**
 * Created by loven on 27-04-2017.
 */

public class Order implements Serializable {
    private String email;
    float price;
    public void setEmail(String email){
        this.email=email;
    }
    public void setPrice(float price){
        this.price=price;
    }
    public String getEmail()
    {
        return email;

    }
    public float getPrice(){
        return price;
    }

}
