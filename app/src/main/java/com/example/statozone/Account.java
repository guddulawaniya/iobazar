package com.example.statozone;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Account extends AppCompatActivity {
    private final static String productUrl = Config.Base_url+"SingleUser.php";
    private ImageView near, alert, explore, cart, account,imageGif;
    
    private TextView t1,t2,t3,t4,t5;
    private TextView phone,profileName, email_d;
    private LinearLayout Logout,wallet,Myorders,profilepage,ManageAddress;
    private  SharedPreferences myPrefs;
    //users details
    private static String Users_name[];
    private static String Users_ID[];
    private static String Users_email[];
    private static String Users_phone[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        near = (ImageView)findViewById(R.id.near);
        alert = (ImageView)findViewById(R.id.bell);
        explore = (ImageView)findViewById(R.id.explore);
        cart = (ImageView)findViewById(R.id.cart);
        imageGif = findViewById(R.id.imageGif);
        phone = (TextView)findViewById(R.id.phone);
        profileName = (TextView)findViewById(R.id.profileName);
        email_d = (TextView)findViewById(R.id.email);
        Logout = (LinearLayout)findViewById(R.id.LogOut);

        Myorders = (LinearLayout)findViewById(R.id.Myorders);
        profilepage = (LinearLayout) findViewById(R.id.Profile);
        ManageAddress = findViewById(R.id.ManageAddress);
        t1= (TextView)findViewById(R.id.t1);
        t2= (TextView)findViewById(R.id.t2);
        t3= (TextView)findViewById(R.id.t3);
        t4= (TextView)findViewById(R.id.t4);
        t5= (TextView)findViewById(R.id.t5);
        
        Glide.with(this).load(R.drawable.user9).into(imageGif);
        near.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account.this,Homepage.class);
                startActivity(intent);
            }
        });
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account.this,Homepage.class);
                startActivity(intent);
            }
        });

        ManageAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account.this,AddressBook.class);
                startActivity(intent);
            }
        });
        profilepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account.this,Profile.class);
                startActivity(intent);
            }
        });

        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account.this,categories.class);
                startActivity(intent);
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account.this,categories.class);
                startActivity(intent);
            }
        });

        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account.this,Explore.class);
                startActivity(intent);
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account.this,Explore.class);
                startActivity(intent);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account.this,CartView.class);
                startActivity(intent);
            }
        });
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account.this,CartView.class);
                startActivity(intent);
            }
        });
        Myorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account.this,My_order_list.class);
                startActivity(intent);
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = "+918077740008";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Account.this);
                String logName = myPrefs.getString("user_Name", "");;;
                builder.setTitle(logName).
                        setMessage("sure, You want to logout?");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences myPrefs = getSharedPreferences("myPrefs",
                                        MODE_PRIVATE);
                                SharedPreferences.Editor editor = myPrefs.edit();
                                editor.clear();
                                editor.commit();
                                Intent i = new Intent(getApplicationContext(),
                                        MainActivity.class);
                                startActivity(i);
                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder.create();
                alert11.show();
            }
        });


        myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        String users_id=myPrefs.getString("user_id", "");
        String userName=myPrefs.getString("user_Name", "");
        String userPhone=myPrefs.getString("user_Phone", "");
        String userEmail=myPrefs.getString("user_Email", "");
        profileName.setText(userName);
        email_d.setText(userEmail);
        phone.setText(userPhone);
        userdetails(users_id);
        isInternetOn();
    }
    private void userdetails(String users_id) {
        String detail_user = users_id;
        String storeIdUrl = "?users_Id="+detail_user;
        class dbManager extends AsyncTask<String, Void, String>
        {
            protected void onPostExecute(String data)
            {
                try {
                    JSONArray ja = new JSONArray(data);
                    JSONObject jo = null;
                    Users_name = new String[ja.length()];
                    Users_ID = new String[ja.length()];
                    Users_email = new String[ja.length()];
                    Users_phone = new String[ja.length()];
                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        Users_name[i] = jo.getString("name");
                        Users_ID[i] = jo.getString("id");
                        Users_email[i] = jo.getString("email");
                        Users_phone[i] = jo.getString("phone");
                        String logName = Users_name[i];
                        profileName.setText(Users_name[i]);
                        email_d.setText(Users_email[i]);
                        phone.setText(Users_phone[i]);


                        Logout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Account.this);
                                builder.setTitle(logName).
                                        setMessage("sure, You want to logout?");
                                builder.setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                SharedPreferences myPrefs = getSharedPreferences("myPrefs",
                                                        MODE_PRIVATE);
                                                SharedPreferences.Editor editor = myPrefs.edit();
                                                editor.clear();
                                                editor.commit();
                                                Intent i = new Intent(getApplicationContext(),
                                                        MainActivity.class);
                                                startActivity(i);
                                            }
                                        });
                                builder.setNegativeButton("No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alert11 = builder.create();
                                alert11.show();
                            }
                        });

                        SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor prefsEditor;
                        prefsEditor = myPrefs.edit();
                        prefsEditor.putString("user_Name",Users_name[i]);
                        prefsEditor.putString("user_Phone",Users_phone[i]);
                        prefsEditor.putString("user_Email",Users_email[i]);
                        prefsEditor.commit();

                    }
                }
                catch (Exception ex)
                {

                  //  Toast.makeText(Account.this,"a",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            protected String doInBackground(String... strings) {
                try {
                    URL url= new URL(strings[0]);
                    HttpURLConnection conn =(HttpURLConnection)url.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer data = new StringBuffer();
                    String line;
                    while((line=  br.readLine())!= null)
                    {
                        data.append(line+"\n");
                    }
                    br.close();
                    return data.toString();
                }
                catch (Exception ex)
                {
                    return ex.getMessage();
                }
            }
        }
        dbManager obj =new dbManager();
        obj.execute(productUrl+storeIdUrl);
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

            Intent intent = new Intent(Account.this,IntenrNetconnection.class);
            startActivity(intent);

            Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }
}