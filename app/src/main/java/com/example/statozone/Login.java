package com.example.statozone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class Login extends AppCompatActivity {
    private static final String loginUrl= Config.Base_url+"login.php";
     private EditText Nameedit, Password;
     private TextView forgetpassword, registration;
     private Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        registration = (TextView) findViewById(R.id.registration);
         button1 = (Button) findViewById(R.id.login);
        Nameedit = (EditText) findViewById(R.id.username);
        Password = (EditText) findViewById(R.id.password);
         forgetpassword = (TextView) findViewById(R.id.forget);
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Login.this, Registration.class);
                startActivity(i);
            }
        });
        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Login.this,ForgetPassword.class);
                startActivity(intent);

            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Name = Nameedit.getText().toString();
                final String password = Password.getText().toString();


                if (Name.length() == 0) {
                    Nameedit.requestFocus();
                    Nameedit.setError("FIELD CANNOT BE EMPTY");
                } else if (password.length() == 0) {
                    Password.requestFocus();
                    Password.setError("FIELD CANNOT BE EMPTY");
                }
                else
                {
                    dltcrt(Name,password);
                    //Toast.makeText(Login.this,"Success Full",Toast.LENGTH_LONG).show();
                }

            }

        });
        isInternetOn();
    }
    public void dltcrt(String name, String password)
    {
        String email = name;
        String passwordget  = password;

        String addcrturl = "?email="+email+"&passwordget="+passwordget;
        class dbclass extends AsyncTask<String ,Void, String>
        {
            protected void onPostExecute(String data)
            {
                try {
                    JSONObject jObj = new JSONObject(data);
                    int message = jObj.getInt("message");
                    String id = jObj.getString("id");
                     int OtpVerify = jObj.getInt("VerifyOtp");
                    String Users_name = jObj.getString("name");
                    String Users_phone = jObj.getString("phone");
                    String Users_email = jObj.getString("email");



                    if(OtpVerify == 1)
                    {
                        SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor prefsEditor;
                        prefsEditor = myPrefs.edit();
                        prefsEditor.putString("user_id", id);
                        prefsEditor.putString("user_Name",Users_name);
                        prefsEditor.putString("user_Phone",Users_phone);
                        prefsEditor.putString("user_Email",Users_email);
                        prefsEditor.commit();
                        Intent s = new Intent(Login.this, Homescreen.class);
                        startActivity(s);
                    }
                    else
                    {
                        Intent s = new Intent(Login.this, Otpverification.class);
                        s.putExtra("Email",Users_email);
                        s.putExtra("Phone",Users_phone);
                        s.putExtra("OtpNotVerify",1);
                        startActivity(s);
                    }

                }
                catch (Exception ex)
                {
                    Toast.makeText(Login.this, "Invalid id and password1", Toast.LENGTH_LONG).show();
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
        obj.execute(loginUrl+addcrturl);
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


           // Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();

            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            Intent intent = new Intent(Login.this,IntenrNetconnection.class);
            startActivity(intent);

            Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }
}




