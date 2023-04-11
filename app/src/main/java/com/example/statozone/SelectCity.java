package com.example.statozone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SelectCity extends AppCompatActivity {
    private final static String URL = "https://food.tbvcsoft.com/app/Locationget.php";
    private   GridView lgrd;
    private  LinearLayout mapButton;
    private static String name[];
    private static String ids[];

    private ArrayList<String>holder = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
         mapButton = (LinearLayout) findViewById(R.id.mapButton);
        lgrd = (GridView)findViewById(R.id.idGVcourses) ;
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SelectCity.this, "Not Active Link", Toast.LENGTH_SHORT).show();
            }
        });

        lgrd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = lgrd.getItemAtPosition(position).toString();

                SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor;
                prefsEditor = myPrefs.edit();
                prefsEditor.putString("location_id",ids[position]);

                prefsEditor.commit();

                Intent intent = new Intent(SelectCity.this,Addressmap.class);
                startActivity(intent);
              //  Toast.makeText(SelectCity.this,ids[position],Toast.LENGTH_LONG).show();


            }
        });
        new dbclass().execute(URL);
        isInternetOn();
    }
    class dbclass extends AsyncTask<String ,Void, String>
    {
        protected void onPostExecute(String data)
        {
            try {
                JSONArray ja = new JSONArray(data);
                JSONObject jo = null;
                name = new String[ja.length()];
                ids = new String[ja.length()];
                for (int i = 0; i < ja.length(); i++) {
                    jo = ja.getJSONObject(i);
                    name[i] = jo.getString("city_name");
                    ids[i] = jo.getString("id");

                }
                myadapter adptr = new myadapter(getApplicationContext(), name);
                lgrd.setAdapter(adptr);
            }
            catch (Exception ex)
            {
                Toast.makeText(SelectCity.this,"Please check your mobile internet",Toast.LENGTH_LONG).show();
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
    class myadapter extends ArrayAdapter<String>
    {
        Context context;
        String ttl[];


        myadapter(Context c, String ttl[])
        {
            super(c,R.layout.activity_select_city,R.id.idTVCourse,ttl);
            context=c;
            this.ttl=ttl;
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
        {
            LayoutInflater inflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=inflater.inflate(R.layout.list_city,parent,false);


            TextView tv1=row.findViewById(R.id.idTVCourse);

            tv1.setText(ttl[position]);

            return row;
        }
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

            Intent intent = new Intent(SelectCity.this,IntenrNetconnection.class);
            startActivity(intent);

            Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }

}