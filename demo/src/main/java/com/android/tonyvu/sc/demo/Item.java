package com.android.tonyvu.sc.demo;

import java.io.Serializable;

/**
 * Created by loven on 27-04-2017.
 */

public class Item implements Serializable {
    private String product;
    private int imageId;
    private float price;
    private int quantity;
    private boolean isSelected;

    public Item(String product, int imageId, float prices, boolean isSelected, int quantity){
        this.product=product;
        this.imageId=imageId;
        this.price=prices;
        this.quantity=quantity;
        this.isSelected=isSelected;
    }
    public int getQuantity(){
        return quantity;
    }
    public void setQuantity(int q){
        this.quantity=q;
    }
    public String getProduct(){
        return this.product;
    }
    public int getImageID(){
        return this.imageId;
    }
    public float getPrice(){
        return this.price;
    }
    public boolean isSelected(){
        return isSelected;
    }
    public void setProduuct(String product){
        this.product=product;
    }
    public void setImageId(int id){
        this.imageId=id;
    }
    public void setPrice(float price){
        this.price=price;
    }
    public void setSelected(boolean b){
        isSelected=b;
    }


}
