package com.example.statozone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ManageAddress extends AppCompatActivity {

    private ImageView leftimage,home;
    private TextView addresstype,address;
    private RelativeLayout footer;
    private LinearLayout newaddress,saveaddress,BackButton;
    private SharedPreferences myPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_address);

        leftimage = (ImageView)findViewById(R.id.lefttbn);
        home = (ImageView) findViewById(R.id.home);
        address = (TextView) findViewById(R.id.addresplace);
        addresstype = (TextView) findViewById(R.id.home_type);
        newaddress = (LinearLayout) findViewById(R.id.newaddress);
        saveaddress = (LinearLayout)findViewById(R.id.saveaddress);
        BackButton = (LinearLayout) findViewById(R.id.BackButton);
        /// get local storage data
        myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        String addressplace = myPrefs.getString("address", "");
        String address_type = myPrefs.getString("type_address", "");
        // set value display
        address.setText(addressplace);
        addresstype.setText(address_type);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        saveaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPrefs = getSharedPreferences("myPrefs",MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor;
                prefsEditor=myPrefs.edit();
                prefsEditor.putString("select_place_address", "1");
                prefsEditor.putString("shippingaddress", addressplace);
                prefsEditor.commit();
                Intent intent = new Intent(ManageAddress.this,CartView.class);
                startActivity(intent);
            }
        });
        newaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ManageAddress.this, "Not Active", Toast.LENGTH_SHORT).show();
            }
        });
        leftimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageAddress.this,Homepage.class);
                startActivity(intent);
            }
        });
    }
}