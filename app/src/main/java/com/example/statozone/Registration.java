package com.example.statozone;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class Registration extends AppCompatActivity {
    private static final String loginurl="https://zone.tbvcsoft.com/app/registration.php";
    private EditText Nameedit, Emailedit, Phoneedit, Passwordedit, Repasswordedit;
    private TextView loginbtn;
    private String OtpVerify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Button registration =  findViewById(R.id.registration);

        Nameedit =  findViewById(R.id.username);
        Emailedit =  findViewById(R.id.email);
        Phoneedit =  findViewById(R.id.number);
        Passwordedit =  findViewById(R.id.password);
        Repasswordedit =  findViewById(R.id.repassword);
        loginbtn =  findViewById(R.id.login);

        OtpVerify  =    GetPassword(4);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Registration.this, Login.class);
                startActivity(i);
            }
        });

        Phoneedit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
             String   OTP1 = Phoneedit.getText().toString();
                if (OTP1.length() == 10) {
                    Passwordedit.requestFocus();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Name = Nameedit.getText().toString();
                final String emailid = Emailedit.getText().toString();
                final String Phone = Phoneedit.getText().toString();
                final String Password = Passwordedit.getText().toString();
                final String Repassword = Repasswordedit.getText().toString();

                if (Name.length() == 0) {
                    Nameedit.requestFocus();
                    Nameedit.setError("FIELD CANNOT BE EMPTY");
                } else if (!Name.matches("[a-zA-Z ]+")) {
                    Nameedit.requestFocus();
                    Nameedit.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                } else if (emailid.length() == 0) {
                    Emailedit.requestFocus();
                    Emailedit.setError("FIELD CANNOT BE EMPTY");
                }
                else if (Phone.length() == 0) {
                    Phoneedit.requestFocus();
                    Phoneedit.setError("FIELD CANNOT BE EMPTY");
                }
                else if (!Phone.matches("[0-9]{10}")) {
                    Phoneedit.requestFocus();
                    Phoneedit.setError("ENTER ONLY 10 Number");
                }
                else if (Password.length() == 0) {
                    Passwordedit.requestFocus();
                    Passwordedit.setError("FIELD CANNOT BE EMPTY");
                }
                else if (Repassword.length() == 0) {
                    Passwordedit.requestFocus();
                    Passwordedit.setError("FIELD CANNOT BE EMPTY");
                }
                else if (!Repassword.equals(Password)) {
                    Passwordedit.requestFocus();
                    Toast.makeText(Registration.this,"Password Is Not Match ",Toast.LENGTH_LONG).show();
                }
                else
                {
                    dltcrt(Name,Password,emailid,Phone,OtpVerify);
                   // Toast.makeText(Registration.this,Repassword,Toast.LENGTH_LONG).show();
                }

            }

            });
        isInternetOn();
    }
    public void dltcrt( String name, String password,String emailid, String phone,String Otp)
    {
        String names = name;
        String passwordget= password;
        String emailids = emailid;
        String phones = phone;
        String OtpCode = Otp;

        String addcrturl = "?name="+names+"&passwordget="+passwordget+"&email="+emailids+"&phone="+phones+"&Otp="+OtpCode;
        class dbclass extends AsyncTask<String ,Void, String>
        {
            protected void onPostExecute(String data)
            {
                try {
                    JSONObject jObj = new JSONObject(data);
                    int message = jObj.getInt("message");
                    String id = jObj.getString("id");

                    if(message == 0) {

                        Toast.makeText(Registration.this, "Email already registered please use different Email", Toast.LENGTH_LONG).show();
                    }
                    else if(message == 1)
                    {

                        Toast.makeText(Registration.this, "Number already registered please use different Number", Toast.LENGTH_LONG).show();
                    }
                    else if(message == 2)
                    {
                        String messageBody = "Dear Customer "+OtpVerify+" is your one time password(OTP).Please enter the OTPto proceed. Thank you, Team TBVCSOFT";
                        String subject = "REGISTRATION(OTP)";
                        SendMail sm = new SendMail(Registration.this, emailids, subject, messageBody);
                        sm.execute();
                        Intent s = new Intent(Registration.this, Otpverification.class);
                        s.putExtra("Phone", phones);
                        s.putExtra("Email",emailids);
                        s.putExtra("Otp",OtpVerify);
                        startActivity(s);

                        Toast.makeText(Registration.this, "Not Verified", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        String messageBody = "Dear Customer "+OtpVerify+" is your one time password(OTP).Please enter the OTPto proceed. Thank you, Team TBVCSOFT";
                        String subject = "REGISTRATION(OTP)";
                        SendMail sm = new SendMail(Registration.this, emailids, subject, messageBody);
                        sm.execute();
                        Intent s = new Intent(Registration.this, Otpverification.class);
                        s.putExtra("Phone", phones);
                        s.putExtra("Email",emailids);
                        s.putExtra("Otp",OtpVerify);
                        startActivity(s);
                        Toast.makeText(Registration.this, "Complete insert", Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(Registration.this,"Please check your mobile internet 1",Toast.LENGTH_LONG).show();
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
        obj.execute(loginurl+addcrturl);
    }
    public final boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

            // if connected with internet
         //   Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();

            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            Intent intent = new Intent(Registration.this,IntenrNetconnection.class);
            startActivity(intent);

            Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }
    public String GetPassword(int length){
        char[] chars = "0123456789".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();

        Random rand = new Random();

        for(int i = 0; i < length; i++){
            char c = chars[rand.nextInt(chars.length)];
            stringBuilder.append(c);





        }

        return stringBuilder.toString();
    }
}


