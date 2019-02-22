package com.android.tonyvu.sc.demo.constant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.android.tonyvu.sc.demo.model.Product;

public final class Constant {
    public static final List<Integer> QUANTITY_LIST = new ArrayList<Integer>();

    static {
        for (int i = 1; i < 11; i++) QUANTITY_LIST.add(i);
    }

    public static final Product PRODUCT1 = new Product(1, "Dosa", BigDecimal.valueOf(10.50), "Stuffed South India Style Dosa", "dosa");
    public static final Product PRODUCT2 = new Product(2, "Samosa", BigDecimal.valueOf(1.25), "aloo samosa","samosa");
    public static final Product PRODUCT3 = new Product(3, "Aloo Paranthe", BigDecimal.valueOf(2.5), "Stuffed Large2 Size Aloo Parantha ", "parantha");
    public static final Product PRODUCT4 = new Product(4, "Dal Makhni", BigDecimal.valueOf(15.25), "Special Punjabi Dal Makhani ","dal");
    public static final Product PRODUCT5 = new Product(5, "Shahe Paneer", BigDecimal.valueOf(17.25), "Best Ever Shahe Paneer with Indian Spices ","paneer");


    public static final List<Product> PRODUCT_LIST = new ArrayList<Product>();

    static {
        PRODUCT_LIST.add(PRODUCT1);
        PRODUCT_LIST.add(PRODUCT2);
        PRODUCT_LIST.add(PRODUCT3);
        PRODUCT_LIST.add(PRODUCT4);
        PRODUCT_LIST.add(PRODUCT5);
    }

    public static final String CURRENCY = "$";
}
