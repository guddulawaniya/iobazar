package com.example.statozone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class Order_placed extends AppCompatActivity {
    private SharedPreferences myPrefs;
    private String order_code;
    private RelativeLayout footer;
    private TextView ordercode;
    private ImageView imgif, left ,home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);
        imgif = (ImageView) findViewById(R.id.imageGif);
        ordercode = (TextView)findViewById(R.id.ordercode);
        left = (ImageView)findViewById(R.id.lefttbn);
        home = (ImageView)findViewById(R.id.home);
        footer=(RelativeLayout)findViewById(R.id.footer);


        myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        order_code = myPrefs.getString("order_code", "");

        Glide.with(this).load(R.drawable.orderplaced).into(imgif);
        ordercode.setText(order_code);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Order_placed.this,Homepage.class);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Order_placed.this,Homepage.class);
                startActivity(intent);
            }
        });
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Order_placed.this,Homepage.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Order_placed.this,Homepage.class);
        startActivity(intent);
    }

}