package com.example.statozone;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class Otpverification extends AppCompatActivity {
    private static final String CheckOtp="https://zone.tbvcsoft.com/app/Otpverification.php";
    private static final String OtpUpdateUrl="https://zone.tbvcsoft.com/app/UpdateOtp.php";
    private SharedPreferences myPrefs;
    private String phone;
    private String emailUsers,otpPassword;
    private LinearLayout OtpInvalid,root_otp_layout;
    private TextView PhoneOtp,ErrorOtp;
    private EditText otp_textbox_one, otp_textbox_two, otp_textbox_three, otp_textbox_four;
    private Button verify_otp;
    private String OTP1,OTP2,OTP3,OTP4;
    private NotificationManagerCompat notificationManagerCompat;
    private Notification notification;
    private TextView TimeButton,ResetLink,ChangeNumber;
    private int OtpNotVerify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);
        otp_textbox_one = findViewById(R.id.otptext1);
        otp_textbox_two = findViewById(R.id.otptext2);
        otp_textbox_three = findViewById(R.id.otptext3);
        otp_textbox_four = findViewById(R.id.otptext4);
        verify_otp = findViewById(R.id.verifyOtp);
        PhoneOtp = findViewById(R.id.PhoneOtp);
        TimeButton = findViewById(R.id.TimeButton);
        ResetLink = findViewById(R.id.ResetLink);
        OtpInvalid = findViewById(R.id.OtpInvalid);
        root_otp_layout = findViewById(R.id.root_otp_layout);
        ErrorOtp = findViewById(R.id.ErrorOtp);
        ChangeNumber = findViewById(R.id.ChangeNumber);
        // get intent value
        Intent intent = getIntent();
        emailUsers = intent.getStringExtra("Email");
        phone = intent.getStringExtra("Phone");
        otpPassword = intent.getStringExtra("Otp");
        OtpNotVerify = intent.getIntExtra("OtpNotVerify",0);
         CountClass();
        PhoneOtp.setText("We've sent an OTP on +91-"+phone);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("mych","My Channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"mych")
                .setSmallIcon(android.R.drawable.stat_notify_sync)
                .setContentTitle("Otp Verify")
                .setContentText(""+otpPassword);
        notification = builder.build();
        notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1,notification);

        ChangeNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Otpverification.this,NumberUpdate.class);
                intent.putExtra("Number",phone);
                intent.putExtra("Email",emailUsers);
                startActivity(intent);
            }
        });
        //not verify otp
        if(OtpNotVerify == 1)
        {
            CountClass();
            otpPassword = GetPassword(4);
            OtpUpdate(otpPassword,emailUsers);
            //  Toast.makeText(Otpverification.this, ""+GetPassword(5), Toast.LENGTH_SHORT).show();
            String emailids = emailUsers;
            String messageBody = "Dear Customer "+otpPassword +" is your one time password(OTP).Please enter the OTPto proceed. Thank you, Team TBVCSOFT";
            String subject = "REGISTRATION(OTP)";
            SendMail sm = new SendMail(Otpverification.this, emailids, subject, messageBody);
            sm.execute();

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                NotificationChannel channel = new NotificationChannel("mych","My Channel", NotificationManager.IMPORTANCE_DEFAULT);
                NotificationManager manager = getSystemService(NotificationManager.class);
                manager.createNotificationChannel(channel);
            }

            builder.setSmallIcon(android.R.drawable.stat_notify_sync)
                    .setContentTitle("Otp Verify")
                    .setContentText(""+otpPassword);
            notification = builder.build();
            notificationManagerCompat = NotificationManagerCompat.from(Otpverification.this);
            notificationManagerCompat.notify(1,notification);
        }
        //not verify otp


        otp_textbox_one.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

                OTP1 = otp_textbox_one.getText().toString();
                if (OTP1.length() == 0) {
                    otp_textbox_one.requestFocus();
                    otp_textbox_one.setError("FIELD CANNOT BE EMPTY");
                }
                if (OTP1.length() == 1) {
                    otp_textbox_two.requestFocus();
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
        otp_textbox_two.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                OTP2 = otp_textbox_two.getText().toString();
                if (OTP2.length() == 1) {
                    otp_textbox_three.requestFocus();
                }
                if (OTP2.length() == 0) {
                    otp_textbox_one.requestFocus();
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
        otp_textbox_three.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                OTP3 = otp_textbox_three.getText().toString();
                if (OTP3.length() == 1) {
                    otp_textbox_four.requestFocus();
                }
                if (OTP3.length() == 0) {
                    otp_textbox_two.requestFocus();
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
        otp_textbox_four.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                OTP4 = otp_textbox_four.getText().toString();
                if (OTP4.length() == 0) {
                    otp_textbox_three.requestFocus();
                }
                if (OTP4.length() == 1) {
                    String OTP = OTP1+OTP2+OTP3+OTP4;
                    dltcrt(OTP,emailUsers);
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
        verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String otp1 = otp_textbox_one.getText().toString();
                final String otp2 = otp_textbox_two.getText().toString();
                final String otp3 = otp_textbox_three.getText().toString();
                final String otp4 = otp_textbox_four.getText().toString();
                if (otp1.length() == 0) {
                    otp_textbox_one.requestFocus();
                    otp_textbox_one.setError("FIELD CANNOT BE EMPTY");
                } else if (otp2.length() == 0) {
                    otp_textbox_two.requestFocus();
                    otp_textbox_two.setError("FIELD CANNOT BE EMPTY");
                }
                else if (otp3.length() == 0) {
                    otp_textbox_three.requestFocus();
                    otp_textbox_three.setError("FIELD CANNOT BE EMPTY");
                }
                else if (otp4.length() == 0) {
                    otp_textbox_four.requestFocus();
                    otp_textbox_four.setError("FIELD CANNOT BE EMPTY");
                }
                String otp = otp1+otp2+otp3+otp4;
                //Toast.makeText(getApplicationContext(), otp, Toast.LENGTH_SHORT).show();
                dltcrt(otp,emailUsers);
            }
        });
        isInternetOn();
    }
    private void CountClass() {
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                TimeButton.setText("| 00:" + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }
            public void onFinish() {
                TimeButton.setText("| 00:00");
                ResetLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CountClass();
                        otpPassword = GetPassword(4);
                        OtpUpdate(otpPassword,emailUsers);
                        //  Toast.makeText(Otpverification.this, ""+GetPassword(5), Toast.LENGTH_SHORT).show();
                        String emailids = emailUsers;
                        String messageBody = "Dear Customer "+otpPassword +" is your one time password(OTP).Please enter the OTPto proceed. Thank you, Team TBVCSOFT";
                        String subject = "REGISTRATION(OTP)";
                        SendMail sm = new SendMail(Otpverification.this, emailids, subject, messageBody);
                        sm.execute();

                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                            NotificationChannel channel = new NotificationChannel("mych","My Channel", NotificationManager.IMPORTANCE_DEFAULT);
                            NotificationManager manager = getSystemService(NotificationManager.class);
                            manager.createNotificationChannel(channel);
                        }
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(Otpverification.this,"mych")
                                .setSmallIcon(android.R.drawable.stat_notify_sync)
                                .setContentTitle("Otp Verify")
                                .setContentText(""+otpPassword);
                        notification = builder.build();
                        notificationManagerCompat = NotificationManagerCompat.from(Otpverification.this);
                        notificationManagerCompat.notify(1,notification);
                    }
                });
            }
        }.start();
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
    public void dltcrt( String OTP1, String email)
    {
        String otps = OTP1;
        String userEmail  = email;
        String addcrturl = "?otp="+otps+"&email="+userEmail;
        class dbclass extends AsyncTask<String ,Void, String>
        {
            protected void onPostExecute(String data)
            {
                try {
                    JSONObject jObj = new JSONObject(data);
                    int message = jObj.getInt("message");
                    String id = jObj.getString("id");
                    String Users_name = jObj.getString("name");
                    String Users_phone = jObj.getString("phone");
                    String Users_email = jObj.getString("email");
                    if(message == 0) {
                        SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor prefsEditor;
                        prefsEditor = myPrefs.edit();
                        prefsEditor.putString("user_id", id);
                        prefsEditor.putString("user_Name",Users_name);
                        prefsEditor.putString("user_Phone",Users_phone);
                        prefsEditor.putString("user_Email",Users_email);
                        prefsEditor.commit();
                        Intent s = new Intent(Otpverification.this, Homescreen.class);
                        startActivity(s);
                    }
                    else
                    {
                        ErrorOtp.setVisibility(View.VISIBLE);
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(Otpverification.this,"Please check your mobile internet",Toast.LENGTH_LONG).show();
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
        obj.execute(CheckOtp+addcrturl);
    }
    public void OtpUpdate( String Otp,String emailUsers)
    {
        String OtpCode = Otp;
        String Email = emailUsers;
        String addcrturl = "?Otp="+OtpCode+"&email="+Email;
        class dbclass extends AsyncTask<String ,Void, String>
        {
            protected void onPostExecute(String data)
            {
                try {
                    JSONObject jObj = new JSONObject(data);

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
        obj.execute(OtpUpdateUrl+addcrturl);
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
        //    Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
            Intent intent = new Intent(Otpverification.this,IntenrNetconnection.class);
            startActivity(intent);
            Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }
}