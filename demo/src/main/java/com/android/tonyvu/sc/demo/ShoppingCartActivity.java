package com.android.tonyvu.sc.demo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tonyvu.sc.demo.adapter.CartItemAdapter;
import com.android.tonyvu.sc.demo.constant.Constant;
import com.android.tonyvu.sc.demo.model.CartItem;
import com.android.tonyvu.sc.demo.model.Product;
import com.android.tonyvu.sc.model.Cart;
import com.android.tonyvu.sc.model.Saleable;
import com.android.tonyvu.sc.util.CartHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ShoppingCartActivity extends AppCompatActivity {
    private static final String TAG = "ShoppingCartActivity";

    ListView lvCartItems;
    Button bClear;
    Button bShop;
    Button checkOut;
    Cart cart;
    TextView tvTotalPrice;
    BigDecimal totalFinal;
    int count=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        String gmail=getIntent().getStringExtra("email");

        checkOut=(Button)findViewById(R.id.bCheckOut);

        lvCartItems = (ListView) findViewById(R.id.lvCartItems);
        LayoutInflater layoutInflater = getLayoutInflater();

        final TextView tvTotalPrice = (TextView) findViewById(R.id.tvTotalPrice);


        //Product Database
        productDatabase pd=new productDatabase(this);
        List<Order> allOrders=pd.dbSearch();

        for (int i=0;i<allOrders.size();i++){
            Log.d("Reading Database",allOrders.get(i).getEmail());
            if(allOrders.get(i).getEmail().equals(gmail)){
                count++;
            }
        }

         cart = CartHelper.getCart();
        final BigDecimal total=cart.getTotalPrice().setScale(2, BigDecimal.ROUND_HALF_UP);


        if(count>0){
            Log.d("Value of count",Integer.toString(count));

            float f=((float)count)/100;

            BigDecimal tp=(total).multiply(new BigDecimal(f));

            BigDecimal ttp=total.subtract(tp);
            totalFinal=ttp;
            totalFinal =totalFinal.setScale(2, RoundingMode.CEILING);
            tvTotalPrice.setText(Constant.CURRENCY+String.valueOf(totalFinal));

            Log.d("totalFinal Cost",totalFinal.toString());
        }
        else {
            Log.d("Value of count",Integer.toString(count));

            tvTotalPrice.setText(Constant.CURRENCY + String.valueOf(total));
            totalFinal=total;
        }
        lvCartItems.addHeaderView(layoutInflater.inflate(R.layout.cart_header, lvCartItems, false));

        final CartItemAdapter cartItemAdapter = new CartItemAdapter(this);
        cartItemAdapter.updateCartItems(getCartItems(cart));

        lvCartItems.setAdapter(cartItemAdapter);

        bClear = (Button) findViewById(R.id.bClear);
        bShop = (Button) findViewById(R.id.bShop);

        bClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clearing the shopping cart");
                cart.clear();
                cartItemAdapter.updateCartItems(getCartItems(cart));
                cartItemAdapter.notifyDataSetChanged();
                tvTotalPrice.setText(Constant.CURRENCY+String.valueOf(cart.getTotalPrice().setScale(2, BigDecimal.ROUND_HALF_UP)));
            }
        });

        bShop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingCartActivity.this, MainActivity.class);
                intent.putExtra("email",getIntent().getStringExtra("email"));
                intent.putExtra("guestMail",getIntent().getStringExtra("guestMail"));
                startActivity(intent);
            }
        });

       /* lvCartItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(ShoppingCartActivity.this)
                        .setTitle(getResources().getString(R.string.delete_item))
                        .setMessage(getResources().getString(R.string.delete_item_message))
                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                List<CartItem> cartItems = getCartItems(cart);
                                cart.remove(cartItems.get(position-1).getProduct());
                                cartItems.remove(position-1);
                                cartItemAdapter.updateCartItems(cartItems);
                                cartItemAdapter.notifyDataSetChanged();
                                tvTotalPrice.setText(Constant.CURRENCY+String.valueOf(cart.getTotalPrice().setScale(2, BigDecimal.ROUND_HALF_UP)));
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.no), null)
                        .show();
                return false;
            }
        });*/

        lvCartItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                List<CartItem> cartItems = getCartItems(cart);
                Product product = cartItems.get(position-1).getProduct();
                Log.d(TAG, "Viewing product: " + product.getName());
                bundle.putSerializable("product", product);
                Intent intent = new Intent(ShoppingCartActivity.this, ProductActivity.class);
                intent.putExtras(bundle);
                intent.putExtra("email",getIntent().getStringExtra("email"));
                intent.putExtra("guestMail",getIntent().getStringExtra("guestMail"));
                startActivity(intent);
            }
        });

        checkOut.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
/*
                productDatabase pd=new productDatabase(ShoppingCartActivity.this);
                Order o=new Order();
                o.setEmail(getIntent().getStringExtra("email"));
                Log.d("totalFinal",totalFinal.toString());
                o.setPrice(totalFinal.floatValue());
                Toast.makeText(ShoppingCartActivity.this, "Price is "+Float.toString(totalFinal.floatValue()), Toast.LENGTH_SHORT).show();
                pd.dbInsert(o);
                pd.close();
*/

                if (lvCartItems.getAdapter().getCount()>1) {
                    Intent goForStore = new Intent(ShoppingCartActivity.this, MapsActivity.class);
                    goForStore.putExtra("email", getIntent().getStringExtra("email"));
                    goForStore.putExtra("totalprice", totalFinal.toString());
                    goForStore.putExtra("guestMail", getIntent().getStringExtra("guestMail"));

                    startActivity(goForStore);
                    cart.clear();
                    cartItemAdapter.updateCartItems(getCartItems(cart));
                    cartItemAdapter.notifyDataSetChanged();
                    tvTotalPrice.setText(Constant.CURRENCY + String.valueOf(cart.getTotalPrice().setScale(2, BigDecimal.ROUND_HALF_UP)));
                    finish();

                }else{
                    showError("checkout");
                }
            }
        });
    }

    private void showError(String field) {
        Animation shake = AnimationUtils.loadAnimation(this,R.anim.shake);

        switch (field){
            case "checkout":
                checkOut.startAnimation(shake);
                checkOut.setError("Cart is Empty");
            }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cart.clear();
        finish();

    }

    private List<CartItem> getCartItems(Cart cart) {
        List<CartItem> cartItems = new ArrayList<CartItem>();
        Log.d(TAG, "Current shopping cart: " + cart);

        Map<Saleable, Integer> itemMap = cart.getItemWithQuantity();

        for (Entry<Saleable, Integer> entry : itemMap.entrySet()) {
            CartItem cartItem = new CartItem();
            cartItem.setProduct((Product) entry.getKey());
            cartItem.setQuantity(entry.getValue());
            cartItems.add(cartItem);
        }

        Log.d(TAG, "Cart item list: " + cartItems);
        return cartItems;
    }
}
