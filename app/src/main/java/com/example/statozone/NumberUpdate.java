package com.example.statozone;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NumberUpdate extends AppCompatActivity {
    private static final String UpdateNumberUrl = Config.Base_url+"UpdateNumber.php";
    private EditText number;
    private String UpdateNumber,Phone,Email;
    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_update);

        submit = findViewById(R.id.submit);
        number = findViewById(R.id.number);

        Intent intent = getIntent();
         Phone = intent.getStringExtra("Number");
         Email = intent.getStringExtra("Email");

        number.setText(Phone);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateNumber = number.getText().toString();
                OtpUpdate(UpdateNumber,Email);
            }
        });

    }
    public void OtpUpdate( String Otp,String emailUsers)
    {
        String OtpCode = Otp;
        String Email = emailUsers;
        String addcrturl = "?Number="+OtpCode+"&email="+Email;
        class dbclass extends AsyncTask<String ,Void, String>
        {
            protected void onPostExecute(String data)
            {
                try {
                    JSONObject jObj = new JSONObject(data);
                    int message = jObj.getInt("message");


                    if(message == 0) {

                        Toast.makeText(NumberUpdate.this, "Number already registered please use different Number", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Intent s = new Intent(NumberUpdate.this, Otpverification.class);
                        s.putExtra("Email",Email);
                        s.putExtra("Phone",UpdateNumber);
                        s.putExtra("OtpNotVerify",1);
                        startActivity(s);
                    }

                }
                catch (Exception ex)
                {
                    //Toast.makeText(Otpverification.this,"Please check your mobile internet",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            protected String doInBackground(String... param) {
                try {
                    URL url =  new URL(param[0]);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    BufferedReader br  = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    return br.readLine();
                }catch (Exception ex)
                {
                    return ex.getMessage();
                }
            }
        }
        dbclass obj= new dbclass();
        obj.execute(UpdateNumberUrl+addcrturl);
    }

}