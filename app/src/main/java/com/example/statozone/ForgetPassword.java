package com.example.statozone;

import android.content.Intent;
import android.net.ConnectivityManager;
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

public class ForgetPassword extends AppCompatActivity {

    private static final String ForgetPassword=  Config.Base_url+"PasswordRest.php";
    private EditText NamedIt;
    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        button1 = (Button) findViewById(R.id.Reset);
        NamedIt = (EditText) findViewById(R.id.email);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String EmailTO = NamedIt.getText().toString();
                if (EmailTO.length() == 0) {
                    NamedIt.requestFocus();
                    NamedIt.setError("FIELD CANNOT BE EMPTY");
                }
                else
                {
                    SMTP(EmailTO);
                }
            }
        });
        isInternetOn();
    }

    public void SMTP(String name)
    {
        String email = name;
        String addcrturl = "?email="+email;
        class dbclass extends AsyncTask<String ,Void, String>
        {
            protected void onPostExecute(String data)
            {
                try {
                    JSONObject jObj = new JSONObject(data);
                    int message = jObj.getInt("message");
                    String id = jObj.getString("id");
                    String email = jObj.getString("email");
                    String messageBody = "https://food.tbvcsoft.com/Password/reset_password/"+id;
                    String subject = "PasswordResetLink";
                    if(message ==1)
                    {

                        SendMail sm = new SendMail(ForgetPassword.this, email, subject, messageBody);
                        sm.execute();

                    }
                    else{
                        Toast.makeText(ForgetPassword.this, " No account exists with this Mail Id", Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(ForgetPassword.this, " No account exists with this Mail Id", Toast.LENGTH_LONG).show();
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
        obj.execute(ForgetPassword+addcrturl);
    }
    public final boolean isInternetOn() {
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            Intent intent = new Intent(ForgetPassword.this,IntenrNetconnection.class);
            startActivity(intent);

            Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }
}