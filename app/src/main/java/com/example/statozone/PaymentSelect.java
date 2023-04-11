package com.example.statozone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentSelect extends AppCompatActivity {
    private LinearLayout paytmupi,phonepay,creditcards,sodexo,gpayupi,bhimupi,payupi,paytmwallet,mobikwik,freecharge,netbanking,LazyPay,cashondelivery;
    private SharedPreferences myPrefs;
    private TextView billPayment;
    private LinearLayout BackButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_select);
        //getpayment option
        paytmupi = (LinearLayout) findViewById(R.id.paytmupi);
        phonepay = (LinearLayout) findViewById(R.id.phonepay);


        LazyPay = (LinearLayout) findViewById(R.id.LazyPay);
        cashondelivery = (LinearLayout) findViewById(R.id.cashondelivery);
        billPayment = (TextView) findViewById(R.id.billPayment);
        BackButton = (LinearLayout) findViewById(R.id.BackButton);
        //get put extra value
        Intent intent = getIntent();
        int amountTotal = intent.getIntExtra("amountPay",1);

        billPayment.setText("₹ "+amountTotal);

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        LazyPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPrefs = getSharedPreferences("myPrefs",MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor;
                prefsEditor=myPrefs.edit();
                prefsEditor.putInt("Payment_type", 12);
                prefsEditor.commit();
                Intent intent = new Intent(PaymentSelect.this,CartView.class);
                startActivity(intent);
            }
        });
        cashondelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPrefs = getSharedPreferences("myPrefs",MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor;
                prefsEditor=myPrefs.edit();
                prefsEditor.putInt("Payment_type", 13);
                prefsEditor.commit();
                Intent intent = new Intent(PaymentSelect.this,CartView.class);
                startActivity(intent);
            }
        });



    }
}