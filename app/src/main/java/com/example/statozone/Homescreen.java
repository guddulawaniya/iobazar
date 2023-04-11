package com.example.statozone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Homescreen extends AppCompatActivity {
private SharedPreferences myPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        String StoredValue=myPrefs.getString("user_id", "");
        String location_id =myPrefs.getString("location_id", "");
        String address =myPrefs.getString("address", "");
      //  result.setText(StoredValue);
        if(StoredValue.isEmpty())
        {
            Intent s = new Intent(Homescreen.this, Login.class);
            startActivity(s);
        }
        else
        {
            if(location_id.isEmpty())
            {
                Intent s = new Intent(Homescreen.this, Homepage.class);
                startActivity(s);
            }
            else
            {
                   if(address.isEmpty())
                   {
                       Intent s = new Intent(Homescreen.this, Addressmap.class);
                       startActivity(s);
                   }
                   else{
                       Intent s = new Intent(Homescreen.this, Homepage.class);
                       startActivity(s);
                   }
            }

        }
        isInternetOn();
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

            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            Intent intent = new Intent(Homescreen.this,IntenrNetconnection.class);
            startActivity(intent);

            Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }
}